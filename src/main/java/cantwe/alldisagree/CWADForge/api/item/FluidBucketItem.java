package cantwe.alldisagree.CWADForge.api.item;

import cantwe.alldisagree.CWADForge.util.TranslationUtil;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

public class FluidBucketItem extends BucketItem {

    private final String fluidName;

    public FluidBucketItem(Fluid fluid, Settings settings, String fluidName) {
        super(fluid, settings);
        this.fluidName = fluidName;
    }

    @Override
    public Text getName(ItemStack stack) {

        return TranslationUtil.getGeneratedTranslation("item.smitheesfoundry.fluidbucket" + "." + fluidName,
                "item.smitheesfoundry.fluidbucket", Text.translatable("block.smitheesfoundry." + fluidName));
    }
}
