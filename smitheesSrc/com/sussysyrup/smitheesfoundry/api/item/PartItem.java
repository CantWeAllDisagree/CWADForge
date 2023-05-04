package com.sussysyrup.smitheesfoundry.api.item;

import com.sussysyrup.smitheesfoundry.Main;
import com.sussysyrup.smitheesfoundry.api.trait.TraitContainer;
import com.sussysyrup.smitheesfoundry.api.material.ApiMaterialRegistry;
import com.sussysyrup.smitheesfoundry.api.material.Material;
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

public class PartItem extends Item {

    private String partName;
    private String materialId;

    public PartItem(Settings settings, String partName, String materialId) {
        super(settings);

        this.partName = partName;
        this.materialId = materialId;
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
            if(!hasKey && Main.config.isTranslationMode())
            {
                return Text.translatable(key);
            }
        }
        return Text.translatable("item." + Main.MODID + "." + partName, Text.translatable(Util.materialAdjTranslationkey(materialId)));
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        Material material = ApiMaterialRegistry.getInstance().getMaterial(materialId);

        String category = ApiPartRegistry.getInstance().getPrePartCategory(partName);

        List<TraitContainer> list = material.getTraits(category);

        if(list == null)
        {
            super.appendTooltip(stack, world, tooltip, context);
            return;
        }

        for(TraitContainer trait : list)
        {
            tooltip.add(trait.getTraitTranslation().formatted(trait.getFormatting()));
        }

        tooltip.add(Text.translatable("part.smitheesfoundry.cost", ApiPartRegistry.getInstance().getPartCost(partName)));

        super.appendTooltip(stack, world, tooltip, context);
    }
}
