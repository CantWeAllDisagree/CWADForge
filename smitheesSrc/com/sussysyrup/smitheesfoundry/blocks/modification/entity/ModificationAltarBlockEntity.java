package com.sussysyrup.smitheesfoundry.blocks.modification.entity;

import com.sussysyrup.smitheesfoundry.api.item.ToolItem;
import com.sussysyrup.smitheesfoundry.api.modification.ApiModificationRegistry;
import com.sussysyrup.smitheesfoundry.api.modification.ModificationRecipe;
import com.sussysyrup.smitheesfoundry.blocks.modification.ItemBinBlock;
import com.sussysyrup.smitheesfoundry.registry.BlocksRegistry;
import com.sussysyrup.smitheesfoundry.util.InventoryUtil;
import com.sussysyrup.smitheesfoundry.util.ToolUtil;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class ModificationAltarBlockEntity extends BlockEntity {

    public Inventory inventory = new SimpleInventory(1);

    public int maxTicks = 200;
    public int ticks = 0;

    public ModificationRecipe modificationRecipe;
    public String modificationRecipeKey;

    public ModificationAltarBlockEntity(BlockPos pos, BlockState state) {
        super(BlocksRegistry.MODIFICATION_ALTAR_ENTITY, pos, state);
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        InventoryUtil.writeNbt(nbt, inventory, true);

        if(modificationRecipeKey != null) {
            nbt.putString("recipe_key", modificationRecipeKey);
        }
        nbt.putInt("ticks", ticks);
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        inventory = new SimpleInventory(1);
        InventoryUtil.readNbt(nbt, inventory);

        modificationRecipeKey = nbt.getString("recipe_key");
        modificationRecipe = ApiModificationRegistry.getInstance().getFromStringRecipe(modificationRecipeKey);

        ticks = nbt.getInt("ticks");
    }

    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        return createNbt();
    }


    public static <E extends BlockEntity> void clientTicker(World world, BlockPos blockPos, BlockState blockState, E e) {
    }

    public static <E extends ModificationAltarBlockEntity> void serverTicker(World world, BlockPos pos, BlockState blockState, E e) {
        if(e.inventory.getStack(0).getItem() instanceof ToolItem)
        {
            e.entityTick();
        }
        else
        {
            e.cancel();
        }

        e.markDirty();

        world.updateListeners(pos, blockState, blockState, Block.NOTIFY_LISTENERS);
    }
    void entityTick() {
        if(ticks == 0) {
        }
        else
        {
            recipeTick();
        }
    }

    private void recipeTick() {
        if(!checkChange()) {
            if (ticks >= maxTicks) {
                applyDeductions();
                applyModification();
                trialModification();
                return;
            }
            ticks++;
        }
    }

    //Current Trial Formula: (x^2)/2 - 0.5. This means that 1 modification (level) will have a 0% chance of destroying the item
    //while having over 14 modifications will destroy the item with certainty
    //10 modifications will have a 49% chance of destruction
    //The safe region is just 4 modifications (under 10% chance of destruction)
    private void trialModification() {
        ItemStack stack = inventory.getStack(0);

        List<String> keys = ToolUtil.getModifications(stack);

        String[] parts;

        int count = 0;

        for(String key : keys)
        {
            parts = key.split(":");

            count += Integer.parseInt(parts[1]);
        }

        int percentage = ((int) Math.floor(((((float) count) * ((float) count)) / 2F) - 0.5F));

        boolean trial = world.random.nextInt(1, 101) <= percentage;

        if(trial)
        {
            inventory.clear();
            world.playSound(null, pos, SoundEvents.ENTITY_ITEM_BREAK, SoundCategory.BLOCKS, 1f, 1f);
        }
    }

    private void applyDeductions()
    {
        @Nullable Storage<FluidVariant> fluidStorage = FluidStorage.SIDED.find(world, this.getPos().add(0, -1, 0), Direction.UP);

        try(Transaction transaction = Transaction.openOuter())
        {
            for(StorageView<FluidVariant> view : fluidStorage.iterable(transaction))
            {
                if(view.getAmount() >= FluidConstants.BUCKET * 4)
                {
                    view.extract(view.getResource(), FluidConstants.BUCKET * 4, transaction);
                }
                break;
            }
            transaction.commit();
        }

        BlockPos containerPos = this.pos.add(0, -1, 0);

        ItemBinBlockEntity bin = (ItemBinBlockEntity) world.getBlockEntity(containerPos.add(2, 0, 0));
        if(bin != null) {
            bin.empty();
        }

        bin = (ItemBinBlockEntity) world.getBlockEntity(containerPos.add(0, 0, 2));
        if(bin != null) {
            bin.empty();
        }

        bin = (ItemBinBlockEntity) world.getBlockEntity(containerPos.add(-2, 0, 0));
        if(bin != null) {
            bin.empty();
        }
        bin = (ItemBinBlockEntity) world.getBlockEntity(containerPos.add(0, 0, -2));
        if(bin != null) {
            bin.empty();
        }
    }

    private boolean checkChange() {
        @Nullable Storage<FluidVariant> fluidStorage = FluidStorage.SIDED.find(world, this.getPos().add(0, -1, 0), Direction.UP);

        if (fluidStorage == null) {
            cancel();
            return true;
        }

        if (modificationRecipe == null) {
            cancel();
            return true;
        }

        Fluid fluid = null;

        try (Transaction transaction = Transaction.openOuter()) {
            for (StorageView<FluidVariant> view : fluidStorage.iterable(transaction)) {
                if (view.getAmount() >= FluidConstants.BUCKET * 4 && view.getResource().getFluid() == Registry.FLUID.get(modificationRecipe.fluid())) {
                    fluid = view.getResource().getFluid();
                }
                break;
            }

            transaction.abort();
        }

        if (fluid == null) {
            cancel();
            return true;
        }

        BlockPos containerPos = this.pos.add(0, -1, 0);
        HashMap<Identifier, Integer> items = new HashMap<>(modificationRecipe.reactants());

        boolean failed = true;
        ItemBinBlockEntity bin = (ItemBinBlockEntity) world.getBlockEntity(containerPos.add(2, 0, 0));
        ItemStack checkStack;
        Identifier id;

        if(bin != null) {
            if (!bin.isEmpty()) {
                checkStack = bin.inventory.getStack(0);
                id = Registry.ITEM.getId(checkStack.getItem());

                if (items.containsKey(id)) {
                    if (items.get(id).equals(bin.getActualAmount())) {
                        failed = false;
                        items.remove(id);
                    }
                }

                if (failed) {
                    cancel();
                    return true;
                }
            }
        }

        failed = true;
        bin = (ItemBinBlockEntity) world.getBlockEntity(containerPos.add(-2, 0, 0));

        if(bin != null) {
            if (!bin.isEmpty()) {
                checkStack = bin.inventory.getStack(0);
                id = Registry.ITEM.getId(checkStack.getItem());

                if (items.containsKey(id)) {
                    if (items.get(id).equals(bin.getActualAmount())) {
                        failed = false;
                        items.remove(id);
                    }
                }

                if (failed) {
                    cancel();
                    return true;
                }
            }
        }

        failed = true;
        bin = (ItemBinBlockEntity) world.getBlockEntity(containerPos.add(0, 0, 2));

        if(bin != null) {
            if (!bin.isEmpty()) {
                checkStack = bin.inventory.getStack(0);
                id = Registry.ITEM.getId(checkStack.getItem());

                if (items.containsKey(id)) {
                    if (items.get(id).equals(bin.getActualAmount())) {
                        failed = false;
                        items.remove(id);
                    }
                }

                if (failed) {
                    cancel();
                    return true;
                }
            }
        }

        failed = true;
        bin = (ItemBinBlockEntity) world.getBlockEntity(containerPos.add(0, 0, -2));

        if(bin != null) {
            if (!bin.isEmpty()) {
                checkStack = bin.inventory.getStack(0);
                id = Registry.ITEM.getId(checkStack.getItem());

                if (items.containsKey(id)) {
                    if (items.get(id).equals(bin.getActualAmount())) {
                        failed = false;
                        items.remove(id);
                    }
                }

                if (failed) {
                    cancel();
                    return true;
                }
            }
        }

        if (items.size() > 0) {
            cancel();
            return true;
        }

        return false;
    }

    private void applyModification() {
        List<String> list = ToolUtil.getModifications(inventory.getStack(0));

        String[] strings = modificationRecipeKey.split(":");
        String searchString = strings[0] + ":";

        AtomicInteger searchID = new AtomicInteger(-1);

        list.forEach(s -> {
            if(s.contains(searchString))
            {
                searchID.set(list.indexOf(s));
            }
        });

        int post = searchID.get();

        if(post == -1)
        {
            list.add(modificationRecipeKey);
        }
        else
        {
            list.set(post, modificationRecipeKey);
        }

        ToolUtil.setModifications(inventory.getStack(0), list);

        cancel();
    }

    public void prepare() {
        ModificationRecipe recipe = findRecipe();

        String modificationKey = ApiModificationRegistry.getInstance().getFromModificationRecipe(recipe);

        if(modificationKey == null)
        {
            return;
        }

        String[] strings = modificationKey.split(":");

        int level = Integer.parseInt(strings[1]);

        List<String> modifications = ToolUtil.getModifications(inventory.getStack(0));

        HashMap<String,Integer> decoded = new HashMap<>();

        for(String string : modifications)
        {
            String[] decodeStrings = string.split(":");
            decoded.put(decodeStrings[0], Integer.parseInt(decodeStrings[1]));
        }

        if(level > 1)
        {
            if(!decoded.containsKey(strings[0]))
            {
                return;
            }
            if(decoded.get(strings[0]) + 1 == level)
            {
                startModification(recipe, modificationKey);
            }
        }
        else
        {
            if(decoded.containsKey(strings[0]))
            {
                return;
            }

            startModification(recipe, modificationKey);
        }
    }

    private void startModification(ModificationRecipe recipe, String key) {
        modificationRecipe = recipe;
        modificationRecipeKey = key;
        ticks++;
    }

    private ModificationRecipe findRecipe()
    {
        //FLUID STUFF
        @Nullable Storage<FluidVariant> fluidStorage = FluidStorage.SIDED.find(world, this.getPos().add(0, -1, 0), Direction.UP);

        if(fluidStorage == null)
        {
            cancel();
            return null;
        }

        Fluid fluid = null;

        try(Transaction transaction = Transaction.openOuter())
        {
            for(StorageView<FluidVariant> view : fluidStorage.iterable(transaction))
            {
                if(view.getAmount() >= FluidConstants.BUCKET * 4)
                {
                    fluid = view.getResource().getFluid();
                }
                break;
            }

            transaction.abort();
        }

        if(fluid == null)
        {
            return null;
        }

        //ITEM STUFF
        BlockPos containerPos = this.pos.add(0, -1, 0);
        HashMap<Identifier,Integer> items = new HashMap<>();

        ContainerResult scan = checkPos(containerPos.add(2, 0, 0));
        if(scan != null)
        {
            items.put(Registry.ITEM.getId(scan.item), scan.amount);
        }
        scan = checkPos(containerPos.add(-2, 0, 0));
        if(scan != null)
        {
            items.put(Registry.ITEM.getId(scan.item), scan.amount);
        }
        scan = checkPos(containerPos.add(0, 0, 2));
        if(scan != null)
        {
            items.put(Registry.ITEM.getId(scan.item), scan.amount);
        }
        scan = checkPos(containerPos.add(0, 0, -2));
        if(scan != null)
        {
            items.put(Registry.ITEM.getId(scan.item), scan.amount);
        }

        ModificationRecipe modificationRecipe = new ModificationRecipe(Registry.FLUID.getId(fluid), items);

        return modificationRecipe;
    }

    private ContainerResult checkPos(BlockPos pos)
    {
        if(world.getBlockState(pos).getBlock() instanceof ItemBinBlock)
        {
            ItemBinBlockEntity itemBin = ((ItemBinBlockEntity) world.getBlockEntity(pos));

            int amount = itemBin.getActualAmount();
            Item item = itemBin.inventory.getStack(0).getItem();

            if(amount == 0)
            {
                return null;
            }

            return new ContainerResult(item, amount);
        }
        else {
            return null;
        }
    }

    void cancel()
    {
        ticks = 0;
        modificationRecipeKey = null;
        modificationRecipe = null;
    }

    private static record ContainerResult(Item item, Integer amount)
    {}
}
