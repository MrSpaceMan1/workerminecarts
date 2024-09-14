package com.github.mrspaceman1.workerminecarts.mixin;

import com.github.mrspaceman1.workerminecarts.WorkerMinecarts;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.MinecartSpawner;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Shadow public abstract Level level();

    @Shadow public abstract UUID getUUID();

    @Inject(method = "interact", at = @At("HEAD"))
    public void onInteract(Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir){
        var workerMinecart = WorkerMinecarts.workerMinecarts.get(this.getUUID());
        workerMinecart.onInteract(player);
    }


    @Inject(method = "kill", at = @At("HEAD"))
    public void onKill(CallbackInfo ci){
        var workerMinecart = WorkerMinecarts.workerMinecarts.get(this.getUUID());
        if(workerMinecart != null)
            WorkerMinecarts.workerMinecarts.remove(this.getUUID());
    }

    @Inject(method = "discard", at = @At("HEAD"))
    public void onDiscard(CallbackInfo ci){
        var workerMinecart = WorkerMinecarts.workerMinecarts.get(this.getUUID());
        if(workerMinecart != null)
            WorkerMinecarts.workerMinecarts.remove(this.getUUID());
    }

}
