package com.sussysyrup.smitheesfoundry.recipe;

import com.sussysyrup.smitheesfoundry.api.item.PartItem;
import com.sussysyrup.smitheesfoundry.api.item.ToolItem;
import com.sussysyrup.smitheesfoundry.api.material.ApiMaterialRegistry;
import com.sussysyrup.smitheesfoundry.api.material.Material;
import com.sussysyrup.smitheesfoundry.api.recipe.ApiToolRecipe;
import com.sussysyrup.smitheesfoundry.util.ToolUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;

import java.util.List;

public class TwoPartToolRecipe extends ApiToolRecipe {

    public TwoPartToolRecipe(Item item) {
        super(item);
    }

    @Override
    public ItemStack getTool(List<ItemStack> inventory) {

        ItemStack tool = new ItemStack(this.item);
        NbtCompound tag = tool.getOrCreateNbt();

        /**
         * HEAD
         */
        PartItem part = getPartItem(inventory.get(2));
        Material material = ApiMaterialRegistry.getInstance().getMaterial(part.getMaterialId());

        tag.putString(ToolItem.HEAD_KEY, part.getMaterialId());

        int durability = material.getDurability() / 2;

        tag.putFloat(ToolItem.ATTACK_DAMAGE_KEY, material.getDamage());

        tag.putInt(ToolItem.MINING_LEVEL_KEY, material.getMiningLevel());
        tag.putFloat(ToolItem.MINING_SPEED_KEY, material.getMiningSpeed());

        /**
         * HANDLE
         */
        part = getPartItem(inventory.get(0));
        material = ApiMaterialRegistry.getInstance().getMaterial(part.getMaterialId());

        tag.putString(ToolItem.HANDLE_KEY, part.getMaterialId());

        tag.putFloat(ToolItem.SWING_SPEED_KEY, 1F);

        durability = (int) ((((float) durability) + (((float) material.getDurability()) / 2)));

        tag.putInt(ToolItem.MAX_DURABILITY_KEY, durability);

        tag.putString(ToolItem.BINDING_KEY, "empty");
        tag.putString(ToolItem.EXTRA1_KEY, "empty");
        tag.putString(ToolItem.EXTRA2_KEY, "empty");

        //DURABILITY CORRECTION
        tool = ToolUtil.getDurabilityCorrectStack(tool);

        return tool;
    }
}
