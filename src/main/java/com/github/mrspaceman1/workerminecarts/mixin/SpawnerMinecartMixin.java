package com.github.mrspaceman1.workerminecarts.mixin;

import com.github.mrspaceman1.workerminecarts.WorkerMinecarts;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.vehicle.MinecartSpawner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MinecartSpawner.class)
public class SpawnerMinecartMixin {
    @Inject(method = "tick", at = @At("HEAD"))
    public void onTick(CallbackInfo ci){
        var workerMinecart = WorkerMinecarts.workerMinecarts.get(((Entity) (Object) this).getUUID());
        if(workerMinecart == null) return;
        if(workerMinecart.fullyInitialized) return;
        workerMinecart.initalizeLoadedWorkerMinecart((MinecartSpawner) (Object) this);
    }
}
