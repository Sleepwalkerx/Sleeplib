package com.sleepwalker.sleeplib.elementa.effect

import com.sleepwalker.sleeplib.gg.essential.universal.UMatrixStack
import net.minecraft.client.Minecraft
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.entity.Entity
import net.minecraft.util.text.ITextProperties
import net.minecraft.util.text.StringTextComponent
import net.minecraft.util.text.TextFormatting
import net.minecraftforge.fml.client.gui.GuiUtils

class EntityTooltipEffect @JvmOverloads constructor(
    private val entity: () -> Entity?,
    private val maxTextWidth: Int = -1
) : BaseTooltipEffect() {

    @JvmOverloads constructor(entity: Entity?, maxTextWidth: Int = -1) : this({ entity }, maxTextWidth)

    override fun draw(matrixStack: UMatrixStack, mouseX: Int, mouseY: Int, partialTicks: Float) {
        val entity = this.entity() ?: return

        val mc = Minecraft.getInstance()
        val font = mc.fontRenderer
        val flag = if (mc.gameSettings.advancedItemTooltips) ITooltipFlag.TooltipFlags.ADVANCED else ITooltipFlag.TooltipFlags.NORMAL

        val lines = mutableListOf<ITextProperties>()
        lines.add(entity.name)
        if(flag == ITooltipFlag.TooltipFlags.ADVANCED){
            lines.add(StringTextComponent(TextFormatting.DARK_GRAY.toString() + entity.type.registryName.toString()))
        }
        GuiUtils.drawHoveringText(
            matrixStack.toMC(),
            lines,
            mouseX, mouseY,
            mc.mainWindow.scaledWidth, mc.mainWindow.scaledHeight,
            maxTextWidth,
            font ?: Minecraft.getInstance().fontRenderer
        )
    }
}