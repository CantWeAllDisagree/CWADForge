package cantwe.alldisagree.CWADForge.impl.recipe;

import cantwe.alldisagree.CWADForge.api.recipe.ApiEnderResonatorRegistry;
import cantwe.alldisagree.CWADForge.api.recipe.EnderResonatorRecipe;
import net.minecraft.util.Identifier;

import java.util.HashMap;

public class ImplEnderResonatorRegistry implements ApiEnderResonatorRegistry {

    private HashMap<Identifier, EnderResonatorRecipe> reloadEnderResonatorRecipeMap = new HashMap<>();
    private static HashMap<Identifier, EnderResonatorRecipe> enderResonatorRecipeMap = new HashMap<>();

    @Override
    public void registerRecipe(Identifier inputItem, EnderResonatorRecipe resonatorRecipe) {
        enderResonatorRecipeMap.put(inputItem, resonatorRecipe);
    }

    @Override
    public void removeRecipe(Identifier inputItem) {
        enderResonatorRecipeMap.remove(inputItem);
    }

    @Override
    public EnderResonatorRecipe getRecipe(Identifier inputItem) {
        return reloadEnderResonatorRecipeMap.get(inputItem);
    }

    @Override
    public HashMap<Identifier, EnderResonatorRecipe> getMap()
    {
        return reloadEnderResonatorRecipeMap;
    }

    @Override
    public void reload() {
        reloadEnderResonatorRecipeMap.putAll(enderResonatorRecipeMap);
    }
}
