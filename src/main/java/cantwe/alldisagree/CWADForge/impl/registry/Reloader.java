package cantwe.alldisagree.CWADForge.impl.registry;

import cantwe.alldisagree.CWADForge.api.casting.ApiCastingRegistry;
import cantwe.alldisagree.CWADForge.api.fluid.ApiSmelteryResourceRegistry;
import cantwe.alldisagree.CWADForge.api.fluid.FluidProperties;
import cantwe.alldisagree.CWADForge.api.fluid.SmelteryResource;
import cantwe.alldisagree.CWADForge.api.item.PartItem;
import cantwe.alldisagree.CWADForge.api.material.ApiMaterialRegistry;
import cantwe.alldisagree.CWADForge.api.material.Material;
import cantwe.alldisagree.CWADForge.api.material.MaterialResource;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.minecraft.util.registry.Registry;
import net.minecraft.item.Item;
import net.minecraft.tag.TagKey;

import java.util.List;

public class Reloader {

    public static void reload()
    {
        List<Item> items = Registry.ITEM.stream().toList();

        for(Item item : items)
        {
            setupMaterials(item);
            setupSmelteryMelting(item);
            setupCasting(item);
        }
    }

    private static void setupMaterials(Item item)
    {
        MaterialResource recipe;
        String fluidID;
        for(TagKey<Item> tag : ApiMaterialRegistry.getInstance().getPreMaterialResourceMap().keySet())
        {
            recipe = ApiMaterialRegistry.getInstance().getPreMaterialResource(tag);

            if(item.getRegistryEntry().isIn(tag))
            {
                ApiMaterialRegistry.getInstance().registerMaterialResource(item, recipe);

                Material material = ApiMaterialRegistry.getInstance().getMaterial(recipe.materialId());

                if(material.isMetal())
                {
                    fluidID = material.getFluidID();

                    ApiSmelteryResourceRegistry.getInstance().registerSmelteryResource(item, new SmelteryResource(fluidID, (long) (recipe.materialValue() * FluidConstants.INGOT)));
                }
                break;
            }
        }
    }

    private static void setupSmelteryMelting(Item item)
    {
        for(TagKey<Item> tag : ApiSmelteryResourceRegistry.getInstance().getTagSmelteryResourceMap().keySet())
        {
            if(item.getRegistryEntry().isIn(tag))
            {
                ApiSmelteryResourceRegistry.getInstance().registerSmelteryResource(item, ApiSmelteryResourceRegistry.getInstance().getTagSmelteryResourceMap().get(tag));
                break;
            }
        }
        FluidProperties fluidProperties;


    }

    private static void setupCasting(Item item)
    {
        if(item instanceof PartItem partItem)
        {
            ApiCastingRegistry.getInstance().addItemToType(partItem.getPartName(), partItem);


        }
    }

}
