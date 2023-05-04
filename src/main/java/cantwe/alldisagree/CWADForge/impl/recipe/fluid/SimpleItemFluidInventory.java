package cantwe.alldisagree.CWADForge.impl.recipe.fluid;

import cantwe.alldisagree.CWADForge.api.recipe.fluid.FluidRecipeInventory;
import cantwe.alldisagree.CWADForge.api.recipe.fluid.FluidStack;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeInputProvider;
import net.minecraft.recipe.RecipeMatcher;
import net.minecraft.util.collection.DefaultedList;

/**
 * Inventory to provide fluid and item matching for recipes and use as an actual inventory is discouraged
 */
public class SimpleItemFluidInventory implements Inventory, RecipeInputProvider, FluidRecipeInventory {

    private int itemSize;
    private int fluidSize;

    public DefaultedList<ItemStack> itemStacks;
    public DefaultedList<FluidStack> fluidStacks;

    public SimpleItemFluidInventory(int itemSize, int fluidSize)
    {
        this.itemSize = itemSize;
        this.fluidSize = fluidSize;
        itemStacks = DefaultedList.ofSize(itemSize, ItemStack.EMPTY);
        fluidStacks = DefaultedList.ofSize(fluidSize, FluidStack.EMPTY);
    }

    @Override
    public FluidVariant getFluidVariant(int slot) {
        return fluidStacks.get(slot).fluid();
    }

    @Override
    public long getFluidAmount(int slot) {
        return fluidStacks.get(slot).amount();
    }

    @Override
    public void setFluid(FluidVariant fluid, long amount, int slot) {
        fluidStacks.set(slot, new FluidStack(fluid, amount));
    }

    @Override
    public FluidStack getFluidStack(int slot) {
        return fluidStacks.get(slot);
    }

    @Override
    public void setFluidStack(FluidStack fluidStack, int slot) {
        fluidStacks.set(slot, fluidStack);
    }

    @Override
    public int sizeFluid() {
        return fluidSize;
    }

    @Override
    public boolean isEmptyFluid() {
        for (FluidStack fluidStack : this.fluidStacks) {
            if (fluidStack.amount() == 0 || fluidStack.fluid().equals(FluidVariant.blank())) continue;
            return false;
        }
        return true;
    }

    @Override
    public int size() {
        return itemSize;
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack itemStack : this.itemStacks) {
            if (itemStack.isEmpty()) continue;
            return false;
        }
        return true;
    }

    @Override
    public ItemStack getStack(int slot) {
        if (slot < 0 || slot >= this.itemStacks.size()) {
            return ItemStack.EMPTY;
        }
        return this.itemStacks.get(slot);
    }

    //UNUSED
    @Override
    public ItemStack removeStack(int slot, int amount) {
        return null;
    }

    //UNUSED
    @Override
    public ItemStack removeStack(int slot) {
        return null;
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        this.itemStacks.set(slot, stack);
        if (!stack.isEmpty() && stack.getCount() > this.getMaxCountPerStack()) {
            stack.setCount(this.getMaxCountPerStack());
        }
    }

    //UNUSED
    @Override
    public void markDirty() {
    }

    //UNUSED
    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return true;
    }

    //UNUSED
    @Override
    public void provideRecipeInputs(RecipeMatcher finder) {
        for (ItemStack itemStack : this.itemStacks) {
            finder.addInput(itemStack);
        }
    }

    //UNUSED
    @Override
    public void clear() {
    }
}
