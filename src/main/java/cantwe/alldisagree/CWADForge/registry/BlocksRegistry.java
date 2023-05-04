package cantwe.alldisagree.CWADForge.registry;

import cantwe.alldisagree.CWADForge.Main;
import cantwe.alldisagree.CWADForge.api.block.ApiAlloySmelteryRegistry;
import cantwe.alldisagree.CWADForge.blocks.alloysmeltery.*;
import cantwe.alldisagree.CWADForge.blocks.alloysmeltery.entity.*;
import cantwe.alldisagree.CWADForge.blocks.modification.EnderResonatorBlock;
import cantwe.alldisagree.CWADForge.blocks.modification.ItemBinBlock;
import cantwe.alldisagree.CWADForge.blocks.modification.entity.EnderResonatorBlockEntity;
import cantwe.alldisagree.CWADForge.blocks.modification.entity.ItemBinBlockEntity;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.BlockItem;
import net.minecraft.util.registry.Registry;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;

import java.util.function.ToIntFunction;

public class BlocksRegistry {

    public static Block ALLOY_SMELTERY_CONTROLLER = new AlloySmelteryControllerBlock(FabricBlockSettings.of(Material.STONE).requiresTool().strength(4.0F).luminance(createLightLevelFromLitBlockState(13)));
    public static Block ALLOY_SMELTERY_DRAIN = new AlloySmelteryDrainBlock(FabricBlockSettings.of(Material.STONE).requiresTool().strength(4.0F));
    public static Block ALLOY_SMELTERY_FAUCET = new AlloySmelteryFaucetBlock(FabricBlockSettings.of(Material.STONE).requiresTool().strength(4.0F));
    public static Block TANK_BLOCK = new TankBlock(FabricBlockSettings.of(Material.STONE).requiresTool().strength(4.0F));
    public static Block CASTING_TABLE_BLOCK = new CastingTableBlock(FabricBlockSettings.of(Material.STONE).requiresTool().strength(4.0F));
    public static Block CASTING_BASIN_BLOCK = new CastingBasinBlock(FabricBlockSettings.of(Material.STONE).requiresTool().strength(4.0F));
    public static Block ALLOY_SMELTERY_HATCH = new AlloySmelteryHatchBlock(FabricBlockSettings.of(Material.STONE).requiresTool().strength(4.0F));
    public static Block ENDER_RESONATOR = new EnderResonatorBlock(FabricBlockSettings.of(Material.STONE).requiresTool().strength(6.0F, 20.0F));
    public static Block ITEM_BIN_BLOCK = new ItemBinBlock(FabricBlockSettings.of(Material.STONE).requiresTool().strength(4.0F));

    public static BlockEntityType<AlloySmelteryControllerBlockEntity> ALLOY_SMELTERY_CONTROLLER_BLOCK_ENTITY;
    public static BlockEntityType<AlloySmelteryDrainBlockEntity> ALLOY_SMELTERY_DRAIN_BLOCK_ENTITY;
    public static BlockEntityType<AlloySmelteryFaucetBlockEntity> ALLOY_SMELTERY_FAUCET_BLOCK_ENTITY;
    public static BlockEntityType<TankBlockEntity> TANK_BLOCK_ENTITY;
    public static BlockEntityType<CastingTableBlockEntity> CASTING_TABLE_ENTITY;
    public static BlockEntityType<CastingBasinBlockEntity> CASTING_BASIN_ENTITY;
    public static BlockEntityType<AlloySmelteryHatchBlockEntity> ALLOY_SMELTERY_HATCH_BLOCK_ENTITY;
    public static BlockEntityType<EnderResonatorBlockEntity> ENDER_RESONATOR_ENTITY;
    public static BlockEntityType<ItemBinBlockEntity> ITEM_BIN_BLOCK_ENTITY;

    public static Block REINFORCED_BRICKS = new Block(FabricBlockSettings.of(Material.STONE).strength(4.0f).requiresTool());
    public static Block ROSEGOLD_BLOCK = new Block(FabricBlockSettings.of(Material.METAL).requiresTool().strength(5.0F));

