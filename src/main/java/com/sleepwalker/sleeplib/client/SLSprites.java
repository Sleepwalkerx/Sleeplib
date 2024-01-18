package com.sleepwalker.sleeplib.client;

import com.sleepwalker.sleeplib.SleepLib;
import com.sleepwalker.sleeplib.client.drawable.Drawable;
import com.sleepwalker.sleeplib.client.drawable.TextureSource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

import static com.sleepwalker.sleeplib.client.util.SpriteUtil.*;

@OnlyIn(Dist.CLIENT)
public final class SLSprites {

    @Nonnull public static final TextureSource DISPOSABLE_BUTTON_TEXTURE = texture("disposable_button", 96, 16);
    @Nonnull public static final Drawable DISPOSABLE_BUTTON_ENABLED = newNineSlice(DISPOSABLE_BUTTON_TEXTURE, 0, 0, 16, 16, 2, 3, 2, 6);
    @Nonnull public static final Drawable DISPOSABLE_BUTTON_FOCUSED = newNineSlice(DISPOSABLE_BUTTON_TEXTURE, 16, 0, 16, 16, 2, 3, 2, 6);
    @Nonnull public static final Drawable DISPOSABLE_BUTTON_PRESSED = newNineSlice(DISPOSABLE_BUTTON_TEXTURE, 32, 0, 16, 16, 2, 3, 2, 6);
    @Nonnull public static final Drawable DISPOSABLE_BUTTON_DISABLED = newNineSlice(DISPOSABLE_BUTTON_TEXTURE, 48, 0, 16, 16, 2, 3, 2, 6);
    @Nonnull public static final Drawable DISPOSABLE_BUTTON_SELECTED_ENABLED = newNineSlice(DISPOSABLE_BUTTON_TEXTURE, 64, 0, 16, 16, 2, 3, 2, 6);
    @Nonnull public static final Drawable DISPOSABLE_BUTTON_SELECTED_FOCUSED = newNineSlice(DISPOSABLE_BUTTON_TEXTURE, 80, 0, 16, 16, 2, 3, 2, 6);

    @Nonnull public static final TextureSource HORIZONTAL_LARGE_SLIDER_TEXTURE = texture("horizontal_large_slider", 128, 21);
    @Nonnull public static final Drawable HORIZONTAL_LARGE_SLIDER_ENABLED = newModal(HORIZONTAL_LARGE_SLIDER_TEXTURE, 0, 0, 32, 21);
    @Nonnull public static final Drawable HORIZONTAL_LARGE_SLIDER_FOCUSED = newModal(HORIZONTAL_LARGE_SLIDER_TEXTURE, 32, 0, 32, 21);
    @Nonnull public static final Drawable HORIZONTAL_LARGE_SLIDER_PRESSED = newModal(HORIZONTAL_LARGE_SLIDER_TEXTURE, 64, 0, 32, 21);
    @Nonnull public static final Drawable HORIZONTAL_LARGE_SLIDER_DISABLED = newModal(HORIZONTAL_LARGE_SLIDER_TEXTURE, 96, 0, 32, 21);

