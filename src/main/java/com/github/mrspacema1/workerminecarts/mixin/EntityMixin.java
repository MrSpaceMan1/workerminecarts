package com.github.mrspacema1.workerminecarts.mixin;

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
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.github.mrspacema1.workerminecarts.WorkerMinecarts.LOGGER;

@Mixin(Entity.class)
public abstract class EntityMixin {

    @Shadow public abstract Level level();

    @Inject(method = "interact", at = @At("HEAD"))
    public void onInteract(Player player, InteractionHand hand, CallbackInfoReturnable<InteractionResult> cir){
        if(!(((Entity) (Object) this) instanceof MinecartSpawner)) return;
        LOGGER.info("Interaction with MinecartSpawner");
        var thisWorkerCart = (MinecartSpawner) (Object) this;
        thisWorkerCart.noPhysics = true;

    }

}
