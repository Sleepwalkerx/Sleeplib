package com.sleepwalker.sleeplib.elementa.effect

import com.sleepwalker.sleeplib.gg.essential.universal.UMatrixStack
import net.minecraft.block.BlockState
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.FontRenderer
import net.minecraft.client.util.ITooltipFlag
import net.minecraft.state.Property
import net.minecraft.util.text.ITextProperties
import net.minecraft.util.text.StringTextComponent
import net.minecraft.util.text.TextFormatting
import net.minecraftforge.fml.client.gui.GuiUtils

class BlockTooltipEffect @JvmOverloads constructor(
    private val blockState: () -> BlockState,
    private val detailed: Boolean = false,
    private val maxTextWidth: Int = -1
) : BaseTooltipEffect() {

    @JvmOverloads constructor(blockState: BlockState, detailed: Boolean = false, maxTextWidth: Int = -1) : this({ blockState }, detailed, maxTextWidth)

    override fun draw(matrixStack: UMatrixStack, mouseX: Int, mouseY: Int, partialTicks: Float) {
        val state = blockState()
        fun <T : Comparable<T>> getValueName(property: Property<T>) = property.getName(state.get(property))

        val mc = Minecraft.getInstance()
        val itemStack = state.block.asItem().defaultInstance
        val font: FontRenderer? = itemStack.item.getFontRenderer(itemStack)
        val flag = if (mc.gameSettings.advancedItemTooltips) ITooltipFlag.TooltipFlags.ADVANCED else ITooltipFlag.TooltipFlags.NORMAL

        if(!itemStack.isEmpty && !detailed){
            GuiUtils.preItemToolTip(itemStack)
            GuiUtils.drawHoveringText(
                matrixStack.toMC(),
                itemStack.getTooltip(mc.player, flag),
                mouseX, mouseY,
                mc.mainWindow.scaledWidth, mc.mainWindow.scaledHeight,
                maxTextWidth,
                font ?: Minecraft.getInstance().fontRenderer
            )
            GuiUtils.postItemToolTip()
        }
        else {
            val lines = mutableListOf<ITextProperties>()
            lines.add(state.block.translatedName)
            if(detailed){
                for (property in state.properties){
                    lines.add(StringTextComponent(TextFormatting.DARK_GRAY.toString() + property.name + ": " + TextFormatting.GOLD + getValueName(property)))
                }
            }
            if(flag == ITooltipFlag.TooltipFlags.ADVANCED){
                lines.add(StringTextComponent(TextFormatting.DARK_GRAY.toString() + state.block.registryName.toString()))
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
}