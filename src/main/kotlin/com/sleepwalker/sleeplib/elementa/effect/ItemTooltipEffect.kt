package com.sleepwalker.sleeplib.elementa.effect

import com.sleepwalker.sleeplib.gg.essential.universal.UMatrixStack
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.FontRenderer
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.item.ItemStack
import net.minecraftforge.fml.client.gui.GuiUtils

class ItemTooltipEffect @JvmOverloads constructor(
    private val item: () -> ItemStack,
    private val maxTextWidth: Int = -1
) : BaseTooltipEffect() {

    @JvmOverloads constructor(item: ItemStack, maxTextWidth: Int = -1) : this({ item }, maxTextWidth)

    override fun draw(matrixStack: UMatrixStack, mouseX: Int, mouseY: Int, partialTicks: Float) {
        val item = this@ItemTooltipEffect.item()
        val mc = Minecraft.getInstance()
        val font: FontRenderer? = item.item.getFontRenderer(item)
        val flag = if (mc.gameSettings.advancedItemTooltips) ITooltipFlag.TooltipFlags.ADVANCED else ITooltipFlag.TooltipFlags.NORMAL

        GuiUtils.preItemToolTip(item)
        GuiUtils.drawHoveringText(
            matrixStack.toMC(),
            item.getTooltip(mc.player, flag),
            mouseX, mouseY,
            mc.mainWindow.scaledWidth, mc.mainWindow.scaledHeight,
            maxTextWidth,
            font ?: Minecraft.getInstance().fontRenderer
        )
        GuiUtils.postItemToolTip()
    }
}