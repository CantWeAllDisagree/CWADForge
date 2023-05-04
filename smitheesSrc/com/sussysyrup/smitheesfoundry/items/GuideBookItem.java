package com.sussysyrup.smitheesfoundry.items;

import com.sussysyrup.smitheesfoundry.networking.s2c.S2CConstants;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class GuideBookItem extends Item {

    public GuideBookItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
        ItemStack stack = playerIn.getStackInHand(handIn);

        if(!worldIn.isClient)
        {
            ServerPlayNetworking.send((ServerPlayerEntity) playerIn, S2CConstants.OpenGuideBook, PacketByteBufs.create());
        }

        return new TypedActionResult<>(ActionResult.SUCCESS, stack);
    }
}
