package com.github.mrspacema1.workerminecarts.utils;

import net.minecraft.core.NonNullList;
import net.minecraft.nbt.*;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;

import java.io.*;

import static com.github.mrspacema1.workerminecarts.WorkerMinecarts.LOGGER;

public class SimpleContainerSerialization {
    public static ByteArrayOutputStream containerToStream(SimpleContainer container, Entity ownerOfContainer) throws IOException {
        CompoundTag itemsSerialized = new CompoundTag();
        ContainerHelper.saveAllItems(itemsSerialized, container.items, ownerOfContainer.registryAccess());
        var byteArrayOutputStream = new ByteArrayOutputStream();
        var dataStreamOutput = new DataOutputStream(byteArrayOutputStream);
        itemsSerialized.write(dataStreamOutput);
        return byteArrayOutputStream;
    }

    public static void streamToContainer(ByteArrayOutputStream serializedContainer, NonNullList<ItemStack> containerItems, Entity ownerOfContainer) throws IOException {
        for (int i = 0; i < containerItems.size(); i++) { containerItems.add(i, ItemStack.EMPTY); }
        CompoundTag deserializedNbt = CompoundTag.TYPE.load(
                new DataInputStream(new ByteArrayInputStream(serializedContainer.toByteArray())),
                NbtAccounter.unlimitedHeap()
        );
        ContainerHelper.loadAllItems(deserializedNbt, containerItems, ownerOfContainer.registryAccess());
    }
}
