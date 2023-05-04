package com.sussysyrup.smitheesfoundry.client.model.toolmodels;

import com.sussysyrup.smitheesfoundry.api.item.ToolItem;
import com.sussysyrup.smitheesfoundry.client.model.context.ModificationTransform;
import com.sussysyrup.smitheesfoundry.util.ToolUtil;
import net.fabricmc.fabric.api.renderer.v1.model.FabricBakedModel;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public interface IToolTypeModel {

    void render(HashMap<String, FabricBakedModel> PART_MODELS, ItemStack stack, RenderContext context);

    static void renderModifiers(HashMap<String, FabricBakedModel> PART_MODELS, RenderContext context, ItemStack stack)
    {
        Set<String> modifications = new HashSet<>();
        String[] strings;
        String tool = ((ToolItem) stack.getItem()).getToolType();
        for(String key :ToolUtil.getModifications(stack))
        {
            strings = key.split(":");
            modifications.add(tool + "_" + strings[0]);
        }

        float i = 0.0001F;
        FabricBakedModel model;

        for(String modTex : modifications)
        {
            context.pushTransform(new ModificationTransform(i));

            model = PART_MODELS.get(modTex);
            if(model != null) {
            model.emitItemQuads(null, null, context);
            }

            context.popTransform();
            i += 0.0001F;
        }
    }
}
