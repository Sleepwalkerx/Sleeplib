package com.sleepwalker.sleeplib.client.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import com.sleepwalker.sleeplib.client.wrap.IWrapCanvas;
import com.sleepwalker.sleeplib.client.wrap.IWrapHandler;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseExtraWrappedContainerScreen<T extends Container> extends BaseExtraContainerScreen<T> implements IWrapHandler {

    @Nonnull
    protected final List<WrapCanvasHolder> wrapCanvasQueue = new ArrayList<>();

    public BaseExtraWrappedContainerScreen(T pMenu, PlayerInventory pPlayerInventory, ITextComponent pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    public void render(@Nonnull MatrixStack ms, int mX, int mY, float pt) {

        if(wrapCanvasQueue.isEmpty()){
            renderBackground(ms);
        }

        renderSuper(ms, mX, mY, pt);
        renderElements(ms, mX, mY, pt);
        renderFloating(mX, mY);

        if(!wrapCanvasQueue.isEmpty()){

            for(int i = 0; i < wrapCanvasQueue.size() - 1; i++){
                ms.translate(0, 0, getBlitOffset() + 200);
                wrapCanvasQueue.get(i).getWrapCanvas().render(ms, mX, mY, pt);
            }

            ms.translate(0, 0, getBlitOffset() + 200);
            renderBackground(ms);
            wrapCanvasQueue.get(wrapCanvasQueue.size() - 1).getWrapCanvas().render(ms, mX, mY, pt);
        }
    }

    private void renderSuper(MatrixStack pMatrixStack, int pMouseX, int pMouseY, float pPartialTicks){

        int i = this.leftPos;
        int j = this.topPos;
        this.renderBg(pMatrixStack, pPartialTicks, pMouseX, pMouseY);
        net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.GuiContainerEvent.DrawBackground(this, pMatrixStack, pMouseX, pMouseY));
        RenderSystem.disableRescaleNormal();
        RenderSystem.disableDepthTest();

        for(int c = 0; i < this.buttons.size(); ++c) {
            this.buttons.get(c).render(pMatrixStack, pMouseX, pMouseY, pPartialTicks);
        }

        RenderSystem.pushMatrix();
        RenderSystem.translatef((float)i, (float)j, 0.0F);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableRescaleNormal();
        this.hoveredSlot = null;
        RenderSystem.glMultiTexCoord2f(33986, 240.0F, 240.0F);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);

        for(int i1 = 0; i1 < this.menu.slots.size(); ++i1) {
            Slot slot = this.menu.slots.get(i1);
            if (slot.isActive()) {
                this.renderSlot(pMatrixStack, slot);
            }

            if (this.isHovering(slot, pMouseX, pMouseY) && slot.isActive()) {
                this.hoveredSlot = slot;
                RenderSystem.disableDepthTest();
                int j1 = slot.x;
                int k1 = slot.y;
                RenderSystem.colorMask(true, true, true, false);
                int slotColor = this.getSlotColor(i1);
                this.fillGradient(pMatrixStack, j1, k1, j1 + 16, k1 + 16, slotColor, slotColor);
                RenderSystem.colorMask(true, true, true, true);
                RenderSystem.enableDepthTest();
            }
        }

        this.renderLabels(pMatrixStack, pMouseX, pMouseY);
        net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.client.event.GuiContainerEvent.DrawForeground(this, pMatrixStack, pMouseX, pMouseY));

        RenderSystem.popMatrix();
        RenderSystem.enableDepthTest();
    }

    private void renderFloating(int mX, int mY){

        RenderSystem.disableRescaleNormal();
        RenderSystem.disableDepthTest();
        RenderSystem.pushMatrix();
        RenderSystem.translatef((float)leftPos, (float)topPos, 0.0F);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableRescaleNormal();
        RenderSystem.glMultiTexCoord2f(33986, 240.0F, 240.0F);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);

        PlayerInventory playerinventory = this.minecraft.player.inventory;
        ItemStack itemstack = this.draggingItem.isEmpty() ? playerinventory.getCarried() : this.draggingItem;
        if (!itemstack.isEmpty()) {
            int j2 = 8;
            int k2 = this.draggingItem.isEmpty() ? 8 : 16;
            String s = null;
            if (!this.draggingItem.isEmpty() && this.isSplittingStack) {
                itemstack = itemstack.copy();
                itemstack.setCount(MathHelper.ceil((float)itemstack.getCount() / 2.0F));
            } else if (this.isQuickCrafting && this.quickCraftSlots.size() > 1) {
                itemstack = itemstack.copy();
                itemstack.setCount(this.quickCraftingRemainder);
                if (itemstack.isEmpty()) {
                    s = "" + TextFormatting.YELLOW + "0";
                }
            }

            this.renderFloatingItem(itemstack, mX - this.leftPos - 8, mY - this.topPos - k2, s);
        }

        if (!this.snapbackItem.isEmpty()) {
            float f = (float)(Util.getMillis() - this.snapbackTime) / 100.0F;
            if (f >= 1.0F) {
                f = 1.0F;
                this.snapbackItem = ItemStack.EMPTY;
            }

            int l2 = this.snapbackEnd.x - this.snapbackStartX;
            int i3 = this.snapbackEnd.y - this.snapbackStartY;
            int l1 = this.snapbackStartX + (int)((float)l2 * f);
            int i2 = this.snapbackStartY + (int)((float)i3 * f);
            this.renderFloatingItem(this.snapbackItem, l1, i2, null);
        }

        RenderSystem.popMatrix();
        RenderSystem.enableDepthTest();
    }

    private void renderFloatingItem(@Nonnull ItemStack pStack, int pX, int pY, String pAltText) {
        RenderSystem.translatef(0.0F, 0.0F, 32.0F);
        this.setBlitOffset(200);
        this.itemRenderer.blitOffset = 200.0F;
        net.minecraft.client.gui.FontRenderer font = pStack.getItem().getFontRenderer(pStack);
        if (font == null) font = this.font;
        this.itemRenderer.renderAndDecorateItem(pStack, pX, pY);
        this.itemRenderer.renderGuiItemDecorations(font, pStack, pX, pY - (this.draggingItem.isEmpty() ? 0 : 8), pAltText);
        this.setBlitOffset(0);
        this.itemRenderer.blitOffset = 0.0F;
    }

    protected abstract void initScreen();

    protected abstract void renderElements(@Nonnull MatrixStack ms, int mX, int mY, float pt);

    @Override
    protected void init() {
        super.init();
        initScreen();
        initWrap();
    }

    protected void initWrap(){

        if(!wrapCanvasQueue.isEmpty()){
            activateWrapCanvas(wrapCanvasQueue.get(wrapCanvasQueue.size() - 1).getWrapCanvas());
        }
    }

    @Override
    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {

        if(pKeyCode == 256 && !wrapCanvasQueue.isEmpty()){
            deactivateWrapCanvas();
            return true;
        }
        else return super.keyPressed(pKeyCode, pScanCode, pModifiers);
    }

    @Override
    public void activateWrapCanvas(@Nonnull IWrapCanvas wrapCanvas, @Nonnull IWrapHandler wrapHandler) {

        WrapCanvasHolder holder = new WrapCanvasHolder(wrapCanvas);

        wrapCanvas.setWrapHandler(wrapHandler);

        holder.getListeners().addAll(children);
        children.clear();

        wrapCanvas.initOnScreen(
            (leftPos + imageWidth / 2) - wrapCanvas.getWidth() / 2,
            (topPos + imageHeight / 2) - wrapCanvas.getHeight() / 2,
            this
        );

        wrapCanvasQueue.add(holder);
    }

    public void deactivateWrapCanvas(){

        if(wrapCanvasQueue.isEmpty()){
            return;
        }

        WrapCanvasHolder holder = wrapCanvasQueue.remove(wrapCanvasQueue.size() - 1);

        holder.getWrapCanvas().onClose();

        children.clear();
        children.addAll(holder.getListeners());

        holder.getListeners().clear();
    }
}
