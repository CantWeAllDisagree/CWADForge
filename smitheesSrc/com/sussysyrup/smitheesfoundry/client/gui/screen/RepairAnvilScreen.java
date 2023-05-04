package com.sussysyrup.smitheesfoundry.client.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.sussysyrup.smitheesfoundry.Main;
import com.sussysyrup.smitheesfoundry.api.item.ToolItem;
import com.sussysyrup.smitheesfoundry.screen.RepairAnvilScreenHandler;
import com.sussysyrup.smitheesfoundry.util.Util;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableTextContent;
import net.minecraft.util.Identifier;

public class RepairAnvilScreen extends HandledScreen<RepairAnvilScreenHandler> {

    private static final Identifier TEXTURE = new Identifier(Main.MODID, "textures/gui/repair_anvil_block.png");

    public RepairAnvilScreen(RepairAnvilScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        drawTexture(matrices, x, y, 0, 0, backgroundWidth, backgroundHeight);

        if(!handler.slots.get(0).getStack().isEmpty()) {
            textRenderer.draw(matrices, Text.translatable("repair.smitheesfoundry.material", Text.translatable(Util.materialTranslationKey(handler.slots.get(0).getStack().getNbt().getString(ToolItem.HEAD_KEY)))),
                    x + 6, y + 58, 0x0000000);
        }
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
