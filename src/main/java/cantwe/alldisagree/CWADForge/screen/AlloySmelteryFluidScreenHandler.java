package cantwe.alldisagree.CWADForge.screen;

import cantwe.alldisagree.CWADForge.registry.ModScreenHandlerRegistry;
import cantwe.alldisagree.CWADForge.blocks.alloysmeltery.entity.AlloySmelteryControllerBlockEntity;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class AlloySmelteryFluidScreenHandler extends ScreenHandler {

    public AlloySmelteryControllerBlockEntity be;

    public double mouseX;
    public double mouseY;

    public boolean shouldMouse;

    public AlloySmelteryFluidScreenHandler(int syncId, PlayerInventory playerInventory, PacketByteBuf buf) {
        this(syncId, playerInventory, (AlloySmelteryControllerBlockEntity) playerInventory.player.world.getBlockEntity(buf.readBlockPos()));

        be = (AlloySmelteryControllerBlockEntity) playerInventory.player.world.getBlockEntity(buf.readBlockPos());

        shouldMouse = buf.readBoolean();

        if(shouldMouse)
        {
            mouseX = buf.readDouble();
            mouseY = buf.readDouble();
        }
    }

    public AlloySmelteryFluidScreenHandler(int syncId, PlayerInventory playerInventory, AlloySmelteryControllerBlockEntity be) {
        super(ModScreenHandlerRegistry.ALLOY_SMELTERYFLUID_SCREEN_HANDLER, syncId);

         this.be = be;

        int m;
        int l;

        //The player inventory
        for (m = 0; m < 3; ++m) {
            for (l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + m * 9 + 9, 8 + l * 18, 84 + m * 18));
            }
        }
        //The player Hotbar
        for (m = 0; m < 9; ++m) {
            this.addSlot(new Slot(playerInventory, m, 8 + m * 18, 142));
        }
    }

    //0-26 is inventory
    //27-35 is hotbar
    public ItemStack quickMove(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);

        if (slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();

            if (invSlot < 26) {
                if (!this.insertItem(originalStack, 27, 35, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, 26, false)) {
                return ItemStack.EMPTY;
            }

            if (originalStack.isEmpty()) {
                slot.onQuickTransfer(newStack, originalStack);
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }

        return newStack;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return true;
    }

    @Override
    public boolean onButtonClick(PlayerEntity player, int id) {


        if(id == 1)
        {
             be.activeFuel = FluidVariant.of(be.currentFuels.get(0));
        }
        if(id == 2)
        {
            be.activeFuel = FluidVariant.of(be.currentFuels.get(1));
        }
        if(id == 3)
        {
            be.activeFuel = FluidVariant.of(be.currentFuels.get(2));
        }

        return super.onButtonClick(player, id);
    }

    @Override
    public ItemStack transferSlot(PlayerEntity player, int index) {
        return null;
    }
}
