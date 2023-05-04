package com.sussysyrup.smitheesfoundry.blocks.modification.entity;

import com.sussysyrup.smitheesfoundry.registry.BlocksRegistry;
import com.sussysyrup.smitheesfoundry.screen.ItemBinScreenHandler;
import com.sussysyrup.smitheesfoundry.util.InventoryUtil;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.fabric.api.transfer.v1.item.InventoryStorage;
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
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableTextContent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ItemBinBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory, SidedInventory {

    public ItemStack storeStack = ItemStack.EMPTY;
    public int storeAmount = 0;
    public int maxSize = 4096;

    public SimpleInventory inventory = new SimpleInventory(2);

    public final InventoryStorage inventoryWrapper = InventoryStorage.of(inventory, null);

    public ItemBinBlockEntity(BlockPos pos, BlockState state) {
        super(BlocksRegistry.ITEM_BIN_BLOCK_ENTITY, pos, state);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        InventoryUtil.writeNbt(nbt, inventory, true);

        NbtCompound storeCompound = new NbtCompound();
        storeCompound = storeStack.writeNbt(storeCompound);
        nbt.put("storeStack", storeCompound);
        nbt.putInt("storeAmount", storeAmount);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        inventory = new SimpleInventory(2);
        InventoryUtil.readNbt(nbt, inventory);

        storeAmount = nbt.getInt("storeAmount");
        storeStack = ItemStack.fromNbt(nbt.getCompound("storeStack"));
    }

    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }


    public static <E extends BlockEntity> void clientTicker(World world, BlockPos blockPos, BlockState blockState, E e) {
    }

    public static <E extends ItemBinBlockEntity> void serverTicker(World world, BlockPos pos, BlockState blockState, E e) {
        if(!e.inventory.getStack(1).isEmpty())
        {
            if(ItemStack.canCombine(e.inventory.getStack(0), e.inventory.getStack(1))) {
                ItemStack stack = e.inventory.getStack(1);

                e.storeStack = stack.copy();

                if(e.storeAmount == e.maxSize)
                {

                }
                if(e.storeAmount < e.maxSize)
                {
                    int stackExpression = e.maxSize - e.storeAmount;

                    if(stack.getCount() > stackExpression)
                    {
                        e.storeAmount = e.maxSize;
                        stack.split(stackExpression);
                    }
                    else
                    {
                        e.storeAmount += stack.getCount();
                        stack.split(stack.getCount());
                    }
                }
            }
            e.markDirty();
        }
        if(e.inventory.getStack(0).isEmpty() || e.inventory.getStack(0).getCount() < e.inventory.getStack(0).getMaxCount())
        {
            ItemStack outStack = e.inventory.getStack(0);

            if(e.storeAmount > 0)
            {
                int stackExpression = outStack.getMaxCount() - outStack.getCount();

                if(e.storeAmount >= 64)
                {
                    if(!outStack.isEmpty()) {
                        outStack.increment(stackExpression);
                        e.storeAmount -= stackExpression;
                    }
                    else
                    {
                        outStack = e.storeStack;
                        stackExpression = outStack.getMaxCount();

                        outStack.setCount(stackExpression);
                        e.storeAmount -= stackExpression;
                        e.inventory.setStack(0, outStack);
                    }
                }
                else
                {
                    if(e.storeAmount > e.storeStack.getMaxCount())
                    {
                        stackExpression = e.storeStack.getMaxCount();
                    }
                    else
                    {
                        stackExpression = e.storeAmount;
                    }

                    if(!outStack.isEmpty()) {
                        outStack.increment(stackExpression);
                        e.storeAmount -= stackExpression;
                    }
                    else
                    {
                        outStack = e.storeStack;
                        outStack.setCount(stackExpression);
                        e.storeAmount -= stackExpression;
                        e.inventory.setStack(0, outStack);
                    }
                }
            }
            e.markDirty();
        }
        world.updateListeners(pos, blockState, blockState, Block.NOTIFY_LISTENERS);
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("smitheesfoundry.container.item_bin");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        return new ItemBinScreenHandler(syncId, inv, inventory);
    }

    @Override
    public int[] getAvailableSlots(Direction side) {
        int[] ints = {0,1};

        return ints;
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
        if(slot == 1 && ItemStack.canCombine(inventory.getStack(0), stack)) {
            return inventory.canInsert(stack);
        }
        return false;
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        if(slot == 0) {
            return true;
        }
        return false;
    }

    @Override
    public int size() {
        return inventory.size();
    }

    @Override
    public boolean isEmpty() {
        return inventory.isEmpty();
    }

    @Override
    public ItemStack getStack(int slot) {
        return inventory.getStack(slot);
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

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(this.pos);
    }

    /**
     * Returns the total amount stored in the entity
     * @return
     */
    public int getActualAmount()
    {
        return inventory.getStack(0).getCount() + storeAmount;
    }

    public void empty() {
        inventory.clear();
        storeAmount = 0;
        storeStack = ItemStack.EMPTY;
    }
}