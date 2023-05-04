package com.sussysyrup.smitheesfoundry.api.trait;

import net.minecraft.text.TranslatableTextContent;
import net.minecraft.util.Formatting;

public abstract class TraitContainer {

    private final Formatting formatting;
    private final String name;

    public TraitContainer(String name, Formatting formatting)
    {
        this.name = name;
        this.formatting = formatting;
    }

    public String getName()
    {
        return name;
    }

    public TranslatableTextContent getTraitTranslation()
    {
        return Text.translatable("smitheesfoundry.trait." + name);
    }
    public TranslatableTextContent getTraitDescription()
    {
        return Text.translatable("smitheesfoundry.trait." + name + ".desc");
    }

    public Formatting getFormatting() {
        return formatting;
    }
}
