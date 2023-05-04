package com.sussysyrup.smitheesfoundry.items;

import com.sussysyrup.smitheesfoundry.api.item.ToolItem;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;


public class AxeToolItem extends ToolItem {

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return TypedActionResult.pass(user.getStackInHand(hand));
    }

    public AxeToolItem(Settings settings, String toolType, TagKey<Block> effectiveBlocks) {
        super(settings, toolType, effectiveBlocks);
    }

    @Override
    public float getMiningSpeed(ItemStack stack) {
        return super.getMiningSpeed(stack);
    }

    @Override
    public double getAttackDamage(ItemStack stack) {
        return super.getAttackDamage(stack) + 6;
    }

    @Override
    public double getAttackSpeed(ItemStack stack) {
        return super.getAttackSpeed(stack) -4.2F;
    }
}
