package com.github.mrspacema1.workerminecarts.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.level.block.BaseRailBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RailBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.RailShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.github.mrspacema1.workerminecarts.WorkerMinecarts.LOGGER;

@Mixin(AbstractMinecart.class)
public class AbstractMinecartMixin {
    @Inject(method = "moveAlongTrack", at = @At("HEAD"))
    public void onMoveAlongTrack(BlockPos pos, BlockState state, CallbackInfo ci) {
        var minecartEntity = ((Entity) (Object) this);
        var level = minecartEntity.level();

        // get next block position
        var movementD = minecartEntity.getDeltaMovement();
        var currentPos = minecartEntity.position();
        var nextPos = currentPos.add(movementD);
        var nextBlockPos = new BlockPos(new Vec3i(
                ((int) Math.floor(nextPos.x)),
                (int) Math.floor(nextPos.y),
                (int) Math.floor(nextPos.z)
        ));

        // if next block is same as current return
        if (nextBlockPos.equals(pos)) return;

        var nextBlock = level.getBlockState(nextBlockPos);
        if(!nextBlock.isAir()) return;

        var railShape = (RailShape) state.getValue(((BaseRailBlock) state.getBlock()).getShapeProperty());
        BaseRailBlock newRail = (BaseRailBlock) Blocks.RAIL;
        var railBlockState = newRail.defaultBlockState();
        railBlockState.setValue(RailBlock.SHAPE, railShape);
        level.setBlock(nextBlockPos, railBlockState, 2);
        LOGGER.info("{}", nextBlockPos.equals(pos));
    }
}
