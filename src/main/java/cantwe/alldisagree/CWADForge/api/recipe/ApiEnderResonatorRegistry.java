package cantwe.alldisagree.CWADForge.api.recipe;

import cantwe.alldisagree.CWADForge.impl.registry.RegistryInstances;
import net.minecraft.util.Identifier;

import java.util.HashMap;

public interface ApiEnderResonatorRegistry {

    static ApiEnderResonatorRegistry getInstance()
    {
        return RegistryInstances.enderResonatorRegistry;
    }

    void registerRecipe(Identifier inputItem, EnderResonatorRecipe resonatorRecipe);

    void removeRecipe(Identifier inputItem);

    EnderResonatorRecipe getRecipe(Identifier inputItem);

    HashMap<Identifier, EnderResonatorRecipe> getMap();

    void reload();
}
