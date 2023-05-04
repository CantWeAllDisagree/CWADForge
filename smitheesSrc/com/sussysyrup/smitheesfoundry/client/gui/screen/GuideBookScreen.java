package com.sussysyrup.smitheesfoundry.client.gui.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.sussysyrup.smitheesfoundry.Main;
import com.sussysyrup.smitheesfoundry.api.item.ApiToolRegistry;
import com.sussysyrup.smitheesfoundry.api.item.ToolItem;
import com.sussysyrup.smitheesfoundry.api.material.ApiMaterialRegistry;
import com.sussysyrup.smitheesfoundry.api.material.Material;
import com.sussysyrup.smitheesfoundry.api.modification.ApiModificationRegistry;
import com.sussysyrup.smitheesfoundry.api.modification.ModificationRecipe;
import com.sussysyrup.smitheesfoundry.api.recipe.ApiToolRecipe;
import com.sussysyrup.smitheesfoundry.api.recipe.ApiToolRecipeRegistry;
import com.sussysyrup.smitheesfoundry.api.trait.TraitContainer;
import com.sussysyrup.smitheesfoundry.util.Util;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.PageTurnWidget;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Item;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableTextContent;
import net.minecraft.util.Identifier;
import java.util.*;

public class GuideBookScreen extends Screen {

    protected int backgroundWidth = 280;
    protected int backgroundHeight = 180;

    private PageTurnWidget nextPageButton;
    private PageTurnWidget previousPageButton;
    private PageTurnWidget returnButton;

    private TexturedButtonWidget toolsButton;
    private TexturedButtonWidget materialButton;

    private TexturedButtonWidget modifierButton;

    private static int page = 0;
    private int toolsIndex = 1;
    private int maxIndex = 0;

    private static final Identifier TEXTURE = new Identifier(Main.MODID, "textures/gui/guide_book.png");

    private static final Identifier WIDGETS = new Identifier(Main.MODID, "textures/gui/widgets.png");

    public GuideBookScreen(Text title) {
        super(title);
    }

    @Override
    protected void init() {
        super.init();

        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        nextPageButton = this.addDrawableChild(new PageTurnWidget(x + 274, y + 175, true, (button) -> {
            this.goToNextPage();
        }, true));
        previousPageButton = this.addDrawableChild(new PageTurnWidget(x - 18,y + 175, false, (button) -> {
            this.goToPreviousPage();
        }, true));
        returnButton = this.addDrawableChild(new PageTurnWidget(x + 128,y +180, false, (button) -> {
            this.goToRoot();
        }, true));
        //110 width
        toolsButton = this.addDrawableChild(new TexturedButtonWidget(x + 150,y + 15, 48, 48, 56, 0, WIDGETS, (button) -> {
            this.goToTools();
        }));
        materialButton = this.addDrawableChild(new TexturedButtonWidget(x + 212,y + 15, 48, 48, 194, 0, WIDGETS, (button) -> {
            this.goToMats();
        }));
        modifierButton = this.addDrawableChild(new TexturedButtonWidget(x + 150,y + 75, 48, 48, 56, 96, WIDGETS, (button) -> {
            this.goToMods();
        }));

        maxIndex = 0 + Math.round(((float) ApiToolRegistry.getInstance().getTools().size()) / 2F) +
                ApiMaterialRegistry.getInstance().getMaterials().size()+ Math.round(((float) ApiModificationRegistry.getInstance().getModificationKeys().size()) / 2F);

        toolsButton.visible = false;
        toolsButton.active = false;

        materialButton.visible = false;
        materialButton.active = false;

        modifierButton.visible = false;
        modifierButton.active = false;
    }

    private void goToMods() {
        page = 1 + Math.round(((float) ApiToolRegistry.getInstance().getTools().size()) / 2F) + ApiMaterialRegistry.getInstance().getMaterials().size();
    }

    private void goToMats() {
        page = 1 + Math.round(((float) ApiToolRegistry.getInstance().getTools().size()) / 2F);
    }

