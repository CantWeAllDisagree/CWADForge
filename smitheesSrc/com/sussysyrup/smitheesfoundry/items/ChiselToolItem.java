package com.sussysyrup.smitheesfoundry.items;

import com.sussysyrup.smitheesfoundry.api.item.ToolItem;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.TagKey;

//INFO This class gets swapped when Fabric's chisel mod is present
public class ChiselToolItem extends ToolItem {

    public ChiselToolItem(Settings settings, String toolType, TagKey<Block> effectiveBlocks) {
        super(settings, toolType, effectiveBlocks);
    }

    @Override
    public double getAttackDamage(ItemStack stack) {
        return 1;
    }
}
