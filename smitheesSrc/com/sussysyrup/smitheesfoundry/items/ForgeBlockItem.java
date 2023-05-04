package com.sussysyrup.smitheesfoundry.items;

import com.sussysyrup.smitheesfoundry.Main;
import com.sussysyrup.smitheesfoundry.util.Util;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableTextContent;

public class ForgeBlockItem extends BlockItem {
    private final String material;
    private final boolean reinforced;

    public ForgeBlockItem(Block block, Settings settings, String material, boolean reinforced) {
        super(block, settings);
        this.material = material;
        this.reinforced = reinforced;
    }

    @Override
    public Text getName(ItemStack stack) {
        if(!reinforced) {
            return Text.translatable("block." + Main.MODID + ".forge_block", Text.translatable(Util.woodTranslationKey(material)));
        }
        else
        {
            return Text.translatable("block." + Main.MODID + ".reinforced_forge_block", Text.translatable(Util.metalTranslationKey(material)));
        }
    }
}
