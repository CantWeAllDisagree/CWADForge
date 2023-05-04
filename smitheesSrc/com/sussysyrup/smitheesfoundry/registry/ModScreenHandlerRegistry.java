package com.sussysyrup.smitheesfoundry.registry;

import com.sussysyrup.smitheesfoundry.Main;
import com.sussysyrup.smitheesfoundry.screen.*;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ModScreenHandlerRegistry {

    public static ScreenHandlerType<ReinforcedForgeScreenHandler> REINFORCED_FORGE_SCREEN_HANDLER;
    public static ScreenHandlerType<ForgeScreenHandler> FORGE_SCREEN_HANDLER;
    public static ScreenHandlerType<PartBenchScreenHandler> PARTBENCH_SCREEN_HANDLER;
    public static ScreenHandlerType<RepairAnvilScreenHandler> REPAIR_ANVIL_SCREEN_HANDLER;
    public static ScreenHandlerType<AlloySmelteryInvScreenHandler> ALLOY_SMELTERY_SCREEN_HANDLER;
    public static ScreenHandlerType<AlloySmelteryFluidScreenHandler> ALLOY_SMELTERYFLUID_SCREEN_HANDLER;
    public static ScreenHandlerType<EnderResonatorScreenHandler> ENDER_RESONATOR_SCREEN_HANDLER;
    public static ScreenHandlerType<ItemBinScreenHandler> ITEM_BIN_SCREEN_HANDLER;

    public static void main()
    {
        FORGE_SCREEN_HANDLER = Registry.register(Registry.SCREEN_HANDLER, new Identifier(Main.MODID, "forge_screen_handler"), new ScreenHandlerType<ForgeScreenHandler>(ForgeScreenHandler::new));
        REINFORCED_FORGE_SCREEN_HANDLER = Registry.register(Registry.SCREEN_HANDLER, new Identifier(Main.MODID, "reinforced_forge_screen_handler"), new ScreenHandlerType<ReinforcedForgeScreenHandler>(ReinforcedForgeScreenHandler::new));
        PARTBENCH_SCREEN_HANDLER = Registry.register(Registry.SCREEN_HANDLER, new Identifier(Main.MODID, "partbench_screen_handler"), new ScreenHandlerType<PartBenchScreenHandler>(PartBenchScreenHandler::new));
        REPAIR_ANVIL_SCREEN_HANDLER = Registry.register(Registry.SCREEN_HANDLER, new Identifier(Main.MODID, "repair_anvil_screen_handler"), new ScreenHandlerType<RepairAnvilScreenHandler>(RepairAnvilScreenHandler::new));

        ALLOY_SMELTERY_SCREEN_HANDLER = Registry.register(Registry.SCREEN_HANDLER, new Identifier(Main.MODID, "alloy_smeltery_screen_handler"), new ExtendedScreenHandlerType<>(AlloySmelteryInvScreenHandler::new));
        ALLOY_SMELTERYFLUID_SCREEN_HANDLER = Registry.register(Registry.SCREEN_HANDLER, new Identifier(Main.MODID, "alloy_smelteryfluid_screen_handler"), new ExtendedScreenHandlerType<>(AlloySmelteryFluidScreenHandler::new));
        ENDER_RESONATOR_SCREEN_HANDLER = Registry.register(Registry.SCREEN_HANDLER, new Identifier(Main.MODID, "ender_resonator_screen_handler"), new ExtendedScreenHandlerType<>(EnderResonatorScreenHandler::new));
        ITEM_BIN_SCREEN_HANDLER = Registry.register(Registry.SCREEN_HANDLER, new Identifier(Main.MODID, "item_bin_screen_handler"), new ExtendedScreenHandlerType<>(ItemBinScreenHandler::new));
    }
}
