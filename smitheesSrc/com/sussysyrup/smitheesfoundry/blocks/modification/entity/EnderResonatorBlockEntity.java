package com.sussysyrup.smitheesfoundry.blocks.modification.entity;

import com.sussysyrup.smitheesfoundry.api.block.ApiAlloySmelteryRegistry;
import com.sussysyrup.smitheesfoundry.api.recipe.ApiEnderResonatorRegistry;
import com.sussysyrup.smitheesfoundry.api.recipe.EnderResonatorRecipe;
import com.sussysyrup.smitheesfoundry.blocks.alloysmeltery.AlloySmelteryControllerBlock;
import com.sussysyrup.smitheesfoundry.registry.BlocksRegistry;
import com.sussysyrup.smitheesfoundry.screen.EnderResonatorScreenHandler;
import com.sussysyrup.smitheesfoundry.util.InventoryUtil;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.item.InventoryStorage;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableTextContent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class EnderResonatorBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory, SidedInventory {

    public final SingleVariantStorage<FluidVariant> fluidStorage = new SingleVariantStorage<>()
    {

        @Override
        protected FluidVariant getBlankVariant() {
            return FluidVariant.blank();
        }

        @Override
        protected long getCapacity(FluidVariant variant) {
            return FluidConstants.BUCKET * 4;
        }
    };

    public SimpleInventory inventory = new SimpleInventory(1);

    public final InventoryStorage inventoryWrapper = InventoryStorage.of(inventory, null);

    public int currentTicks = 0;
    public int maxTicks = 0;
    public String fluidID = "";
    ItemStack oldStack = ItemStack.EMPTY;

    public EnderResonatorBlockEntity(BlockPos pos, BlockState state) {
        super(BlocksRegistry.ENDER_RESONATOR_ENTITY, pos, state);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        InventoryUtil.writeNbt(nbt, inventory, true);
        nbt.putInt("TICKS", currentTicks);
        nbt.putInt("MAX", maxTicks);

        nbt.put("fluidVariant", fluidStorage.variant.toNbt());
        nbt.putLong("amount", fluidStorage.amount);
        nbt.putString("fluidID", fluidID);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.inventory = new SimpleInventory(1);
        InventoryUtil.readNbt(nbt, inventory);
        currentTicks = nbt.getInt("TICKS");
        maxTicks = nbt.getInt("MAX");

        fluidStorage.variant = FluidVariant.fromNbt(nbt.getCompound("fluidVariant"));
        fluidStorage.amount = nbt.getLong("amount");
        fluidID = nbt.getString("fluidID");

        oldStack = inventory.getStack(0);
    }

    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }



    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(pos);
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("smitheesfoundry.container.ender_resonator");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new EnderResonatorScreenHandler(syncId, inv, inventory);
    }

    @Override
    public int[] getAvailableSlots(Direction side) {
        return new int[0];
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
        return inventory.canInsert(stack);
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return false;
    }

    @Override
    public int size() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return inventory.isEmpty();
    }

    @Override
    public ItemStack getStack(int slot) {
        return inventory.getStack(0);
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        return inventory.removeStack(slot, amount);
    }

    @Override
    public ItemStack removeStack(int slot) {
        return inventory.removeStack(slot);
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        inventory.setStack(slot, stack);
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return inventory.canPlayerUse(player);
    }

    @Override
    public void clear() {
        inventory.clear();
    }

    public static <E extends BlockEntity> void clientTicker(World world, BlockPos blockPos, BlockState blockState, E e) {
    }

    public static <E extends EnderResonatorBlockEntity> void serverTicker(World world, BlockPos blockPos, BlockState blockState, E e) {
        e.resonatorTick();

        if(e.currentTicks > 0)
        {
            blockState = blockState.with(AlloySmelteryControllerBlock.LIT, true);
            world.setBlockState(blockPos, blockState, Block.NOTIFY_ALL);
        }
        else
        {
            blockState = blockState.with(AlloySmelteryControllerBlock.LIT, false);
            world.setBlockState(blockPos, blockState, Block.NOTIFY_ALL);
        }

        world.updateListeners(blockPos, blockState, blockState, Block.NOTIFY_LISTENERS);
    }

    void resonatorTick() {

        if(inventory.getStack(0).isEmpty())
        {
            currentTicks = 0;
            maxTicks = 0;
            return;
        }

        if(!oldStack.equals(inventory.getStack(0)))
        {
            currentTicks = 0;
            maxTicks = 0;
        }

        oldStack = inventory.getStack(0);

        fluidID = "";

        if(fluidStorage.amount >= fluidStorage.getCapacity())
        {
            return;
        }

        if(!ApiAlloySmelteryRegistry.getInstance().getTankBlocks().contains(world.getBlockState(this.getPos().add(0, -1, 0)).getBlock()))
        {
            return;
        }

        @Nullable Storage<FluidVariant> storage = FluidStorage.SIDED.find(world, this.getPos().add(0, -1, 0), Direction.UP);

        if(storage == null)
        {
            return;
        }

        EnderResonatorRecipe recipe = ApiEnderResonatorRegistry.getInstance().getRecipe(Registry.ITEM.getId(inventory.getStack(0).getItem()));

        if (recipe == null) {
            return;
        }

        maxTicks = recipe.ticks();

        if(!Registry.FLUID.get(recipe.fluidID()).equals(fluidStorage.variant.getFluid()) && !fluidStorage.variant.isBlank())
        {
            return;
        }

        boolean noCont = true;

        try(Transaction transaction = Transaction.openOuter())
        {
            for (StorageView<FluidVariant> view : storage.iterable(transaction))
            {
                if(ApiAlloySmelteryRegistry.getInstance().getFuelFluids().containsKey(view.getResource().getFluid()))
                {
                    if(view.getAmount() > 5) {
                        noCont = false;
                        view.extract(view.getResource(), 5, transaction);
                        transaction.commit();
                    }
                }

                break;
            }
        }

        if(noCont)
        {
            return;
        }

        markDirty();

        if(currentTicks == 0 || currentTicks < recipe.ticks())
        {
            fluidID = recipe.fluidID().toString();
            currentTicks++;

            return;
        }
        if(currentTicks >= recipe.ticks())
        {
            try(Transaction transaction = Transaction.openOuter())
            {
                fluidStorage.insert(FluidVariant.of(Registry.FLUID.get(recipe.fluidID())), recipe.ticks() * 100, transaction);

                transaction.commit();
            }

            inventory.getStack(0).split(1);

            currentTicks = 0;
            return;
        }
    }
}
