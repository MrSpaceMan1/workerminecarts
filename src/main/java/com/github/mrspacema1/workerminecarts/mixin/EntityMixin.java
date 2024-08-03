package com.github.mrspacema1.workerminecarts.mixin;

import com.github.mrspacema1.workerminecarts.utils.SimpleContainerSerialization;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.MinecartSpawner;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.io.IOException;

import static com.github.mrspacema1.workerminecarts.WorkerMinecarts.LOGGER;
import static com.github.mrspacema1.workerminecarts.WorkerMinecarts.WorkerMinecarts;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Shadow public abstract Level level();

    @Inject(method = "interact", at = @At("HEAD"))
    public void onInteract(Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir){
        if(!(((Entity) (Object) this) instanceof MinecartSpawner)) return;
        LOGGER.info("Interaction with MinecartSpawner");
        var thisWorkerCart = (MinecartSpawner) (Object) this;
        thisWorkerCart.noPhysics = true;
        SimpleContainer workerInventory = (SimpleContainer) WorkerMinecarts.computeIfAbsent(((EntityAccessor) this).getId(), (key) -> new SimpleContainer(54));

        workerInventory.addListener(container -> {
            NonNullList<ItemStack> items = NonNullList.createWithCapacity(54);

            try {
                var test = SimpleContainerSerialization.containerToStream((SimpleContainer) container, thisWorkerCart);
                SimpleContainerSerialization.streamToContainer(test, items, thisWorkerCart);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        var inventoryProvider = new SimpleMenuProvider((i, inventory, player1) -> ChestMenu.sixRows(i, inventory, workerInventory), Component.literal("KURWA"));
        player.openMenu(inventoryProvider);

    }

}
