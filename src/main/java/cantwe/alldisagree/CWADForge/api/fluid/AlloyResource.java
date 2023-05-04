package cantwe.alldisagree.CWADForge.api.fluid;

import net.minecraft.fluid.Fluid;

public record AlloyResource(Fluid keyFluid, long keyFluidAmount, AlloyResource alloyResourceOut, Fluid fluidOut, long fluidOutAmount) {
}
