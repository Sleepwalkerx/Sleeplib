package com.sleepwalker.sleeplib.elementa.drawable

import com.sleepwalker.sleeplib.SleepLib
import net.minecraft.util.ResourceLocation
import javax.annotation.Nonnull

object Drawables {

    val DISPOSABLE_BUTTON_TEXTURE = texture("disposable_button", 96, 16)
    val DISPOSABLE_BUTTON_ENABLED: Drawable = NineSliceSprite(DISPOSABLE_BUTTON_TEXTURE, 0, 0, 16, 16, 2, 3, 2, 6)
    val DISPOSABLE_BUTTON_FOCUSED: Drawable = NineSliceSprite(DISPOSABLE_BUTTON_TEXTURE, 16, 0, 16, 16, 2, 3, 2, 6)
    val DISPOSABLE_BUTTON_PRESSED: Drawable = NineSliceSprite(DISPOSABLE_BUTTON_TEXTURE, 32, 0, 16, 16, 2, 3, 2, 6)
    val DISPOSABLE_BUTTON_DISABLED: Drawable = NineSliceSprite(DISPOSABLE_BUTTON_TEXTURE, 48, 0, 16, 16, 2, 3, 2, 6)
    val DISPOSABLE_BUTTON_SELECTED_ENABLED: Drawable = NineSliceSprite(DISPOSABLE_BUTTON_TEXTURE, 64, 0, 16, 16, 2, 3, 2, 6)
    val DISPOSABLE_BUTTON_SELECTED_FOCUSED: Drawable = NineSliceSprite(DISPOSABLE_BUTTON_TEXTURE, 80, 0, 16, 16, 2, 3, 2, 6)

    val HORIZONTAL_LARGE_SLIDER_TEXTURE = texture("horizontal_large_slider", 128, 21)
    val HORIZONTAL_LARGE_SLIDER_ENABLED: Drawable = ModalSprite(HORIZONTAL_LARGE_SLIDER_TEXTURE, 0, 0, 32, 21)
    val HORIZONTAL_LARGE_SLIDER_FOCUSED: Drawable = ModalSprite(HORIZONTAL_LARGE_SLIDER_TEXTURE, 32, 0, 32, 21)
    val HORIZONTAL_LARGE_SLIDER_PRESSED: Drawable = ModalSprite(HORIZONTAL_LARGE_SLIDER_TEXTURE, 64, 0, 32, 21)
    val HORIZONTAL_LARGE_SLIDER_DISABLED: Drawable = ModalSprite(HORIZONTAL_LARGE_SLIDER_TEXTURE, 96, 0, 32, 21)

    val HORIZONTAL_SLIDER_TEXTURE = texture("horizontal_slider", 28, 24)

    val HORIZONTAL_SLIDER_ENABLED_BACKGROUND: Drawable = NineSliceSprite(HORIZONTAL_SLIDER_TEXTURE, 0, 0, 23, 6, 2, 1, 2, 2)
    val HORIZONTAL_SLIDER_ENABLED_FOREGROUND: Drawable = ModalSprite(HORIZONTAL_SLIDER_TEXTURE, 23, 2, 5, 2)
    val HORIZONTAL_SLIDER_ENABLED: Drawable = makeSlider(HORIZONTAL_SLIDER_ENABLED_BACKGROUND, HORIZONTAL_SLIDER_ENABLED_FOREGROUND)
    val HORIZONTAL_SLIDER_FOCUSED_BACKGROUND: Drawable = NineSliceSprite(HORIZONTAL_SLIDER_TEXTURE, 0, 6, 23, 6, 2, 1, 2, 2)
    val HORIZONTAL_SLIDER_FOCUSED_FOREGROUND: Drawable = ModalSprite(HORIZONTAL_SLIDER_TEXTURE, 23, 8, 5, 2)
    val HORIZONTAL_SLIDER_FOCUSED: Drawable = makeSlider(HORIZONTAL_SLIDER_FOCUSED_BACKGROUND, HORIZONTAL_SLIDER_FOCUSED_FOREGROUND)
    val HORIZONTAL_SLIDER_PRESSED_BACKGROUND: Drawable = NineSliceSprite(HORIZONTAL_SLIDER_TEXTURE, 0, 12, 23, 6, 2, 1, 2, 2)
    val HORIZONTAL_SLIDER_PRESSED_FOREGROUND: Drawable = ModalSprite(HORIZONTAL_SLIDER_TEXTURE, 23, 14, 5, 2)
    val HORIZONTAL_SLIDER_PRESSED: Drawable = makeSlider(HORIZONTAL_SLIDER_PRESSED_BACKGROUND, HORIZONTAL_SLIDER_PRESSED_FOREGROUND)
    val HORIZONTAL_SLIDER_DISABLED_BACKGROUND: Drawable = NineSliceSprite(HORIZONTAL_SLIDER_TEXTURE, 0, 18, 23, 6, 2, 1, 2, 2)
    val HORIZONTAL_SLIDER_DISABLED_FOREGROUND: Drawable = ModalSprite(HORIZONTAL_SLIDER_TEXTURE, 23, 20, 5, 2)
    val HORIZONTAL_SLIDER_DISABLED: Drawable = makeSlider(HORIZONTAL_SLIDER_DISABLED_BACKGROUND, HORIZONTAL_SLIDER_DISABLED_FOREGROUND)
    
