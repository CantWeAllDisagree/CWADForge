package cantwe.alldisagree.CWADForge.api.fluid;

import cantwe.alldisagree.CWADForge.impl.registry.RegistryInstances;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.RegistryEntry;

import java.awt.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

public interface ApiMoltenFluidRegistry {

    static ApiMoltenFluidRegistry getInstance()
    {
        return (ApiMoltenFluidRegistry) RegistryInstances.moltenFluidRegistry;
    }

    void registerCreateFluid(String fluidName, FluidProperties fluidProperties);

    void removeCreateFluid(String fluidName);

    void clearCreateFluids();

    void registerExternalFluidProperties(String fluidName, FluidProperties fluidProperties);

    void removeExternalFluidProperties(String fluidName);

    void clearExternalFluidProperties();

    HashMap<String, FluidProperties> getCreateFluidRegistry();

    HashMap<String, FluidProperties> getFluidPropertiesRegistry();

    FluidProperties getFluidProperties(String fluidName);

    List<Identifier> getBucketIDs();

    Collection<? extends RegistryEntry<?>> getCreateFluidEntries();

    void registerColours(String fluidKey, Color first, Color second, Color third, Color fourth, Color fifth, Color sixth, Color seventh);

    void preReload();

    void reload();
}
