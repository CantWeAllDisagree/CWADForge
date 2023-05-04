package com.sussysyrup.smitheesfoundry.fluid;

import com.sussysyrup.smitheesfoundry.api.fluid.AbstractResonatorFluid;
import com.sussysyrup.smitheesfoundry.registry.FluidRegistry;
import net.minecraft.block.BlockState;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Properties;

public abstract class CrudeResonatorFluid extends AbstractResonatorFluid {

    @Override
    public Fluid getFlowing() {
        return FluidRegistry.FLOWING_CRUDE_RESONATOR;
    }

    @Override
    public Fluid getStill() {
        return FluidRegistry.STILL_CRUDE_RESONATOR;
    }

    @Override
    public Item getBucketItem() {
        return FluidRegistry.CRUDE_RESONATOR_BUCKET;
    }

    @Override
    protected BlockState toBlockState(FluidState state) {
        return FluidRegistry.CRUDE_RESONATOR.getDefaultState().with(Properties.LEVEL_15, getBlockStateLevel(state));
    }

    public static class Flowing extends CrudeResonatorFluid {
        @Override
        protected void appendProperties(StateManager.Builder<Fluid, FluidState> builder) {
            super.appendProperties(builder);
            builder.add(LEVEL);
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

    public static class Still extends CrudeResonatorFluid {
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