    @Nonnull public static final TextureSource HORIZONTAL_SLIDER_TEXTURE = texture("horizontal_slider", 28, 24);
    @Nonnull public static final Drawable HORIZONTAL_SLIDER_ENABLED_BACKGROUND = newNineSlice(HORIZONTAL_SLIDER_TEXTURE, 0, 0, 23, 6, 2, 1, 2, 2);
    @Nonnull public static final Drawable HORIZONTAL_SLIDER_ENABLED_FOREGROUND = newModal(HORIZONTAL_SLIDER_TEXTURE, 23, 2, 5, 2);
    @Nonnull public static final Drawable HORIZONTAL_SLIDER_ENABLED = makeSlider(HORIZONTAL_SLIDER_ENABLED_BACKGROUND, HORIZONTAL_SLIDER_ENABLED_FOREGROUND);
    @Nonnull public static final Drawable HORIZONTAL_SLIDER_FOCUSED_BACKGROUND = newNineSlice(HORIZONTAL_SLIDER_TEXTURE, 0, 6, 23, 6, 2, 1, 2, 2);
    @Nonnull public static final Drawable HORIZONTAL_SLIDER_FOCUSED_FOREGROUND = newModal(HORIZONTAL_SLIDER_TEXTURE, 23, 8, 5, 2);
    @Nonnull public static final Drawable HORIZONTAL_SLIDER_FOCUSED = makeSlider(HORIZONTAL_SLIDER_FOCUSED_BACKGROUND, HORIZONTAL_SLIDER_FOCUSED_FOREGROUND);
    @Nonnull public static final Drawable HORIZONTAL_SLIDER_PRESSED_BACKGROUND = newNineSlice(HORIZONTAL_SLIDER_TEXTURE, 0, 12, 23, 6, 2, 1, 2, 2);
    @Nonnull public static final Drawable HORIZONTAL_SLIDER_PRESSED_FOREGROUND = newModal(HORIZONTAL_SLIDER_TEXTURE, 23, 14, 5, 2);
    @Nonnull public static final Drawable HORIZONTAL_SLIDER_PRESSED = makeSlider(HORIZONTAL_SLIDER_PRESSED_BACKGROUND, HORIZONTAL_SLIDER_PRESSED_FOREGROUND);
    @Nonnull public static final Drawable HORIZONTAL_SLIDER_DISABLED_BACKGROUND = newNineSlice(HORIZONTAL_SLIDER_TEXTURE, 0, 18, 23, 6, 2, 1, 2, 2);
    @Nonnull public static final Drawable HORIZONTAL_SLIDER_DISABLED_FOREGROUND = newModal(HORIZONTAL_SLIDER_TEXTURE, 23, 20, 5, 2);
    @Nonnull public static final Drawable HORIZONTAL_SLIDER_DISABLED = makeSlider(HORIZONTAL_SLIDER_DISABLED_BACKGROUND, HORIZONTAL_SLIDER_DISABLED_FOREGROUND);

    @Nonnull public static final TextureSource VERTICAL_SLIDER_TEXTURE = texture("vertical_slider", 24, 28);
    @Nonnull public static final Drawable VERTICAL_SLIDER_ENABLED_BACKGROUND = newNineSlice(VERTICAL_SLIDER_TEXTURE, 0, 0, 6, 23, 1, 2, 2, 2);
    @Nonnull public static final Drawable VERTICAL_SLIDER_ENABLED_FOREGROUND = newModal(VERTICAL_SLIDER_TEXTURE, 2, 23, 2, 5);
    @Nonnull public static final Drawable VERTICAL_SLIDER_ENABLED = makeSlider(VERTICAL_SLIDER_ENABLED_BACKGROUND, VERTICAL_SLIDER_ENABLED_FOREGROUND);
    @Nonnull public static final Drawable VERTICAL_SLIDER_FOCUSED_BACKGROUND = newNineSlice(VERTICAL_SLIDER_TEXTURE, 6, 0, 6, 23, 1, 2, 2, 2);
    @Nonnull public static final Drawable VERTICAL_SLIDER_FOCUSED_FOREGROUND = newModal(VERTICAL_SLIDER_TEXTURE, 8, 23, 2, 5);
    @Nonnull public static final Drawable VERTICAL_SLIDER_FOCUSED = makeSlider(VERTICAL_SLIDER_FOCUSED_BACKGROUND, VERTICAL_SLIDER_FOCUSED_FOREGROUND);
    @Nonnull public static final Drawable VERTICAL_SLIDER_PRESSED_BACKGROUND = newNineSlice(VERTICAL_SLIDER_TEXTURE, 12, 0, 6, 23, 1, 2, 2, 2);
    @Nonnull public static final Drawable VERTICAL_SLIDER_PRESSED_FOREGROUND = newModal(VERTICAL_SLIDER_TEXTURE, 14, 23, 2, 5);
    @Nonnull public static final Drawable VERTICAL_SLIDER_PRESSED = makeSlider(VERTICAL_SLIDER_PRESSED_BACKGROUND, VERTICAL_SLIDER_PRESSED_FOREGROUND);
    @Nonnull public static final Drawable VERTICAL_SLIDER_DISABLED_BACKGROUND = newNineSlice(VERTICAL_SLIDER_TEXTURE, 18, 0, 6, 23, 1, 2, 2, 2);
    @Nonnull public static final Drawable VERTICAL_SLIDER_DISABLED_FOREGROUND = newModal(VERTICAL_SLIDER_TEXTURE, 20, 23, 2, 5);
    @Nonnull public static final Drawable VERTICAL_SLIDER_DISABLED = makeSlider(VERTICAL_SLIDER_DISABLED_BACKGROUND, VERTICAL_SLIDER_DISABLED_FOREGROUND);

