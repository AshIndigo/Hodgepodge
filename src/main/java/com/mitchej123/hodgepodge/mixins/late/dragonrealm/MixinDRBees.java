package com.mitchej123.hodgepodge.mixins.late.dragonrealm;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.item.ItemStack;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import Reika.DragonAPI.ModInteract.ItemHandlers.BerryBushHandler;
import Reika.DragonRealmCore.Bees.DRBees;
import Reika.DragonRealmCore.ItemsAndBlocks.ItemDRCrafting;
import forestry.api.apiculture.IAlleleBeeSpecies;
import forestry.api.apiculture.IAlleleBeeSpeciesCustom;

@Mixin(DRBees.class)
public class MixinDRBees {

    @Inject(
            method = "initialize",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/lang/Class;forName(Ljava/lang/String;)Ljava/lang/Class;",
                    shift = At.Shift.BY,
                    by = -1),
            cancellable = true,
            locals = LocalCapture.CAPTURE_FAILHARD,
            remap = false)
    private static void hodgepodge$initialize(CallbackInfo ci, IAlleleBeeSpecies fruity) {
        if (fruity instanceof IAlleleBeeSpeciesCustom) {
            IAlleleBeeSpeciesCustom fruitCustom = (IAlleleBeeSpeciesCustom) fruity;
            fruitCustom.addSpecialty(ItemDRCrafting.Items.BERRYCOMB.getStack(), .15F);
        }
        List<Object> li = new ArrayList();

        for (int i = 0; i < 4; ++i) {
            li.add(new ItemStack(BerryBushHandler.getInstance().berryID, 1, i));
            li.add(25);
        }

        DRBees.addCombProducts(ItemDRCrafting.Items.BERRYCOMB.getStack(), 1.5, li.toArray(new Object[li.size()]));
        ci.cancel();
    }
}
