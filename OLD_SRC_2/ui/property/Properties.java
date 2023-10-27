package com.sleepwalker.sleeplib.ui.property;

import com.sleepwalker.sleeplib.SleepLib;
import com.sleepwalker.sleeplib.ui.graphics.drawable.Drawable;
import com.sleepwalker.sleeplib.ui.graphics.drawable.Drawables;
import com.sleepwalker.sleeplib.ui.layout.WidgetSize;
import com.sleepwalker.sleeplib.ui.widget.ConstraintLayout;
import com.sleepwalker.sleeplib.ui.widget.Widget;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.text.ITextProperties;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

import javax.annotation.Nonnull;

@SuppressWarnings({"rawtypes", "unchecked"})
public final class Properties {

    @Nonnull
    public static final DeferredRegister<Property<?, ?>> REGISTER = DeferredRegister.create((Class)Property.class, SleepLib.MODID);

    @Nonnull public static final RegistryObject<Property<Widget, WidgetSize>> WIDTH = REGISTER.register("width", DimensionMatchProperty::new);
    @Nonnull public static final RegistryObject<Property<Widget, WidgetSize>> HEIGHT = REGISTER.register("height", DimensionMatchProperty::new);

    /*  ============ Layout Property ============= */

    @Nonnull public static final Property<Widget, Float> BASIC_LAYOUT_MATH = new FloatProperty<>(w -> 0f, Widget.class, updateLayout());

    @Nonnull public static final RegistryObject<Property<Widget, Float>> X = delegate("x", BASIC_LAYOUT_MATH);
    @Nonnull public static final RegistryObject<Property<Widget, Float>> Y = delegate("y", BASIC_LAYOUT_MATH);
    @Nonnull public static final RegistryObject<Property<Widget, Float>> BIAS_X = delegate("bias-x", BASIC_LAYOUT_MATH);
    @Nonnull public static final RegistryObject<Property<Widget, Float>> BIAS_Y = delegate("bias-y", BASIC_LAYOUT_MATH);
    @Nonnull public static final RegistryObject<Property<Widget, Float>> MARGIN_LEFT = delegate("margin-left", BASIC_LAYOUT_MATH);
    @Nonnull public static final RegistryObject<Property<Widget, Float>> MARGIN_RIGHT = delegate("margin-right", BASIC_LAYOUT_MATH);
    @Nonnull public static final RegistryObject<Property<Widget, Float>> MARGIN_TOP = delegate("margin-top", BASIC_LAYOUT_MATH);
    @Nonnull public static final RegistryObject<Property<Widget, Float>> MARGIN_BOTTOM = delegate("margin-bottom", BASIC_LAYOUT_MATH);
    @Nonnull public static final RegistryObject<Property<Widget, ConstraintLayout.SideAttach>>
        CONSTRAINT_ATTACH_LEFT = REGISTER.register("constraint-attach-left", () -> new ConstraintLayout.SideAttachProperty(ConstraintLayout.DEFAULT_ATTACH_LEFT)),
        CONSTRAINT_ATTACH_RIGHT = REGISTER.register("constraint-attach-right", () -> new ConstraintLayout.SideAttachProperty(ConstraintLayout.DEFAULT_ATTACH_RIGHT)),
        CONSTRAINT_ATTACH_TOP = REGISTER.register("constraint-attach-top", () -> new ConstraintLayout.SideAttachProperty(ConstraintLayout.DEFAULT_ATTACH_TOP)),
        CONSTRAINT_ATTACH_BOTTOM = REGISTER.register("constraint-attach-bottom", () -> new ConstraintLayout.SideAttachProperty(ConstraintLayout.DEFAULT_ATTACH_BOTTOM));

    /*  ============ TextComponent Property ============= */

    @Nonnull public static final Property<Widget, Float> BASIC_DRAWABLE_MATH = new FloatProperty<>(w -> 0f, Widget.class, doNothing());
    @Nonnull public static final Property<Widget, Integer> BASIC_DRAWABLE_COLOR = new ColorProperty<>(w -> 0xFFFFFF, Widget.class, doNothing());
    @Nonnull public static final Property<Widget, Boolean> BASIC_DRAWABLE_TRUE = new BoolProperty<>(w -> true, Widget.class, doNothing());
    @Nonnull public static final Property<Widget, Integer> BASIC_DRAWABLE_INT_MAX = new IntProperty<>(w -> Integer.MAX_VALUE, Widget.class, doNothing());
    @Nonnull public static final Property<Widget, Float> BASIC_ALPHA = new FloatProperty<>(w -> 1f, Widget.class, doNothing());

