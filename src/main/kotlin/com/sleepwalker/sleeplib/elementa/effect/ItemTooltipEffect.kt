package com.sleepwalker.sleeplib.elementa.effect

import com.sleepwalker.sleeplib.gg.essential.elementa.UIComponent
import com.sleepwalker.sleeplib.gg.essential.elementa.effects.Effect
import com.sleepwalker.sleeplib.gg.essential.universal.UMatrixStack
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.FontRenderer
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.item.ItemStack
import net.minecraftforge.fml.client.gui.GuiUtils
import javax.annotation.Nonnull

class ItemTooltipEffect @JvmOverloads constructor(
    private val item: () -> ItemStack,
    private val maxTextWidth: Int = -1
) : Effect() {

    private var tooltip: UITooltip? = null

    @JvmOverloads constructor(item: ItemStack, maxTextWidth: Int = -1) : this({ item }, maxTextWidth)

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
                && boundComponent.hasEffect(this@ItemTooltipEffect)
                && boundComponent.isActive())){
                hide()
                tooltip = null
            }
        }

        override fun draw(@Nonnull matrixStack: UMatrixStack) {
            beforeDrawCompat(matrixStack)

            val item = this@ItemTooltipEffect.item.invoke()
            val (x, y) = getMousePosition()
            val mc = Minecraft.getInstance()
            val font: FontRenderer? = item.item.getFontRenderer(item)
            val flag = if (mc.gameSettings.advancedItemTooltips) ITooltipFlag.TooltipFlags.ADVANCED else ITooltipFlag.TooltipFlags.NORMAL

            GuiUtils.preItemToolTip(item)
            GuiUtils.drawHoveringText(
                matrixStack.toMC(),
                item.getTooltip(mc.player, flag),
                x.toInt(), y.toInt(),
                mc.mainWindow.width, mc.mainWindow.height,
                -1,
                font ?: Minecraft.getInstance().fontRenderer
            )
            GuiUtils.postItemToolTip()

            super.draw(matrixStack)
        }
    }
}