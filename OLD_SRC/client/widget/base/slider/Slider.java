package com.sleepwalker.sleeplib.client.widget.base.slider;

import com.sleepwalker.sleeplib.client.utils.ISavable;
import com.sleepwalker.sleeplib.client.widget.base.button.Button;
import com.sleepwalker.sleeplib.client.widget.base.sprite.ISprite;
import com.sleepwalker.sleeplib.client.widget.core.IExtraNestedGuiEventHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.DoubleNBT;
import net.minecraft.util.math.MathHelper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class Slider extends Button implements ISavable<DoubleNBT> {

    protected double value;
    protected float pos;
    protected int min, max, zoneSize;

    protected final Minecraft mc;

    @Nullable
    protected ISliderListener listener;

    protected final ISprite sprite;

    public Slider(@Nonnull ISprite sprite) {
        mc = Minecraft.getInstance();

        this.sprite = sprite;

        width = sprite.getWidth();
        height = sprite.getHeight();
    }

    public float getPos() {
        return pos;
    }

    public abstract void initSliderOnScreen(int posX, int posY, int max, int min, @Nonnull IExtraNestedGuiEventHandler parent);

    @Override
    public abstract boolean mouseDragged(double x, double y, int button, double deltaX, double deltaY);

    protected void onUpdateValue(){
        if(listener != null){
            listener.onChangeValue(value);
        }
    }

    @Nonnull
    @Override
    public DoubleNBT saveData() {
        return DoubleNBT.valueOf(value);
    }

    public int getMax() {
        return max;
    }

    public int getMin() {
        return min;
    }

    public int getZoneSize() {
        return zoneSize;
    }

    public void setPos(float pos) {
        this.pos = MathHelper.clamp(pos, min, max);
    }

    public void setValue(double value) {
        this.value = MathHelper.clamp(value, 0.0, 1.0);
        setPos((zoneSize * (float)value) + min);
        onUpdateValue();
    }

    public double getValue() {
        return value;
    }

    @Override
    public void readData(@Nullable DoubleNBT nbt) {

        if(nbt != null){
            value = nbt.getAsDouble();
            onUpdateValue();
        }
    }

    public void setSliderListener(@Nullable ISliderListener listener) {
        this.listener = listener;
    }

    public interface ISliderListener {
        void onChangeValue(double value);
    }
}
