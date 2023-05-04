package com.sussysyrup.smitheesfoundry.api.modification;

import net.minecraft.util.Identifier;

import java.util.HashMap;

public record ModificationRecipe(Identifier fluid, HashMap<Identifier,Integer> reactants) {
}
