package cantwe.alldisagree.CWADForge.client.render;

import cantwe.alldisagree.CWADForge.api.client.render.ApiSpriteRendering;
import cantwe.alldisagree.CWADForge.blocks.alloysmeltery.TankBlock;
import cantwe.alldisagree.CWADForge.blocks.alloysmeltery.entity.TankBlockEntity;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.fluid.Fluid;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3f;

public class TankBlockEntityRenderer implements BlockEntityRenderer<TankBlockEntity> {

    public TankBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {}

    @Override
    public void render(TankBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {

        if(entity.getWorld().getBlockState(entity.getPos()).getBlock() instanceof TankBlock) {

            int lightAbove = WorldRenderer.getLightmapCoordinates(entity.getWorld(), entity.getPos().add(entity.getWorld().getBlockState(entity.getPos()).get(TankBlock.FACING).getVector()));

            if(entity.fluidStorage == null)
            {
                return;
            }
            if(entity.fluidStorage.isResourceBlank())
            {
                return;
            }
            if(entity.fluidStorage.amount == 0)
            {
                return;
            }

            Direction direction = entity.getWorld().getBlockState(entity.getPos()).get(TankBlock.FACING);

            matrices.push();

            if (direction.equals(Direction.SOUTH)) {

            }
            if (direction.equals(Direction.EAST)) {
                matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(90));
                matrices.translate(-1, 0, 0);
            }
            if (direction.equals(Direction.NORTH)) {
                matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180));
                matrices.translate(-1, 0, -1);
            }
            if (direction.equals(Direction.WEST)) {
                matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(270));
                matrices.translate(0, 0, -1);
            }

            matrices.push();

            Fluid fluid = entity.fluidStorage.variant.getFluid();

            float height = 1 * (((float) entity.fluidStorage.getAmount()) / ((float) entity.fluidStorage.getCapacity()));

            Sprite sprite = FluidRenderHandlerRegistry.INSTANCE.get(fluid).getFluidSprites(entity.getWorld(), entity.getPos(), fluid.getDefaultState())[0];

            matrices.translate(1, 0, 1F);
            matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180));

            matrices.translate(0, height - 0.01F, 0);

            int colour = MinecraftClient.getInstance().getBlockColors().getColor(fluid.getDefaultState().getBlockState(), entity.getWorld(), entity.getPos(), 0);

            int lightCor = WorldRenderer.getLightmapCoordinates(entity.getWorld(), entity.getPos().add(direction.getVector()));

            if(fluid.getRegistryEntry().isIn(FluidTags.LAVA))
            {
                lightCor = 15728832;
            }

            VertexConsumer consumer = vertexConsumers.getBuffer(RenderLayer.getEntityTranslucentCull(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE));

            ApiSpriteRendering.renderConsumerSpriteUp(matrices, sprite, consumer, 0.001F, 0.998F, 0.001F, 0.998F, colour, overlay, lightCor, 1);

            matrices.translate(0, -height, 0.05F);
            ApiSpriteRendering.renderConsumerSpriteTile(matrices, sprite, consumer, 0.001F, 0.998F, 0.001F, height - 0.001F, colour, overlay, lightCor, 1);

            matrices.pop();
            matrices.pop();


        }
    }
}
