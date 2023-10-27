package com.sleepwalker.sleeplib.client.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.util.text.ITextComponent;
import com.sleepwalker.sleeplib.client.widget.core.ITooltipRenderable;
import com.sleepwalker.sleeplib.client.wrap.IWrapCanvas;
import com.sleepwalker.sleeplib.client.wrap.IWrapHandler;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseExtraWrappedScreen extends BaseExtraScreen implements IWrapHandler {

    @Nonnull
    protected final List<WrapCanvasHolder> wrapCanvasQueue = new ArrayList<>();

    protected BaseExtraWrappedScreen(ITextComponent name) {
        super(name);
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

    @SuppressWarnings("deprecation")
    @Override
    public void render(@Nonnull MatrixStack ms, int mX, int mY, float pt) {

        if(wrapCanvasQueue.isEmpty()){
            renderBackground(ms);
        }

        renderElements(ms, mX, mY, pt);

        if(!wrapCanvasQueue.isEmpty()){

            for(int i = 0; i < wrapCanvasQueue.size() - 1; i++){
                ms.translate(0, 0, getBlitOffset() + 200);
                wrapCanvasQueue.get(i).getWrapCanvas().render(ms, mX, mY, pt);
            }

            RenderSystem.pushMatrix();
            RenderSystem.translatef(0, 0, 200);
            renderBackground(ms);
            IWrapCanvas canvas = wrapCanvasQueue.get(wrapCanvasQueue.size() - 1).getWrapCanvas();
            canvas.render(ms, mX, mY, pt);
            if(canvas instanceof ITooltipRenderable){
                ((ITooltipRenderable) canvas).renderTooltips(ms, mX, mY, pt);
            }
            RenderSystem.popMatrix();
        }
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
}
