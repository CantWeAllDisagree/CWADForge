package com.sussysyrup.smitheesfoundry.blocks.entity;

import com.sussysyrup.smitheesfoundry.api.blockentity.InventoryCraftingBlockEntity;
import com.sussysyrup.smitheesfoundry.impl.block.ImplVariationRegistry;
import com.sussysyrup.smitheesfoundry.screen.ForgeScreenHandler;
import com.sussysyrup.smitheesfoundry.screen.ReinforcedForgeScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableTextContent;
import net.minecraft.util.math.BlockPos;

public class ForgeBlockEntity extends InventoryCraftingBlockEntity {

    private boolean reinforced;

    public ForgeBlockEntity(BlockPos blockPos, BlockState blockState, boolean reinforced) {
        super(ImplVariationRegistry.FORGE_BLOCK_ENTITY, blockPos, blockState, reinforced?6:4);
        this.reinforced = reinforced;
    }

    public ForgeBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ImplVariationRegistry.FORGE_BLOCK_ENTITY, blockPos, blockState);
    }

    @Override
    protected Text getContainerName() {
        return Text.translatable("smitheesfoundry.container.forge");
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        
        if(size > 4) {
            return new ReinforcedForgeScreenHandler(syncId, playerInventory, this);
        }
        else
        {
            return new ForgeScreenHandler(syncId, playerInventory, this);
        }
    }

}
