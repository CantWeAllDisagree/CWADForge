package com.sussysyrup.smitheesfoundry.blocks;

import com.sussysyrup.smitheesfoundry.blocks.entity.ForgeBlockEntity;
import com.sussysyrup.smitheesfoundry.impl.block.ImplVariationRegistry;
import com.sussysyrup.smitheesfoundry.registry.BlocksRegistry;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ForgeBlock extends BlockWithEntity {

    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
    public final boolean reinforced;

    public ForgeBlock(Settings settings, boolean reinforced) {
        super(settings);
        this.reinforced = reinforced;
        this.setDefaultState((BlockState)((BlockState)this.stateManager.getDefaultState()).with(FACING, Direction.NORTH));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        VoxelShape shape = VoxelShapes.cuboid(0.0625, 0.6875f, 0.0625, 0.9375F, 0.875F, 0.9375F);
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.0625, 0, 0.0625, 0.9375F, 0.3125F, 0.9375F));
        shape = VoxelShapes.union(shape, VoxelShapes.cuboid(0.1875F, 0.3125F, 0.1875F, 0.8125F, 0.6875f, 0.8125F));
        return shape;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.SUCCESS;
        }
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof ForgeBlockEntity) {
            player.openHandledScreen((ForgeBlockEntity)blockEntity);
        }

        return ActionResult.CONSUME;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ForgeBlockEntity(pos, state, reinforced);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return world.isClient ? ForgeBlock.checkType(type, ImplVariationRegistry.FORGE_BLOCK_ENTITY, ForgeBlockEntity::clientTicker) : ForgeBlock.checkType(type, ImplVariationRegistry.FORGE_BLOCK_ENTITY, ForgeBlockEntity::serverTicker);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return (BlockState)this.getDefaultState().with(FACING, ctx.getPlayerFacing().getOpposite());
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (state.isOf(newState.getBlock())) {
            return;
        }
        if(world.getBlockEntity(pos) instanceof ForgeBlockEntity be)
        {
            Inventory inventory = new SimpleInventory(be.size());
            for(int i = 0; i < be.size(); i++) {
                inventory.setStack(i, be.getStack(i));
            }
            inventory.removeStack(inventory.size()-1);
            ItemScatterer.spawn(world, pos, inventory);
        }

        super.onStateReplaced(state, world, pos, newState, moved);
    }
}