    @Nonnull
    private static Drawable makeSlider(@Nonnull Drawable background, @Nonnull Drawable foreground){
        return newComplex(background, (ms, x, y, width, height, color) -> {
            background.drawImage(ms, x, y, width, height, color);
            foreground.drawImage(ms,
                x + width / 2f - foreground.getWidth() / 2f,
                y + height / 2f - foreground.getHeight() / 2f,
                width,
                height,
                color
            );
        });
    }

    @Nonnull public static final TextureSource SIMPLE_BUTTON_TEXTURE = texture("simple_button", 32, 16);
    @Nonnull public static final Drawable SIMPLE_BUTTON_ENABLED = newNineSlice(SIMPLE_BUTTON_TEXTURE, 0, 0, 16, 16, 2);
    @Nonnull public static final Drawable SIMPLE_BUTTON_FOCUSED = newNineSlice(SIMPLE_BUTTON_TEXTURE, 16, 0, 16, 16, 2);

    @Nonnull public static final TextureSource SWITCH_TEXTURE = texture("switch", 48, 27);
    @Nonnull public static final Drawable SWITCH_BACKGROUND_ENABLED = newModal(SWITCH_TEXTURE, 0, 0, 24, 11);
    @Nonnull public static final Drawable SWITCH_BACKGROUND_FOCUSED = newModal(SWITCH_TEXTURE, 24, 0, 24, 11);
    @Nonnull public static final Drawable SWITCH_BUTTON_ENABLED = newNineSlice(SWITCH_TEXTURE, 0, 11, 16, 16, 2, 2, 2, 4);
    @Nonnull public static final Drawable SWITCH_BUTTON_DISABLED = newNineSlice(SWITCH_TEXTURE, 16, 11, 16, 16, 2, 2, 2, 4);

    @Nonnull public static final TextureSource DOWN_ARROW_TEXTURE = texture("down_arrow", 12, 4);
    @Nonnull public static final Drawable DOWN_ARROW_ENABLED = newModal(DOWN_ARROW_TEXTURE, 6, 4);
    @Nonnull public static final Drawable DOWN_ARROW_DISABLED = newModal(DOWN_ARROW_TEXTURE, 6, 0, 6, 4);

    public static final TextureSource POINTING_TRIANGLE_TEXTURE = texture("pointing_triangle", 12, 4);
    public static final Drawable DOWN_POINTING_TRIANGLE = newModal(POINTING_TRIANGLE_TEXTURE, 6, 4);
    public static final Drawable UP_POINTING_TRIANGLE = newModal(POINTING_TRIANGLE_TEXTURE, 6, 0, 6, 4);

