package com.sussysyrup.smitheesfoundry.impl.block;

import com.google.common.collect.Lists;
import com.google.gson.JsonObject;
import com.sussysyrup.smitheesfoundry.Main;
import com.sussysyrup.smitheesfoundry.api.block.ApiVariationRegistry;
import com.sussysyrup.smitheesfoundry.api.block.VariationMetalRecord;
import com.sussysyrup.smitheesfoundry.api.block.VariationWoodRecord;
import com.sussysyrup.smitheesfoundry.api.itemgroup.ItemGroups;
import com.sussysyrup.smitheesfoundry.blocks.ForgeBlock;
import com.sussysyrup.smitheesfoundry.blocks.PartBenchBlock;
import com.sussysyrup.smitheesfoundry.blocks.entity.ForgeBlockEntity;
import com.sussysyrup.smitheesfoundry.blocks.entity.PartBenchBlockEntity;
import com.sussysyrup.smitheesfoundry.items.ForgeBlockItem;
import com.sussysyrup.smitheesfoundry.items.PartBenchBlockItem;
import com.sussysyrup.smitheesfoundry.util.Util;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import java.util.*;

public class ImplVariationRegistry implements ApiVariationRegistry {

    private HashMap<Identifier, JsonObject> reloadRecipes = new HashMap<>();

    private HashMap<Identifier, VariationWoodRecord> reloadVariantWoodMap = new HashMap<>();
    private static HashMap<Identifier, VariationWoodRecord> variantWoodMap = new HashMap<>();

    private HashMap<Identifier, VariationMetalRecord> reloadVariantMetalMap = new HashMap<>();
    private static HashMap<Identifier, VariationMetalRecord> variantMetalMap = new HashMap<>();

    private Set<Block> reloadPartBenchBlocks = new HashSet<>();
    private static Set<Block> partBenchBlocks = new HashSet<>();

    private Set<Block> reloadForgeBlocks = new HashSet<>();
    private static Set<Block> forgeBlocks = new HashSet<>();

    public static BlockEntityType<PartBenchBlockEntity> PART_BENCH_BLOCK_ENTITY;
    public static BlockEntityType<ForgeBlockEntity> FORGE_BLOCK_ENTITY;

    @Override
    public HashMap<Identifier, VariationWoodRecord> getVariantWoodMap()
    {
        return reloadVariantWoodMap;
    }

    @Override
    public HashMap<Identifier, VariationMetalRecord> getVariantMetalMap() {
        return reloadVariantMetalMap;
    }

    @Override
    public void registerVariantWood(Identifier id, VariationWoodRecord wood)
    {
        variantWoodMap.put(id, wood);
    }

    @Override
    public void registerVariantWoodQuick(Identifier id, Identifier wood) {
        variantWoodMap.put(id,  new VariationWoodRecord(
                new Identifier(wood.getNamespace(), wood.getPath()+"_log"),
                new Identifier(wood.getNamespace(), wood.getPath()+"_planks"),
                new Identifier(wood.getNamespace(), wood.getPath()+"_log_top"),
                wood.getPath(),
                new Identifier(wood.getNamespace(), wood.getPath()+"_log"),
                new Identifier(wood.getNamespace(), wood.getPath()+"_planks")
        ));
    }

    @Override
    public void removeVariantWood(Identifier id)
    {
        variantWoodMap.remove(id);
    }

    @Override
    public void clearVariantWood()
    {
        variantWoodMap.clear();
    }

    @Override
    public VariationWoodRecord getVariantWood(Identifier id)
    {
        return reloadVariantWoodMap.get(id);
    }

    @Override
    public VariationMetalRecord getVariantMetal(Identifier id) {
        return reloadVariantMetalMap.get(id);
    }

    @Override
    public void registerVariantMetal(Identifier id, VariationMetalRecord metal) {
        variantMetalMap.put(id, metal);
    }

    @Override
    public void registerVariantMetalQuick(Identifier id, Identifier metal) {
        String names = metal.getNamespace();
        String path = metal.getPath();
        variantMetalMap.put(id,
                new VariationMetalRecord(new Identifier(names, path + "_block"),
                        path,
                        new Identifier(names, path + "_nugget"),
                        new Identifier(names, path + "_ingot"),
                        new Identifier(names, path + "_block")));
    }

    @Override
    public void removeVariantMetal(Identifier id) {
        variantMetalMap.remove(id);
    }

    @Override
    public void clearVariantMetal() {
        variantMetalMap.clear();
    }

    @Override
    public HashMap<Identifier, JsonObject> getRecipes()
    {
        return reloadRecipes;
    }

