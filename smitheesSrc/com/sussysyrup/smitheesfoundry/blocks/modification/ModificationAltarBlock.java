package com.sussysyrup.smitheesfoundry.blocks.modification;

import com.sussysyrup.smitheesfoundry.blocks.modification.entity.ModificationAltarBlockEntity;
import com.sussysyrup.smitheesfoundry.registry.BlocksRegistry;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ModificationAltarBlock extends BlockWithEntity {

    private static VoxelShape composeShape(BlockState state) {
        VoxelShape voxelShape = VoxelShapes.cuboid(0, 0, 0, 1, 0.375, 1);

        voxelShape = VoxelShapes.union(voxelShape, VoxelShapes.cuboid(0.1875, 0.375, 0.1875, 0.8125, 0.5, 0.8125));

        return voxelShape;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return composeShape(state);
    }

    public ModificationAltarBlock(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.SUCCESS;
        }

        if(world.getBlockEntity(pos) instanceof ModificationAltarBlockEntity be) {

            if(!player.getStackInHand(hand).isOf(Items.GLOWSTONE_DUST)) {
                if (be.inventory.isEmpty()) {
                    be.inventory.setStack(0, player.getStackInHand(hand).split(1));
                    world.playSound(null, pos, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.BLOCKS, 1f, 1f);
                } else {
                    player.giveItemStack(be.inventory.getStack(0).split(1));
                    world.playSound(null, pos, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.BLOCKS, 1f, 1f);
                }
            }
            else
            {
                be.prepare();

                if(!player.isCreative()) {
                    player.getStackInHand(hand).split(1);
                }

                world.playSound(null, pos, SoundEvents.BLOCK_BEACON_ACTIVATE, SoundCategory.BLOCKS, 1f, 1f);
            }
        }

        return ActionResult.PASS;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ModificationAltarBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return world.isClient ? checkType(type, BlocksRegistry.MODIFICATION_ALTAR_ENTITY, ModificationAltarBlockEntity::clientTicker) : checkType(type, BlocksRegistry.MODIFICATION_ALTAR_ENTITY, ModificationAltarBlockEntity::serverTicker);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.isOf(newState.getBlock())) {
            return;
        }

        if(world.getBlockEntity(pos) instanceof ModificationAltarBlockEntity be)
        {
            Inventory inventory = new SimpleInventory(be.inventory.size());
            for(int i = 0; i < be.inventory.size(); i++) {
                inventory.setStack(i, be.inventory.getStack(i));
            }
            ItemScatterer.spawn(world, pos, inventory);
        }

        super.onStateReplaced(state, world, pos, newState, moved);
    }
}
