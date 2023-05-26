package com.mitchej123.hodgepodge.mixins.late.dragonrealm;

import java.lang.reflect.Field;

import net.minecraft.util.MathHelper;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.mitchej123.hodgepodge.mixins.late.immersiveengineering.MixinArcFurnaceRecipe;
import com.mitchej123.hodgepodge.mixins.late.thermalexpansion.MixinRecipeSmelter;

import Reika.DragonRealmCore.Recipe.DRRecipeUtil;
import blusunrize.immersiveengineering.api.crafting.ArcFurnaceRecipe;

@Mixin(DRRecipeUtil.class)
public class MixinDRRecipeUtil {
    @Redirect(
            method = "modifyDieselProductionThroughput",
            at = @At(
                    value = "INVOKE",
                    target = "LReika/DragonAPI/Libraries/Java/ReikaReflectionHelper;setFinalField(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Object;)V"),
            remap = false)
    public void hodgepodge$modifyDieselProductionThroughput(Class c, String s, Object instance, Object o) {
        if (instance instanceof MixinRecipeSmelter) {
            ((MixinRecipeSmelter) instance).setEnergy((Integer) o);
        }
    }

    /**
     * @author AshIndigo
     * @reason Yeet
     */
    @Overwrite(remap = false)
    public void increaseArcFurnaceSpeedAndPowerCost(float speedFactor, float powerFactor) {

        for (ArcFurnaceRecipe ar : ArcFurnaceRecipe.recipeList) {
            ((MixinArcFurnaceRecipe) ar)
                    .setEnergyPerTick(MathHelper.ceiling_float_int((float) ar.energyPerTick * powerFactor));
            ((MixinArcFurnaceRecipe) ar).setTime(Math.max(1, MathHelper.floor_float((float) ar.time / speedFactor)));
        }

    }

    @Redirect(
            method = "increaseThermalRFCosts",
            at = @At(
                    value = "INVOKE",
                    target = "LReika/DragonAPI/Libraries/Java/ReikaReflectionHelper;setFinalField(Ljava/lang/reflect/Field;Ljava/lang/Object;Ljava/lang/Object;)V"),
            remap = false)
    public void hodgepodge$setField(Field modifiersField, Object recipe, Object put) {
        if (recipe instanceof MixinRecipeSmelter) {
            ((MixinRecipeSmelter) recipe).setEnergy((Integer) put);
        }
    }

}
