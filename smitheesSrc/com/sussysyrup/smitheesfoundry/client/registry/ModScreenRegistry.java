package com.sussysyrup.smitheesfoundry.client.registry;

import com.sussysyrup.smitheesfoundry.client.gui.screen.*;
import com.sussysyrup.smitheesfoundry.registry.ModScreenHandlerRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;

@Environment(EnvType.CLIENT)
public class ModScreenRegistry {

    public static void clientInit()
    {
        ScreenRegistry.register(ModScreenHandlerRegistry.FORGE_SCREEN_HANDLER, ForgeScreen::new);
        ScreenRegistry.register(ModScreenHandlerRegistry.REINFORCED_FORGE_SCREEN_HANDLER, ReinforcedForgeScreen::new);
        ScreenRegistry.register(ModScreenHandlerRegistry.PARTBENCH_SCREEN_HANDLER, PartBenchScreen::new);
        ScreenRegistry.register(ModScreenHandlerRegistry.REPAIR_ANVIL_SCREEN_HANDLER, RepairAnvilScreen::new);
        ScreenRegistry.register(ModScreenHandlerRegistry.ALLOY_SMELTERY_SCREEN_HANDLER, AlloySmelteryInvScreen::new);
        ScreenRegistry.register(ModScreenHandlerRegistry.ALLOY_SMELTERYFLUID_SCREEN_HANDLER, AlloySmelteryFluidScreen::new);
        ScreenRegistry.register(ModScreenHandlerRegistry.ENDER_RESONATOR_SCREEN_HANDLER, EnderResonatorScreen::new);
        ScreenRegistry.register(ModScreenHandlerRegistry.ITEM_BIN_SCREEN_HANDLER, ItemBinScreen::new);
    }
}
