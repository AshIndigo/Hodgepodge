package com.mitchej123.hodgepodge.mixins.late.dragonrealm;

import java.lang.reflect.Field;
import java.util.Map;

import net.minecraft.item.ItemStack;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import com.mitchej123.hodgepodge.mixins.late.buildcraft.MixinBCFuel;
import com.mitchej123.hodgepodge.mixins.late.forestry.MixinEngineBronzeFuel;
import com.mitchej123.hodgepodge.mixins.late.immersiveengineering.MixinMetalPressRecipe;

import Reika.DragonAPI.Instantiable.IO.CustomRecipeList;
import Reika.DragonAPI.Libraries.Registry.ReikaItemHelper;
import Reika.DragonAPI.ModList;
import Reika.DragonRealmCore.DragonRealmCore;
import Reika.DragonRealmCore.Recipe.Mods.GearUnifyMod;
import Reika.DragonRealmCore.Util.ReflectiveManagers;
import binnie.extrabees.genetics.ExtraBeeDefinition;
import blusunrize.immersiveengineering.api.crafting.MetalPressRecipe;
import cpw.mods.fml.common.Loader;
import forestry.api.apiculture.EnumBeeChromosome;
import forestry.api.apiculture.IAlleleBeeSpecies;
import forestry.api.apiculture.IAlleleBeeSpeciesCustom;
import forestry.api.genetics.AlleleManager;

@Mixin(ReflectiveManagers.class)
public class MixinReflectiveManagers {

    @Inject(method = "tweakIEGearPressOutput", at = @At("HEAD"), remap = false)
    private void hodgepodge$tweakIEGearPressOutput(CallbackInfo info) {
        for (MetalPressRecipe r : MetalPressRecipe.recipeList.values()) {
            ItemStack out = GearUnifyMod.getGearReplacement(r.output);
            if (!ItemStack.areItemStacksEqual(out, r.output)) {
                try {
                    ((MixinMetalPressRecipe) r).setOutput(out);
                    // ReikaReflectionHelper.setFinalField(r.getClass(), "output", r, out);
                } catch (Exception var5) {
                    throw new RuntimeException(
                            "Error setting gear type in metal press recipe " + r
                                    + " = "
                                    + CustomRecipeList.fullID(r.input)
                                    + " > "
                                    + CustomRecipeList.fullID(r.output),
                            var5);
                }
            }
        }
    }

    /**
     * @author AshIndigo
     * @reason More!
     */
    @Overwrite(remap = false)
    public void filterBeeProducts() {
        Class c;
        if (ModList.MAGICBEES.isLoaded() && Loader.isModLoaded("ExtraBees")) {
            try {
                ItemStack comb = ReikaItemHelper.lookupItem("ExtraBees:honeyComb:64"); // New Binnie/Extrabee update
                                                                                       // re-arranged metadata it seems.
                                                                                       // 81 Now corresponds to barren
                                                                                       // comb. 64 is current Certus
                                                                                       // comb value
                DragonRealmCore.logger.log("Removing certus comb (" + comb + ") from quantum and spatial bees.");

                IAlleleBeeSpeciesCustom quantum = (IAlleleBeeSpeciesCustom) ExtraBeeDefinition.QUANTUM.getGenome()
                        .getActiveAllele(EnumBeeChromosome.SPECIES);
                quantum.getProductChances().remove(comb);
                quantum.getSpecialtyChances().remove(comb);

                IAlleleBeeSpeciesCustom spatial = (IAlleleBeeSpeciesCustom) ExtraBeeDefinition.SPATIAL.getGenome()
                        .getActiveAllele(EnumBeeChromosome.SPECIES);
                spatial.getProductChances().remove(comb);
                spatial.getSpecialtyChances().remove(comb);
            } catch (Exception var20) {
                throw new RuntimeException(var20);
            }
        }

        try {
            IAlleleBeeSpecies iae = (IAlleleBeeSpecies) AlleleManager.alleleRegistry.getAllele("forestry.speciesBoggy");
            c = Class.forName("forestry.apiculture.genetics.alleles.AlleleBeeSpecies");
            Field f = c.getDeclaredField("specialtyChances");
            f.setAccessible(true);
            Map<ItemStack, Float> map = (Map) f.get(iae);
            map.clear();
            ItemStack is = null;
            if (ModList.BOP.isLoaded()) {
                is = ReikaItemHelper.lookupItem("BiomesOPlenty:moss");
            }

            if (is == null && ModList.TWILIGHT.isLoaded()) {
                is = ReikaItemHelper.lookupItem("TwilightForest:tile.TFPlant:3");
            }

            if (is != null) {
                map.put(is, 0.1F);
            }

        } catch (Exception var19) {
            throw new RuntimeException(var19);
        }
    }

    /**
     * See MixinTileEntityDesk
     * 
     * @see com.mitchej123.hodgepodge.mixins.late.mystcraft.MixinTileEntityDesk
     */
    @Inject(method = "makeMystDeskAutomatable", at = @At("HEAD"), remap = false, cancellable = true)
    public void hodgepodge$makeMystDeskAutomatable(CallbackInfo ci) {
        ci.cancel();
    }

    /**
     * See MixinSmelteryLogic
     *
     * @see com.mitchej123.hodgepodge.mixins.late.tconstruct.MixinSmelteryLogic
     */
    @Inject(method = "increaseSmelterySize(I)V", at = @At("HEAD"), remap = false, cancellable = true)
    public void hodgepodge$increaseSmelterySize(int size, CallbackInfo ci) {
        ci.cancel();
    }

    @Redirect(
            method = "boostBCEthanol",
            at = @At(
                    value = "INVOKE",
                    target = "LReika/DragonAPI/Libraries/Java/ReikaReflectionHelper;setFinalField(Ljava/lang/reflect/Field;Ljava/lang/Object;Ljava/lang/Object;)V"),
            remap = false)
    public void hodgepodge$boostBCEthanol(Field modifiersField, Object recipe, Object put) {
        if (recipe instanceof MixinBCFuel) {
            ((MixinBCFuel) recipe).setPowerPerCycle((Integer) put);
        }
    }

    @Redirect(
            method = "boostForestryBiogas",
            at = @At(
                    value = "INVOKE",
                    target = "LReika/DragonAPI/Libraries/Java/ReikaReflectionHelper;setFinalField(Ljava/lang/reflect/Field;Ljava/lang/Object;Ljava/lang/Object;)V"),
            remap = false)
    public void hodgepodge$boostForestryBiogas(Field modifiersField, Object recipe, Object put) {
        if (recipe instanceof MixinEngineBronzeFuel) {
            ((MixinEngineBronzeFuel) recipe).setPowerPerCycle((Integer) put);
        }
    }
}
