package cantwe.alldisagree.CWADForge.client.gui.screen;

import cantwe.alldisagree.CWADForge.screen.EnderResonatorScreenHandler;
import com.mojang.blaze3d.systems.RenderSystem;
import cantwe.alldisagree.CWADForge.Main;
import cantwe.alldisagree.CWADForge.api.client.render.ApiSpriteRendering;
import cantwe.alldisagree.CWADForge.blocks.modification.entity.EnderResonatorBlockEntity;
import net.fabricmc.fabric.api.client.render.fluid.v1.FluidRenderHandlerRegistry;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

import java.util.ArrayList;
import java.util.List;

public class EnderResonatorScreen extends HandledScreen<EnderResonatorScreenHandler> {

    private static final Identifier TEXTURE = new Identifier(Main.MODID, "textures/gui/ender_resonator.png");

    public EnderResonatorScreen(EnderResonatorScreenHandler handler, PlayerInventory inventory, Text title) {
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
        drawElements(matrices, x, y);
    }

    private void drawElements(MatrixStack matrices, int x, int y) {
        BlockPos pos = handler.pos;
        EnderResonatorBlockEntity entity = ((EnderResonatorBlockEntity) MinecraftClient.getInstance().world.getBlockEntity(pos));

        if(!entity.fluidStorage.variant.isBlank())
        {
            Fluid fluid = entity.fluidStorage.variant.getFluid();
            Sprite sprite = FluidRenderHandlerRegistry.INSTANCE.get(fluid).getFluidSprites(MinecraftClient.getInstance().world, new BlockPos(0, 0, 0), fluid.getDefaultState())[0];

            int height = Math.round((((float) entity.fluidStorage.amount) / ((float) entity.fluidStorage.getCapacity())) * 48);


            matrices.push();

            for(int i = 0; i < height; i+=16) {

                if(height > 16)
                {
                    renderRow(matrices, fluid, 16, sprite);
                    matrices.translate(0, -16, 0);
                    height -=16;
                }
                if (height <= 16) {
                    renderRow(matrices, fluid, height, sprite);
                    break;
                }
            }

            matrices.pop();
        }

        if(entity.currentTicks > 0 && entity.maxTicks > 0)
        {
            String[] strings = entity.fluidID.split(":");
            Fluid fluid = Registry.FLUID.get(new Identifier(strings[0], strings[1]));

            if(fluid == null)
            {
                return;
            }

            int colour = MinecraftClient.getInstance().getBlockColors().getColor(fluid.getDefaultState().getBlockState(), MinecraftClient.getInstance().world, MinecraftClient.getInstance().player.getBlockPos(), 0);

            int size = ((int) ((((float) entity.currentTicks) / ((float) entity.maxTicks)) * 22));

            Sprite sprite = FluidRenderHandlerRegistry.INSTANCE.get(fluid).getFluidSprites(MinecraftClient.getInstance().world, new BlockPos(0, 0, 0), fluid.getDefaultState())[0];

            matrices.push();

            matrices.translate(x +74, y+36, 0);

            matrices.scale(16, 16, 0);

            if(size > 16) {
                ApiSpriteRendering.renderColouredSpriteTile(matrices, sprite, 0, 1, 0, 0.9375F, colour, 1);
                matrices.translate(1, 0, 0);
                ApiSpriteRendering.renderColouredSpriteTile(matrices, sprite, 0, ((float) (size - 16)) / 16F, 0, 0.9375F, colour, 1);
            }
            else
            {
                ApiSpriteRendering.renderColouredSpriteTile(matrices, sprite, 0, ((float)(size)) / 16F, 0, 0.9375F, colour, 1);
            }

            matrices.pop();

            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.setShaderTexture(0, TEXTURE);
            drawTexture(matrices, x + 74, y + 36, 176, 0, 22, 15, 256, 256);

        }
    }

    private void renderRow(MatrixStack matrices, Fluid fluid, int height, Sprite sprite)
    {
        int colour = MinecraftClient.getInstance().getBlockColors().getColor(fluid.getDefaultState().getBlockState(), MinecraftClient.getInstance().world, MinecraftClient.getInstance().player.getBlockPos(), 0);

        float size = (((float) height) / 16F);

        for (int t = 0; t < 2; t++) {

            matrices.push();

            if (!(t == 7)) {
                matrices.translate(x + 127 + (t * 16), y + 52, 0);
                matrices.push();
                matrices.scale(16, 16, 1);
                ApiSpriteRendering.renderColouredSpriteTile(matrices, sprite, 0, 1, 1-size, size, colour, 1F);
                matrices.pop();

            }

            matrices.pop();
        }
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        drawMouseoverTooltip(matrices, mouseX, mouseY);

        this.renderMouseToolTip(matrices, mouseX, mouseY);
    }

    private void renderMouseToolTip(MatrixStack matrices, int mouseX, int mouseY) {
        super.mouseMoved(mouseX, mouseY);

        BlockPos pos = handler.pos;
        EnderResonatorBlockEntity entity = ((EnderResonatorBlockEntity) MinecraftClient.getInstance().world.getBlockEntity(pos));

        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        if(entity.fluidStorage.isResourceBlank())
        {
            return;
        }

        Identifier fluidID = Registry.FLUID.getId(entity.fluidStorage.getResource().getFluid());
        if(mouseX >= x+127 && mouseX <= x+159 && mouseY <= y+68 && mouseY >= y+20)
        {
            List<Text> texts = new ArrayList<>();
            texts.add(Text.translatable("block." + fluidID.getNamespace() + "." + fluidID.getPath()));

            int amount = (int) (Math.floor(((float) entity.fluidStorage.getAmount())) / ((float) FluidConstants.BUCKET));

            long remainder = entity.fluidStorage.getAmount() - (amount * FluidConstants.BUCKET);

            texts.add(Text.translatable("fluid.smitheesfoundry.bamount", amount));
            texts.add(Text.translatable("fluid.smitheesfoundry.remainder", remainder));

            renderTooltip(matrices, texts, (int) mouseX, (int) mouseY);
        }
    }

    @Override
    protected void init() {
        super.init();

        titleX = (backgroundWidth - textRenderer.getWidth(title)) / 2;
    }
}
