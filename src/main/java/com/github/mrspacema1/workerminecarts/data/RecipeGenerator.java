package com.github.mrspacema1.workerminecarts.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fabricmc.fabric.mixin.item.ItemAccessor;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.component.CustomModelData;

import java.util.concurrent.CompletableFuture;

public class RecipeGenerator extends FabricRecipeProvider {
    public RecipeGenerator(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void buildRecipes(RecipeOutput exporter) {
        var workerMinecart = Items.WOODEN_HOE;
        ((ItemAccessor) workerMinecart).setComponents(
                DataComponentMap.composite(
                        workerMinecart.components(),
                        DataComponentMap.builder()
                                .set(DataComponents.CUSTOM_MODEL_DATA, new CustomModelData(1))
                                .build()
                )
        );
        for(var comp : workerMinecart.components()){
            System.out.println(comp.toString());
        }
        ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, workerMinecart)
                .requires(Items.MINECART)
                .requires(Items.BLAST_FURNACE)
                .unlockedBy("has_minecart", has(Items.MINECART))
                .save(exporter, "worker_minecart");
    }
}
