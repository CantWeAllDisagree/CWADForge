package cantwe.alldisagree.CWADForge.api.block;

import com.google.gson.JsonObject;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Set;

public interface ApiVariationRegistry {

    /**
     * returns currently active instance
     * @return
     */

    HashMap<Identifier, VariationWoodRecord> getVariantWoodMap();

    HashMap<Identifier, VariationMetalRecord> getVariantMetalMap();

    void registerVariantWood(Identifier id, VariationWoodRecord wood);

    void registerVariantWoodQuick(Identifier id, Identifier wood);

    void removeVariantWood(Identifier id);

    void clearVariantWood();

    VariationWoodRecord getVariantWood(Identifier id);

    VariationMetalRecord getVariantMetal(Identifier id);

    void registerVariantMetal(Identifier id, VariationMetalRecord metal);

    void registerVariantMetalQuick(Identifier id, Identifier metal);

    void removeVariantMetal(Identifier id);

    void clearVariantMetal();


    HashMap<Identifier, JsonObject> getRecipes();

    JsonObject getRecipe(Identifier id);

    Set<Block> getPartBenchBlocks();

    Set<Block> getForgeBlocks();

    void reload();

    void postReload();
}
