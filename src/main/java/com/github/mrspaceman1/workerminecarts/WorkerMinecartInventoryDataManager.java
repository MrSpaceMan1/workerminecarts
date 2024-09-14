package com.github.mrspaceman1.workerminecarts;

import com.github.mrspaceman1.workerminecarts.utils.DebouncedRunnable;
import com.github.mrspaceman1.workerminecarts.facade.WorkerMinecartEntity;
import com.github.mrspaceman1.workerminecarts.utils.SimpleContainerSerialization;
import io.netty.channel.FileRegion;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;
import java.util.stream.Stream;

import static com.github.mrspaceman1.workerminecarts.WorkerMinecarts.LOGGER;
import static com.github.mrspaceman1.workerminecarts.WorkerMinecarts.workerMinecarts;

public class WorkerMinecartInventoryDataManager {
    private static final HashMap<UUID, DebouncedRunnable> requestedSaves = new HashMap<>();
    private static final Path configPath = Paths.get("")
            .toAbsolutePath()
            .resolve("config")
            .resolve("workerminecarts");

    public static void initialize(){
        try {
            Files.createDirectories(configPath);
            Files.createDirectories(configPath.resolve("workers"));
        } catch (IOException e) {
            throw new RuntimeException("Config directories couldn't be created");
        }

        var workerFiles = configPath.resolve("workers").toFile().listFiles();
        if(workerFiles != null){
            Stream.of(workerFiles)
                    .filter(file -> file.getName().split("-", 2)[0].equals("worker"))
                    .forEach(file -> {
                        UUID workerUuid;
                        try {
                            workerUuid = UUID.fromString(file.getName().split("-", 2)[1]);
                        } catch (IllegalArgumentException e){
                            LOGGER.error("Malformed worker file name. Skipping.");
                            return;
                        }
                        try{
                            var content = Files.readString(file.toPath(), StandardCharsets.UTF_8);
                            var byteArrayStream = new ByteArrayOutputStream();
                            byteArrayStream.writeBytes(StandardCharsets.UTF_8.encode(content).array());
                            new WorkerMinecartEntity(workerUuid, byteArrayStream);
                        } catch (IOException e){
                            LOGGER.error("Failed to read worker file. Skipping.");
                            return;
                        }
                    });
        }
    }

    public static void saveInventory(WorkerMinecartEntity workerMinecart){
        var workerInventoryFile = WorkerMinecartInventoryDataManager.configPath
                .resolve("workers")
                .resolve("worker-%s".formatted(workerMinecart.getUUID().toString()))
                .toFile();

        requestedSaves.computeIfAbsent(workerMinecart.getUUID(), x -> new DebouncedRunnable(() -> {
            try (var writer = new FileWriter(workerInventoryFile)) {
                var containerSerialized = SimpleContainerSerialization.containerToStream(workerMinecart.inventory, workerMinecart.entity);
                var charset = StandardCharsets.UTF_8;
                writer.write(charset.decode(ByteBuffer.wrap(containerSerialized.toByteArray())).array());
            } catch (IOException e) {
                LOGGER.error("Worker minecart with UUID: {}, failed to save inventory changes", workerMinecart.getUUID().toString());
            }
        }, "worker-%s".formatted(workerMinecart.getUUID()), 5000));
        for (var runnable : requestedSaves.values()) runnable.run();
    }
}
