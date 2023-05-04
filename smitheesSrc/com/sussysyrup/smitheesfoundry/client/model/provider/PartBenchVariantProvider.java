package com.sussysyrup.smitheesfoundry.client.model.provider;

import com.sussysyrup.smitheesfoundry.Main;
import com.sussysyrup.smitheesfoundry.api.block.ApiVariationRegistry;
import com.sussysyrup.smitheesfoundry.api.block.VariationWoodRecord;
import com.sussysyrup.smitheesfoundry.client.model.block.PartBenchModel;
import net.fabricmc.fabric.api.client.model.ModelProviderContext;
import net.fabricmc.fabric.api.client.model.ModelProviderException;
import net.fabricmc.fabric.api.client.model.ModelVariantProvider;
import net.minecraft.client.render.model.UnbakedModel;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class PartBenchVariantProvider implements ModelVariantProvider {

    @Override
    public @Nullable UnbakedModel loadModelVariant(ModelIdentifier modelId, ModelProviderContext context) throws ModelProviderException {
        if(!modelId.getNamespace().equals(Main.MODID))
        {
            return null;
        }
        if(!modelId.getPath().contains("_part_bench_block"))
        {
            return null;
        }

        Identifier id = new Identifier(modelId.getNamespace(), modelId.getPath().replace("_part_bench_block", ""));

        if(ApiVariationRegistry.getInstance().getVariantWoodMap().containsKey(id))
        {
            VariationWoodRecord wood = ApiVariationRegistry.getInstance().getVariantWood(id);

            return new PartBenchModel(wood);
        }

        return null;
    }
}
