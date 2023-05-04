package cantwe.alldisagree.CWADForge.api.fluid;

import net.minecraft.fluid.Fluid;

import java.awt.*;
import java.util.LinkedHashSet;
import java.util.Set;

public class FluidProperties {

    private final String name;
    private final String materialID;
    private final int cookTime;
    private Fluid fluid;

    private Set<Color> colourSet;

    public FluidProperties(String name, String materialID, int cookTime)
    {
        this.name = name;
        this.materialID = materialID;
        this.cookTime = cookTime;
    }

    public Set<Color> getColourSet() {
        return colourSet;
    }

    public void setColours(Color first, Color second, Color third, Color fourth, Color fifth, Color sixth, Color seventh)
    {
        colourSet = new LinkedHashSet<>(){{
            add(first);
            add(second);
            add(third);
            add(fourth);
            add(fifth);
            add(sixth);
            add(seventh);
        }};
    }

    public String getMaterialID()
    {
        return materialID;
    }

    public int getCookTime()
    {
        return cookTime;
    }

    public Fluid getFluid() {
        return fluid;
    }

    public void setFluid(Fluid fluid)
    {
        this.fluid = fluid;
    }
}
