package com.github.mrspaceman1.workerminecarts.mixin;

import com.github.mrspaceman1.workerminecarts.WorkerMinecarts;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractMinecart.class)
public class AbstractMinecartMixin {
    @Inject(method = "moveAlongTrack", at = @At("HEAD"))
    public void onMoveAlongTrack(BlockPos pos, BlockState state, CallbackInfo ci) {
        var workerMinecart = WorkerMinecarts.workerMinecarts.get(((Entity) (Object) this).getUUID());
        if(workerMinecart == null) return;
        workerMinecart.onMoveAlongTrack(pos, state);
    }
}
