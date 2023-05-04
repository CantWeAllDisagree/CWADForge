package cantwe.alldisagree.CWADForge.registry;

import cantwe.alldisagree.CWADForge.Main;
import cantwe.alldisagree.CWADForge.api.casting.ApiCastingRegistry;
import cantwe.alldisagree.CWADForge.api.item.CastItem;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.Identifier;

public class ItemsRegistry {
    public static Item COMPOUND_CLAY = new Item(new FabricItemSettings());
    public static Item REINFORCED_BRICK = new Item(new FabricItemSettings());
    public static Item ROSEGOLD_INGOT = new Item((new FabricItemSettings()));
    public static Item ROSEGOLD_NUGGET = new Item((new FabricItemSettings()));
    public static Item NETHERITE_NUGGET = new Item((new FabricItemSettings()));
    public static Item COPPER_NUGGET = new Item((new FabricItemSettings()));


    public static CastItem BLANK_CAST = new CastItem(new FabricItemSettings(), "blank");
    public static CastItem INGOT_CAST = new CastItem(new FabricItemSettings(), "ingot");
    public static CastItem NUGGET_CAST = new CastItem(new FabricItemSettings(), "nugget");

    public static void main()
    {

        register("compound_clay", COMPOUND_CLAY);
        register("reinforced_brick", REINFORCED_BRICK);
        register("rosegold_ingot", ROSEGOLD_INGOT);
        register("rosegold_nugget", ROSEGOLD_NUGGET);
        register("netherite_nugget", NETHERITE_NUGGET);
        register("copper_nugget", COPPER_NUGGET);

        register("blank_cast", BLANK_CAST);
        ApiCastingRegistry.getInstance().addCastItem("blank", BLANK_CAST);

        register("ingot_cast", INGOT_CAST);
        ApiCastingRegistry.getInstance().addCastItem("ingot", INGOT_CAST);

        register("nugget_cast", NUGGET_CAST);
        ApiCastingRegistry.getInstance().addCastItem("nugget", NUGGET_CAST);
    }

    private static void register(String name, Item item)
    {
        Registry.register(Registry.ITEM, new Identifier(Main.MODID, name), item);
    }

}
