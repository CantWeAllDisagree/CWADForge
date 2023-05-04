package com.sussysyrup.smitheesfoundry.client.render;

import com.sussysyrup.smitheesfoundry.blocks.modification.ModificationAltarBlock;
import com.sussysyrup.smitheesfoundry.blocks.modification.entity.ItemBinBlockEntity;
import com.sussysyrup.smitheesfoundry.blocks.modification.entity.ModificationAltarBlockEntity;
import com.sussysyrup.smitheesfoundry.registry.ParticleRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3f;

public class ModificationAltarBlockEntityRender implements BlockEntityRenderer<ModificationAltarBlockEntity> {

    public ModificationAltarBlockEntityRender(BlockEntityRendererFactory.Context context)
    {}

    @Override
    public void render(ModificationAltarBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {

        if(entity.getWorld().getBlockState(entity.getPos()).getBlock() instanceof ModificationAltarBlock) {

            matrices.push();

            int offsetExp = entity.getPos().getX() - entity.getPos().getY() + entity.getPos().getZ();

            double offset = Math.sin((entity.getWorld().getTime() + tickDelta + offsetExp) / 8.0) / 8.0;

            matrices.translate(0.5, 0.75 + offset, 0.5);

            matrices.multiply(Vec3f.POSITIVE_Y.rotationDegrees((entity.getWorld().getTime() + tickDelta + offsetExp) * 4));

            int lightAbove = WorldRenderer.getLightmapCoordinates(entity.getWorld(), entity.getPos().up());
            MinecraftClient.getInstance().getItemRenderer().renderItem(entity.inventory.getStack(0), ModelTransformation.Mode.GROUND, lightAbove, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, 0);

            matrices.pop();

            if(entity.ticks > 0)
            {
                BlockPos pos = entity.getPos().add(0, -1, 0);
                int repeat = 15;
                double yOffset = 0.25D;

                BlockPos usePos = pos.add(2, 0, 0);

                ItemBinBlockEntity bin = (ItemBinBlockEntity) entity.getWorld().getBlockEntity(usePos);
                usePos = usePos.add(0, 1, 0);

                if(bin != null)
                {
                    if(!bin.isEmpty())
                    {
                        for(int i = 0; i < repeat; i++) {
                            MinecraftClient.getInstance().world.addParticle(new ItemStackParticleEffect(ParticleRegistry.NO_GRAV_CRACK, bin.inventory.getStack(0)),
                                    usePos.getX()+ 0.5, usePos.getY()+yOffset, usePos.getZ()+ 0.5, 0, 0, 0);
                        }
                    }
                }

                usePos = pos.add(-2, 0, 0);

                bin = (ItemBinBlockEntity) entity.getWorld().getBlockEntity(usePos);
                usePos = usePos.add(0, 1, 0);

                if(bin != null)
                {
                    if(!bin.isEmpty())
                    {
                        for(int i = 0; i < repeat; i++) {
                            MinecraftClient.getInstance().world.addParticle(new ItemStackParticleEffect(ParticleRegistry.NO_GRAV_CRACK, bin.inventory.getStack(0)),
                                    usePos.getX()+ 0.5, usePos.getY()+yOffset, usePos.getZ()+ 0.5, 0, 0, 0);
                        }
                    }
                }

                usePos = pos.add(0, 0, 2);

                bin = (ItemBinBlockEntity) entity.getWorld().getBlockEntity(usePos);
                usePos = usePos.add(0, 1, 0);

                if(bin != null)
                {
                    if(!bin.isEmpty())
                    {
                        for(int i = 0; i < repeat; i++) {
                            MinecraftClient.getInstance().world.addParticle(new ItemStackParticleEffect(ParticleRegistry.NO_GRAV_CRACK, bin.inventory.getStack(0)),
                                    usePos.getX()+ 0.5, usePos.getY()+yOffset, usePos.getZ()+ 0.5, 0, 0, 0);
                        }
                    }
                }

                usePos = pos.add(0, 0, -2);

                bin = (ItemBinBlockEntity) entity.getWorld().getBlockEntity(usePos);
                usePos = usePos.add(0, 1, 0);

                if(bin != null)
                {
                    if(!bin.isEmpty())
                    {
                        for(int i = 0; i < repeat; i++) {
                            MinecraftClient.getInstance().world.addParticle(new ItemStackParticleEffect(ParticleRegistry.NO_GRAV_CRACK, bin.inventory.getStack(0)),
                                    usePos.getX() + 0.5, usePos.getY()+yOffset, usePos.getZ() + 0.5, 0, 0, 0);
                        }
                    }
                }
            }
        }
    }
}
