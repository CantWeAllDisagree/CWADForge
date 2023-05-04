package cantwe.alldisagree.CWADForge.registry;

import cantwe.alldisagree.CWADForge.Main;
import cantwe.alldisagree.CWADForge.api.fluid.ApiMoltenFluidRegistry;
import cantwe.alldisagree.CWADForge.api.fluid.FluidProperties;
import cantwe.alldisagree.CWADForge.fluid.CrudeResonatorFluid;
import cantwe.alldisagree.CWADForge.fluid.ResonatorFluid;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.Identifier;

public class FluidRegistry {

    public static void main()
    {
        ApiMoltenFluidRegistry.getInstance().registerCreateFluid("molten_iron", new FluidProperties("molten_iron", "iron", 300));
        ApiMoltenFluidRegistry.getInstance().registerCreateFluid("molten_copper", new FluidProperties("molten_copper", "copper", 150));
        ApiMoltenFluidRegistry.getInstance().registerCreateFluid("molten_netherite", new FluidProperties("molten_netherite","netherite", 900));
        ApiMoltenFluidRegistry.getInstance().registerCreateFluid("molten_gold", new FluidProperties("molten_gold",null, 450));
        ApiMoltenFluidRegistry.getInstance().registerCreateFluid("molten_rosegold", new FluidProperties("molten_rosegold","rosegold", 300));

        normalFluids();
    }

    public static FlowableFluid STILL_CRUDE_RESONATOR;
    public static FlowableFluid FLOWING_CRUDE_RESONATOR;
    public static Item CRUDE_RESONATOR_BUCKET;
    public static Block CRUDE_RESONATOR;

    public static FlowableFluid STILL_RESONATOR;
    public static FlowableFluid FLOWING_RESONATOR;
    public static Item RESONATOR_BUCKET;
    public static Block RESONATOR;
    private static void normalFluids()
    {
        STILL_CRUDE_RESONATOR = Registry.register(Registry.FLUID, new Identifier(Main.MODID, "crude_resonator"), new CrudeResonatorFluid.Still());
        FLOWING_CRUDE_RESONATOR = Registry.register(Registry.FLUID, new Identifier(Main.MODID, "flowing_crude_resonator"), new CrudeResonatorFluid.Flowing());
        CRUDE_RESONATOR_BUCKET = Registry.register(Registry.ITEM, new Identifier(Main.MODID, "crude_resonator_bucket"),
                new BucketItem(STILL_CRUDE_RESONATOR, new Item.Settings().recipeRemainder(Items.BUCKET).maxCount(1)));
        CRUDE_RESONATOR = Registry.register(Registry.BLOCK, new Identifier(Main.MODID, "crude_resonator"), new FluidBlock(STILL_CRUDE_RESONATOR, FabricBlockSettings.copy(Blocks.WATER)){});

        STILL_RESONATOR = Registry.register(Registry.FLUID, new Identifier(Main.MODID, "resonator"), new ResonatorFluid.Still());
        FLOWING_RESONATOR = Registry.register(Registry.FLUID, new Identifier(Main.MODID, "flowing_resonator"), new ResonatorFluid.Flowing());
        RESONATOR_BUCKET = Registry.register(Registry.ITEM, new Identifier(Main.MODID, "resonator_bucket"),
                new BucketItem(STILL_RESONATOR, new Item.Settings().recipeRemainder(Items.BUCKET).maxCount(1)));
        RESONATOR = Registry.register(Registry.BLOCK, new Identifier(Main.MODID, "resonator"), new FluidBlock(STILL_RESONATOR, FabricBlockSettings.copy(Blocks.WATER)){});

        }
    }

