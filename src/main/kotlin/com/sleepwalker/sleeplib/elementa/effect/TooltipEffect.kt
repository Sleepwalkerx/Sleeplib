package com.sleepwalker.sleeplib.elementa.effect

import com.sleepwalker.sleeplib.gg.essential.universal.UMatrixStack
import net.minecraft.client.Minecraft
import net.minecraft.util.text.ITextProperties
import net.minecraft.util.text.StringTextComponent
import net.minecraftforge.fml.client.gui.GuiUtils

open class TooltipEffect @JvmOverloads constructor(
    private val lines: List<ITextProperties>,
    private val maxTextWidth: Int = -1
) : BaseTooltipEffect() {

    @JvmOverloads
    constructor(vararg lines: ITextProperties, maxTextWidth: Int = -1) : this(lines.map { it }, maxTextWidth)
    @JvmOverloads
    constructor(vararg lines: String, maxTextWidth: Int = -1) : this(
        lines.map { StringTextComponent(it) },
        maxTextWidth
    )

    override fun draw(matrixStack: UMatrixStack, mouseX: Int, mouseY: Int, partialTicks: Float) {
        val window = Minecraft.getInstance().mainWindow

        GuiUtils.drawHoveringText(
            matrixStack.toMC(),
            lines,
            mouseX,
            mouseY,
            window.scaledWidth,
            window.scaledHeight,
            maxTextWidth,
            Minecraft.getInstance().fontRenderer
        )
    }
}