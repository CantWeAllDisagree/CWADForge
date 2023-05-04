package com.sussysyrup.smitheesfoundry.client.render;

import com.sussysyrup.smitheesfoundry.api.block.ApiVariationRegistry;
import com.sussysyrup.smitheesfoundry.blocks.ForgeBlock;
import com.sussysyrup.smitheesfoundry.blocks.entity.ForgeBlockEntity;
import com.sussysyrup.smitheesfoundry.registry.BlocksRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3f;

public class ForgeBlockEntityRender implements BlockEntityRenderer<ForgeBlockEntity> {

    public ForgeBlockEntityRender(BlockEntityRendererFactory.Context ctx) {}

    @Override
    public void render(ForgeBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {

        BlockPos pos = entity.getPos();

        if(ApiVariationRegistry.getInstance().getForgeBlocks().contains(entity.getWorld().getBlockState(pos).getBlock())) {

            Direction direction = entity.getWorld().getBlockState(pos).get(ForgeBlock.FACING);

            matrices.push();

            if (direction.equals(Direction.SOUTH)) {

            }
            if (direction.equals(Direction.EAST)) {
                matrices.multiply(Vec3f.POSITIVE_Y.rotationDegrees(90));
                matrices.translate(-1, 0, 0);
            }
            if (direction.equals(Direction.NORTH)) {
                matrices.multiply(Vec3f.POSITIVE_Y.rotationDegrees(180));
                matrices.translate(-1, 0, -1);
            }
            if (direction.equals(Direction.WEST)) {
                matrices.multiply(Vec3f.POSITIVE_Y.rotationDegrees(270));
                matrices.translate(0, 0, -1);
            }

            matrices.push();

            matrices.translate(0.25, 0.9, 0.82);

            matrices.multiply(Vec3f.POSITIVE_X.rotationDegrees(-90));

            matrices.scale(0.58F, 0.58F, 0.58F);

            MinecraftClient.getInstance().getItemRenderer().renderItem(entity.getStack(0), ModelTransformation.Mode.GROUND, light, overlay, matrices, vertexConsumers, 0);

            matrices.pop();

            matrices.push();

            matrices.translate(0.54, 0.9, 0.60);

            matrices.multiply(Vec3f.POSITIVE_X.rotationDegrees(-90));

            matrices.scale(0.58F, 0.58F, 0.58F);

            MinecraftClient.getInstance().getItemRenderer().renderItem(entity.getStack(1), ModelTransformation.Mode.GROUND, light, overlay, matrices, vertexConsumers, 0);

            matrices.pop();

            matrices.push();

            matrices.translate(0.75, 0.9, 0.32);

            matrices.multiply(Vec3f.POSITIVE_X.rotationDegrees(-90));

            matrices.scale(0.58F, 0.58F, 0.58F);

            MinecraftClient.getInstance().getItemRenderer().renderItem(entity.getStack(2), ModelTransformation.Mode.GROUND, light, overlay, matrices, vertexConsumers, 0);

            matrices.pop();

            if(entity.size() > 4) {
                matrices.push();

                matrices.translate(0.25, 0.9, 0.4);

                matrices.multiply(Vec3f.POSITIVE_X.rotationDegrees(-90));

                matrices.scale(0.58F, 0.58F, 0.58F);

                MinecraftClient.getInstance().getItemRenderer().renderItem(entity.getStack(3), ModelTransformation.Mode.GROUND, light, overlay, matrices, vertexConsumers, 0);

                matrices.pop();


                matrices.push();

                matrices.translate(0.8, 0.9, 0.85);

                matrices.multiply(Vec3f.POSITIVE_X.rotationDegrees(-90));

                matrices.scale(0.58F, 0.58F, 0.58F);

                MinecraftClient.getInstance().getItemRenderer().renderItem(entity.getStack(4), ModelTransformation.Mode.GROUND, light, overlay, matrices, vertexConsumers, 0);

                matrices.pop();
            }

            matrices.push();

            double offset = Math.sin((entity.getWorld().getTime() + tickDelta) / 8.0) / 6.0;
            // Move the item
            matrices.translate(0.5, 1.25 + offset, 0.5);

            // Rotate the item
            matrices.multiply(Vec3f.POSITIVE_Y.rotationDegrees((entity.getWorld().getTime() + tickDelta) * 4));

            if(entity.size() > 4) {
                MinecraftClient.getInstance().getItemRenderer().renderItem(entity.getStack(5), ModelTransformation.Mode.GROUND, light, overlay, matrices, vertexConsumers, 0);
            }
            else
            {
                MinecraftClient.getInstance().getItemRenderer().renderItem(entity.getStack(3), ModelTransformation.Mode.GROUND, light, overlay, matrices, vertexConsumers, 0);
            }
            matrices.pop();

            matrices.pop();
        }
    }
}