    public static void main()
    {
        register(ALLOY_SMELTERY_CONTROLLER, "alloy_smeltery_controller");
        register(ALLOY_SMELTERY_DRAIN, "alloy_smeltery_drain");
        register(ALLOY_SMELTERY_FAUCET, "alloy_smeltery_faucet");
        register(TANK_BLOCK, "tank_block");
        register(CASTING_TABLE_BLOCK, "casting_table_block");
        register(CASTING_BASIN_BLOCK, "casting_basin_block");
        register(ALLOY_SMELTERY_HATCH, "alloy_smeltery_hatch");
        register(ENDER_RESONATOR, "ender_resonator");
        register(ITEM_BIN_BLOCK, "item_bin_block");

        ALLOY_SMELTERY_CONTROLLER_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(Main.MODID, "alloy_smeltery_controller_block"), FabricBlockEntityTypeBuilder.create(AlloySmelteryControllerBlockEntity::new, ALLOY_SMELTERY_CONTROLLER).build());
        ALLOY_SMELTERY_DRAIN_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(Main.MODID, "alloy_smeltery_drain"), FabricBlockEntityTypeBuilder.create(AlloySmelteryDrainBlockEntity::new, ALLOY_SMELTERY_DRAIN).build());
        ALLOY_SMELTERY_FAUCET_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(Main.MODID, "alloy_smeltery_faucet"), FabricBlockEntityTypeBuilder.create(AlloySmelteryFaucetBlockEntity::new, ALLOY_SMELTERY_FAUCET).build());
        TANK_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(Main.MODID, "tank"), FabricBlockEntityTypeBuilder.create(TankBlockEntity::new, TANK_BLOCK).build());
        CASTING_TABLE_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(Main.MODID, "casting_table"), FabricBlockEntityTypeBuilder.create(CastingTableBlockEntity::new, CASTING_TABLE_BLOCK).build());
        CASTING_BASIN_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(Main.MODID, "casting_basin"), FabricBlockEntityTypeBuilder.create(CastingBasinBlockEntity::new, CASTING_BASIN_BLOCK).build());
        ALLOY_SMELTERY_HATCH_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(Main.MODID, "alloy_smeltery_hatch"), FabricBlockEntityTypeBuilder.create(AlloySmelteryHatchBlockEntity::new, ALLOY_SMELTERY_HATCH).build());
        ENDER_RESONATOR_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(Main.MODID, "ender_resonator"), FabricBlockEntityTypeBuilder.create(EnderResonatorBlockEntity::new, ENDER_RESONATOR).build());
        ITEM_BIN_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(Main.MODID, "item_bin"), FabricBlockEntityTypeBuilder.create(ItemBinBlockEntity::new, ITEM_BIN_BLOCK).build());

        register(REINFORCED_BRICKS, "reinforced_bricks");
        register(ROSEGOLD_BLOCK, "rosegold_block");

        ApiAlloySmelteryRegistry.getInstance().addStructureBlock(REINFORCED_BRICKS);
        ApiAlloySmelteryRegistry.getInstance().addStructureBlock(ALLOY_SMELTERY_CONTROLLER);
        ApiAlloySmelteryRegistry.getInstance().addFunctionalBlock(ALLOY_SMELTERY_DRAIN);
        ApiAlloySmelteryRegistry.getInstance().addFunctionalBlock(ALLOY_SMELTERY_HATCH);
        ApiAlloySmelteryRegistry.getInstance().addTankBlock(TANK_BLOCK);
        ApiAlloySmelteryRegistry.getInstance().addFuelFluid(Fluids.LAVA, 1);
        ApiAlloySmelteryRegistry.getInstance().addFuelFluid(FluidRegistry.STILL_RESONATOR, 2);

        FluidStorage.SIDED.registerForBlockEntity((entity, direction) -> {
            if(entity.master != null) {
                return entity.master.fluidStorage;
            }
            return null;
        }, ALLOY_SMELTERY_DRAIN_BLOCK_ENTITY);
        FluidStorage.SIDED.registerForBlockEntity((entity, direction) -> entity.fluidStorage, TANK_BLOCK_ENTITY);
        FluidStorage.SIDED.registerForBlockEntity(((blockEntity, direction) -> {
            if(direction.equals(Direction.UP))
            {
                return blockEntity.fluidStorage;
            }
            return null;
        }), CASTING_TABLE_ENTITY);
        FluidStorage.SIDED.registerForBlockEntity(((blockEntity, direction) -> {
            if(direction.equals(Direction.UP))
            {
                return blockEntity.fluidStorage;
            }
            return null;
        }), CASTING_BASIN_ENTITY);

        ItemStorage.SIDED.registerForBlockEntity(((blockEntity, direction) -> blockEntity.inventoryWrapper()), ALLOY_SMELTERY_HATCH_BLOCK_ENTITY);

        ItemStorage.SIDED.registerForBlockEntity(((blockEntity, direction) -> blockEntity.inventoryWrapper), ENDER_RESONATOR_ENTITY);
        FluidStorage.SIDED.registerForBlockEntity((entity, direction) -> entity.fluidStorage, ENDER_RESONATOR_ENTITY);

        ItemStorage.SIDED.registerForBlockEntity(((blockEntity, direction) -> blockEntity.inventoryWrapper), ITEM_BIN_BLOCK_ENTITY);
    }

    private static void register(Block block, String name)
    {
        Registry.register(Registry.BLOCK, new Identifier(Main.MODID, name), block);
        Registry.register(Registry.ITEM, new Identifier(Main.MODID, name), new BlockItem(block, new FabricItemSettings()));
    }

    private static ToIntFunction<BlockState> createLightLevelFromLitBlockState(int litLevel) {
        return state -> state.get(Properties.LIT) != false ? litLevel : 0;
    }
}
