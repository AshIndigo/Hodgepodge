package com.mitchej123.hodgepodge.mixins.late.immersiveengineering;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import blusunrize.immersiveengineering.api.energy.DieselHandler;

@Mixin(DieselHandler.SqueezerRecipe.class)
public interface MixinSqueezerRecipe {

    @Mutable
    @Accessor
    void setTime(int time);
}
