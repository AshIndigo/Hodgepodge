package com.mitchej123.hodgepodge.mixins.late.buildcraft;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(targets = "buildcraft.energy.fuels.FuelManager$BCFuel")
public interface MixinBCFuel {

    @Accessor
    @Mutable
    void setPowerPerCycle(int pow);

}
