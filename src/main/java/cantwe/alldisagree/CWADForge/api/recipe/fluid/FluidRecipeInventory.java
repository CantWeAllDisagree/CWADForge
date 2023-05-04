package cantwe.alldisagree.CWADForge.api.recipe.fluid;

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;

/**
 * This interface is designed to provide fluid matching for recipes and use as an actual inventory is discouraged
 * Furthermore, given that this will be used with fluids, this will not be designed for UI only crafting
 */
public interface FluidRecipeInventory {

    /**
     * The amount of fluid instances that the inventory contains
     * @return
     */
    int sizeFluid();

    boolean isEmptyFluid();

    /**
     * Returns the fluid variant found at a certain slot
     * @param slot
     * @return
     */
    FluidVariant getFluidVariant(int slot);

    /**
     * Returns the amount of fluid found at a certain slot
     * @param slot
     * @return
     */
    long getFluidAmount(int slot);

    /**
     * Sets a fluid to a certain slot
     * @param fluid
     * @param amount
     */
    void setFluid(FluidVariant fluid, long amount, int slot);

    /**
     * Returns a FluidStack instance containing a FluidVariant and Amount
     * Normally this should not be used unless you are working with serializing and deserializing recipes
     * @param slot
     * @return
     */
    FluidStack getFluidStack(int slot);

    /**
     * Sets a FluidStack in a certain slot
     * Normally this should not be used unless you are working with serializing and deserializing recipes
     * @param slot
     */
    void setFluidStack(FluidStack fluidStack, int slot);
}
