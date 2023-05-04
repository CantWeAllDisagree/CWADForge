package cantwe.alldisagree.CWADForge.registry;

import cantwe.alldisagree.CWADForge.Main;
import cantwe.alldisagree.CWADForge.screen.*;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.util.registry.Registry;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;

public class ModScreenHandlerRegistry {

    public static ScreenHandlerType<AlloySmelteryInvScreenHandler> ALLOY_SMELTERY_SCREEN_HANDLER;
    public static ScreenHandlerType<AlloySmelteryFluidScreenHandler> ALLOY_SMELTERYFLUID_SCREEN_HANDLER;
    public static ScreenHandlerType<EnderResonatorScreenHandler> ENDER_RESONATOR_SCREEN_HANDLER;
    public static ScreenHandlerType<ItemBinScreenHandler> ITEM_BIN_SCREEN_HANDLER;

    public static void main()
    {

        ALLOY_SMELTERY_SCREEN_HANDLER = Registry.register(Registry.SCREEN_HANDLER, new Identifier(Main.MODID, "alloy_smeltery_screen_handler"), new ExtendedScreenHandlerType<>(AlloySmelteryInvScreenHandler::new));
        ALLOY_SMELTERYFLUID_SCREEN_HANDLER = Registry.register(Registry.SCREEN_HANDLER, new Identifier(Main.MODID, "alloy_smelteryfluid_screen_handler"), new ExtendedScreenHandlerType<>(AlloySmelteryFluidScreenHandler::new));
        ENDER_RESONATOR_SCREEN_HANDLER = Registry.register(Registry.SCREEN_HANDLER, new Identifier(Main.MODID, "ender_resonator_screen_handler"), new ExtendedScreenHandlerType<>(EnderResonatorScreenHandler::new));
        ITEM_BIN_SCREEN_HANDLER = Registry.register(Registry.SCREEN_HANDLER, new Identifier(Main.MODID, "item_bin_screen_handler"), new ExtendedScreenHandlerType<>(ItemBinScreenHandler::new));
    }
}
