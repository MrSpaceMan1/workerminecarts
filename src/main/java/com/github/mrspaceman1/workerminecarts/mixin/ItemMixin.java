package com.github.mrspaceman1.workerminecarts.mixin;

import com.github.mrspaceman1.workerminecarts.facade.WorkerMinecartItem;
import com.github.mrspaceman1.workerminecarts.utils.UtilMethods;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.github.mrspaceman1.workerminecarts.WorkerMinecarts.LOGGER;

@Mixin(Item.class)
public class ItemMixin {
    @Inject(method = "use", at = @At("HEAD"), cancellable = true)
    public void onUse(Level level, Player player, InteractionHand usedHand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir){
        var heldItem = player.getMainHandItem();
        for (var comp : UtilMethods.getComponentsWithoutTool(heldItem.getComponents())){
            LOGGER.info(comp.toString());
        }
        var blockHit = (BlockHitResult) player.pick(10.0, 1.0F, false);
        if(!WorkerMinecartItem.isWorkerMinecartItem(heldItem)) return;
        var workerMinecartItem = new WorkerMinecartItem(heldItem);
        if(level.isClientSide()) return;
        var interactionResult = workerMinecartItem.useOn(blockHit, player, (ServerLevel) level);
        cir.setReturnValue(interactionResult.equals(InteractionResult.SUCCESS) ?
                InteractionResultHolder.success(heldItem) :
                InteractionResultHolder.pass(heldItem));
    }
}
