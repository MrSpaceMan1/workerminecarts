package com.github.mrspacema1.workerminecarts.facade;

import com.github.mrspacema1.workerminecarts.mixin.CustomDataAccessor;
import com.mojang.serialization.*;
import com.mojang.serialization.codecs.PairMapCodec;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.CustomData;
import com.github.mrspacema1.workerminecarts.WorkerMinecarts.*;

import static com.github.mrspacema1.workerminecarts.WorkerMinecarts.LOGGER;

public class WorkerMinecartItem {
//    public static boolean IsWorkerMinecartItem(ItemStack itemStack) {
//        var components = itemStack.getComponents();
//        CustomData customData = components.getTyped(DataComponents.CUSTOM_DATA).value();
//        CompoundTag nbt = ((CustomDataAccessor) (Object) customData).getTag();
//        LOGGER.info(nbt.toString());
//        return false;
//    }
}
