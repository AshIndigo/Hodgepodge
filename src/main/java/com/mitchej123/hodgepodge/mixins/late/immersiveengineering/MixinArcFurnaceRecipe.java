package com.mitchej123.hodgepodge.mixins.late.immersiveengineering;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import blusunrize.immersiveengineering.api.crafting.ArcFurnaceRecipe;

@Mixin(ArcFurnaceRecipe.class)
public interface MixinArcFurnaceRecipe {

    @Mutable
    @Accessor
    void setTime(int time);

    @Mutable
    @Accessor
    void setEnergyPerTick(int time);

    @Mutable
    @Accessor
    void setAdditives(Object[] additives);
}
