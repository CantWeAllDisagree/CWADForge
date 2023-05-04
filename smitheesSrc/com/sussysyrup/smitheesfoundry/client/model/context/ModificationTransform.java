package com.sussysyrup.smitheesfoundry.client.model.context;

import net.fabricmc.fabric.api.renderer.v1.mesh.MutableQuadView;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.util.math.Vec3i;

public class ModificationTransform implements RenderContext.QuadTransform {

    float transform;

    public ModificationTransform(float modificationTransform) {
        this.transform = modificationTransform;
    }

    /**
     * I am well aware of the extent to which this is a hack and how it depends on small numbers but its the best we can do with the information given
     * @param quad
     * @return
     */
    @Override
    public boolean transform(MutableQuadView quad) {

        BakedQuad bakedQuad = quad.toBakedQuad(0, null, false);

        int[] data = bakedQuad.getVertexData();

        Vec3i normal = bakedQuad.getFace().getVector();

        for (int index = 0; index < 4; index++) {

            int offset = index * 8;
            float x = Float.intBitsToFloat(data[offset]);
            float y = Float.intBitsToFloat(data[offset + 1]);
            float z = Float.intBitsToFloat(data[offset + 2]);

            z -= 0.5F;

            z *= 1+ (transform*10);

            z += 0.5F;

            if(normal.equals(new Vec3i(-1, 0, 0)))
            {
                x *=1F-transform;
                x-=transform;
            }
            if(normal.equals(new Vec3i(+1, 0, 0)))
            {
                x*=1F+transform;
            }
            if(normal.equals(new Vec3i(0, -1, 0)))
            {
                y *=1F-transform;
                y -=0.0001F;
            }
            if(normal.equals(new Vec3i(0, 1, 0)))
            {
                y*=1F+transform;
            }

            quad.pos(index, x, y, z);
        }

        return true;
    }

}
