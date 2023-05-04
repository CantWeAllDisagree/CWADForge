package cantwe.alldisagree.CWADForge.blocks.modification;

import cantwe.alldisagree.CWADForge.blocks.modification.entity.ItemBinBlockEntity;
import cantwe.alldisagree.CWADForge.registry.BlocksRegistry;
import cantwe.alldisagree.CWADForge.Main;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ItemBinBlock extends BlockWithEntity {

    public ItemBinBlock(Settings settings) {
        super(settings);
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new ItemBinBlockEntity(pos, state);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return world.isClient? checkType(type, BlocksRegistry.ITEM_BIN_BLOCK_ENTITY, ItemBinBlockEntity::clientTicker) : checkType(type, BlocksRegistry.ITEM_BIN_BLOCK_ENTITY, ItemBinBlockEntity::serverTicker);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.SUCCESS;
        }
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof ItemBinBlockEntity) {
            player.openHandledScreen((ItemBinBlockEntity)blockEntity);
        }

        return ActionResult.CONSUME;
    }

    @Override
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        BlockEntity be = world.getBlockEntity(pos);
        if(be instanceof ItemBinBlockEntity)
        {
            ItemStack stack = new ItemStack(this.asItem());
            be.setStackNbt(stack);
            return stack;
        }

        return super.getPickStack(world, pos, state);
    }

    @Override
    public List<ItemStack> getDroppedStacks(BlockState state, LootContext.Builder builder) {
        BlockEntity blockEntity = builder.getNullable(LootContextParameters.BLOCK_ENTITY);
        if (blockEntity instanceof ItemBinBlockEntity be) {
            builder = builder.putDrop(new Identifier(Main.MODID, "entity"), (context, consumer) -> {
                ItemStack stack = new ItemStack(this.asItem());
                be.setStackNbt(stack);
                consumer.accept(stack);
            });

        }
        return super.getDroppedStacks(state, builder);
    }
}
