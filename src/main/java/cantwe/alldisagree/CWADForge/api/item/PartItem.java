package cantwe.alldisagree.CWADForge.api.item;

import cantwe.alldisagree.CWADForge.Main;
import cantwe.alldisagree.CWADForge.util.TranslationUtil;
import cantwe.alldisagree.CWADForge.util.Util;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Language;

public class PartItem extends Item {

    private String partName;
    private String category;
    private String materialId;

    public PartItem(Settings settings, String partName, String materialId, String category) {
        super(settings);

        this.partName = partName;
        this.materialId = materialId;
        this.category = category;
    }

    public String getPartName() {
        return partName;
    }

    public String getMaterialId() {
        return materialId;
    }

    @Override
    public Text getName(ItemStack stack) {
        if(TranslationUtil.fullKeys)
        {
            String key = "item." + Main.MODID + "." + partName + "." + materialId;

            boolean hasKey = Language.getInstance().hasTranslation(key);

            if(hasKey) {
                return Text.translatable(key);
            }
            if(Main.config.isTranslationMode())
            {
                return Text.translatable(key);
            }
        }
        return Text.translatable("item." + Main.MODID + "." + partName, Text.translatable(Util.materialAdjTranslationkey(materialId)));
    }

    public String getCategory()
    {
        return category;
    }
}
