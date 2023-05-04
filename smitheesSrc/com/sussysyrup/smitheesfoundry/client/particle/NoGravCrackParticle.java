package com.sussysyrup.smitheesfoundry.client.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.CrackParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ItemStackParticleEffect;

@Environment(EnvType.CLIENT)
public class NoGravCrackParticle extends CrackParticle {

    protected NoGravCrackParticle(ClientWorld world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, ItemStack stack) {
        this(world, x, y, z, stack);
        this.velocityX *= 0.10000000149011612;
        this.velocityY *= 0.10000000149011612;
        this.velocityZ *= 0.10000000149011612;
        this.velocityX += velocityX;
        this.velocityY += velocityY;
        this.velocityZ += velocityZ;
    }

    protected NoGravCrackParticle(ClientWorld world, double x, double y, double z, ItemStack stack) {
        super(world, x, y, z, stack);

        this.gravityStrength = 0;

    }

    @Environment(EnvType.CLIENT)
    public static class NoGravFactory implements ParticleFactory<ItemStackParticleEffect> {
        public NoGravFactory() {
        }

        public Particle createParticle(ItemStackParticleEffect itemStackParticleEffect, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            return new NoGravCrackParticle(clientWorld, d, e, f, g, h, i, itemStackParticleEffect.getItemStack());
        }
    }
}
