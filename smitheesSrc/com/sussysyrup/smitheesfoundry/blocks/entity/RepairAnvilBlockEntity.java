package com.sussysyrup.smitheesfoundry.blocks.entity;

import com.sussysyrup.smitheesfoundry.api.blockentity.InventoryCraftingBlockEntity;
import com.sussysyrup.smitheesfoundry.registry.BlocksRegistry;
import com.sussysyrup.smitheesfoundry.screen.RepairAnvilScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableTextContent;
import net.minecraft.util.math.BlockPos;

public class RepairAnvilBlockEntity extends InventoryCraftingBlockEntity {


    public RepairAnvilBlockEntity(BlockPos blockPos, BlockState blockState, int size) {
        super(BlocksRegistry.REPAIR_ANVIL_BLOCK_ENTITY, blockPos, blockState, size);
    }

    public RepairAnvilBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(BlocksRegistry.REPAIR_ANVIL_BLOCK_ENTITY, blockPos, blockState);
    }

    @Override
    protected Text getContainerName() {
        return Text.translatable("smitheesfoundry.container.repairanvil");
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return new RepairAnvilScreenHandler(syncId, playerInventory, this);
    }
}
