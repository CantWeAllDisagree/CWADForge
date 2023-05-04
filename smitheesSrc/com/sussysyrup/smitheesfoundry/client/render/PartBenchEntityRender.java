package com.sussysyrup.smitheesfoundry.client.render;

import com.sussysyrup.smitheesfoundry.blocks.PartBenchBlock;
import com.sussysyrup.smitheesfoundry.blocks.entity.PartBenchBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3f;

public class PartBenchEntityRender implements BlockEntityRenderer<PartBenchBlockEntity> {

    public PartBenchEntityRender(BlockEntityRendererFactory.Context ctx) {}

    @Override
    public void render(PartBenchBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        BlockPos pos = entity.getPos();

        if(entity.getWorld().getBlockState(pos).getBlock() instanceof PartBenchBlock) {

            Direction direction = entity.getWorld().getBlockState(pos).get(PartBenchBlock.FACING);

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

            matrices.translate(0.49, 0.9375, 0.59);

            matrices.multiply(Vec3f.POSITIVE_X.rotationDegrees(-90));

            matrices.scale(0.8F, 0.8F, 0.8F);

            MinecraftClient.getInstance().getItemRenderer().renderItem(entity.getStack(0), ModelTransformation.Mode.GROUND, light, overlay, matrices, vertexConsumers, 0);

            matrices.pop();

            matrices.push();

            matrices.translate(0.2, 1, 0.88);

            matrices.multiply(Vec3f.POSITIVE_X.rotationDegrees(-90));

            matrices.scale(0.8F, 0.8F, 0.8F);

            MinecraftClient.getInstance().getItemRenderer().renderItem(entity.getStack(1), ModelTransformation.Mode.GROUND, light, overlay, matrices, vertexConsumers, 0);

            matrices.pop();

            matrices.pop();
        }
    }
}
