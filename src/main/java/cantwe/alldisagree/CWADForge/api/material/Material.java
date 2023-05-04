package cantwe.alldisagree.CWADForge.api.material;


import java.awt.*;
import java.util.LinkedHashSet;
import java.util.Set;

public class Material {

    private boolean metal;

    private String fluidID;
    private String name;


    public Material(String name, boolean metal, String fluidID, int miningLevel, int durability, float durabilityMultiplier, float miningSpeed, float damage)
    {
        this.name = name;
        this.metal = metal;

        this.fluidID = fluidID;

    }

    private Set<Color> colourSet;
    public String getName() {
        return name;
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

    public boolean isMetal() {
        return metal;
    }

    public String getFluidID() {
        return fluidID;
    }
}
