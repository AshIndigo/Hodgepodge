package com.mitchej123.hodgepodge.mixins.late.tconstruct;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import tconstruct.smeltery.logic.SmelteryLogic;

@Mixin(SmelteryLogic.class)
public class MixinSmelteryLogic {

    @Mutable
    @Accessor
    private static void setMAX_SMELTERY_SIZE(int size) {
        throw new AssertionError();
    }

    @Inject(method = "<init>", at = @At("RETURN"), remap = false)
    private void hodgepodge$init(CallbackInfo info) {
        setMAX_SMELTERY_SIZE(18);
    }
}
