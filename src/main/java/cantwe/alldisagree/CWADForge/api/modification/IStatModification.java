package cantwe.alldisagree.CWADForge.api.modification;

public interface IStatModification {

    int durabilityAdd();

    default float durabilityMultiply()
    {
        return 1F;
    }

    float damageAdd();

    default float damageMultiply()
    {
        return 1F;
    }

    float swingSpeedAdd();

    default float swingSpeedMultiply()
    {
        return 1F;
    }

    float miningSpeedAdd();

    default float miningSpeedMultiply()
    {
        return 1F;
    }

    float miningLevelAdd();
}
