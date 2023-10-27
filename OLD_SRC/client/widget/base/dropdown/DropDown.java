package com.sleepwalker.sleeplib.client.widget.base.dropdown;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.sleepwalker.sleeplib.client.utils.ISavable;
import com.sleepwalker.sleeplib.client.utils.SpriteUtils;
import com.sleepwalker.sleeplib.client.widget.base.button.TextButton;
import com.sleepwalker.sleeplib.client.widget.base.text.SimpleText;
import com.sleepwalker.sleeplib.client.widget.core.BaseWidget;
import com.sleepwalker.sleeplib.client.widget.core.IExtraNestedGuiEventHandler;
import com.sleepwalker.sleeplib.client.widget.core.ILostFocusListener;
import com.sleepwalker.sleeplib.client.widget.core.math.Align;
import com.sleepwalker.sleeplib.math.Vector2i;
import net.minecraft.nbt.IntNBT;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.text.ITextComponent;
import org.lwjgl.opengl.GL11;
import com.sleepwalker.sleeplib.client.SLSprites;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

public class DropDown<T> extends TextButton implements ISavable<IntNBT>, ILostFocusListener {

    @Nonnull
    private Vector2i imgPosCache = Vector2i.ZERO;

    private boolean dropped;

    @Nonnull
    protected DropElement current;
    @Nonnull
    protected final List<DropElement> elements;
    private IExtraNestedGuiEventHandler parent;
    @Nonnull
    protected final Function<T, ITextComponent> displayNameSupplier;

    public DropDown(@Nonnull Collection<T> elements, @Nonnull Function<T, ITextComponent> displayNameSupplier){
        this(elements, displayNameSupplier, null);
    }

    public DropDown(@Nonnull Collection<T> elements, @Nonnull Function<T, ITextComponent> displayNameSupplier, @Nullable T current) {
        super(Align.LEFT_MIDDLE);

        this.displayNameSupplier = displayNameSupplier;

        if(elements.size() == 0){
            throw new IllegalArgumentException("Elements cannot be empty");
        }

        this.elements = Lists.newArrayList();

        for(T e : elements){
            this.elements.add(new DropElement(e, displayNameSupplier.apply(e)));
        }

        if(current != null){
            Optional<DropElement> findCurrent = this.elements.stream().filter(e -> e.object == current).findFirst();

            if(findCurrent.isPresent()){
                setCurrent(findCurrent.get());
                return;
            }
        }

        setCurrent(this.elements.get(0));
    }

    public void setCurrent(@Nonnull T current){

        elements
            .stream()
            .filter(e -> e.object == current)
            .findFirst()
            .ifPresent(this::setCurrent);
    }

    private void setCurrent(@Nonnull DropElement element){
        this.current = element;
        setText(element.displayName.getText());
    }

    @Override
    public void initOnScreen(int posX, int posY, int width, int height, @Nonnull IExtraNestedGuiEventHandler parent) {
        super.initOnScreen(posX, posY, width, height, parent);

        this.parent = parent;

        imgPosCache = SpriteUtils.spriteAlign(SLSprites.DROP_ICON, Align.RIGHT_MIDDLE, width, height);

        AtomicInteger ing = new AtomicInteger();
        elements.forEach(dropElement -> dropElement.initOnScreen(posX, posY + height + ing.getAndAdd(height), width, height, parent));
    }

    @Override
    public void renderWidget(@Nonnull MatrixStack ms, int x, int y, float pt) {

        if(dropped){
            RenderSystem.enableDepthTest();
            GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
            elements.forEach(e -> e.render(ms, x, y, pt));
        }

        super.renderWidget(ms, x, y, pt);
        SLSprites.DROP_ICON.render(ms, posX + imgPosCache.x - 4, posY + imgPosCache.y, dropped ? 1 : 0);
    }

    @Override
    public boolean mouseClicked(double x, double y, int button) {

        if(!isMouseOver(x, y)){
            return false;
        }

        setDropped(!dropped);

        return true;
    }

    private void setDropped(boolean value){
        dropped = value;

        if(dropped){
            elements.forEach(dropElement -> parent.addChildren(dropElement));
        }
        else {
            elements.forEach(dropElement -> dropElement.forceRemove(parent));
        }
    }

    @Nonnull
    public T getCurrent() {
        return current.object;
    }

    @Nonnull
    @Override
    public IntNBT saveData() {
        return IntNBT.valueOf(elements.indexOf(current));
    }

    @Override
    public void readData(@Nonnull IntNBT nbt) {
        int id = nbt.getAsInt();
        if(id >= 0 && id < elements.size()){
            setCurrent(elements.get(id));
        }
    }

    @Override
    public void onLostFocus() {
        setDropped(false);
    }

    public class DropElement extends BaseWidget {

        @Nonnull
        private final T object;

        private final SimpleText displayName;

        public DropElement(@Nonnull T object, @Nonnull ITextComponent displayName) {
            this.object = object;
            this.displayName = new SimpleText(displayName, Align.CENTER_MIDDLE, new Vector2f(0, 1));
        }

        @Override
        public void initOnScreen(int posX, int posY, int width, int height, @Nonnull IExtraNestedGuiEventHandler screen) {
            setSize(width, height);
            setPosition(posX, posY);
            displayName.mathTextPos(this);
        }

        @Override
        public boolean mouseClicked(double x, double y, int id) {

            if(!isMouseOver(x, y)){
                return false;
            }

            setCurrent(this);
            setDropped(false);

            return true;
        }

        @Override
        public void renderWidget(@Nonnull MatrixStack ms, int x, int y, float pt) {
            SLSprites.RECT_4.render(ms, posX, posY, width, height, isMouseFocused() ? 1 : 0);
            displayName.render(ms, x, y, pt);
        }
    }
}
