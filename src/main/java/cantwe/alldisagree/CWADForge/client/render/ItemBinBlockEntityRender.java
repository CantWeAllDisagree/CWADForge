package cantwe.alldisagree.CWADForge.client.render;

import cantwe.alldisagree.CWADForge.blocks.modification.ItemBinBlock;
import cantwe.alldisagree.CWADForge.blocks.modification.entity.ItemBinBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Vec3f;

public class ItemBinBlockEntityRender implements BlockEntityRenderer<ItemBinBlockEntity> {

    public ItemBinBlockEntityRender(BlockEntityRendererFactory.Context context)
    {}

    @Override
    public void render(ItemBinBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {

        if(entity.getWorld().getBlockState(entity.getPos()).getBlock() instanceof ItemBinBlock) {

            matrices.push();

            int offsetExp = entity.getPos().getX() - entity.getPos().getY() + entity.getPos().getZ();

            matrices.translate(0.5, 1.25, 0.5);

            matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion((entity.getWorld().getTime() + tickDelta + offsetExp) * 4));

            matrices.scale(1.25F, 1.25F, 1.25F);

            int lightAbove = WorldRenderer.getLightmapCoordinates(entity.getWorld(), entity.getPos().up());
            MinecraftClient.getInstance().getItemRenderer().renderItem(entity.inventory.getStack(0), ModelTransformation.Mode.GROUND, lightAbove, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, 0);

            matrices.pop();
        }
    }
}
