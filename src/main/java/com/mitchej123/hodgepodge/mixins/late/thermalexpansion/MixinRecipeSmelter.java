package com.mitchej123.hodgepodge.mixins.late.thermalexpansion;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import cofh.thermalexpansion.util.crafting.SmelterManager;

@Mixin(SmelterManager.RecipeSmelter.class)
public interface MixinRecipeSmelter {

    // @Accessor
    // Map<List<SmelterManager.ComparableItemStackSmelter>, SmelterManager.RecipeSmelter> getRecipeMap();

    // @Mixin(SmelterManager.RecipeSmelter.class)
    // interface MixinRecipeSmelter {

    @Mutable
    @Accessor
    void setEnergy(int energy);
    // }
}
