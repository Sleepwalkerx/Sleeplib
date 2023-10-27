package com.sleepwalker.sleeplib.ui.style;

import com.sleepwalker.sleeplib.SleepLib;
import com.sleepwalker.sleeplib.ui.graphics.drawable.Drawable;
import com.sleepwalker.sleeplib.ui.graphics.drawable.sprite.NineSliceSprite;
import com.sleepwalker.sleeplib.ui.graphics.drawable.sprite.TextureSource;
import com.sleepwalker.sleeplib.ui.property.*;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public class Styles {

    @Nonnull private static final TextureSource DISPOSABLE_BACKGROUND_SOURCE = new TextureSource(new ResourceLocation(SleepLib.MODID, "textures/gui/widget/disposable_button.png"), 96, 16);
    @Nonnull public static final Drawable DISPOSABLE_BACKGROUND_ENABLED = new NineSliceSprite(DISPOSABLE_BACKGROUND_SOURCE, 0, 0, 16, 16, 2, 2, 3, 6);
    @Nonnull public static final Drawable DISPOSABLE_BACKGROUND_FOCUSED = new NineSliceSprite(DISPOSABLE_BACKGROUND_SOURCE, 16, 0, 16, 16, 2, 2, 3, 6);
    @Nonnull public static final Drawable DISPOSABLE_BACKGROUND_PRESSED = new NineSliceSprite(DISPOSABLE_BACKGROUND_SOURCE, 32, 0, 16, 16, 2, 2, 3, 6);
    @Nonnull public static final Drawable DISPOSABLE_BACKGROUND_DISABLED = new NineSliceSprite(DISPOSABLE_BACKGROUND_SOURCE, 48, 0, 16, 16, 2, 2, 3, 6);

    @Nonnull public static final PropertySource DISPOSABLE_BUTTON = SourceMapPropertySource.builder()
        .put(Properties.TEXT_COLOR, SelectorPropertySource.builder(Properties.TEXT_COLOR)
            .forValue(0xA0A0A0).condition(WidgetStates.ENABLED, false)
        )
        .put(Properties.TEXT_BIAS_X, 0.5f)
        .put(Properties.TEXT_BIAS_Y, 0.475f)
        .put(Properties.TEXT_OFFSET_Y, SelectorPropertySource.builder(Properties.TEXT_OFFSET_Y)
            .forValue(1f).condition(WidgetStates.PRESSED, true)
        )
        .put(Properties.BACKGROUND, SelectorPropertySource.builder(Properties.BACKGROUND)
            .forValue(DISPOSABLE_BACKGROUND_DISABLED).condition(WidgetStates.ENABLED, false)
            .forValue(DISPOSABLE_BACKGROUND_PRESSED).condition(WidgetStates.PRESSED, true)
            .forValue(DISPOSABLE_BACKGROUND_FOCUSED).condition(WidgetStates.FOCUSED, true)
            .forValue(DISPOSABLE_BACKGROUND_ENABLED).condition(WidgetStates.ENABLED, true)
        )
        .build();
}