    @Nonnull public static final TextureSource EDITING_TEXTURE = texture("editing", 25, 17);
    @Nonnull public static final Drawable GREY_PLUS = newModal(EDITING_TEXTURE, 0, 0, 6, 6);
    @Nonnull public static final Drawable GREEN_PLUS = newModal(EDITING_TEXTURE, 6, 0, 6, 6);
    @Nonnull public static final Drawable GREY_MINUS = newModal(EDITING_TEXTURE, 0, 7, 6, 2);
    @Nonnull public static final Drawable GREEN_MINUS = newModal(EDITING_TEXTURE, 6, 7, 6, 2);
    @Nonnull public static final Drawable TRASH = newModal(EDITING_TEXTURE, 12, 1, 6, 8);
    @Nonnull public static final Drawable CHECK_MARK = newModal(EDITING_TEXTURE, 18, 2, 7, 7);
    @Nonnull public static final Drawable DUPLICATE = newModal(EDITING_TEXTURE, 0, 9, 8, 8);
    @Nonnull public static final Drawable CROSS = newModal(EDITING_TEXTURE, 14, 12, 5, 5);
    @Nonnull public static final Drawable STYLUS = newModal(EDITING_TEXTURE, 19, 11, 6, 6);

    @Nonnull public static final TextureSource NAVIGATION_TEXTURE = texture("navigation", 26, 7);
    @Nonnull public static final Drawable RIGHTWARDS = newModal(NAVIGATION_TEXTURE, 0, 0, 13, 7);
    @Nonnull public static final Drawable LEFTWARDS = newModal(NAVIGATION_TEXTURE, 13, 0, 13, 7);

    @Nonnull public static final TextureSource PADLOCK_TEXTURE = texture("padlock", 14, 9);
    @Nonnull public static final Drawable PADLOCK_CLOSE = newModal(PADLOCK_TEXTURE, 0, 0, 7, 9);
    @Nonnull public static final Drawable PADLOCK_OPEN = newModal(PADLOCK_TEXTURE, 7, 0, 7, 9);

    @Nonnull public static final TextureSource ICONS_TEXTURE = texture("icons", 256, 256);
    @Nonnull public static final Drawable BOOK = newModal(ICONS_TEXTURE, 0, 0, 12, 12);
    @Nonnull public static final Drawable CHEST = newModal(ICONS_TEXTURE, 12, 0, 12, 12);
    @Nonnull public static final Drawable EARTH = newModal(ICONS_TEXTURE, 24, 0, 11, 11);

    @Nonnull public static final TextureSource WIDGETS_TEXTURE = texture("widgets", 256, 256);
    @Nonnull public static final Drawable DECOR_CELL = newNineSlice(WIDGETS_TEXTURE, 0, 0, 16, 16, 2);
    @Nonnull public static final Drawable DECOR_CELL_SELECTED = newNineSlice(WIDGETS_TEXTURE, 16, 0, 16, 16, 2);
    @Nonnull public static final Drawable CELL = newNineSlice(WIDGETS_TEXTURE, 16 * 2, 0, 16, 16, 1);
    @Nonnull public static final Drawable CELL_FOCUSED = newNineSlice(WIDGETS_TEXTURE, 16 * 3, 0, 16, 16, 1);
    @Nonnull public static final Drawable WINDOW_BACKGROUND = newNineSlice(WIDGETS_TEXTURE, 16 * 4, 0, 16, 16, 2);
    @Nonnull public static final Drawable SCROLL_BACKGROUND = newNineSlice(WIDGETS_TEXTURE, 16 * 5, 0, 16, 16, 2, 3, 8, 2);
    @Nonnull public static final Drawable FIELD_BACKGROUND = newNineSlice(WIDGETS_TEXTURE, 16 * 6, 0, 16, 16, 1);

    @Nonnull
    private static TextureSource texture(@Nonnull String path, int width, int height){
        return new TextureSource(new ResourceLocation(SleepLib.MODID, "textures/gui/" + path + ".png"), width, height);
    }
}