    val VERTICAL_SLIDER_TEXTURE = texture("vertical_slider", 24, 28)
    val VERTICAL_SLIDER_ENABLED_BACKGROUND: Drawable = NineSliceSprite(VERTICAL_SLIDER_TEXTURE, 0, 0, 6, 23, 1, 2, 2, 2)
    val VERTICAL_SLIDER_ENABLED_FOREGROUND: Drawable = ModalSprite(VERTICAL_SLIDER_TEXTURE, 2, 23, 2, 5)
    val VERTICAL_SLIDER_ENABLED = makeSlider(VERTICAL_SLIDER_ENABLED_BACKGROUND, VERTICAL_SLIDER_ENABLED_FOREGROUND)
    val VERTICAL_SLIDER_FOCUSED_BACKGROUND: Drawable = NineSliceSprite(VERTICAL_SLIDER_TEXTURE, 6, 0, 6, 23, 1, 2, 2, 2)
    val VERTICAL_SLIDER_FOCUSED_FOREGROUND: Drawable = ModalSprite(VERTICAL_SLIDER_TEXTURE, 8, 23, 2, 5)
    val VERTICAL_SLIDER_FOCUSED = makeSlider(VERTICAL_SLIDER_FOCUSED_BACKGROUND, VERTICAL_SLIDER_FOCUSED_FOREGROUND)
    val VERTICAL_SLIDER_PRESSED_BACKGROUND: Drawable = NineSliceSprite(VERTICAL_SLIDER_TEXTURE, 12, 0, 6, 23, 1, 2, 2, 2)
    val VERTICAL_SLIDER_PRESSED_FOREGROUND: Drawable = ModalSprite(VERTICAL_SLIDER_TEXTURE, 14, 23, 2, 5)
    val VERTICAL_SLIDER_PRESSED = makeSlider(VERTICAL_SLIDER_PRESSED_BACKGROUND, VERTICAL_SLIDER_PRESSED_FOREGROUND)
    val VERTICAL_SLIDER_DISABLED_BACKGROUND: Drawable = NineSliceSprite(VERTICAL_SLIDER_TEXTURE, 18, 0, 6, 23, 1, 2, 2, 2)
    val VERTICAL_SLIDER_DISABLED_FOREGROUND: Drawable = ModalSprite(VERTICAL_SLIDER_TEXTURE, 20, 23, 2, 5)
    val VERTICAL_SLIDER_DISABLED = makeSlider(VERTICAL_SLIDER_DISABLED_BACKGROUND, VERTICAL_SLIDER_DISABLED_FOREGROUND)
    
    val SIMPLE_BUTTON_TEXTURE = texture("simple_button", 32, 16)
    val SIMPLE_BUTTON_ENABLED: Drawable = NineSliceSprite(SIMPLE_BUTTON_TEXTURE, 0, 0, 16, 16, 2)
    val SIMPLE_BUTTON_FOCUSED: Drawable = NineSliceSprite(SIMPLE_BUTTON_TEXTURE, 16, 0, 16, 16, 2)
    
    val SWITCH_TEXTURE: TextureSource = texture("switch", 48, 27)
    val SWITCH_BACKGROUND_ENABLED: Drawable = ModalSprite(SWITCH_TEXTURE, 0, 0, 24, 11)
    val SWITCH_BACKGROUND_FOCUSED: Drawable = ModalSprite(SWITCH_TEXTURE, 24, 0, 24, 11)
    val SWITCH_BUTTON_ENABLED: Drawable = NineSliceSprite(SWITCH_TEXTURE, 0, 11, 16, 16, 2, 2, 2, 4)
    val SWITCH_BUTTON_DISABLED: Drawable = NineSliceSprite(SWITCH_TEXTURE, 16, 11, 16, 16, 2, 2, 2, 4)
    
