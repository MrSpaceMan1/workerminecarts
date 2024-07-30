package com.github.mrspacema1.workerminecarts;

import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;


public class WorkerMinecart extends AbstractMinecart {
    @Override
    public AbstractMinecart.Type getMinecartType(){
        return Type.CHEST;
    }

    @Override
    public Item getDropItem(){
        return Items.CHEST_MINECART;
    }

}


