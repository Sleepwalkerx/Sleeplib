package com.sleepwalker.sleeplib.ui.graphics.drawable;

import com.sleepwalker.sleeplib.ui.graphics.drawable.shape.*;
import com.sleepwalker.sleeplib.ui.property.PropertySource;

import javax.annotation.Nonnull;

public final class Drawables {

    @Nonnull public static final Drawable EMPTY = canvas -> {};

    @Nonnull public static final Drawable WHITE_RECT = new RectShape(ShapePadding.EMPTY, ShapeSize.FIT_CANVAS, new ShapeSolid(0xffFFFFFF), ShapeStroke.EMPTY);
    @Nonnull public static final Drawable BLACK_RECT = new RectShape(ShapePadding.EMPTY, ShapeSize.FIT_CANVAS, new ShapeSolid(0xff000000), ShapeStroke.EMPTY);
    @Nonnull public static final Drawable RED_RECT = new RectShape(ShapePadding.EMPTY, ShapeSize.FIT_CANVAS, new ShapeSolid(0xffff0000), ShapeStroke.EMPTY);
    @Nonnull public static final Drawable BLUE_RECT = new RectShape(ShapePadding.EMPTY, ShapeSize.FIT_CANVAS, new ShapeSolid(0xff00ccff), ShapeStroke.EMPTY);
}
