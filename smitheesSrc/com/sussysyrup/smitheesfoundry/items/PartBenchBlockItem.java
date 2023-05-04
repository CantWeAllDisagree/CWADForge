package com.sussysyrup.smitheesfoundry.items;

import com.sussysyrup.smitheesfoundry.Main;
import com.sussysyrup.smitheesfoundry.util.Util;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableTextContent;

public class PartBenchBlockItem extends BlockItem {
    private final String wood;

    public PartBenchBlockItem(Block block, Settings settings, String wood) {
        super(block, settings);
        this.wood = wood;
    }

    @Override
    public Text getName(ItemStack stack) {
        return Text.translatable("block." + Main.MODID + ".part_bench_block", Text.translatable(Util.woodTranslationKey(wood)));
    }
}
