package cantwe.alldisagree.CWADForge.api.modification;

import net.minecraft.text.MutableText;
import net.minecraft.text.Text;

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

    public MutableText getModificationTranslation()
    {
        return Text.translatable("smitheesfoundry.modification." + name);
    }
    public MutableText getModificationDescription()
    {
        return Text.translatable("smitheesfoundry.modification." + name + ".desc");
    }


    public int getLevel()
    {
        return level;
    }
}