    @Nonnull public static final RegistryObject<Property<Widget, Float>> ANIMATION_X = delegate("animation-x", BASIC_DRAWABLE_MATH);
    @Nonnull public static final RegistryObject<Property<Widget, Float>> ANIMATION_Y = delegate("animation-y", BASIC_DRAWABLE_MATH);

    @Nonnull public static final RegistryObject<Property<Widget, FontRenderer>> FONT = REGISTER.register("font", FontProperty::new);
    @Nonnull public static final RegistryObject<Property<Widget, Float>> LINE_HEIGHT = REGISTER.register("line-height",
        () -> new FloatProperty<>(w -> (float)w.getPropertyValue(FONT).lineHeight, Widget.class, doNothing())
    );
    @Nonnull public static final RegistryObject<Property<Widget, ITextProperties>> TEXT = REGISTER.register("text", TextProperty::new);
    @Nonnull public static final RegistryObject<Property<Widget, Integer>> MAX_LINES = delegate("max-lines", BASIC_DRAWABLE_INT_MAX);
    @Nonnull public static final RegistryObject<Property<Widget, Boolean>> HIDE_OVERFLOW = delegate("hide-overflow", BASIC_DRAWABLE_TRUE);
    @Nonnull public static final RegistryObject<Property<Widget, Boolean>> TEXT_SHADOW = delegate("text-shadow", BASIC_DRAWABLE_TRUE);
    @Nonnull public static final RegistryObject<Property<Widget, Integer>> TEXT_COLOR = delegate("text-color", BASIC_DRAWABLE_COLOR);
    @Nonnull public static final RegistryObject<Property<Widget, Float>> TEXT_ALPHA = delegate("text-alpha", BASIC_ALPHA);
    @Nonnull public static final RegistryObject<Property<Widget, Float>> TEXT_OFFSET_X = delegate("text-offset-x", BASIC_DRAWABLE_MATH);
    @Nonnull public static final RegistryObject<Property<Widget, Float>> TEXT_OFFSET_Y = delegate("text-offset-y", BASIC_DRAWABLE_MATH);
    @Nonnull public static final RegistryObject<Property<Widget, Float>> TEXT_BIAS_X = delegate("text-bias-x", BASIC_DRAWABLE_MATH);
    @Nonnull public static final RegistryObject<Property<Widget, Float>> TEXT_BIAS_Y = delegate("text-bias-y", BASIC_DRAWABLE_MATH);

    /*  ============ DrawableComponent Property ============= */

    @Nonnull public static final Property<Widget, Drawable> BASIC_SRC = new DrawableProperty<>(w -> Drawables.EMPTY, Widget.class, doNothing());

    @Nonnull public static final RegistryObject<Property<Widget, Drawable>> BACKGROUND = delegate("background", BASIC_SRC);
    @Nonnull public static final RegistryObject<Property<Widget, Drawable>> SRC = delegate("src", BASIC_SRC);

    public static <T extends Widget, V> RegistryObject<Property<T, V>> delegate(@Nonnull String name, @Nonnull Property<T, V> property){
        return REGISTER.register(name, () -> new PropertyDelegate<>(property));
    }

    @Nonnull private static final BaseProperty.Listener<Widget, ?> UPDATE_LAYOUT = (o, o2) -> o.getParent().updateLayout();

    @SuppressWarnings("unchecked")
    @Nonnull
    public static <T extends Widget, V> BaseProperty.Listener<T, V> updateLayout(){
        return (BaseProperty.Listener<T, V>) UPDATE_LAYOUT;
    }

    @SuppressWarnings("rawtypes")
    @Nonnull private static final BaseProperty.Listener DO_NOTHING = (t, v) -> {};

    @SuppressWarnings("unchecked")
    @Nonnull
    public static <T, V> BaseProperty.Listener<T, V> doNothing(){
        return DO_NOTHING;
    }
}
