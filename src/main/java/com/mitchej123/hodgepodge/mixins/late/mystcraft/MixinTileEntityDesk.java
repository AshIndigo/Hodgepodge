package com.mitchej123.hodgepodge.mixins.late.mystcraft;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.xcompwiz.mystcraft.tileentity.TileEntityDesk;

@Mixin(TileEntityDesk.class)
public class MixinTileEntityDesk {

    @Accessor
    @Mutable
    private static void setIsidedslots(int[] i) {
        throw new AssertionError();
    }

    @Inject(method = "<init>", at = @At("RETURN"), remap = false)
    private void hodgepodge$init(CallbackInfo info) {
        setIsidedslots(new int[] { 1, 2 });
    }
}
