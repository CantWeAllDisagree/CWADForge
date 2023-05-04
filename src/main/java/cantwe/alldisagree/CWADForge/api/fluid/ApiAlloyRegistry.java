package cantwe.alldisagree.CWADForge.api.fluid;

import com.google.common.collect.ArrayListMultimap;
import cantwe.alldisagree.CWADForge.impl.registry.RegistryInstances;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.Identifier;

import java.util.List;

public interface ApiAlloyRegistry {

    static ApiAlloyRegistry getInstance()
    {
        return (ApiAlloyRegistry) RegistryInstances.alloyRegistry;
    }

    ArrayListMultimap<Fluid, AlloyResource> getAlloyMap();
    void addAlloy(Identifier output, long outputAmount, Identifier[] fluids, long[] amount);

    void removeAlloyResource(Identifier fluidID);

    void clear();
    List<AlloyResource> getAlloyResources(Fluid firstFluid);

    void reload();
}
