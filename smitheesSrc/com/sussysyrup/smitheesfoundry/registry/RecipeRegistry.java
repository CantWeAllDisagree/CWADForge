package com.sussysyrup.smitheesfoundry.registry;

import com.sussysyrup.smitheesfoundry.Main;
import com.sussysyrup.smitheesfoundry.api.recipe.ApiEnderResonatorRegistry;
import com.sussysyrup.smitheesfoundry.api.recipe.EnderResonatorRecipe;
import net.minecraft.util.Identifier;

public class RecipeRegistry {

    public static void main()
    {
        ApiEnderResonatorRegistry.getInstance().registerRecipe(new Identifier("ender_pearl"), new EnderResonatorRecipe(new Identifier(Main.MODID, "crude_resonator"), 810));
        ApiEnderResonatorRegistry.getInstance().registerRecipe(new Identifier("ender_eye"), new EnderResonatorRecipe(new Identifier(Main.MODID, "resonator"), 405));
    }

}
