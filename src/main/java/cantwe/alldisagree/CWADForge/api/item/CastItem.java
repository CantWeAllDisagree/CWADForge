package cantwe.alldisagree.CWADForge.api.item;

import cantwe.alldisagree.CWADForge.Main;
import cantwe.alldisagree.CWADForge.util.TranslationUtil;
import cantwe.alldisagree.CWADForge.util.Util;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Language;

public class CastItem extends Item {

    public String castType;

    public CastItem(Settings settings, String castType) {
        super(settings);
        this.castType = castType;
    }


    //TODO Part cost for casts
    // @Override
    // public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {

    //   Float value = ApiPartRegistry.getInstance().getPartCost(castType);

    //   if(value == null)
    //   {
    //      value = Float.valueOf(0);
    //      if(castType.equals("ingot"))
    //      {
    //           value = Float.valueOf(1);
    //        }
    //    }

    //    tooltip.add(Text.translatable("part.smitheesfoundry.cost", value));

    //   super.appendTooltip(stack, world, tooltip, context);
    //  }

    @Override
    public Text getName(ItemStack stack) {

        String metal = "gold";

        if(TranslationUtil.fullKeys)
        {
            String key = "item." + Main.MODID + ".cast" + "." + castType + "." + metal;

            boolean hasKey = Language.getInstance().hasTranslation(key);

            if(hasKey) {
                return Text.translatable(key);
            }
            if(!hasKey && Main.config.isTranslationMode())
            {
                return Text.translatable(key);
            }
        }
        return Text.translatable("item." + Main.MODID + ".cast", Text.translatable("item." + Main.MODID + "." + castType, Text.translatable(Util.materialAdjTranslationkey(metal))));
    }
}
