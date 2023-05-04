package cantwe.alldisagree.CWADForge.client.registry;

import cantwe.alldisagree.CWADForge.client.render.*;
import cantwe.alldisagree.CWADForge.registry.BlocksRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.BlockEntityRendererRegistry;
import net.minecraft.client.render.RenderLayer;

@Environment(EnvType.CLIENT)
public class BlocksRegistryClient {
    @Environment(EnvType.CLIENT)
    public static void clientInit()
    {
        BlockEntityRendererRegistry.register(BlocksRegistry.ALLOY_SMELTERY_CONTROLLER_BLOCK_ENTITY, AlloySmelteryBlockEntityRenderer::new);
        BlockEntityRendererRegistry.register(BlocksRegistry.TANK_BLOCK_ENTITY, TankBlockEntityRenderer::new);
        BlockEntityRendererRegistry.register(BlocksRegistry.ALLOY_SMELTERY_FAUCET_BLOCK_ENTITY, AlloySmelteryFaucetRenderer::new);
        BlockEntityRendererRegistry.register(BlocksRegistry.CASTING_TABLE_ENTITY, CastingTableBlockEntityRender::new);
        BlockEntityRendererRegistry.register(BlocksRegistry.CASTING_BASIN_ENTITY, CastingBasinBlockEntityRender::new);
        BlockEntityRendererRegistry.register(BlocksRegistry.ITEM_BIN_BLOCK_ENTITY, ItemBinBlockEntityRender::new);

        BlockRenderLayerMap.INSTANCE.putBlock(BlocksRegistry.TANK_BLOCK, RenderLayer.getCutout());
    }
}
