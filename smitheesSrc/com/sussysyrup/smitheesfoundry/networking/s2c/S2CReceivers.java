package com.sussysyrup.smitheesfoundry.networking.s2c;

import com.sussysyrup.smitheesfoundry.client.gui.screen.AlloySmelteryInvScreen;
import com.sussysyrup.smitheesfoundry.client.gui.screen.GuideBookScreen;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.LiteralTextContent;

public class S2CReceivers {

    public static void clientInit()
    {
        ClientPlayNetworking.registerGlobalReceiver(S2CConstants.AlloySmelteryInvSync, (client, handler, buf, packetSender) ->
        {
            client.execute(() ->
            {
                ((AlloySmelteryInvScreen) client.currentScreen).getScreenHandler().updateClient();
            });
        });

        ClientPlayNetworking.registerGlobalReceiver(S2CConstants.OpenGuideBook, (client, handler, buf, packetSender) ->
        {
            client.execute(() ->
            {
                MinecraftClient.getInstance().setScreen(new GuideBookScreen(new LiteralTextContent("")));
            });
        });
    }

}
