package com.mitchej123.hodgepodge.mixins.late.immersiveengineering;

import net.minecraft.item.ItemStack;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import blusunrize.immersiveengineering.api.crafting.MetalPressRecipe;

@Mixin(MetalPressRecipe.class)
public interface MixinMetalPressRecipe {

    @Mutable
    @Accessor
    void setOutput(ItemStack stack);
}