    val DOWN_ARROW_TEXTURE = texture("down_arrow", 12, 4)
    val DOWN_ARROW_ENABLED: Drawable = ModalSprite(DOWN_ARROW_TEXTURE, 6, 4)
    val DOWN_ARROW_DISABLED: Drawable = ModalSprite(DOWN_ARROW_TEXTURE, 6, 0, 6, 4)

    val POINTING_TRIANGLE_TEXTURE = texture("pointing_triangle", 12, 4)
    val DOWN_POINTING_TRIANGLE: Drawable = ModalSprite(POINTING_TRIANGLE_TEXTURE, 6, 4)
    val UP_POINTING_TRIANGLE: Drawable = ModalSprite(POINTING_TRIANGLE_TEXTURE, 6, 0, 6, 4)
    
    val EDITING_TEXTURE = texture("editing", 25, 17)
    val GREY_PLUS: Drawable = ModalSprite(EDITING_TEXTURE, 0, 0, 6, 6)
    val GREEN_PLUS: Drawable = ModalSprite(EDITING_TEXTURE, 6, 0, 6, 6)
    val GREY_MINUS: Drawable = ModalSprite(EDITING_TEXTURE, 0, 7, 6, 2)
    val GREEN_MINUS: Drawable = ModalSprite(EDITING_TEXTURE, 6, 7, 6, 2)
    val TRASH: Drawable = ModalSprite(EDITING_TEXTURE, 12, 1, 6, 8)
    val CHECK_MARK: Drawable = ModalSprite(EDITING_TEXTURE, 18, 2, 7, 7)
    val DUPLICATE: Drawable = ModalSprite(EDITING_TEXTURE, 0, 9, 8, 8)
    val CROSS: Drawable = ModalSprite(EDITING_TEXTURE, 14, 12, 5, 5)
    val STYLUS: Drawable = ModalSprite(EDITING_TEXTURE, 19, 11, 6, 6)
    
    val NAVIGATION_TEXTURE = texture("navigation", 26, 7)
    val RIGHTWARDS: Drawable = ModalSprite(NAVIGATION_TEXTURE, 0, 0, 13, 7)
    val LEFTWARDS: Drawable = ModalSprite(NAVIGATION_TEXTURE, 13, 0, 13, 7)
    
    val PADLOCK_TEXTURE = texture("padlock", 14, 9)
    val PADLOCK_CLOSE: Drawable = ModalSprite(PADLOCK_TEXTURE, 0, 0, 7, 9)
    val PADLOCK_OPEN: Drawable = ModalSprite(PADLOCK_TEXTURE, 7, 0, 7, 9)
    
    val ICONS_TEXTURE = texture("icons", 256, 256)
    val BOOK: Drawable = ModalSprite(ICONS_TEXTURE, 0, 0, 12, 12)
    val CHEST: Drawable = ModalSprite(ICONS_TEXTURE, 12, 0, 12, 12)
    val EARTH: Drawable = ModalSprite(ICONS_TEXTURE, 24, 0, 11, 11)
    
    val WIDGETS_TEXTURE = texture("widgets", 256, 256)
    val DECOR_CELL: Drawable = NineSliceSprite(WIDGETS_TEXTURE, 0, 0, 16, 16, 2)
    val DECOR_CELL_SELECTED: Drawable = NineSliceSprite(WIDGETS_TEXTURE, 16, 0, 16, 16, 2)
    val CELL: Drawable = NineSliceSprite(WIDGETS_TEXTURE, 16 * 2, 0, 16, 16, 1)
    val CELL_FOCUSED: Drawable = NineSliceSprite(WIDGETS_TEXTURE, 16 * 3, 0, 16, 16, 1)
    val WINDOW_BACKGROUND: Drawable = NineSliceSprite(WIDGETS_TEXTURE, 16 * 4, 0, 16, 16, 2)
    val SCROLL_BACKGROUND: Drawable = NineSliceSprite(WIDGETS_TEXTURE, 16 * 5, 0, 16, 16, 2, 3, 8, 2)
    val FIELD_BACKGROUND: Drawable = NineSliceSprite(WIDGETS_TEXTURE, 16 * 6, 0, 16, 16, 1)
    
    private fun makeSlider(background: Drawable, foreground: Drawable): Drawable = ComplexSprite(background) { ms, x, y, width, height, color ->
        background.drawImage(ms, x, y, width, height, color)
        foreground.drawImage(ms, x + width / 2f - foreground.width / 2f, y + height / 2f - foreground.height / 2f, width, height, color)
    }

    private fun texture(@Nonnull path: String, width: Int, height: Int): TextureSource {
        return TextureSource(ResourceLocation(SleepLib.MODID, "textures/gui/$path.png"), width, height)
    }
}