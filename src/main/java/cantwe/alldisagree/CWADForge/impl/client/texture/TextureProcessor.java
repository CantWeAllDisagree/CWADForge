package cantwe.alldisagree.CWADForge.impl.client.texture;

import cantwe.alldisagree.CWADForge.Main;
import cantwe.alldisagree.CWADForge.api.fluid.ApiMoltenFluidRegistry;
import cantwe.alldisagree.CWADForge.resource.ResourcePackHelper;
import cantwe.alldisagree.CWADForge.resource.SussyResourcePack;
import cantwe.alldisagree.CWADForge.resource.client.util.ColourScheme;
import cantwe.alldisagree.CWADForge.resource.client.util.TemplateRecolourTextureRecord;
import net.minecraft.util.Identifier;

import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

public class TextureProcessor {

    private static Set<Color> templatePart;

    private static final Map<String, ColourScheme> cache = new HashMap<>();

    public static final Set<String> partRenderValidator = new HashSet<>();

    public static void process()
    {
        templatePart = new LinkedHashSet<>(){
            {
                add(new Color(0, 0, 0));
                add(new Color(43, 43, 43));
                add(new Color(85, 85, 85));
                add(new Color(128, 128, 128));
                add(new Color(170, 170, 170));
                add(new Color(213, 213, 213));
                add(new Color(255, 255, 255));
            }};

        SussyResourcePack.addNamespace(Main.MODID);

        loadTemplates();
       // generateItems();
       // generatePartRenders();
        generateMoltenFluids();
    }

    private static void loadTemplates()
    {
        //PART RENDERS REGISTERED FROM API

        ResourcePackHelper.registerTemplateTexture(new Identifier(Main.MODID, "molten_bucket"), new Identifier(Main.MODID, "textures/item/fluidbucket.png"));

        ResourcePackHelper.registerTemplateTexture(new Identifier(Main.MODID, "molten_metal_flow"), new Identifier(Main.MODID, "textures/block/molten_metal_flow.png"));
        ResourcePackHelper.registerTemplateTexture(new Identifier(Main.MODID, "molten_metal_still"), new Identifier(Main.MODID, "textures/block/molten_metal_still.png"));
    }

  /*  private static void generateItems()
    {
        ColourScheme scheme;

        FluidProperties fluidProps;

        for(String key : ApiMoltenFluidRegistry.getInstance().getFluidPropertiesRegistry().keySet())
        {
            fluidProps = ApiMoltenFluidRegistry.getInstance().getFluidProperties(key);
            scheme = new ColourScheme(templatePart, fluidProps.getColourSet());

            ResourcePackHelper.recolourItemSet.add(new TemplateRecolourItemRecord(new Identifier(Main.MODID, "molten_bucket"),
                    new Identifier(Main.MODID, "textures/item/fluidbucket_" + key + ".png"),
                    new Identifier(Main.MODID, "models/item/fluidbucket_" + key + ".json"),
                    scheme));
        }
    }*/

    /*private static void generatePartRenders()
    {
        ColourScheme scheme;

        for(Identifier templateID : ApiTemplateTextureRegistry.getInstance().getPartRenders())
        {
            for(Material material :ApiMaterialRegistry.getInstance().getMaterials()) {

                if(cache.containsKey(material.getName()))
                {
                    scheme = cache.get(material.getName());
                }
                else
                {
                    scheme = new ColourScheme(templatePart, material.getColourSet());
                    cache.put(material.getName(), scheme);
                }

                ResourcePackHelper.recolourItemSet.add(new TemplateRecolourItemRecord(templateID,
                        new Identifier(Main.MODID, "textures/item/" + material.getName() + "_" + templateID.getPath() + ".png"),
                        new Identifier(Main.MODID, "models/item/"+material.getName() + "_" + templateID.getPath() + ".json"),
                        scheme
                ));

                partRenderValidator.add(material.getName() + "_" + templateID.getPath());
            }
        }
    }*/

    private static void generateMoltenFluids()
    {
        String still = "{\n" +
                "  \"animation\": {\n" +
                "    \"frametime\": 2,\n" +
                "    \"frames\": [\n" +
                "      0,\n" +
                "      1,\n" +
                "      2,\n" +
                "      3,\n" +
                "      4,\n" +
                "      5,\n" +
                "      6,\n" +
                "      7,\n" +
                "      8,\n" +
                "      9,\n" +
                "      10,\n" +
                "      11,\n" +
                "      12,\n" +
                "      13,\n" +
                "      14,\n" +
                "      15,\n" +
                "      16,\n" +
                "      17,\n" +
                "      18,\n" +
                "      19,\n" +
                "      18,\n" +
                "      17,\n" +
                "      16,\n" +
                "      15,\n" +
                "      14,\n" +
                "      13,\n" +
                "      12,\n" +
                "      11,\n" +
                "      10,\n" +
                "      9,\n" +
                "      8,\n" +
                "      7,\n" +
                "      6,\n" +
                "      5,\n" +
                "      4,\n" +
                "      3,\n" +
                "      2,\n" +
                "      1\n" +
                "    ]\n" +
                "  }\n" +
                "}";

        String flow = "{\n" +
                "  \"animation\": {\n" +
                "    \"frametime\": 3\n" +
                "  }\n" +
                "}\n";

        ColourScheme scheme;

        for(String name : ApiMoltenFluidRegistry.getInstance().getCreateFluidRegistry().keySet())
        {
            scheme = new ColourScheme(templatePart, ApiMoltenFluidRegistry.getInstance().getCreateFluidRegistry().get(name).getColourSet());

            fluidBlockStateReg(name);

            ResourcePackHelper.registerFluidModel(new Identifier(Main.MODID, "models/block/" + name + ".json"), new Identifier(Main.MODID, "block/still_"+name));

           ResourcePackHelper.recolourTextureSet.add(new TemplateRecolourTextureRecord(new Identifier(Main.MODID, "molten_metal_flow"),
                    new Identifier(Main.MODID, "textures/block/flow_"+name +".png"),
                    scheme));
            ResourcePackHelper.recolourTextureSet.add(new TemplateRecolourTextureRecord(new Identifier(Main.MODID, "molten_metal_still"),
                    new Identifier(Main.MODID, "textures/block/still_"+name +".png"),
                    scheme));
            ResourcePackHelper.registerMeta(new Identifier(Main.MODID, "textures/block/still_"+name +".png.mcmeta"), still);
            ResourcePackHelper.registerMeta(new Identifier(Main.MODID, "textures/block/flow_"+name +".png.mcmeta"), flow);
        }
    }

    private static void fluidBlockStateReg(String name)
    {
        String state = "{\n" +
                "  \"variants\": {\n" +
                "    \"\": {\n" +
                "      \"model\": \"" + Main.MODID + ":block/" + name + "\"\n" +
                "    }\n" +
                "  }\n" +
                "}";

        try {
            SussyResourcePack.registerClientResource(new Identifier(Main.MODID, "blockstates/" + name + ".json"), new ByteArrayInputStream(state.getBytes(Charset.defaultCharset())));
        } catch (IOException e)
        {
            Main.LOGGER.error(e.toString());
        }
    }
}
