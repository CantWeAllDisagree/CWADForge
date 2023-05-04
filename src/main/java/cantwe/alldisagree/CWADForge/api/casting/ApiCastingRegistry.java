package cantwe.alldisagree.CWADForge.api.casting;

import cantwe.alldisagree.CWADForge.impl.registry.RegistryInstances;
import cantwe.alldisagree.CWADForge.api.item.CastItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

public interface ApiCastingRegistry {

    static ApiCastingRegistry getInstance()
    {
        return RegistryInstances.castingRegistry;
    }

    HashMap<Identifier, Item> getPreIngotFluidMap();
    HashMap<Identifier, Item> getPreNuggetFluidMap();

    Map<Item, String> getItemTypeMap();

    Map<String, CastingResource> getTypeCastingMap();

    void setPreIngotFluidMap(HashMap<Identifier, Item> map);

    void setPreNuggetFluidMap(HashMap<Identifier, Item> map);
    void addCastItem(String type, CastItem item);

    void removeCastItem(String type);

    void addItemToType(String type, Item item);

    void removeItemToType(Item item);

    String getTypeFromItem(Item item);

    CastItem getCastItem(String type);

    void addCastingResource(String type, CastingResource castingResource);

    void removeCastingResource(String type);

    CastingResource getCastingResource(String type);

    void addIdentifierNugget(Identifier fluid, Identifier nuggetID);

    void removeIdentifierNugget(Identifier fluid);

    void clearIdentifierNuggets();

    void addIdentifierIngot(Identifier fluid, Identifier ingotID);

    void removeIdentifierIngot(Identifier fluid);

    void clearIdentifierIngots();

    void preReload();

    void reload();
}
