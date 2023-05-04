package com.sussysyrup.smitheesfoundry.client.model;

import com.mojang.datafixers.util.Pair;
import com.sussysyrup.smitheesfoundry.Main;
import com.sussysyrup.smitheesfoundry.api.client.item.ApiToolRegistryClient;
import com.sussysyrup.smitheesfoundry.api.client.model.ApiToolTypeModelRegistry;
import com.sussysyrup.smitheesfoundry.api.item.ApiToolRegistry;
import com.sussysyrup.smitheesfoundry.api.item.ToolItem;
import com.sussysyrup.smitheesfoundry.api.modification.ApiModificationRegistry;
import net.fabricmc.fabric.api.renderer.v1.model.FabricBakedModel;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.model.*;
import net.minecraft.client.render.model.json.ModelOverrideList;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockRenderView;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

public class ToolModel implements UnbakedModel, BakedModel, FabricBakedModel {

    private static final HashMap<String, FabricBakedModel> PART_MODELS = new HashMap<>();
    public static final HashSet<String> MODIFICATION_MODELS_BAKE = new HashSet<>();

    String tool;

    public ToolModel(String tool)
    {
        this.tool = tool;
    }

    @Override
    public boolean isVanillaAdapter() {
        return false;
    }

    @Override
    public void emitBlockQuads(BlockRenderView blockView, BlockState state, BlockPos pos, Supplier<Random> randomSupplier, RenderContext context) {
        //EMPTY
    }

    @Override
    public void emitItemQuads(ItemStack stack, Supplier<Random> randomSupplier, RenderContext context) {

        if(stack.getItem() instanceof ToolItem item) {
            String toolType = item.getToolType();

            ApiToolTypeModelRegistry.getInstance().getToolTypeModel(toolType).render(PART_MODELS, stack, context);
        }
    }

    @Nullable
    @Override
    public BakedModel bake(ModelLoader loader, Function<SpriteIdentifier, Sprite> textureGetter, ModelBakeSettings rotationContainer, Identifier modelId) {

        for (String s : ApiToolRegistryClient.getInstance().getReloadToolRenderedParts()) {
            PART_MODELS.put(s, (FabricBakedModel) loader.bake(new ModelIdentifier(new Identifier(Main.MODID, s), "inventory"), ModelRotation.X0_Y0));
        }
        bakeModifications(loader);

        return this;
    }

    private void bakeModifications(ModelLoader loader) {

        Set<String> pre_modifications = new HashSet<>();
        String[] split;

        for(String key : ApiModificationRegistry.getInstance().getModificationKeys())
        {
            split = key.split(":");

            pre_modifications.add(split[0]);
        }

        for(String key : ApiToolRegistry.getInstance().getTools())
        {
            for(String mod :pre_modifications)
            {
                MODIFICATION_MODELS_BAKE.add(key+"_"+mod);
                PART_MODELS.put(key+"_"+mod, (FabricBakedModel) loader.bake(new ModelIdentifier(new Identifier(Main.MODID, "tool/"+key+"/"+mod), "inventory"), ModelRotation.X0_Y0));
            }
        }
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction face, Random random) {
        return Collections.emptyList();
    }

    @Override
    public boolean useAmbientOcclusion() {
        return false;
    }

    @Override
    public boolean hasDepth() {
        return false;
    }

    @Override
    public boolean isSideLit() {
        return false;
    }

    @Override
    public boolean isBuiltin() {
        return false;
    }

    @Override
    public Sprite getParticleSprite() {
        return MinecraftClient.getInstance().getSpriteAtlas(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE).apply(new Identifier("block/cobblestone"));
    }

    @Override
    public ModelTransformation getTransformation() {
        return MinecraftClient.getInstance().getItemRenderer().getModels().getModel(Registry.ITEM.get(new Identifier("diamond_pickaxe"))).getTransformation();
    }

    @Override
    public ModelOverrideList getOverrides() {
        return ModelOverrideList.EMPTY;
    }

    @Override
    public Collection<Identifier> getModelDependencies() {
        return Collections.emptyList();
    }

    @Override
    public Collection<SpriteIdentifier> getTextureDependencies(Function<Identifier, UnbakedModel> unbakedModelGetter, Set<Pair<String, String>> unresolvedTextureReferences) {
        return Collections.emptyList();
    }
}
