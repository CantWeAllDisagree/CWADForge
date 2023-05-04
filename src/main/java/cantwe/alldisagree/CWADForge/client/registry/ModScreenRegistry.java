package cantwe.alldisagree.CWADForge.client.registry;

import cantwe.alldisagree.CWADForge.client.gui.screen.*;
import cantwe.alldisagree.CWADForge.registry.ModScreenHandlerRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;

@Environment(EnvType.CLIENT)
public class ModScreenRegistry {

    public static void clientInit()
    {
        ScreenRegistry.register(ModScreenHandlerRegistry.ALLOY_SMELTERY_SCREEN_HANDLER, AlloySmelteryInvScreen::new);
        ScreenRegistry.register(ModScreenHandlerRegistry.ALLOY_SMELTERYFLUID_SCREEN_HANDLER, AlloySmelteryFluidScreen::new);
        ScreenRegistry.register(ModScreenHandlerRegistry.ENDER_RESONATOR_SCREEN_HANDLER, EnderResonatorScreen::new);
        ScreenRegistry.register(ModScreenHandlerRegistry.ITEM_BIN_SCREEN_HANDLER, ItemBinScreen::new);
    }
}
