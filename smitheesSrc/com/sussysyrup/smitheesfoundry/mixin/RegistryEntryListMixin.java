package com.sussysyrup.smitheesfoundry.mixin;

import com.sussysyrup.smitheesfoundry.api.fluid.ApiMoltenFluidRegistry;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.registry.tag.TagKey;

/**
 * This is now hackery
 */
@Mixin(RegistryEntryList.Named.class)
public class RegistryEntryListMixin {

    @Shadow @Final private TagKey<?> tag;

    @Shadow private List<RegistryEntry<?>> entries;

    @Inject(method = "copyOf", at = @At(value = "RETURN", target = "Ljava/util/List;copyOf(Ljava/util/Collection;)Ljava/util/List;"))
    public void copyOf(List<RegistryEntry<?>> entries, CallbackInfo ci)
    {
        if(this.tag.equals(FluidTags.LAVA))
        {
            List<RegistryEntry<?>> copyList = new ArrayList<>(this.entries);

            copyList.addAll(ApiMoltenFluidRegistry.getInstance().getCreateFluidEntries());

            this.entries = List.copyOf(copyList);
        }
    }
}
