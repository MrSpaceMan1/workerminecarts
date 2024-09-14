package com.github.mrspaceman1.workerminecarts.utils;

import net.minecraft.core.Vec3i;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.world.phys.Vec3;

public class UtilMethods {
    public static DataComponentMap getComponentsWithoutTool(DataComponentMap components){
        return components != null ?
                DataComponentMap.EMPTY :
                components.filter(dataComponentType -> !dataComponentType.toString().equals("minecraft:tool"));
    }

    public static Vec3i floorVecValuewise(Vec3 vector){
        int x;
        if(vector.x < 0)
            x = (int) -Math.floor(-vector.x);
        else
            x = (int) Math.floor(vector.x);

        int y;
        if(vector.x < 0)
            y = (int) -Math.floor(-vector.y);
        else
            y = (int) Math.floor(vector.y);

        int z;
        if(vector.x < 0)
            z = (int) -Math.floor(-vector.z);
        else
            z = (int) Math.floor(vector.z);
        return new Vec3i(
                x,
                y,
                z
        );
    }

    public static Vec3 roundVec(Vec3 vector, int decimalPlaces){
        double x;
        if(vector.x < 0)
            x = -Math.round(-vector.x * Math.pow(10, decimalPlaces))/Math.pow(10, decimalPlaces);
        else
            x = Math.round(vector.x * Math.pow(10, decimalPlaces))/Math.pow(10, decimalPlaces);
        double y;
        if(vector.y < 0)
            y = -Math.round(-vector.y * Math.pow(10, decimalPlaces))/Math.pow(10, decimalPlaces);
        else
            y = Math.round(vector.y * Math.pow(10, decimalPlaces))/Math.pow(10, decimalPlaces);
        double z;
        if(vector.z < 0)
            z = -Math.round(-vector.z * Math.pow(10, decimalPlaces))/Math.pow(10, decimalPlaces);
        else
            z = Math.round(vector.z * Math.pow(10, decimalPlaces))/Math.pow(10, decimalPlaces);
        return new Vec3(x, y, z);
    }

    public static Vec3 roundVec(Vec3 vector){
        double x;
        if (vector.x < 0)
            x = -Math.round(-vector.x);
        else
            x = Math.round(vector.x);
        double y;
        if (vector.y < 0)
            y = -Math.round(-vector.y);
        else
            y = Math.round(vector.y);
        double z;
        if (vector.z < 0)
            z = -Math.round(-vector.z);
        else
            z= Math.round(vector.z);
        return new Vec3(x, y, z);
    }
}
