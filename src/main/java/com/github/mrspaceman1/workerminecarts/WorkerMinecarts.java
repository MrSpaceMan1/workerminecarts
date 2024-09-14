package com.github.mrspaceman1.workerminecarts;

import com.github.mrspaceman1.workerminecarts.facade.WorkerMinecartEntity;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class WorkerMinecarts implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger("workerminecarts");
	public static final Map<UUID, WorkerMinecartEntity> workerMinecarts = new HashMap<>();
	@Override
	public void onInitialize() {
		WorkerMinecartInventoryDataManager.initialize();
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Hello Fabric world!");
	}
}