package cantwe.alldisagree.CWADForge.api.json;

import com.google.gson.*;
import cantwe.alldisagree.CWADForge.Main;
import cantwe.alldisagree.CWADForge.api.recipe.fluid.FluidStack;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.fluid.Fluid;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.Identifier;

import java.lang.reflect.Type;

public class FluidStackAdapter implements JsonSerializer<FluidStack>, JsonDeserializer<FluidStack> {

    @Override
    public JsonElement serialize(FluidStack fluidStack, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject object = new JsonObject();

        object.addProperty("id", Registry.FLUID.getId(fluidStack.fluid().getFluid()).toString());
        object.addProperty("nbt", NbtHelper.toNbtProviderString(fluidStack.fluid().getNbt()));
        object.addProperty("amount", fluidStack.amount());

        return object;
    }

    @Override
    public FluidStack deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) {
        try {
        JsonObject object = jsonElement.getAsJsonObject();

        Fluid fluid = Registry.FLUID.get(Identifier.tryParse(object.get("id").getAsString()));
        NbtCompound compound = NbtHelper.fromNbtProviderString(object.get("nbt").getAsString());
        long amount = object.get("amount").getAsLong();

            return new FluidStack(FluidVariant.of(fluid, compound), amount);

        } catch (Exception e) {
            Main.LOGGER.error(e.toString());
            return new FluidStack(FluidVariant.blank(), 0);
        }
    }
}
