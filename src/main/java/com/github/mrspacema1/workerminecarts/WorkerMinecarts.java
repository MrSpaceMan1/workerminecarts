package com.github.mrspacema1.workerminecarts;

import net.fabricmc.api.ModInitializer;

import net.minecraft.world.Container;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class WorkerMinecarts implements ModInitializer {
	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
    public static final Logger LOGGER = LoggerFactory.getLogger("workerminecarts");
	public static final Map<Integer, String> WorkerMinecartSerialized = new HashMap<>();
	public static final Map<Integer, Container> WorkerMinecarts = new HashMap<>();
	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("Hello Fabric world!");
	}
}