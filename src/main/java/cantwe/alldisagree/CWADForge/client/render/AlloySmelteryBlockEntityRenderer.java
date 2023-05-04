package cantwe.alldisagree.CWADForge.client.render;

import cantwe.alldisagree.CWADForge.api.client.render.ApiSpriteRendering;
import cantwe.alldisagree.CWADForge.blocks.alloysmeltery.AlloySmelteryControllerBlock;
import cantwe.alldisagree.CWADForge.blocks.alloysmeltery.entity.AlloySmelteryControllerBlockEntity;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandler;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;
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
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3f;

public class AlloySmelteryBlockEntityRenderer implements BlockEntityRenderer<AlloySmelteryControllerBlockEntity> {

    public AlloySmelteryBlockEntityRenderer(BlockEntityRendererFactory.Context ctx) {}

    @Override
    public void render(AlloySmelteryControllerBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {

        if(!(entity.getWorld().getBlockState(entity.getPos()).getBlock() instanceof AlloySmelteryControllerBlock))
        {
            return;
        }
        if(!entity.isValid())
        {
            return;
        }

        if(!entity.fluidStorage.views.isEmpty()) {
            StorageView<FluidVariant> view = entity.fluidStorage.views.get(entity.fluidStorage.views.size() - 1);

            int length = entity.oldLength;
            int width = entity.oldWidth;
            int widthCorrect = entity.widthCorrection;

            float yShift = entity.oldHeight * (((float) entity.fluidStorage.getCurrentCapacity()) / ((float) entity.fluidStorage.maxCapacity));

            Fluid fluid = view.getResource().getFluid();

            FluidRenderHandler renderInstance = FluidRenderHandlerRegistry.INSTANCE.get(fluid);

            if(renderInstance == null)
            {
                return;
            }

            Sprite sprite = renderInstance.getFluidSprites(entity.getWorld(), entity.getPos(), fluid.getDefaultState())[0];

            Direction direction = entity.getWorld().getBlockState(entity.getPos()).get(AlloySmelteryControllerBlock.FACING);

            int colour = MinecraftClient.getInstance().getBlockColors().getColor(fluid.getDefaultState().getBlockState(), entity.getWorld(), entity.getPos(), 0);

            matrices.push();

            if (direction.equals(Direction.SOUTH)) {
                matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(270));
                matrices.translate(0, 0, -1);
            }
            if (direction.equals(Direction.EAST)) {
            }
            if (direction.equals(Direction.NORTH)) {
                matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(90));
                matrices.translate(-1, 0, 0);
            }
            if (direction.equals(Direction.WEST)) {
                matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180));
                matrices.translate(-1, 0, -1);
            }


            VertexConsumer consumer = vertexConsumers.getBuffer(RenderLayer.getEntityTranslucentCull(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE));

            int lightCor;

            BlockPos pos;

            for(int z = 0; z < length; z++) {
                for (int x = 0; x < width; x++) {

                    pos = new BlockPos(x + entity.getPos().getX() + 1, entity.getPos().getY() + yShift, z + entity.getPos().getZ() - widthCorrect);

                    lightCor = WorldRenderer.getLightmapCoordinates(entity.getWorld(), pos);

                    if(fluid.getRegistryEntry().isIn(FluidTags.LAVA))
                    {
                        lightCor = 15728832;
                    }

                    matrices.push();

                    matrices.translate(x + 1, yShift, z - widthCorrect);
                    
                    ApiSpriteRendering.renderConsumerSpriteUp(matrices, sprite, consumer, 0, 1, 0, 1, colour, overlay, lightCor, 1);

                    matrices.pop();

                }
            }

            matrices.pop();
        }
    }

    //LIGHT MAX: 15728832
    //OVERLAY: 655360 seems to always be that
}
