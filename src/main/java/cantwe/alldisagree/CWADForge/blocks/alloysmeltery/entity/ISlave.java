package cantwe.alldisagree.CWADForge.blocks.alloysmeltery.entity;

import net.minecraft.util.math.BlockPos;

public interface ISlave {

    void addMaster(BlockPos pos);
    void removeMaster();

}
