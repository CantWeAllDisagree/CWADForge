package com.sussysyrup.smitheesfoundry.client.registry;

import com.sussysyrup.smitheesfoundry.Main;
import com.sussysyrup.smitheesfoundry.api.client.texture.ApiTemplateTextureRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class TextureRegistry {

    public static void clientInit()
    {
        registerPart("pickaxehead");
        registerPart("toolhandle");
        registerPart("toolbinding");
        registerPart("axehead");
        registerPart("hoehead");
        registerPart("shovelhead");
        registerPart("swordblade");
        registerPart("swordguard");
        registerPart("chiselblade");

        registerRenderPart("head", "pickaxe");
        registerRenderPart("handle", "pickaxe");
        registerRenderPart("binding", "pickaxe");
        registerRenderPart("headbroken", "pickaxe");
        registerRenderPart("handlebroken", "pickaxe");

        registerRenderPart("head", "axe");
        registerRenderPart("handle", "axe");
        registerRenderPart("binding", "axe");
        registerRenderPart("handlebroken", "axe");
        registerRenderPart("headbroken", "axe");

        registerRenderPart("head", "hoe");
        registerRenderPart("handle", "hoe");
        registerRenderPart("binding", "hoe");
        registerRenderPart("headbroken", "hoe");
        registerRenderPart("handlebroken", "hoe");

        registerRenderPart("head", "shovel");
        registerRenderPart("handle", "shovel");
        registerRenderPart("binding", "shovel");
        registerRenderPart("headbroken", "shovel");
        registerRenderPart("handlebroken", "shovel");

        registerRenderPart("blade", "sword");
        registerRenderPart("handle", "sword");
        registerRenderPart("guard", "sword");
        registerRenderPart("bladebroken", "sword");
        registerRenderPart("handlebroken", "sword");
        registerRenderPart("guardbroken", "sword");

        registerRenderPart("blade", "chisel");
        registerRenderPart("handle", "chisel");
        registerRenderPart("bladebroken", "chisel");

        ApiTemplateTextureRegistry.getInstance().registerTexture("molten_metal_flow", new Identifier(Main.MODID, "textures/block/molten_metal_flow.png"));
        ApiTemplateTextureRegistry.getInstance().registerTexture("molten_metal_still", new Identifier(Main.MODID, "textures/block/molten_metal_still.png"));
        ApiTemplateTextureRegistry.getInstance().registerTexture("fluidbucket", new Identifier(Main.MODID, "textures/item/fluidbucket.png"));
    }

    private static void registerPart(String name)
    {
        ApiTemplateTextureRegistry.getInstance().registerTexture(name, new Identifier(Main.MODID, "textures/item/" + name + ".png"));
    }

    private static void registerRenderPart(String name, String tool)
    {
        ApiTemplateTextureRegistry.getInstance().registerTexture(tool + "_" + name, new Identifier(Main.MODID, "textures/tool/" + tool + "/" + name + ".png"));
    }
}
