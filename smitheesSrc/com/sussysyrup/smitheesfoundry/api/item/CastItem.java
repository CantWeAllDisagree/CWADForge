package com.sussysyrup.smitheesfoundry.api.item;

import com.sussysyrup.smitheesfoundry.Main;
import com.sussysyrup.smitheesfoundry.util.TranslationUtil;
import com.sussysyrup.smitheesfoundry.util.Util;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableTextContent;
import net.minecraft.util.Language;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class CastItem extends Item {

    public String castType;

    public CastItem(Settings settings, String castType) {
        super(settings);
        this.castType = castType;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {

        Float value = ApiPartRegistry.getInstance().getPartCost(castType);

        if(value == null)
        {
            value = Float.valueOf(0);
            if(castType.equals("ingot"))
            {
                value = Float.valueOf(1);
            }
        }

        tooltip.add(Text.translatable("part.smitheesfoundry.cost", value));

        super.appendTooltip(stack, world, tooltip, context);
    }

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
