package com.sussysyrup.smitheesfoundry.client.registry;

import com.sussysyrup.smitheesfoundry.Main;
import com.sussysyrup.smitheesfoundry.api.client.item.ApiToolRegistryClient;
import com.sussysyrup.smitheesfoundry.api.client.model.ApiToolTypeModelRegistry;
import com.sussysyrup.smitheesfoundry.client.model.toolmodels.*;
import com.sussysyrup.smitheesfoundry.client.render.TankItemRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class ItemsRegistryClient {
    @Environment(EnvType.CLIENT)
    public static void clientInit()
    {
        ApiToolTypeModelRegistry.getInstance().addToolTypeModel("pickaxe", new PickaxeToolTypeModel());
        ApiToolTypeModelRegistry.getInstance().addToolTypeModel("axe", new AxeToolTypeModel());
        ApiToolTypeModelRegistry.getInstance().addToolTypeModel("hoe", new HoeToolTypeModel());
        ApiToolTypeModelRegistry.getInstance().addToolTypeModel("shovel", new ShovelToolTypeModel());
        ApiToolTypeModelRegistry.getInstance().addToolTypeModel("sword", new SwordToolTypeModel());
        ApiToolTypeModelRegistry.getInstance().addToolTypeModel("chisel", new ChiselToolTypeModel());

        ApiToolRegistryClient toolC = ApiToolRegistryClient.getInstance();
        
        toolC.addPreToolRenderedPart("pickaxe_head");
        toolC.addPreToolRenderedPart("pickaxe_binding");
        toolC.addPreToolRenderedPart("pickaxe_handle");
        toolC.addPreToolRenderedPart("pickaxe_headbroken");
        toolC.addPreToolRenderedPart("pickaxe_handlebroken");

        toolC.addPreToolRenderedPart("axe_head");
        toolC.addPreToolRenderedPart("axe_binding");
        toolC.addPreToolRenderedPart("axe_handle");
        toolC.addPreToolRenderedPart("axe_handlebroken");
        toolC.addPreToolRenderedPart("axe_headbroken");

        toolC.addPreToolRenderedPart("hoe_head");
        toolC.addPreToolRenderedPart("hoe_binding");
        toolC.addPreToolRenderedPart("hoe_handle");
        toolC.addPreToolRenderedPart("hoe_headbroken");
        toolC.addPreToolRenderedPart("hoe_handlebroken");

        toolC.addPreToolRenderedPart("shovel_head");
        toolC.addPreToolRenderedPart("shovel_binding");
        toolC.addPreToolRenderedPart("shovel_handle");
        toolC.addPreToolRenderedPart("shovel_headbroken");
        toolC.addPreToolRenderedPart("shovel_handlebroken");

        toolC.addPreToolRenderedPart("sword_blade");
        toolC.addPreToolRenderedPart("sword_guard");
        toolC.addPreToolRenderedPart("sword_handle");
        toolC.addPreToolRenderedPart("sword_bladebroken");
        toolC.addPreToolRenderedPart("sword_guardbroken");
        toolC.addPreToolRenderedPart("sword_handlebroken");
        
        toolC.addPreToolRenderedPart("chisel_blade");
        toolC.addPreToolRenderedPart("chisel_handle");
        toolC.addPreToolRenderedPart("chisel_bladebroken");

        BuiltinItemRendererRegistry.INSTANCE.register(Registry.ITEM.get(new Identifier(Main.MODID, "tank_block")), new TankItemRenderer());
    }
}
