package com.sleepwalker.sleeplib.universal

import com.mojang.blaze3d.matrix.MatrixStack
import com.sleepwalker.sleeplib.gg.essential.universal.UKeyboard
import com.sleepwalker.sleeplib.gg.essential.universal.UKeyboard.toInt
import com.sleepwalker.sleeplib.gg.essential.universal.UKeyboard.toModifiers
import com.sleepwalker.sleeplib.gg.essential.universal.UMatrixStack
import com.sleepwalker.sleeplib.gg.essential.universal.UMinecraft
import com.sleepwalker.sleeplib.gg.essential.universal.UResolution
import net.minecraft.client.gui.screen.inventory.ContainerScreen
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.container.Container
import net.minecraft.util.text.ITextComponent
import net.minecraft.util.text.TranslationTextComponent
import org.lwjgl.glfw.GLFW

abstract class UScreenContainer<T : Container>(
    menu: T,
    inv: PlayerInventory,
    open var newGuiScale: Int = -1,
    open var unlocalizedName: String? = null
) : ContainerScreen<T>(menu, inv, TranslationTextComponent(unlocalizedName ?: "")) {

    private var guiScaleToRestore = -1
    private var lastClick = 0L
    private var lastDraggedDx = -1.0
    private var lastDraggedDy = -1.0
    private var lastScrolledX = -1.0
    private var lastScrolledY = -1.0

    final override fun init() {
        updateGuiScale()
        initScreen(width, height)
    }

    override fun getTitle(): ITextComponent = TranslationTextComponent(unlocalizedName ?: "")

    final override fun render(matrixStack: MatrixStack, mouseX: Int, mouseY: Int, partialTicks: Float) {
        onDrawScreenCompat(UMatrixStack(matrixStack), mouseX, mouseY, partialTicks)
    }

    final override fun keyPressed(keyCode: Int, scanCode: Int, modifierCode: Int): Boolean {
        if(keyCode == GLFW.GLFW_KEY_ESCAPE && shouldCloseOnEsc()){
            closeScreen()
            return true
        }

        onKeyPressed(keyCode, 0.toChar(), modifierCode.toModifiers())
        return false
    }

    final override fun keyReleased(keyCode: Int, scanCode: Int, modifierCode: Int): Boolean {
        onKeyReleased(keyCode, 0.toChar(), modifierCode.toModifiers())
        return false
    }

    final override fun charTyped(char: Char, modifierCode: Int): Boolean {
        onKeyPressed(0, char, modifierCode.toModifiers())
        return false
    }

    final override fun mouseClicked(mouseX: Double, mouseY: Double, mouseButton: Int): Boolean {
        if (mouseButton == 1)
            lastClick = UMinecraft.getTime()
        onMouseClicked(mouseX, mouseY, mouseButton)
        return false
    }

    final override fun mouseReleased(mouseX: Double, mouseY: Double, mouseButton: Int): Boolean {
        onMouseReleased(mouseX, mouseY, mouseButton)
        return false
    }

    final override fun mouseDragged(x: Double, y: Double, mouseButton: Int, dx: Double, dy: Double): Boolean {
        lastDraggedDx = dx
        lastDraggedDy = dy
        onMouseDragged(x, y, mouseButton, UMinecraft.getTime() - lastClick)
        return false
    }

    final override fun mouseScrolled(mouseX: Double, mouseY: Double, delta: Double): Boolean {
        lastScrolledX = mouseX
        lastScrolledY = mouseY
        onMouseScrolled(delta)
        return false
    }

    final override fun renderBackground(matrixStack: MatrixStack, vOffset: Int) {
        onDrawBackgroundCompat(UMatrixStack(matrixStack), vOffset)
    }

    final override fun tick(): Unit = onTick()

    final override fun onClose() {
        onScreenClose()
        if (guiScaleToRestore != -1)
            UMinecraft.guiScale = guiScaleToRestore
    }

    open fun updateGuiScale() {
        if (newGuiScale != -1) {
            if (guiScaleToRestore == -1)
                guiScaleToRestore = UMinecraft.guiScale
            UMinecraft.guiScale = newGuiScale
            width = UResolution.scaledWidth
            height = UResolution.scaledHeight
        }
    }

    open fun initScreen(width: Int, height: Int) {
        super.init()
    }

    open fun onDrawScreen(matrixStack: UMatrixStack, mouseX: Int, mouseY: Int, partialTicks: Float) {
        super.render(matrixStack.toMC(), mouseX, mouseY, partialTicks)
    }

    @Deprecated(
        UMatrixStack.Compat.DEPRECATED,
        ReplaceWith("onDrawScreen(matrixStack, mouseX, mouseY, partialTicks)")
    )
    open fun onDrawScreen(mouseX: Int, mouseY: Int, partialTicks: Float) {
        onDrawScreen(UMatrixStack.Compat.get(), mouseX, mouseY, partialTicks)
    }

    // Calls the deprecated method (for backwards compat) which then calls the new method (read the deprecation message)
    private fun onDrawScreenCompat(matrixStack: UMatrixStack, mouseX: Int, mouseY: Int, partialTicks: Float) =
        UMatrixStack.Compat.runLegacyMethod(matrixStack) {
            @Suppress("DEPRECATION")
            onDrawScreen(mouseX, mouseY, partialTicks)
        }

    open fun onKeyPressed(keyCode: Int, typedChar: Char, modifiers: UKeyboard.Modifiers?) {
        if (keyCode != 0) {
            super.keyPressed(keyCode, 0, modifiers.toInt())
        }
        if (typedChar != 0.toChar()) {
            super.charTyped(typedChar, modifiers.toInt())
        }
    }

    open fun onKeyReleased(keyCode: Int, typedChar: Char, modifiers: UKeyboard.Modifiers?) {
        if (keyCode != 0) {
            super.keyReleased(keyCode, 0, modifiers.toInt())
        }
    }

    open fun onMouseClicked(mouseX: Double, mouseY: Double, mouseButton: Int) {
        if (mouseButton == 1)
            lastClick = UMinecraft.getTime()
        super.mouseClicked(mouseX, mouseY, mouseButton)
    }

    open fun onMouseReleased(mouseX: Double, mouseY: Double, state: Int) {
        super.mouseReleased(mouseX, mouseY, state)
    }

    open fun onMouseDragged(x: Double, y: Double, clickedButton: Int, timeSinceLastClick: Long) {
        super.mouseDragged(x, y, clickedButton, lastDraggedDx, lastDraggedDy)
    }

    open fun onMouseScrolled(delta: Double) {
        super.mouseScrolled(lastScrolledX, lastScrolledY, delta)
    }

    open fun onTick() {
        super.tick()
    }

    open fun onScreenClose() {
        super.onClose()
    }

    open fun onDrawBackground(matrixStack: UMatrixStack, tint: Int) {
        super.renderBackground(matrixStack.toMC(), tint)
    }

    @Deprecated(
        UMatrixStack.Compat.DEPRECATED,
        ReplaceWith("onDrawBackground(matrixStack, tint)")
    )
    open fun onDrawBackground(tint: Int) {
        onDrawBackground(UMatrixStack.Compat.get(), tint)
    }

    // Calls the deprecated method (for backwards compat) which then calls the new method (read the deprecation message)
    fun onDrawBackgroundCompat(matrixStack: UMatrixStack, tint: Int) =
        UMatrixStack.Compat.runLegacyMethod(matrixStack) {
            @Suppress("DEPRECATION")
            onDrawBackground(tint)
        }

    override fun drawGuiContainerBackgroundLayer(matrixStack: MatrixStack, partialTicks: Float, x: Int, y: Int) {}
}