    @Override
    public JsonObject getRecipe(Identifier id)
    {
        return reloadRecipes.get(id);
    }

    @Override
    public Set<Block> getPartBenchBlocks() {
        return reloadPartBenchBlocks;
    }

    @Override
    public Set<Block> getForgeBlocks()
    {
        return forgeBlocks;
    }

    @Override
    public void reload()
    {
        Block block;

        //Part Bench
        Map<Identifier, JsonObject> partbenchRecipes = new HashMap<>();
        for(Identifier id : variantWoodMap.keySet())
        {
            VariationWoodRecord wood = variantWoodMap.get(id);

            block = new PartBenchBlock(FabricBlockSettings.of(Material.WOOD).strength(2.0F), wood.woodName());
            Registry.register(Registry.BLOCK, id + "_part_bench_block", block);
            Registry.register(Registry.ITEM, id + "_part_bench_block", new PartBenchBlockItem(block, new FabricItemSettings().group(ItemGroups.BLOCK_GROUP), wood.woodName()));
            partBenchBlocks.add(block);

            Identifier id1 = new Identifier(id.getNamespace(), id.getPath()+"_part_bench_block");

            partbenchRecipes.put(id1, Util.createShapedRecipeJson(
                    Lists.newArrayList(
                            'L', 'I'), Lists.newArrayList(wood.itemlog(), wood.itemPlanks()),

                    Lists.newArrayList("item", "item"),
                    Lists.newArrayList("LLL",
                            "I I",
                            "I I"
                    ),
                    id1
            ));
        }

        //Forge
        Map<Identifier, JsonObject> forgeRecipes = new HashMap<>();
            //wood
        for(Identifier id : variantWoodMap.keySet())
        {
            VariationWoodRecord wood = variantWoodMap.get(id);

            block = new ForgeBlock(FabricBlockSettings.of(Material.WOOD).strength(2.0F), false);
            Registry.register(Registry.BLOCK, id + "_forge_block", block);
            Registry.register(Registry.ITEM, id + "_forge_block", new ForgeBlockItem(block, new FabricItemSettings().group(ItemGroups.BLOCK_GROUP), wood.woodName(), false));
            forgeBlocks.add(block);

            Identifier id1 = new Identifier(id.getNamespace(), id.getPath()+"_forge_block");

            partbenchRecipes.put(id1, Util.createShapedRecipeJson(
                    Lists.newArrayList(
                            'L', 'I'), Lists.newArrayList(wood.itemlog(), wood.itemPlanks()),

                    Lists.newArrayList("item", "item"),
                    Lists.newArrayList("III",
                            " L ",
                            "LLL"
                    ),
                    id1
            ));
        }
        //metal
        for(Identifier id : variantMetalMap.keySet())
        {
            VariationMetalRecord metal = variantMetalMap.get(id);

            block = new ForgeBlock(FabricBlockSettings.of(Material.WOOD).strength(2.0F), true);
            Registry.register(Registry.BLOCK, id + "_reinforced_forge_block", block);
            Registry.register(Registry.ITEM, id + "_reinforced_forge_block", new ForgeBlockItem(block, new FabricItemSettings().group(ItemGroups.BLOCK_GROUP), metal.metalName(), true));
            forgeBlocks.add(block);

            Identifier id1 = new Identifier(id.getNamespace(), id.getPath()+"_reinforced_forge_block");

            partbenchRecipes.put(id1, Util.createShapedRecipeJson(
                    Lists.newArrayList('B', 'I', 'R'),
                    Lists.newArrayList(metal.itemBlock(), metal.itemIngot(), new Identifier(Main.MODID, "reinforced_bricks")),
                    Lists.newArrayList("item", "item", "item"),
                    Lists.newArrayList("RRR",
                            "IBI",
                            "BBB"
                    ),
                    id1
            ));
        }

        reloadVariantWoodMap.putAll(variantWoodMap);
        reloadVariantMetalMap.putAll(variantMetalMap);
        reloadPartBenchBlocks.addAll(partBenchBlocks);
        reloadForgeBlocks.addAll(forgeBlocks);
        reloadRecipes.putAll(partbenchRecipes);
        reloadRecipes.putAll(forgeRecipes);
    }

    @Override
    public void postReload()
    {
        PART_BENCH_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(Main.MODID, "part_bench"), FabricBlockEntityTypeBuilder.create(PartBenchBlockEntity::new, reloadPartBenchBlocks.stream().toArray(Block[]::new)).build());
        FORGE_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, new Identifier(Main.MODID, "forge"), FabricBlockEntityTypeBuilder.create(ForgeBlockEntity::new, reloadForgeBlocks.stream().toArray(Block[]::new)).build());
    }

}
