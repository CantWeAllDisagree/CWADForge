package cantwe.alldisagree.CWADForge.api.modification;

import java.util.List;

public interface ApiModificationRegistry {

    void registerModification(String name, int level, ModificationContainer modificationContainer);

    void removeModification(String name, int level);

    ModificationContainer getModification(String name, int level);

    void registerModificationRecipe(String name, int level, ModificationRecipe modificationRecipe);

    void removeModificationRecipe(ModificationRecipe modificationRecipe);

    String getFromModificationRecipe(ModificationRecipe modificationRecipe);

    ModificationRecipe getFromStringRecipe(String recipeKey);

    void reload();

    List<String> getModificationKeys();
}
