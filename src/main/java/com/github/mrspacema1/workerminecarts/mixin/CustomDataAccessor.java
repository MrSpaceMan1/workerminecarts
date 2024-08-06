package com.github.mrspacema1.workerminecarts.mixin;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.component.CustomData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(CustomData.class)
public interface CustomDataAccessor {
    @Accessor
    CompoundTag getTag();
}
