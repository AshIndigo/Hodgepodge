package com.mitchej123.hodgepodge.mixins.late.dragonrealm;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import Reika.DragonAPI.Instantiable.IO.ModLogger;
import Reika.DragonRealmCore.WorldGen.TerritoryStrongholdSystem;

@Mixin(TerritoryStrongholdSystem.class)
public class MixinTerritoryStrongholdSystem {

    @Redirect(
            method = "initWorld",
            at = @At(value = "INVOKE", target = "LReika/DragonAPI/Instantiable/IO/ModLogger;log(Ljava/lang/Object;)V"),
            remap = false)
    public void hodgepodge$shutup(ModLogger instance, Object o) {
        // NO-OP
    }
}
