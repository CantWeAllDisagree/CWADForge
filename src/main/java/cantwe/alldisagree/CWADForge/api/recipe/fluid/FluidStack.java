package cantwe.alldisagree.CWADForge.api.recipe.fluid;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import cantwe.alldisagree.CWADForge.Main;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.fluid.Fluid;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

/**
 * A one object container class to be used with fluid involving recipes
 * @param fluid
 * @param amount
 */
public record FluidStack(FluidVariant fluid, long amount) {

    public static FluidStack EMPTY = new FluidStack(FluidVariant.blank(), 0);

    public static FluidStack fromJson(JsonElement jsonElement) {
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

    public static JsonElement toJson(FluidStack fluidStack) {
        JsonObject object = new JsonObject();

        object.addProperty("id", Registry.FLUID.getId(fluidStack.fluid().getFluid()).toString());
        object.addProperty("nbt", NbtHelper.toNbtProviderString(fluidStack.fluid().getNbt()));
        object.addProperty("amount", fluidStack.amount());

        return object;
    }

    public static void writeBuf(PacketByteBuf buf, FluidStack fluidStack)
    {
        fluidStack.fluid.toPacket(buf);
        buf.writeLong(fluidStack.amount);
    }

    public static FluidStack readBuf(PacketByteBuf buf)
    {
        return new FluidStack(FluidVariant.fromPacket(buf), buf.readLong());
    }

    public static void writeBufList(PacketByteBuf buf, List<FluidStack> stacks)
    {
        buf.writeInt(stacks.size());

        for(int i = 0; i < stacks.size(); i++)
        {
            writeBuf(buf, stacks.get(i));
        }
    }

    public static List<FluidStack> readBufList(PacketByteBuf buf)
    {
        int size = buf.readInt();
        List<FluidStack> stacks = new ArrayList<>();

        for(int i = 0; i < size; i++)
        {
            stacks.add(readBuf(buf));
        }

        return stacks;
    }
}
