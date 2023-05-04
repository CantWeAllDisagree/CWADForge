package com.sussysyrup.smitheesfoundry.items;

import com.sussysyrup.smitheesfoundry.api.item.ToolItem;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.HoeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registry;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;


public class HoeToolItem extends ToolItem {

    HoeItem hoe;

    public HoeToolItem(Settings settings, String toolType, TagKey<Block> effectiveBlocks) {
        super(settings, toolType, effectiveBlocks);
        hoe = (HoeItem) Registry.ITEM.get(new Identifier("wooden_hoe"));
    }

    @Override
    public float getMiningSpeed(ItemStack stack) {
        return super.getMiningSpeed(stack);
    }

    @Override
    public double getAttackDamage(ItemStack stack) {
        return super.getAttackDamage(stack);
    }

    @Override
    public double getAttackSpeed(ItemStack stack) {
        return super.getAttackSpeed(stack) -4F;
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {

        NbtCompound nbt = context.getStack().getNbt();

        if(nbt == null)
        {
            return ActionResult.FAIL;
        }

        int dur = getDurability(context.getStack());

        ActionResult result = ActionResult.FAIL;

        if(!(dur <= 0)) {
            result = hoe.useOnBlock(context);
        }

        World world = context.getWorld();

        if(!world.isClient) {
            if (result.equals(ActionResult.CONSUME)) {

                if (dur > 0) {
                    nbt.putInt(DURABILITY_KEY, dur - 1);

                    if (dur == 1) {
                        world.playSound(null, context.getPlayer().getBlockPos(), SoundEvents.ENTITY_ITEM_BREAK, SoundCategory.PLAYERS, 0.8f, 0.8f + world.random.nextFloat() * 0.4f);
                    }
                }

            }
        }

        return result;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return TypedActionResult.pass(user.getStackInHand(hand));
    }
}
