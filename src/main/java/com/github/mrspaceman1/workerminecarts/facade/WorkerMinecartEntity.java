package com.github.mrspaceman1.workerminecarts.facade;

import com.github.mrspaceman1.workerminecarts.WorkerMinecartInventoryDataManager;
import com.github.mrspaceman1.workerminecarts.WorkerMinecarts;
import com.github.mrspaceman1.workerminecarts.utils.SimpleContainerSerialization;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.MinecartSpawner;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.BaseRailBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.RailShape;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

import static com.github.mrspaceman1.workerminecarts.WorkerMinecarts.LOGGER;

public class WorkerMinecartEntity {
    private static final int INVENTORY_SIZE = 27;
    public MinecartSpawner entity = null;
    public SimpleContainer inventory = null;
    private MenuProvider menuProvider = null;
    public boolean fullyInitialized = false;
    private ByteArrayOutputStream loadedItems = null;

    public WorkerMinecartEntity(MinecartSpawner entity) {
        this.entity = entity;
        this.inventory = new SimpleContainer(WorkerMinecartEntity.INVENTORY_SIZE);
        inventory.addListener(this::inventoryChanged);
        this.menuProvider = new SimpleMenuProvider((i, playerInventory, player) ->
                ChestMenu.threeRows(i, playerInventory, inventory)
                , Component.literal("Worker Minecart"));
        WorkerMinecarts.workerMinecarts.put(entity.getUUID(), this);
        fullyInitialized = true;
    }

    public WorkerMinecartEntity(UUID uuid, ByteArrayOutputStream inventoryByteArray) {
        loadedItems = inventoryByteArray;
        WorkerMinecarts.workerMinecarts.put(uuid, this);
    }

    public void initalizeLoadedWorkerMinecart(MinecartSpawner entity){
        if( fullyInitialized) {
            LOGGER.info("Tried to initialize worker minecart that is already initialized.");
        }
        this.entity = entity;
        if(loadedItems == null) throw new RuntimeException("Tried to initialize worker minecart, but no items were loaded.");
        try {
            this.inventory = new SimpleContainer(WorkerMinecartEntity.INVENTORY_SIZE);
            SimpleContainerSerialization.streamToContainer(loadedItems, inventory.items, entity);
            this.inventory.addListener(this::inventoryChanged);
        } catch (IOException e){
            throw new RuntimeException("Loaded items stream was empty");
        }
        fullyInitialized = true;
    }

    public UUID getUUID() {
        if (!fullyInitialized) throw new RuntimeException("Tried to use uninitialized worker minecart.");
        return entity.getUUID();
    }

    public void inventoryChanged(Container inventory) {
        WorkerMinecartInventoryDataManager
                .saveInventory(this);
    }

    public void onInteract(Player player) {
        player.openMenu(menuProvider);
    }

    public void onMoveAlongTrack(BlockPos pos, BlockState state) {
        var minecartEntity = ((Entity) this.entity);
        var level = minecartEntity.level();
        var blockPos = minecartEntity.blockPosition();
        var blockUnder = level.getBlockState(blockPos);
        if (!blockUnder.is(BlockTags.RAILS)) return;
        var railShape = blockUnder.getValue(((BaseRailBlock) blockUnder.getBlock()).getShapeProperty());

        var newRail = blockUnder.getBlock().defaultBlockState();
        switch (railShape) {
            case NORTH_EAST -> {
                var northBlock = level.getBlockState(blockPos.north());
                var eastBlock = level.getBlockState(blockPos.east());
                if (northBlock.isAir()) {
                    newRail.setValue(((BaseRailBlock) newRail.getBlock()).getShapeProperty(), RailShape.NORTH_SOUTH);
                    level.setBlockAndUpdate(blockPos.north(), newRail);
                    return;
                }
                if (eastBlock.isAir()) {
                    newRail.setValue(((BaseRailBlock) newRail.getBlock()).getShapeProperty(), RailShape.EAST_WEST);
                    level.setBlockAndUpdate(blockPos.east(), newRail);
                }
            }
            case NORTH_WEST -> {
                var northBlock = level.getBlockState(blockPos.north());
                var westBlock = level.getBlockState(blockPos.west());
                if (northBlock.isAir()) {
                    newRail.setValue(((BaseRailBlock) newRail.getBlock()).getShapeProperty(), RailShape.NORTH_SOUTH);
                    level.setBlockAndUpdate(blockPos.north(), newRail);
                    return;
                }
                if (westBlock.isAir()) {
                    newRail.setValue(((BaseRailBlock) newRail.getBlock()).getShapeProperty(), RailShape.EAST_WEST);
                    level.setBlockAndUpdate(blockPos.west(), newRail);
                }
            }
            case SOUTH_WEST -> {
                var southBlock = level.getBlockState(blockPos.south());
                var westBlock = level.getBlockState(blockPos.west());
                if (southBlock.isAir()) {
                    newRail.setValue(((BaseRailBlock) newRail.getBlock()).getShapeProperty(), RailShape.NORTH_SOUTH);
                    level.setBlockAndUpdate(blockPos.south(), newRail);
                    return;
                }
                if (westBlock.isAir()) {
                    newRail.setValue(((BaseRailBlock) newRail.getBlock()).getShapeProperty(), RailShape.EAST_WEST);
                    level.setBlockAndUpdate(blockPos.west(), newRail);
                }
            }
            case SOUTH_EAST -> {
                var southBlock = level.getBlockState(blockPos.south());
                var eastBlock = level.getBlockState(blockPos.east());
                if (southBlock.isAir()) {
                    newRail.setValue(((BaseRailBlock) newRail.getBlock()).getShapeProperty(), RailShape.NORTH_SOUTH);
                    level.setBlockAndUpdate(blockPos.south(), newRail);
                    return;
                }
                if (eastBlock.isAir()) {
                    newRail.setValue(((BaseRailBlock) newRail.getBlock()).getShapeProperty(), RailShape.EAST_WEST);
                    level.setBlockAndUpdate(blockPos.east(), newRail);
                }
            }
            case NORTH_SOUTH -> {
                var northBlock = level.getBlockState(blockPos.north());
                var southBlock = level.getBlockState(blockPos.south());
                if (northBlock.isAir()) {
                    newRail.setValue(((BaseRailBlock) newRail.getBlock()).getShapeProperty(), RailShape.NORTH_SOUTH);
                    level.setBlockAndUpdate(blockPos.north(), newRail);
                    return;
                }
                if (southBlock.isAir()) {
                    newRail.setValue(((BaseRailBlock) newRail.getBlock()).getShapeProperty(), RailShape.NORTH_SOUTH);
                    level.setBlockAndUpdate(blockPos.south(), newRail);
                }
            }
            case EAST_WEST -> {
                var eastBlock = level.getBlockState(blockPos.east());
                var westBlock = level.getBlockState(blockPos.west());
                if (eastBlock.isAir()) {
                    newRail.setValue(((BaseRailBlock) newRail.getBlock()).getShapeProperty(), RailShape.EAST_WEST);
                    level.setBlockAndUpdate(blockPos.east(), newRail);
                    return;
                }
                if (westBlock.isAir()) {
                    newRail.setValue(((BaseRailBlock) newRail.getBlock()).getShapeProperty(), RailShape.EAST_WEST);
                    level.setBlockAndUpdate(blockPos.west(), newRail);
                }
            }
        }
    }
}
