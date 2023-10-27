package com.sleepwalker.sleeplib.client.wrap;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.sleepwalker.sleeplib.math.Vector2i;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import com.sleepwalker.sleeplib.client.SLSprites;
import com.sleepwalker.sleeplib.client.utils.SpriteUtils;
import com.sleepwalker.sleeplib.client.widget.base.button.Button;
import com.sleepwalker.sleeplib.client.widget.base.sprite.ISprite;
import com.sleepwalker.sleeplib.client.widget.base.text.SimpleText;
import com.sleepwalker.sleeplib.client.widget.core.BaseNestedWidget;
import com.sleepwalker.sleeplib.client.widget.core.IExtraNestedGuiEventHandler;
import com.sleepwalker.sleeplib.client.widget.core.math.Align;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public class WrapContentElement<T> extends BaseNestedWidget {

    protected final T element;

    @Nonnull
    protected final WrapContentEditor<T> parent;
    @Nonnull
    private final ActionButton actionButton;
    @Nonnull
    private final SimpleText text;

    public WrapContentElement(@Nonnull ITextComponent name, T element, @Nonnull WrapContentEditor<T> parent, @Nonnull WrapListAction action) {

        String str = Minecraft.getInstance().font.substrByWidth(name, parent.getWidth() - 8).getString();

        if(str.length() != name.getString().length()){
            name = new StringTextComponent(str.substring(0, str.length() - 3) + "...");
        }

        text = new SimpleText(name, Align.LEFT_MIDDLE, new Vector2f(3, 0));

        setHeight(20);

        this.parent = parent;
        this.element = element;

        actionButton = new ActionButton();
        actionButton.action = action;
    }

    public WrapContentElement(@Nonnull ITextComponent name, T element, @Nonnull WrapContentEditor<T> parent) {
        this(name, element, parent, WrapListAction.NONE);
    }

    @Override
    public void initOnScreen(int posX, int posY, int width, int height, @Nonnull IExtraNestedGuiEventHandler screen) {
        super.initOnScreen(posX, posY, parent.getWidth(), height, screen);
        text.mathTextPos(this);

        ISprite img = actionButton.action.sprite;

        actionButton.initOnScreen(posX, posY, img.getWidth(), img.getHeight(), screen);
    }

    @Nonnull
    public SimpleText getText() {
        return text;
    }

    @Override
    public void setPosY(int posY) {
        actionButton.setPosY(actionButton.getPosY() + posY - this.posY);

        Vector2f vector2f = text.getTextPosCache();
        text.setTextPosCache(new Vector2f(vector2f.x, vector2f.y + posY - this.posY));

        super.setPosY(posY);
    }

    @Override
    public void renderWidget(@Nonnull MatrixStack ms, int mX, int mY, float pt) {
        SLSprites.RECT_4.render(ms, posX, posY, width, height, isMouseFocused() ? 1 : 0);
        text.render(ms, mX, mY, pt);
        actionButton.render(ms, mX, mY, pt);
    }

    @Nonnull
    public WrapListAction getAction() {
        return actionButton.action;
    }

    public void setAction(@Nonnull WrapListAction action){

        actionButton.action = action;

        if(action != WrapListAction.NONE){
            Vector2i vector2i = SpriteUtils.spriteAlign(action.sprite, Align.RIGHT_MIDDLE, width, height);
            actionButton.setSize(action.sprite.getWidth(), action.sprite.getHeight());
            actionButton.setPosition(vector2i.x + posX - 4, vector2i.y + posY);
        }
    }

    public void switchAction(){
        setAction(actionButton.action == WrapListAction.ADD ? WrapListAction.DELETE : WrapListAction.ADD);
    }

    public T getElement() {
        return element;
    }

    private class ActionButton extends Button {

        @Nonnull
        private WrapListAction action = WrapListAction.NONE;

        @Override
        public void initOnScreen(int posX, int posY, int width, int height, @Nonnull IExtraNestedGuiEventHandler parent) {
            parent.addChildren(this);

            setSize(width, height);

            Vector2i vector2i = SpriteUtils.spriteAlign(action.sprite, Align.RIGHT_MIDDLE,
                WrapContentElement.this.width, WrapContentElement.this.height);
            setPosition(vector2i.x + posX - 4, vector2i.y + posY);
        }

        @Override
        public void renderWidget(@Nonnull MatrixStack ms, int mX, int mY, float pt) {

            if(action != WrapListAction.NONE){

                if(isMouseFocused()){
                    RenderSystem.color4f(1.0f, pressed ? 0.7f : 0.8f, 1.0f, 1.0f);
                }
                RenderSystem.enableAlphaTest();
                action.sprite.render(ms, posX, posY);
                RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
            }
        }

        @Override
        public boolean mouseReleased(double x, double y, int button) {

            if(super.mouseReleased(x, y, button) && action != WrapListAction.NONE){
                parent.onAction(WrapContentElement.this);
                return true;
            }

            return false;
        }
    }

    public enum WrapListAction {

        NONE(SLSprites.EMPTY), DELETE(SLSprites.TRASH_ICON), ADD(SLSprites.GREEN_PLUS_ICON);

        @Nonnull
        public final ISprite sprite;

        WrapListAction(@Nonnull ISprite sprite){
            this.sprite = sprite;
        }
    }
}
