package com.sussysyrup.smitheesfoundry.impl.registry;

import com.sussysyrup.smitheesfoundry.api.casting.CastingResource;
import com.sussysyrup.smitheesfoundry.api.casting.ApiCastingRegistry;
import com.sussysyrup.smitheesfoundry.api.fluid.FluidProperties;
import com.sussysyrup.smitheesfoundry.api.fluid.ApiMoltenFluidRegistry;
import com.sussysyrup.smitheesfoundry.api.fluid.ApiSmelteryResourceRegistry;
import com.sussysyrup.smitheesfoundry.api.fluid.SmelteryResource;
import com.sussysyrup.smitheesfoundry.api.item.ApiPartRegistry;
import com.sussysyrup.smitheesfoundry.api.material.ApiMaterialRegistry;
import com.sussysyrup.smitheesfoundry.api.material.Material;
import com.sussysyrup.smitheesfoundry.api.material.MaterialResource;
import com.sussysyrup.smitheesfoundry.api.item.PartItem;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.registry.Registry;
import net.minecraft.registry.tag.TagKey;
import java.util.HashMap;
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

        CastingResource castingResource;
        if(item instanceof PartItem partItem)
        {
            for(String key : ApiMoltenFluidRegistry.getInstance().getFluidPropertiesRegistry().keySet())
            {
                fluidProperties = ApiMoltenFluidRegistry.getInstance().getFluidProperties(key);
                if(partItem.getMaterialId().equals(fluidProperties.getMaterialID()))
                {
                    ApiSmelteryResourceRegistry.getInstance().registerSmelteryResource(item, new SmelteryResource(key, ((long) ApiPartRegistry.getInstance().getPartCost(partItem.getPartName()).floatValue()) * FluidConstants.INGOT));

                    castingResource = ApiCastingRegistry.getInstance().getCastingResource(partItem.getPartName());
                    if(castingResource == null)
                    {
                        FluidProperties finalFluidProperties = fluidProperties;

                        castingResource = new CastingResource(((long) ApiPartRegistry.getInstance().getPartCost(partItem.getPartName()).floatValue()) * FluidConstants.INGOT, new HashMap<Fluid,Item>(){{
                            put(finalFluidProperties.getFluid() ,partItem);
                        }});
                        ApiCastingRegistry.getInstance().addCastingResource(partItem.getPartName(), castingResource);
                    }
                    else
                    {
                        HashMap<Fluid,Item> map = castingResource.fluidItemMap();
                        map.put(fluidProperties.getFluid(), partItem);

                        /**
                        Long fluidValue = castingResource.fluidValue();
                        ForgeCastingRegistry.removeCastingResource(partItem.getPartName());
                        ForgeCastingRegistry.addCastingResource(partItem.getPartName(), new CastingResource(fluidValue, map));
                         **/
                    }
                    break;
                }
            }
        }
    }

    private static void setupCasting(Item item)
    {
        if(item instanceof PartItem partItem)
        {
            ApiCastingRegistry.getInstance().addItemToType(partItem.getPartName(), partItem);


        }
    }

}
