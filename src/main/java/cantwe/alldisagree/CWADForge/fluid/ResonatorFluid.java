package cantwe.alldisagree.CWADForge.fluid;

import cantwe.alldisagree.CWADForge.registry.FluidRegistry;
import cantwe.alldisagree.CWADForge.api.fluid.AbstractResonatorFluid;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;

public abstract class ResonatorFluid extends AbstractResonatorFluid {

    @Override
    public Fluid getFlowing() {
        return FluidRegistry.FLOWING_RESONATOR;
    }

    @Override
    public Fluid getStill() {
        return FluidRegistry.STILL_RESONATOR;
    }

    @Override
    public Item getBucketItem() {
        return FluidRegistry.RESONATOR_BUCKET;
    }

    @Override
    protected BlockState toBlockState(FluidState state) {
        return FluidRegistry.RESONATOR.getDefaultState().with(Properties.LEVEL_15, getBlockStateLevel(state));
    }

    public static class Flowing extends ResonatorFluid {
        @Override
        protected void appendProperties(StateManager.Builder<Fluid, FluidState> builder) {
            super.appendProperties(builder);
            builder.add(LEVEL);
        }

        @Override
        protected boolean isInfinite() {
            return false;
        }

        @Override
        public int getLevel(FluidState fluidState) {
            return fluidState.get(LEVEL);
        }

        @Override
        public boolean isStill(FluidState fluidState) {
            return false;
        }
    }

    public static class Still extends ResonatorFluid {
        @Override
        protected boolean isInfinite() {
            return false;
        }

        @Override
        public int getLevel(FluidState fluidState) {
            return 8;
        }

        @Override
        public boolean isStill(FluidState fluidState) {
            return true;
        }
    }
}
