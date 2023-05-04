package cantwe.alldisagree.CWADForge.networking.s2c;

import cantwe.alldisagree.CWADForge.client.gui.screen.AlloySmelteryInvScreen;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

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

    }

}
