package cantwe.alldisagree.CWADForge.impl.recipe.fluid;

import cantwe.alldisagree.CWADForge.api.recipe.fluid.FluidRecipeInventory;
import cantwe.alldisagree.CWADForge.api.recipe.fluid.FluidStack;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.util.collection.DefaultedList;

/**
 * Inventory to provide fluid matching for recipes and use as an actual inventory is discouraged
 */
public class SimpleFluidInventory implements FluidRecipeInventory {

    private int size;
    
    public DefaultedList<FluidStack> stacks;

    public SimpleFluidInventory(int size)
    {
        this.size = size;
        stacks = DefaultedList.ofSize(size, FluidStack.EMPTY);
    }

    @Override
    public boolean isEmptyFluid() {
        for (FluidStack fluidStack : this.stacks) {
            if (fluidStack.amount() == 0 || fluidStack.fluid().equals(FluidVariant.blank())) continue;
            return false;
        }
        return true;
    }

    @Override
    public FluidVariant getFluidVariant(int slot) {
        return stacks.get(slot).fluid();
    }

    @Override
    public long getFluidAmount(int slot) {
        return stacks.get(slot).amount();
    }

    @Override
    public void setFluid(FluidVariant fluid, long amount, int slot) {
        stacks.set(slot, new FluidStack(fluid, amount));
    }

    @Override
    public FluidStack getFluidStack(int slot) {
        return stacks.get(slot);
    }

    @Override
    public void setFluidStack(FluidStack fluidStack, int slot) {
        stacks.set(slot, fluidStack);
    }

    @Override
    public int sizeFluid() {
        return size;
    }
}