    private void goToTools() {
        page = toolsIndex;
    }

    private void goToRoot() {
        page = 0;
    }

    private void goToPreviousPage() {
        if(page > 0)
        {
            page--;
        }
    }

    private void goToNextPage() {
        if(page < maxIndex) {
            page++;
        }
    }

    @Override
    public void renderBackground(MatrixStack matrices) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;
        drawTexture(matrices, x, y, 0, 0, backgroundWidth, 180, 280, 217);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        renderPageContents(matrices);
    }

    //text width 120
    private void renderPageContents(MatrixStack matrixStack)
    {
        int x = (width - backgroundWidth) / 2;
        int y = (height - backgroundHeight) / 2;

        int textX = x + 14;
        int textY = y + 16;

        toolsButton.visible = false;
        toolsButton.active = false;

        materialButton.visible = false;
        materialButton.active = false;

        modifierButton.visible = false;
        modifierButton.active = false;

        if(page == 0)
        {
            Text text = Text.translatable("guide.smitheesfoundry.introduction1");

            String opString = text.getString();

            List<String> strings = Util.wrapString(textRenderer, opString, 120);

            for(int i = 0; i < strings.size(); i++) {
                textRenderer.draw(matrixStack, strings.get(i), textX, textY + (i*9), 0x0000000);
            }

            drawIndex(matrixStack, x, y);
        }

        for(int i = 0; i < ApiToolRegistry.getInstance().getTools().size(); i+=2)
        {
            if(page == (i/2) + 1)
            {
                buildToolPage(matrixStack, ApiToolRegistry.getInstance().getTools().get(i), x, y);

                if(i+1 < ApiToolRegistry.getInstance().getTools().size())
                {
                    buildToolPage(matrixStack, ApiToolRegistry.getInstance().getTools().get(i+1), x + 140, y);
                }
            }
        }

        for(int i = 0; i < ApiMaterialRegistry.getInstance().getMaterials().size(); i++)
        {
            if(page == Math.round(((float) ApiToolRegistry.getInstance().getTools().size()) / 2F) + 1 + i)
            buildMaterialpage(matrixStack, ApiMaterialRegistry.getInstance().getMaterials().get(i), x, y);
        }

        List<String> modKeys = new ArrayList<>(ApiModificationRegistry.getInstance().getModificationKeys());

        Collections.sort(modKeys);

        for(int i = 0; i < ApiModificationRegistry.getInstance().getModificationKeys().size(); i+=2)
        {
            if(page == (i/2) + 1 + Math.round(((float) ApiToolRegistry.getInstance().getTools().size()) / 2F) + ApiMaterialRegistry.getInstance().getMaterials().size())
            {
                buildModificationPage(matrixStack, modKeys.get(i), x, y);

                if(i+1 < ApiModificationRegistry.getInstance().getModificationKeys().size())
                {
                    buildModificationPage(matrixStack, modKeys.get(i+1), x + 140, y);
                }
            }
        }
    }

    private void buildModificationPage(MatrixStack matrices, String modKey, int x, int y) {
        ModificationRecipe recipe = ApiModificationRegistry.getInstance().getFromStringRecipe(modKey);

        String[] parts = modKey.split(":");

        String modTranslationKey = "modification.smitheesfoundry." + parts[0];

        Text modText = Text.translatable(modTranslationKey, Integer.parseInt(parts[1]));

        textRenderer.draw(matrices, modText, x + 67- (textRenderer.getWidth(modText) / 2), y + 16, 0x0000000);

        TranslatableTextContent modDesc = Text.translatable("guide.smitheesfoundry." + parts[0]);

        List<String> strings = Util.wrapString(textRenderer, modDesc.getString(), 120);

        for(int i = 0; i < strings.size(); i++) {
            if(i > 3)
            {
                break;
            }
            textRenderer.draw(matrices, strings.get(i), x + 14, y + 28 + (i*9), 0x0000000);
        }

        textRenderer.draw(matrices, Text.translatable("guide.smitheesfoundry.requirements"), x + 14, y + 70, 0x0000000);
        textRenderer.draw(matrices, Text.translatable("block." + recipe.fluid().getNamespace() + "." + recipe.fluid().getPath()), x+14, y+79, 0x5A5A5A);

        int i = 1;
        for(Identifier id : recipe.reactants().keySet())
        {
            strings = Util.wrapString(textRenderer, Text.translatable("guide.smitheesfoundry.itemcount", recipe.reactants().get(id), Registry.ITEM.get(id).getName()).getString(), 120);
            for(int z = 0; z < strings.size(); z++)
            {
                textRenderer.draw(matrices, Text.translatable("guide.smitheesfoundry.itemcount", recipe.reactants().get(id), Registry.ITEM.get(id).getName()), x + 14, y + 79 + (i*9), 0x5A5A5A);
                i++;
            }
        }
    }

    private void buildMaterialpage(MatrixStack matrices, Material material, int x, int y) {
        TranslatableTextContent materialText = Text.translatable(Util.materialTranslationKey(material));

        textRenderer.draw(matrices, materialText, x + 67- (textRenderer.getWidth(materialText) / 2), y + 16, 0x0000000);

        Item partItem = Registry.ITEM.get(new Identifier(Main.MODID, material.getName() + "_axehead"));

        MinecraftClient.getInstance().getItemRenderer().renderInGui(partItem.getDefaultStack(), x + 59, y + 26);

        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, WIDGETS);
        drawTexture(matrices, x + 55, y + 25, 104, 0, 24, 18, 256, 256);

        textRenderer.draw(matrices, Text.translatable("tool.smitheesfoundry.durability", material.getDurability()), x + 14, y + 48, 0x0000000);
        textRenderer.draw(matrices, Text.translatable("tool.smitheesfoundry.durabilitymult", material.getDurabilityMultiplier()), x + 14, y + 57, 0x0000000);
        textRenderer.draw(matrices, Text.translatable("tool.smitheesfoundry.miningspeed", material.getMiningSpeed()), x + 14, y + 66, 0x0000000);
        textRenderer.draw(matrices, Text.translatable("tool.smitheesfoundry.mininglevel", material.getMiningLevel()), x + 14, y + 75, 0x0000000);
        textRenderer.draw(matrices, Text.translatable("tool.smitheesfoundry.damage", material.getDamage()), x + 14, y + 84, 0x0000000);

        Set<TraitContainer> preTraits = new HashSet<>();

        preTraits.addAll(material.getTraits("head"));
        preTraits.addAll(material.getTraits("binding"));
        preTraits.addAll(material.getTraits("handle"));
        preTraits.addAll(material.getTraits("extra"));

        List<TraitContainer> traits = new ArrayList<>();

        traits.addAll(preTraits);

        if(traits.isEmpty())
        {
            return;
        }

        TraitContainer trait = traits.get(0);

        textRenderer.draw(matrices, trait.getTraitTranslation(), x + 67- (textRenderer.getWidth(trait.getTraitTranslation()) / 2), y + 102, 0x0000000);

        List<String> strings = Util.wrapString(textRenderer, trait.getTraitDescription().getString(), 120);

        for(int i = 0; i < strings.size(); i++) {
            textRenderer.draw(matrices, strings.get(i), x + 14, y + 120 + (i * 9), 0x0000000);
        }

        if(traits.size() > 1)
        {
            int height = 0;

            for(int z = 1; z < traits.size(); z++)
            {
                trait = traits.get(z);

                textRenderer.draw(matrices, trait.getTraitTranslation(), x + 140 + 67- (textRenderer.getWidth(trait.getTraitTranslation()) / 2), y + 16 + (height * 9), 0x0000000);

                height++;
                height++;

                strings = Util.wrapString(textRenderer, trait.getTraitDescription().getString(), 120);

                for(int i = 0; i < strings.size(); i++) {
                    textRenderer.draw(matrices, strings.get(i), x + 140 + 14, y + 16 + (height * 9), 0x0000000);
                    height++;
                }
                height++;
            }
        }
    }

    private void buildToolPage(MatrixStack matrices, String toolString, int x, int y) {
        ToolItem toolItem = (ToolItem) ApiToolRegistry.getInstance().getToolsItem().get(ApiToolRegistry.getInstance().getTools().indexOf(toolString));

        MinecraftClient.getInstance().getItemRenderer().renderInGui(toolItem.getDefaultStack(), x + 59, y + 26);

        TranslatableTextContent toolsText = Text.translatable("item.smitheesfoundry.forge_" + toolString);

        textRenderer.draw(matrices, toolsText, x + 67- (textRenderer.getWidth(toolsText) / 2), y + 16, 0x0000000);

        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, WIDGETS);
        drawTexture(matrices, x + 55, y + 25, 104, 0, 24, 18, 256, 256);

        drawTexture(matrices, x + 34, y + 55, 128, 0, 66, 56, 256, 256);

        ApiToolRecipe recipe = ApiToolRecipeRegistry.getInstance().getRecipeByType(toolString);

        String[] parts = (recipe.getKey().split(","));

        if(!parts[0].equals("empty")) {
            MinecraftClient.getInstance().getItemRenderer().renderInGui(Registry.ITEM.get(new Identifier(Main.MODID, "wood_" + parts[0])).getDefaultStack(), x + 59, y + 94);
        }
        if(!parts[1].equals("empty")) {
            MinecraftClient.getInstance().getItemRenderer().renderInGui(Registry.ITEM.get(new Identifier(Main.MODID, "wood_" + parts[1])).getDefaultStack(), x + 59, y + 75);
        }
        if(!parts[2].equals("empty")) {
            MinecraftClient.getInstance().getItemRenderer().renderInGui(Registry.ITEM.get(new Identifier(Main.MODID, "wood_" + parts[2])).getDefaultStack(), x + 59, y + 56);
        }
        if(!parts[4].equals("empty")) {
            MinecraftClient.getInstance().getItemRenderer().renderInGui(Registry.ITEM.get(new Identifier(Main.MODID, "wood_" + parts[4])).getDefaultStack(), x + 83, y + 75);
        }
        if(!parts[3].equals("empty")) {
            MinecraftClient.getInstance().getItemRenderer().renderInGui(Registry.ITEM.get(new Identifier(Main.MODID, "wood_" + parts[3])).getDefaultStack(), x + 35, y + 75);
        }

        TranslatableTextContent toolDesc = Text.translatable("guide.smitheesfoundry." + toolString);

        List<String> strings = Util.wrapString(textRenderer, toolDesc.getString(), 120);

        for(int i = 0; i < strings.size(); i++) {
            if(i > 5)
            {
                break;
            }
            textRenderer.draw(matrices, strings.get(i), x + 14, y + 115 + (i*9), 0x0000000);
        }
    }

    private void drawIndex(MatrixStack matrixStack, int x, int y) {
        toolsButton.active = true;
        toolsButton.visible = true;

        materialButton.active = true;
        materialButton.visible = true;

        modifierButton.active = true;
        modifierButton.visible = true;

        TranslatableTextContent toolsText = Text.translatable("guide.smitheesfoundry.tools");

        textRenderer.draw(matrixStack, toolsText, x + 174 - (textRenderer.getWidth(toolsText) / 2), y + 65, 0x0000000);

        TranslatableTextContent materialsText = Text.translatable("guide.smitheesfoundry.materials");

        textRenderer.draw(matrixStack, materialsText, x + 236 - (textRenderer.getWidth(materialsText) / 2), y + 65, 0x0000000);

        TranslatableTextContent modificationText = Text.translatable("guide.smitheesfoundry.modifications");

        textRenderer.draw(matrixStack, modificationText, x + 174 - (textRenderer.getWidth(modificationText) / 2), y + 125, 0x0000000);
    }

    @Override
    public boolean shouldPause() {
        return false;
    }
}
