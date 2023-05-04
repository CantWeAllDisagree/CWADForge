package com.sussysyrup.smitheesfoundry.api.modification;

import net.minecraft.text.TranslatableTextContent;
import net.minecraft.util.Formatting;

public abstract class ModificationContainer {

    private final String name;
    private final int level;

    public ModificationContainer(String name, int level)
    {
        this.name = name;
        this.level = level;
    }

    public String getName()
    {
        return name;
    }

    public TranslatableTextContent getModificationTranslation()
    {
        return Text.translatable("smitheesfoundry.modification." + name);
    }
    public TranslatableTextContent getModificationDescription()
    {
        return Text.translatable("smitheesfoundry.modification." + name + ".desc");
    }


    public int getLevel()
    {
        return level;
    }
}
