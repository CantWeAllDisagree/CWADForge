package com.sussysyrup.smitheesfoundry.items;

import com.sussysyrup.smitheesfoundry.Main;
import com.sussysyrup.smitheesfoundry.util.TranslationUtil;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableTextContent;
import net.minecraft.util.Language;

public class FluidBucketItem extends BucketItem {

    private final String fluidName;

    public FluidBucketItem(Fluid fluid, Settings settings, String fluidName) {
        super(fluid, settings);
        this.fluidName = fluidName;
    }

    @Override
    public Text getName(ItemStack stack) {
        if(TranslationUtil.fullKeys)
        {
            String key = "item.smitheesfoundry.fluidbucket" + "." + fluidName;

            boolean hasKey = Language.getInstance().hasTranslation(key);

            if(hasKey) {
                return Text.translatable(key);
            }
            if(!hasKey && Main.config.isTranslationMode())
            {
                return Text.translatable(key);
            }
        }
        return Text.translatable("item.smitheesfoundry.fluidbucket", Text.translatable("block.smitheesfoundry." + fluidName));
    }
}
