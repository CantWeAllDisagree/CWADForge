package com.sussysyrup.smitheesfoundry.client.model.toolmodels;

import com.sussysyrup.smitheesfoundry.api.material.ApiMaterialRegistry;
import com.sussysyrup.smitheesfoundry.api.item.ToolItem;
import net.fabricmc.fabric.api.renderer.v1.model.FabricBakedModel;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

import java.util.HashMap;

public class PickaxeToolTypeModel implements IToolTypeModel {

    public void render(HashMap<String, FabricBakedModel> PART_MODELS, ItemStack stack, RenderContext context)
    {
        NbtCompound nbt = stack.getNbt();

        if(!(nbt == null)) {
            if(ToolItem.getDurability(stack) <= 0)
            {
                if (nbt.contains(ToolItem.HEAD_KEY))
                    PART_MODELS.get(ApiMaterialRegistry.getInstance().getMaterial(nbt.getString(ToolItem.HEAD_KEY)).getName() + "_pickaxe_headbroken").emitItemQuads(null, null, context);
                if (nbt.contains(ToolItem.BINDING_KEY))
                    PART_MODELS.get(ApiMaterialRegistry.getInstance().getMaterial(nbt.getString(ToolItem.BINDING_KEY)).getName() + "_pickaxe_binding").emitItemQuads(null, null, context);
                if (nbt.contains(ToolItem.HANDLE_KEY))
                    PART_MODELS.get(ApiMaterialRegistry.getInstance().getMaterial(nbt.getString(ToolItem.HANDLE_KEY)).getName() + "_pickaxe_handlebroken").emitItemQuads(null, null, context);
            }
            else {
                if (nbt.contains(ToolItem.HEAD_KEY))
                    PART_MODELS.get(ApiMaterialRegistry.getInstance().getMaterial(nbt.getString(ToolItem.HEAD_KEY)).getName() + "_pickaxe_head").emitItemQuads(null, null, context);
                if (nbt.contains(ToolItem.BINDING_KEY))
                    PART_MODELS.get(ApiMaterialRegistry.getInstance().getMaterial(nbt.getString(ToolItem.BINDING_KEY)).getName() + "_pickaxe_binding").emitItemQuads(null, null, context);
                if (nbt.contains(ToolItem.HANDLE_KEY))
                    PART_MODELS.get(ApiMaterialRegistry.getInstance().getMaterial(nbt.getString(ToolItem.HANDLE_KEY)).getName() + "_pickaxe_handle").emitItemQuads(null, null, context);
            }
            IToolTypeModel.renderModifiers(PART_MODELS, context, stack);
        }
        else
        {
            PART_MODELS.get("stone_pickaxe_head").emitItemQuads(null, null, context);
            PART_MODELS.get("wood_pickaxe_binding").emitItemQuads(null, null, context);
            PART_MODELS.get("wood_pickaxe_handle").emitItemQuads(null, null, context);
        }
    }
}
