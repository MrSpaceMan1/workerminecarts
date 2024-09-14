package com.github.mrspaceman1.workerminecarts.facade;

import com.github.mrspaceman1.workerminecarts.WorkerMinecarts;
import com.github.mrspaceman1.workerminecarts.WorkerMinecartsCustomData;
import com.github.mrspaceman1.workerminecarts.mixin.accessor.CustomDataAccessor;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

import static com.github.mrspaceman1.workerminecarts.WorkerMinecarts.LOGGER;

public class WorkerMinecartItem {
    public static String ID = "worker_minecart_item";
    private final ItemStack minecraftItem;
    public WorkerMinecartItem(ItemStack item){
        this.minecraftItem = item;
    }

    public InteractionResult useOn(BlockHitResult hit, Player player, ServerLevel level){
        if(hit.getType() == HitResult.Type.MISS) return InteractionResult.FAIL;
        var blockState = level.getBlockState(hit.getBlockPos());
        if(!blockState.is(BlockTags.RAILS)) return InteractionResult.FAIL;
        player.gameEvent(GameEvent.ITEM_INTERACT_FINISH);
        level.gameEvent(GameEvent.ENTITY_INTERACT, hit.getBlockPos(), GameEvent.Context.of(player, blockState));
        var workerMinecraft = EntityType.SPAWNER_MINECART.spawn(level, hit.getBlockPos(), MobSpawnType.MOB_SUMMONED);
        workerMinecraft.addTag(WorkerMinecartsCustomData.WORKER_ENTITY_TAG);
        var workerMinecraftFacade = new WorkerMinecartEntity(workerMinecraft);
        return InteractionResult.SUCCESS;
    }

    public static boolean isWorkerMinecartItem(ItemStack itemStack) {
        var components = itemStack.getComponents();
        CustomData customData = components.getTyped(DataComponents.CUSTOM_DATA).value();
        CompoundTag nbt = ((CustomDataAccessor) (Object) customData).getTag();
        var id = nbt.getString(WorkerMinecartsCustomData.ID);
        return id.equals(WorkerMinecartItem.ID);
    }
}
