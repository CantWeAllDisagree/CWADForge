package cantwe.alldisagree.CWADForge.registry;

import cantwe.alldisagree.CWADForge.Main;
import cantwe.alldisagree.CWADForge.api.fluid.ApiAlloyRegistry;
import net.minecraft.util.Identifier;

public class AlloyRegistry {

    public static void main()
    {
        ApiAlloyRegistry.getInstance().addAlloy(new Identifier(Main.MODID, "molten_rosegold"), 20,
                new Identifier[]{new Identifier(Main.MODID, "molten_gold"),
                            new Identifier(Main.MODID, "molten_copper")}, new long[]{15,5});
    }

}
