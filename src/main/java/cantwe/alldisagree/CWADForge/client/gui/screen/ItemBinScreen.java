package cantwe.alldisagree.CWADForge.client.gui.screen;

import cantwe.alldisagree.CWADForge.screen.ItemBinScreenHandler;
import com.mojang.blaze3d.systems.RenderSystem;
import cantwe.alldisagree.CWADForge.Main;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ItemBinScreen extends HandledScreen<ItemBinScreenHandler> {

    private static final Identifier TEXTURE = new Identifier(Main.MODID, "textures/gui/item_bin.png");

    public ItemBinScreen(ItemBinScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        drawTexture(matrices, x, y, 0, 0, backgroundWidth, backgroundHeight);

        renderStore(matrices, x, y);
    }

    private void renderStore(MatrixStack matrices, int x, int y) {

        textRenderer.draw(matrices, Text.translatable("text.smitheesfoundry.amountinc", handler.be.getActualAmount()), x + 4, y + 42, 0x0000000);
        textRenderer.draw(matrices, Text.translatable("text.smitheesfoundry.amount", handler.be.storeAmount), x + 4, y + 52, 0x0000000);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        drawMouseoverTooltip(matrices, mouseX, mouseY);
    }

    @Override
    protected void init() {
        super.init();

        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;
    }
}
