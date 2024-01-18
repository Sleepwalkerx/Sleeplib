package com.sleepwalker.sleeplib.elementa

import com.sleepwalker.sleeplib.gg.essential.elementa.ElementaVersion
import com.sleepwalker.sleeplib.gg.essential.elementa.components.Window
import com.sleepwalker.sleeplib.gg.essential.elementa.constraints.animation.AnimationStrategy
import com.sleepwalker.sleeplib.gg.essential.universal.UKeyboard
import com.sleepwalker.sleeplib.gg.essential.universal.UMatrixStack
import com.sleepwalker.sleeplib.gg.essential.universal.UMouse
import com.sleepwalker.sleeplib.universal.UScreenContainer
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.container.Container
import java.awt.Color
import kotlin.math.floor
import kotlin.reflect.KMutableProperty0

abstract class WindowScreenContainer<T : Container> @JvmOverloads constructor(
    menu: T,
    inv: PlayerInventory,
    private val version: ElementaVersion,
    private val enableRepeatKeys: Boolean = true,
    private val drawDefaultBackground: Boolean = true,
    newGuiScale: Int = -1
) : UScreenContainer<T>(menu, inv, newGuiScale) {
    val window = Window(version)
    private var isInitialized = false

    init {
        window.onKeyType { typedChar, keyCode ->
            defaultKeyBehavior(typedChar, keyCode)
        }
    }

    open fun afterInitialization() { }

    override fun onDrawScreen(matrixStack: UMatrixStack, mouseX: Int, mouseY: Int, partialTicks: Float) {
        if (!isInitialized) {
            isInitialized = true
            afterInitialization()
        }

        if (drawDefaultBackground)
            super.onDrawBackground(matrixStack, 0)

        window.draw(matrixStack)
        super.onDrawScreen(matrixStack, mouseX, mouseY, partialTicks)
    }

    override fun onMouseClicked(mouseX: Double, mouseY: Double, mouseButton: Int) {
        super.onMouseClicked(mouseX, mouseY, mouseButton)

        val (adjustedMouseX, adjustedMouseY) =
            if (version >= ElementaVersion.v2 && (mouseX == floor(mouseX) && mouseY == floor(mouseY))) {
                val x = UMouse.Scaled.x
                val y = UMouse.Scaled.y

                mouseX + (x - floor(x)) to mouseY + (y - floor(y))
            } else {
                mouseX to mouseY
            }

        window.mouseClick(adjustedMouseX, adjustedMouseY, mouseButton)
    }

    override fun onMouseReleased(mouseX: Double, mouseY: Double, state: Int) {
        super.onMouseReleased(mouseX, mouseY, state)
        window.mouseRelease()
    }

    override fun onMouseScrolled(delta: Double) {
        super.onMouseScrolled(delta)
        window.mouseScroll(delta.coerceIn(-1.0, 1.0))
    }

    override fun onKeyPressed(keyCode: Int, typedChar: Char, modifiers: UKeyboard.Modifiers?) {
        window.keyType(typedChar, keyCode)
    }

    override fun initScreen(width: Int, height: Int) {
        window.onWindowResize()
        super.initScreen(width, height)

        if (enableRepeatKeys)
            UKeyboard.allowRepeatEvents(true)
    }

    override fun onScreenClose() {
        super.onScreenClose()

        if (enableRepeatKeys)
            UKeyboard.allowRepeatEvents(false)
    }

    fun defaultKeyBehavior(typedChar: Char, keyCode: Int) {
        super.onKeyPressed(keyCode, typedChar, UKeyboard.getModifiers())
    }

    fun KMutableProperty0<Int>.animate(strategy: AnimationStrategy, time: Float, newValue: Int, delay: Float = 0f) {
        window.apply { this@animate.animate(strategy, time, newValue, delay) }
    }

    fun KMutableProperty0<Float>.animate(strategy: AnimationStrategy, time: Float, newValue: Float, delay: Float = 0f) {
        window.apply { this@animate.animate(strategy, time, newValue, delay) }
    }

    fun KMutableProperty0<Long>.animate(strategy: AnimationStrategy, time: Float, newValue: Long, delay: Float = 0f) {
        window.apply { this@animate.animate(strategy, time, newValue, delay) }
    }

    fun KMutableProperty0<Double>.animate(strategy: AnimationStrategy, time: Float, newValue: Double, delay: Float = 0f) {
        window.apply { this@animate.animate(strategy, time, newValue, delay) }
    }

    fun KMutableProperty0<Color>.animate(strategy: AnimationStrategy, time: Float, newValue: Color, delay: Float = 0f) {
        window.apply { this@animate.animate(strategy, time, newValue, delay) }
    }

    fun KMutableProperty0<*>.stopAnimating() {
        window.apply { this@stopAnimating.stopAnimating() }
    }
}