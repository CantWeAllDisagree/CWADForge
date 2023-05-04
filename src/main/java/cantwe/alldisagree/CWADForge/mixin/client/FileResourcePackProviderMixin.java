package cantwe.alldisagree.CWADForge.mixin.client;


//POTENTIALLY USE
/*@Environment(EnvType.CLIENT)
@Mixin(FileResourcePackProvider.class)
public abstract class FileResourcePackProviderMixin {

    @Shadow @Final
    private ResourceType type;

    @Inject(method = "register", at = @At("HEAD"))
    private void reg(Consumer<ResourcePackProfile> profileAdder, CallbackInfo ci) {

        ResourcePackProfile profile = ResourcePackProfile.create("Sussy Resource Pack", Text.literal("Sussy Resource Pack"),
                true, (name) -> new SussyResourcePack("Sussy Resource Pack"), this.type, ResourcePackProfile.InsertionPosition.BOTTOM,
                ResourcePackSource.create((param) -> Text.literal("sussylib"), false));

        if (profile != null) {
            profileAdder.accept(profile);
        }
    }*/