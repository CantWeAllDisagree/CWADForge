package cantwe.alldisagree.CWADForge.blocks.alloysmeltery.entity;

import cantwe.alldisagree.CWADForge.registry.BlocksRegistry;
import cantwe.alldisagree.CWADForge.screen.AlloySmelteryInvScreenHandler;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.fabric.api.transfer.v1.item.InventoryStorage;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class AlloySmelteryHatchBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory, ISlave, SidedInventory {

    public boolean valid = false;
    public AlloySmelteryControllerBlockEntity master;
    public BlockPos masterPos = new BlockPos(0, 0, 0);

    public AlloySmelteryHatchBlockEntity(BlockPos pos, BlockState state) {
        super(BlocksRegistry.ALLOY_SMELTERY_HATCH_BLOCK_ENTITY, pos, state);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        nbt.putLong("MASTER", masterPos.asLong());
        nbt.putBoolean("VALID", valid);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        masterPos = BlockPos.fromLong(nbt.getLong("MASTER"));
        valid = nbt.getBoolean("VALID");
    }
    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeInt(master.itemInventory.size());
        buf.writeBlockPos(masterPos);
        buf.writeBlockPos(masterPos);
        buf.writeBoolean(false);
    }

    @Override
    public Text getDisplayName() {
        return Text.translatable("smitheesfoundry.container.alloysmeltery");
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory inv, PlayerEntity player) {
        if(valid) {
            return new AlloySmelteryInvScreenHandler(syncId, inv, master.itemInventory, master) {
                @Override
                public ItemStack transferSlot(PlayerEntity player, int index) {
                    return null;
                }
            };
        }
        return null;
    }

    @Override
    public void addMaster(BlockPos pos) {
        if(world.getBlockEntity(pos) instanceof AlloySmelteryControllerBlockEntity be)
        {
            valid = true;
            master = be;
            masterPos = master.getPos();
            markDirty();
        }
    }

    @Override
    public void removeMaster() {
        master = null;
        valid = false;
        masterPos = new BlockPos(0, 0, 0);
        markDirty();
    }

    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }

    public static <E extends AlloySmelteryHatchBlockEntity> void serverTicker(World world, BlockPos pos, BlockState blockState, E e) {
        if(e.valid)
        {
            e.master = ((AlloySmelteryControllerBlockEntity) world.getBlockEntity(e.masterPos));
        }
        if(e.master == null)
        {
            e.removeMaster();
        }
        world.updateListeners(pos, blockState, blockState, Block.NOTIFY_LISTENERS);
    }

    public static <E extends AlloySmelteryHatchBlockEntity> void clientTicker(World world, BlockPos pos, BlockState blockState, E e) {
        if(e.valid)
        {
            e.master = ((AlloySmelteryControllerBlockEntity) world.getBlockEntity(e.masterPos));
        }
        else
        {
            e.master = null;
        }
    }

    @Override
    public int[] getAvailableSlots(Direction side) {
        if(master == null)
        {
            int[] empty = {};
            return empty;
        }

        List<Integer> ints = new ArrayList<>();
        for(int i = 0; i < master.itemInventory.size(); i++)
        {
            ints.add(i);
        }
        return ints.stream().mapToInt(Integer::intValue).toArray();
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction dir) {
        if(master == null)
        {
            return false;
        }
        if(master.itemInventory.getStack(slot).isEmpty()) {
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction dir) {
        return false;
    }

    @Override
    public int size() {
        if(master == null)
        {
            return 0;
        }
        return master.itemInventory.size();
    }

    @Override
    public boolean isEmpty() {
        if(master == null)
        {
            return false;
        }
        return master.itemInventory.isEmpty();
    }

    @Override
    public ItemStack getStack(int slot) {
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeStack(int slot) {
        return ItemStack.EMPTY;
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        if(master == null)
        {
            return;
        }
        master.itemInventory.setStack(slot, stack);
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        if(master == null)
        {
            return false;
        }
        return true;
    }

    @Override
    public void clear() {
        if(master == null)
        {
            return;
        }
        master.itemInventory.clear();
    }

    public InventoryStorage inventoryWrapper()
    {
        if(master == null)
        {
            return null;
        }

        return InventoryStorage.of(master.itemInventory, null);
    }
}
