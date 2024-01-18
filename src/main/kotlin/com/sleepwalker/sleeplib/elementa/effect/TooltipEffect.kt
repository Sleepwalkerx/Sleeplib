package com.sleepwalker.sleeplib.elementa.effect

import com.sleepwalker.sleeplib.gg.essential.elementa.UIComponent
import com.sleepwalker.sleeplib.gg.essential.elementa.dsl.effect
import com.sleepwalker.sleeplib.gg.essential.elementa.effects.Effect
import com.sleepwalker.sleeplib.gg.essential.universal.UMatrixStack
import net.minecraft.client.Minecraft
import net.minecraft.util.text.ITextProperties
import net.minecraft.util.text.StringTextComponent
import net.minecraftforge.fml.client.gui.GuiUtils
import javax.annotation.Nonnull

open class TooltipEffect @JvmOverloads constructor(
    private val lines: List<ITextProperties>,
    private val maxTextWidth: Int = -1
) : Effect() {

    private var tooltip: UITooltip? = null

    override fun animationFrame() {
        if(tooltip == null && boundComponent.isHovered()){
            tooltip = UITooltip().setChildOf(boundComponent.cachedWindow!!) as UITooltip
        }
    }

    private inner class UITooltip : UIComponent() {

        override fun animationFrame() {
            super.animationFrame()

            if(!(boundComponent.hasParent
                && boundComponent.parent.children.contains(boundComponent)
                && boundComponent.isHovered()
                && boundComponent.hasEffect(this@TooltipEffect)
                && boundComponent.isActive())){
                hide()
                tooltip = null
            }
        }

        override fun draw(@Nonnull matrixStack: UMatrixStack) {
            beforeDrawCompat(matrixStack)
            val (x, y) = getMousePosition()
            val window = Minecraft.getInstance().mainWindow

            GuiUtils.drawHoveringText(
                matrixStack.toMC(),
                lines,
                x.toInt(),
                y.toInt(),
                window.width,
                window.height,
                maxTextWidth,
                Minecraft.getInstance().fontRenderer
            )

            super.draw(matrixStack)
        }
    }

    companion object {

        @JvmOverloads fun ofStringList(lines: List<String>, maxTextWidth: Int = -1): TooltipEffect {
            return TooltipEffect(lines.map { StringTextComponent(it) }, maxTextWidth)
        }

        @JvmOverloads fun ofVararg(maxTextWidth: Int = -1, vararg lines: String): TooltipEffect {
            return TooltipEffect(lines.map { StringTextComponent(it) }, maxTextWidth)
        }
    }
}