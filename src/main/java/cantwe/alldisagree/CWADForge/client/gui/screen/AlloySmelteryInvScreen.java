package cantwe.alldisagree.CWADForge.client.gui.screen;

import cantwe.alldisagree.CWADForge.networking.c2s.C2SConstants;
import cantwe.alldisagree.CWADForge.screen.AlloySmelteryInvScreenHandler;
import com.mojang.blaze3d.systems.RenderSystem;
import cantwe.alldisagree.CWADForge.Main;
import cantwe.alldisagree.CWADForge.api.fluid.FluidProperties;
import cantwe.alldisagree.CWADForge.api.fluid.SmelteryResource;
import cantwe.alldisagree.CWADForge.blocks.alloysmeltery.entity.AlloySmelteryControllerBlockEntity;
import cantwe.alldisagree.CWADForge.mixin.client.MouseAccessor;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class AlloySmelteryInvScreen extends HandledScreen<AlloySmelteryInvScreenHandler> {

    private static final Identifier TEXTURE = new Identifier(Main.MODID, "textures/gui/alloy_smeltery_controller.png");

    private static final Identifier WIDGETS = new Identifier(Main.MODID, "textures/gui/widgets.png");
    private static boolean buttonUp = false;
    private static boolean buttonDown = false;
    private static boolean buttonFluid = false;
    private final AlloySmelteryControllerBlockEntity be;

    private TexturedButtonWidget buttonDownWidget = new TexturedButtonWidget(-500, -500, 18, 18, 18, 0, 18, WIDGETS, 256, 256, button -> buttonDown = true);
    private TexturedButtonWidget buttonUpWidget = new TexturedButtonWidget(-500, -500, 18, 18, 0, 0, 18, WIDGETS, 256, 256, button -> buttonUp = true);
    private TexturedButtonWidget buttonFluidWidget = new TexturedButtonWidget(-500, -500, 28, 30, 27, 36, 30, WIDGETS, 256, 256, button -> buttonFluid = true);

    public AlloySmelteryInvScreen(AlloySmelteryInvScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);

        this.be = handler.getBlockEntity();
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        drawTexture(matrices, x, y, 0, 0, backgroundWidth, backgroundHeight);

        int cookTime;
        SmelteryResource smelteryResource;
        FluidProperties fluidProperties;
        int unitSize;
        Item item;

        int x1;
        int y1;
        int sideStep = 0;
        int verticalStep = 0;

        for(int i = be.itemPageShift * 21; i < be.smeltTicks.size(); i++)
        {
            item = be.itemInventory.getStack(i).getItem();
            if(verticalStep > 2)
            {
                break;
            }
            if(be.itemInventory.getStack(i).equals(ItemStack.EMPTY))
            {
                sideStep++;
                if(sideStep > 6)
                {
                    sideStep =0;
                    verticalStep++;
                }

                continue;
            }

            if(be.smeltTicks.get(i) == 0)
            {
                RenderSystem.setShader(GameRenderer::getPositionTexShader);
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                RenderSystem.setShaderTexture(0, WIDGETS);
                x1 = x + 31;
                y1 = y + 17;
                drawTexture(matrices, x1 + (sideStep * 20), y1 + (verticalStep * 18), 36, 1, 2, 16, 256, 256);
                sideStep++;
                if(sideStep > 6)
                {
                    sideStep =0;
                    verticalStep++;
                }
                continue;
            }
            if(be.smeltTicks.get(i) > 0)
            {
                RenderSystem.setShader(GameRenderer::getPositionTexShader);
                RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
                RenderSystem.setShaderTexture(0, WIDGETS);
                x1 = x + 31;
                y1 = y + 17;
                cookTime = be.cookTicks.get(i);
                unitSize = Math.round((((float) be.smeltTicks.get(i)) / ((float) cookTime)) * 16F);

                drawTexture(matrices, x1 + (sideStep * 20), y1 + (verticalStep * 18) + 16 - unitSize, 38, 1 + 16 - unitSize, 2, unitSize, 256, 256);
                sideStep++;
                if(sideStep > 6)
                {
                    sideStep =0;
                    verticalStep++;
                }
            }
        }
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        drawMouseoverTooltip(matrices, mouseX, mouseY);

        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        buttonDownWidget.setPos(x + 155, y + 52);
        buttonUpWidget.setPos(x + 155, y + 16);
        buttonFluidWidget.setPos(x + 28, y - 27);

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, WIDGETS);
        RenderSystem.enableDepthTest();
        drawTexture(matrices, x, y - 27, 0, 66, 28, 30, 256, 256);

        handler.updateClient();
    }

    @Override
    protected void init() {
        super.init();

        if(getScreenHandler().shouldMouse) {
            MouseAccessor mouse = ((MouseAccessor) MinecraftClient.getInstance().mouse);

            mouse.setX(getScreenHandler().mouseX);
            mouse.setY(getScreenHandler().mouseY);

            InputUtil.setCursorParameters(this.client.getWindow().getHandle(), 212993, getScreenHandler().mouseX, getScreenHandler().mouseY);
        }

        this.addDrawableChild(buttonDownWidget);
        this.addDrawableChild(buttonUpWidget);
        this.addDrawableChild(buttonFluidWidget);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {

        if(buttonUp) {
            buttonUp = false;
            MinecraftClient.getInstance().interactionManager.clickButton(getScreenHandler().syncId, 0);
            getScreenHandler().clientCalculateShift(0);
        }
        if(buttonDown)
        {
            buttonDown = false;
            MinecraftClient.getInstance().interactionManager.clickButton(getScreenHandler().syncId, 1);
            getScreenHandler().calculateSlots();
            getScreenHandler().clientCalculateShift(1);
        }
        if(buttonFluid)
        {
            buttonFluid = false;

            PacketByteBuf buf = PacketByteBufs.create();

            buf.writeInt(0);

            buf.writeBlockPos(be.getPos());
            buf.writeDouble(MinecraftClient.getInstance().mouse.getX());
            buf.writeDouble(MinecraftClient.getInstance().mouse.getY());

            ClientPlayNetworking.send(C2SConstants.AlloySmelteryOpenScreen, buf);
        }

        buttonUp = false;
        buttonDown = false;
        buttonFluid = false;

        return super.mouseReleased(mouseX, mouseY, button);
    }
}
