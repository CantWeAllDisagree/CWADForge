package com.sussysyrup.smitheesfoundry.screen;

import com.sussysyrup.smitheesfoundry.Main;
import com.sussysyrup.smitheesfoundry.api.item.ApiPartRegistry;
import com.sussysyrup.smitheesfoundry.api.item.ToolItem;
import com.sussysyrup.smitheesfoundry.api.material.ApiMaterialRegistry;
import com.sussysyrup.smitheesfoundry.api.material.MaterialResource;
import com.sussysyrup.smitheesfoundry.items.ChiselToolItem;
import com.sussysyrup.smitheesfoundry.registry.ItemsRegistry;
import com.sussysyrup.smitheesfoundry.registry.ModScreenHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import java.util.List;

public class PartBenchScreenHandler extends ScreenHandler {

    private final Inventory inventory;
    private int outputShift = 0;
    private PlayerEntity player;

    public PartBenchScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(20));
    }

    public PartBenchScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        super(ModScreenHandlerRegistry.PARTBENCH_SCREEN_HANDLER, syncId);

        ForgeScreenHandler.checkSize(inventory, 20);

        this.inventory = inventory;
        this.player = playerInventory.player;

        inventory.onOpen(playerInventory.player);

        //Material
        this.addSlot(new MaterialSlot(inventory, 0, 8, 8));

        //Chisel
        this.addSlot(new ChiselSlot(inventory, 1, 8, 44));

        //Outputs
        int index = 2;

        for(int j = 0; j < 3; j++) {

            for (int i = 0; i < 6; i++) {

                this.addSlot(new ResultSlot(inventory, index, 44 + 18 * i, 8 + 18 * j, player));
                index++;

                if(index > 20)
                {
                    break;
                }
            }

            if(index > 20)
            {
                break;
            }
        }

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

        onContentChanged(inventory);
    }

    @Override
    public boolean canUse(PlayerEntity player) {

        return this.inventory.canPlayerUse(player);
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int invSlot) {
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(invSlot);

        if (slot != null && slot.hasStack()) {
            ItemStack originalStack = slot.getStack();
            newStack = originalStack.copy();

            if (invSlot < this.inventory.size()) {
                if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(originalStack, 0, this.inventory.size(), false)) {
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
    public void onSlotClick(int slotIndex, int button, SlotActionType actionType, PlayerEntity player) {
        super.onSlotClick(slotIndex, button, actionType, player);
        onContentChanged(inventory);
    }

    @Override
    public void onContentChanged(Inventory inventory) {

        MaterialResource materialResource = ApiMaterialRegistry.getInstance().getMaterialResource(inventory.getStack(0).getItem());

        List<String> partNames = ApiPartRegistry.getInstance().getPrePartNames();

        ItemStack stack;

        float cost;

        String part;

        if(materialResource != null && !ApiMaterialRegistry.getInstance().getMaterial(materialResource.materialId()).isMetal())
        {
            for(int i = 2; i < 20; i++)
            {
                if(i - 2 + outputShift < partNames.size())
                {
                    part = partNames.get(i-2);

                    cost = ApiPartRegistry.getInstance().getPartCost(part);

                    if(inventory.getStack(0).getCount() * materialResource.materialValue() >= cost && !inventory.getStack(1).isEmpty()) {

                        if(inventory.getStack(1).getItem() instanceof ChiselToolItem chisel)
                        {
                            ItemStack chiselStack = inventory.getStack(1);
                            int dur = chisel.getDurability(chiselStack);

                            if(dur > 0)
                            {
                                stack = new ItemStack(Registry.ITEM.get(new Identifier(Main.MODID, materialResource.materialId() + "_" + part)));
                            }
                            else
                            {
                                stack = ItemStack.EMPTY;
                            }
                        }
                        else {
                            stack = new ItemStack(Registry.ITEM.get(new Identifier(Main.MODID, materialResource.materialId() + "_" + part)));
                        }

                    }
                    else {
                        stack = ItemStack.EMPTY;
                    }
                }
                else
                {
                    stack = ItemStack.EMPTY;
                }
                inventory.setStack(i, stack);
            }
        }
        else
        {
            for(int i = 2; i < inventory.size(); i++)
            {
                inventory.setStack(i, ItemStack.EMPTY);
            }
        }

        super.onContentChanged(inventory);
    }

    @Override
    public boolean onButtonClick(PlayerEntity player, int id) {

        if(id == 0)
        {
            if(!(outputShift == 0))
            {
                outputShift -= 6;
            }
        }
        if(id == 1)
        {
            List<String> partNames = ApiPartRegistry.getInstance().getPrePartNames();

            int outputShiftPost = 18 + outputShift + 6;

            if(outputShiftPost < partNames.size() - 2)
            {
                outputShift +=6;
            }
        }

        onContentChanged(inventory);
        return super.onButtonClick(player, id);
    }

    private static class ResultSlot extends Slot
    {

        private final PlayerEntity player;

        public ResultSlot(Inventory inventory, int index, int x, int y, PlayerEntity player) {
            super(inventory, index, x, y);
            this.player = player;
        }

        @Override
        public boolean canInsert(ItemStack stack) {
            return false;
        }

        @Override
        public void onTakeItem(PlayerEntity player, ItemStack stack) {
            super.onTakeItem(player, stack);

            if(player.world.isClient)
            {
                return;
            }

            ItemStack stack1 = inventory.getStack(0);

            MaterialResource materialResource = ApiMaterialRegistry.getInstance().getMaterialResource(inventory.getStack(0).getItem());

            String[] strings = stack.getItem().toString().split("_");

            float materialValue = materialResource.materialValue();

            float partCost = ApiPartRegistry.getInstance().getPartCost(strings[1]);

            float actualCost = (partCost) / (materialValue);

            if(!player.world.isClient) {
                stack1.split(((int) Math.ceil(actualCost)));

                stack1 = inventory.getStack(1);

                if(stack1.getItem().equals(ItemsRegistry.CRUDE_CHISEL)) {

                    stack1.damage(1, player.getRandom(), (ServerPlayerEntity) player);

                    if (stack1.getDamage() == stack1.getMaxDamage()) {
                        stack1.split(1);
                    }
                }
                else if(stack1.getItem().getClass().isInstance(ItemsRegistry.FORGE_CHISEL))
                {
                    ToolItem item = ((ToolItem) stack1.getItem());
                    item.setDurability(stack1, item.getDurability(stack1) - 1);
                }
            }
        }

        @Override
        public void onQuickTransfer(ItemStack newItem, ItemStack original) {

            if(player.world.isClient)
            {
                return;
            }

            ItemStack stack1 = inventory.getStack(0);

            MaterialResource materialResource = ApiMaterialRegistry.getInstance().getMaterialResource(inventory.getStack(0).getItem());

            String[] strings = newItem.getItem().toString().split("_");

            float materialValue = materialResource.materialValue();

            float partCost = ApiPartRegistry.getInstance().getPartCost(strings[1]);

            float actualCost = (partCost) / (materialValue);

            stack1.split(((int) Math.ceil(actualCost)));

            stack1 = inventory.getStack(1);

            if(stack1.getItem().equals(ItemsRegistry.CRUDE_CHISEL)) {

                stack1.damage(1, player.getRandom(), (ServerPlayerEntity) player);

                if (stack1.getDamage() == stack1.getMaxDamage()) {
                    stack1.split(1);
                }
            }
            else if(stack1.getItem().getClass().isInstance(ItemsRegistry.FORGE_CHISEL))
            {
                ToolItem item = ((ToolItem) stack1.getItem());
                item.setDurability(stack1, item.getDurability(stack1) - 1);
            }

            super.onQuickTransfer(newItem, original);
        }
    }

    private static class ChiselSlot extends Slot
    {

        public ChiselSlot(Inventory inventory, int index, int x, int y) {
            super(inventory, index, x, y);
        }

        @Override
        public boolean canInsert(ItemStack stack) {
            if(stack.getItem().equals(ItemsRegistry.CRUDE_CHISEL) || stack.getItem().equals(ItemsRegistry.FORGE_CHISEL))
            {
                return true;
            }
            else
            {
                return false;
            }
        }
    }

    private static class MaterialSlot extends Slot
    {

        public MaterialSlot(Inventory inventory, int index, int x, int y) {
            super(inventory, index, x, y);
        }

        @Override
        public boolean canInsert(ItemStack stack) {
            if(!(stack.getItem().equals(ItemsRegistry.CRUDE_CHISEL) || stack.getItem().equals(ItemsRegistry.FORGE_CHISEL)))
            {
                return true;
            }
            else
            {
                return false;
            }
        }
    }
}
