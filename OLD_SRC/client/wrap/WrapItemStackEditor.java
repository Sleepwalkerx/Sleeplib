package com.sleepwalker.sleeplib.client.wrap;

import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.sleepwalker.sleeplib.client.utils.SLGuiUtils;
import com.sleepwalker.sleeplib.client.wrap.objgrid.WrapItemCell;
import com.sleepwalker.sleeplib.client.wrap.widget.BaseWrapNestedCanvas;
import com.sleepwalker.sleeplib.client.wrap.widget.WCCloseButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import com.sleepwalker.sleeplib.client.SLSprites;
import com.sleepwalker.sleeplib.client.widget.base.button.Button;
import com.sleepwalker.sleeplib.client.widget.base.button.DisposableTextButton;
import com.sleepwalker.sleeplib.client.widget.base.inputfield.TextField;
import com.sleepwalker.sleeplib.client.widget.core.IExtraNestedGuiEventHandler;
import com.sleepwalker.sleeplib.client.widget.grouped.IntegerExtraField;
import com.sleepwalker.sleeplib.client.wrap.objgrid.WrapObjectGrid;

import javax.annotation.Nonnull;
import java.util.function.Consumer;

public class WrapItemStackEditor extends BaseWrapNestedCanvas {

    private final IntegerExtraField countField;
    private final TextField nbtField;
    private final DisposableTextButton confirmButton;
    private final DisposableTextButton editItemRegistry;
    private final WCCloseButton closeButton;

    private final ITextComponent countText, nbtText;
    private ITextComponent itemNameText;

    private final ItemRenderer ir;
    private final FontRenderer fr;

    private final boolean isLimited;

    @Nonnull
    private ItemStack itemStack;
    @Nonnull
    private final Consumer<ItemStack> itemStackConsumer;

    public WrapItemStackEditor(@Nonnull ItemStack itemStack, @Nonnull Consumer<ItemStack> itemStackConsumer) {
        this(itemStack, true, itemStackConsumer);
    }

    public WrapItemStackEditor(@Nonnull ItemStack itemStack, boolean isLimited, @Nonnull Consumer<ItemStack> itemStackConsumer) {

        this.itemStack = itemStack;
        this.itemStackConsumer = itemStackConsumer;
        this.isLimited = isLimited;

        ir = Minecraft.getInstance().getItemRenderer();
        fr = Minecraft.getInstance().font;

        setSize(200, 128);

        itemNameText = itemStack.getHoverName();

        countField = new IntegerExtraField();
        countField.setSize(40, 20);
        countField.getIntegerField().setNumberConsumer(this::onEditIntegerField);
        countText = new TranslationTextComponent(langKey("itemStackEditor.itemCount"));

        nbtField = new TextField();
        nbtField.setSize(width - 10, 20);
        nbtField.setResponder(s -> {

            JsonParser parser = new JsonParser();

            try {
                parser.parse(s);
                nbtField.setTextColor(-1);
            }
            catch (JsonSyntaxException ignore){
                nbtField.setTextColor(0xff4d4d);
            }

        });
        nbtField.setMaxLength(Integer.MAX_VALUE);
        nbtText = new StringTextComponent("NBT");

        confirmButton = createConfirmButton();
        confirmButton.setClickListener(this::onConfirmClick);

        editItemRegistry = new DisposableTextButton(new TranslationTextComponent(langKey("itemStackEditor.changeItem")));
        editItemRegistry.setSize(Minecraft.getInstance().font.width(editItemRegistry.getText()) + 8, 20);
        editItemRegistry.setClickListener(this::onEditRegistryClick);

        closeButton = createCloseButton();

        initItemStack();
    }

    private void onEditIntegerField(int count){
        this.itemStack.setCount(count);
    }

    private void initItemStack(){

        countField.getIntegerField().setBoundaries(isLimited ? itemStack.getMaxStackSize() : Integer.MAX_VALUE, 1);
        countField.getIntegerField().setNumber(itemStack.getCount());

        if(itemStack.getTag() != null){
            nbtField.setValue(itemStack.getTag().toString());
        }
        else {
            nbtField.setValue("");
        }

        itemNameText = itemStack.getHoverName();
    }

    private void onConfirmClick(Button element, int button) {

        try {
            CompoundNBT compoundNBT = JsonToNBT.parseTag(nbtField.getValue());
            itemStack.setTag(compoundNBT);
        }
        catch (CommandSyntaxException ignore){
        }

        itemStackConsumer.accept(itemStack);
        wrapHandler.deactivateWrapCanvas();
    }

    private void onEditRegistryClick(Button element, int button){

        WrapObjectGrid<WrapItemCell> wrapObjectGrid = new WrapObjectGrid<>(
            WrapItemCell.makeProviderListener(WrapItemCell::new),
            wrapItemCellWrapObjectGrid -> {

                if(wrapItemCellWrapObjectGrid.getSelectedObjects().isEmpty()){
                    return;
                }

                ItemStack newStack = wrapItemCellWrapObjectGrid.getSelectedObjects().get(0).getItemStack();



                if(!isLimited || (!itemStack.isEmpty() && newStack.getMaxStackSize() >= itemStack.getMaxStackSize())){
                    newStack.setCount(itemStack.getCount());
                }

                this.itemStack = newStack;

                initItemStack();
            }
        );
        wrapObjectGrid.setOverflowSwitch(true);
        wrapObjectGrid.setMaxSelectedCount(1);

        wrapHandler.activateWrapCanvas(wrapObjectGrid);
    }

    @Override
    public void initOnScreen(int posX, int posY, int width, int height, @Nonnull IExtraNestedGuiEventHandler parent) {
        super.initOnScreen(posX, posY, width, height, parent);

        countField.initOnScreen(posX + 5, posY + 40, this);
        nbtField.initOnScreen(posX + 5, posY + 77, this);
        closeButton.defaultInitOnScreen(this);

        int endY = getPosEndY() - confirmButton.getHeight() - 4;

        confirmButton.initOnScreen(posEndX - 5 - confirmButton.getWidth(), endY, this);
        editItemRegistry.initOnScreen(posX + 5, endY, this);
    }

    @Override
    public void renderWidget(@Nonnull MatrixStack ms, int mX, int mY, float pt) {

        RenderSystem.disableDepthTest();
        SLSprites.RECT_3.render(ms, posX, posY, width, height);
        SLSprites.RECT_6.render(ms, posX + 5, posY + 5, 18, 18);
        ir.renderGuiItem(itemStack, posX + 6, posY + 6);
        ir.renderGuiItemDecorations(fr, itemStack, posX + 6, posY + 6);

        fr.draw(ms, itemNameText, posX + 26, posY + 10, -1);

        fr.draw(ms, countText, countField.getPosX(), countField.getPosY() - fr.lineHeight - 1, 0xA4A4A4);
        countField.render(ms, mX, mY, pt);

        fr.draw(ms, nbtText, nbtField.getPosX(), nbtField.getPosY() - fr.lineHeight - 1, 0xA4A4A4);
        nbtField.render(ms, mX, mY, pt);

        confirmButton.render(ms, mX, mY, pt);
        editItemRegistry.render(ms, mX, mY, pt);

        closeButton.render(ms, mX, mY, pt);

        if(isMouseFocused() && (mX > posX + 6 && mX <= posX + 6 + 16 && mY > posY + 6 && mY <= posY + 6 + 16)){
            SLGuiUtils.renderItemTooltip(ms, itemStack, mX, mY);
        }
    }
}
