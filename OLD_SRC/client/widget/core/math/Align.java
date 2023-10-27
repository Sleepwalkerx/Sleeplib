package com.sleepwalker.sleeplib.client.widget.core.math;

import com.sleepwalker.sleeplib.math.Vector2i;
import net.minecraft.util.math.vector.Vector2f;

import javax.annotation.Nonnull;

public enum Align {

    LEFT_TOP(AlignX.LEFT, AlignY.TOP),
    CENTER_TOP(AlignX.CENTER, AlignY.TOP),
    RIGHT_TOP(AlignX.RIGHT, AlignY.TOP),
    LEFT_MIDDLE(AlignX.LEFT, AlignY.MIDDLE),
    CENTER_MIDDLE(AlignX.CENTER, AlignY.MIDDLE),
    RIGHT_MIDDLE(AlignX.RIGHT, AlignY.MIDDLE),
    LEFT_BOTTOM(AlignX.LEFT, AlignY.BOTTOM),
    CENTER_BOTTOM(AlignX.CENTER, AlignY.BOTTOM),
    RIGHT_BOTTOM(AlignX.RIGHT, AlignY.BOTTOM);


    @Nonnull
    public final AlignX alignX;
    @Nonnull
    public final AlignY alignY;

    Align(@Nonnull AlignX alignX, @Nonnull AlignY alignY){
        this.alignX = alignX;
        this.alignY = alignY;
    }

    @Nonnull
    public Vector2f math(int elementWidth, int elementHeight, int parentWidth, int parentHeight){
        return new Vector2f(alignX.math(elementWidth, parentWidth), alignY.math(elementHeight, parentHeight));
    }

    @Nonnull
    public Vector2i mathInt(int elementWidth, int elementHeight, int parentWidth, int parentHeight){
        return new Vector2i((int)alignX.math(elementWidth, parentWidth), (int)alignY.math(elementHeight, parentHeight));
    }
}
