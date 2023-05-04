package com.sussysyrup.smitheesfoundry.api.modification;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IActiveModification {

    void activeInventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected);

    void activePostHit(ItemStack stack, LivingEntity target, LivingEntity attacker);

    void activePostMine(ItemStack stack, World world, ItemStack stack1, BlockPos pos, LivingEntity miner);

    void use(ItemStack stackInHand, World world, PlayerEntity playerEntity);
}
