package cantwe.alldisagree.CWADForge.config;

public class Configuration {

    private boolean translationMode = false;
    private boolean disableVanillaMaterials = false;


    public boolean isTranslationMode() {
        return translationMode;
    }

    public void setTranslationMode(boolean translationMode) {
        this.translationMode = translationMode;
    }

    public boolean isDisableVanillaMaterials() {
        return disableVanillaMaterials;
    }

    public void setDisableVanillaMaterials(boolean disableVanillaMaterials) {
        this.disableVanillaMaterials = disableVanillaMaterials;
    }
}
