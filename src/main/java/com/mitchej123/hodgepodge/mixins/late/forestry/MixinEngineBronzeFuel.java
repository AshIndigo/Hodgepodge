package com.mitchej123.hodgepodge.mixins.late.forestry;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

import forestry.api.fuels.EngineBronzeFuel;

@Mixin(EngineBronzeFuel.class)
public interface MixinEngineBronzeFuel {

    @Mutable
    @Accessor
    void setPowerPerCycle(int pow);
}
