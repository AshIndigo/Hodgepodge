package com.mitchej123.hodgepodge.mixins.late.dragonrealm;

import java.lang.reflect.Field;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.mitchej123.hodgepodge.mixins.late.immersiveengineering.MixinArcFurnaceRecipe;

import Reika.DragonRealmCore.Recipe.NikoliteInjectionHandler;
import blusunrize.immersiveengineering.api.crafting.ArcFurnaceRecipe;

@Mixin(NikoliteInjectionHandler.class)
public class MixinNikoliteInjectionHandler {

    @Shadow
    @Final
    public static NikoliteInjectionHandler instance;

    @Redirect(
            method = "addNikolite",
            at = @At(
                    value = "INVOKE",
                    target = "LReika/DragonAPI/Libraries/Java/ReikaReflectionHelper;setFinalField(Ljava/lang/reflect/Field;Ljava/lang/Object;Ljava/lang/Object;)V"),
            remap = false)
    public void hodgepodge$setField(Field modifiersField, Object recipe, Object put) {
        if (recipe instanceof ArcFurnaceRecipe && put instanceof Object[]) {
            ((MixinArcFurnaceRecipe) recipe).setAdditives((Object[]) put);
        }
    }
}
