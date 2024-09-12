package com.sleepwalker.sleeplib.elementa.components

import com.mojang.blaze3d.matrix.MatrixStack
import com.mojang.blaze3d.systems.RenderSystem
import com.mojang.blaze3d.vertex.IVertexBuilder
import com.sleepwalker.sleeplib.world.FakeWorldInstance
import com.sleepwalker.sleeplib.elementa.effect.BlockTooltipEffect
import com.sleepwalker.sleeplib.gg.essential.elementa.UIComponent
import com.sleepwalker.sleeplib.gg.essential.elementa.components.UIText
import com.sleepwalker.sleeplib.gg.essential.elementa.constraints.PixelConstraint
import com.sleepwalker.sleeplib.gg.essential.elementa.dsl.constrain
import com.sleepwalker.sleeplib.gg.essential.elementa.dsl.pixel
import com.sleepwalker.sleeplib.gg.essential.elementa.state.BasicState
import com.sleepwalker.sleeplib.gg.essential.elementa.state.State
import com.sleepwalker.sleeplib.gg.essential.universal.UMatrixStack
import net.minecraft.block.BlockRenderType
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.client.Minecraft
import net.minecraft.client.renderer.IRenderTypeBuffer
import net.minecraft.client.renderer.RenderType
import net.minecraft.client.renderer.RenderTypeLookup
import net.minecraft.client.renderer.texture.OverlayTexture
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher
import net.minecraft.fluid.FluidState
import net.minecraft.fluid.Fluids
import net.minecraft.item.ItemStack
import net.minecraft.nbt.CompoundNBT
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.Direction
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.vector.Quaternion
import net.minecraft.world.EmptyBlockReader
import net.minecraft.world.IBlockDisplayReader
import net.minecraft.world.LightType
import net.minecraft.world.level.ColorResolver
import net.minecraft.world.lighting.WorldLightManager
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import net.minecraftforge.client.model.data.EmptyModelData

class UIBlockState @JvmOverloads constructor(
    blockState: BlockState,
    val canDisplayItem: Boolean = true,
    val detailed: Boolean = false,
    val padding: Float = 0f
) : UIComponent() {

    var blockState: BlockState = blockState
        set(value) {
            if(field != value){
                field = value
                if(canDisplayItem){
                    itemStackCache = value.block.asItem().defaultInstance
                }
                blockEntityCache = null
                blockEntityCached = false
            }
        }
    private var blockEntityCached: Boolean = false
    private var blockEntityCache: TileEntity? = null
    private val countState: State<String> = BasicState("")
    private val overlay: UIText = UIText(countState)
    private var itemStackCache = if(canDisplayItem) blockState.block.asItem().defaultInstance else ItemStack.EMPTY

    private var drawOverlay: Boolean = false
        set(value) {
            if(field != value){
                field = value
                if (drawOverlay) {
                    if (!overlay.parent.children.contains(overlay)) {
                        overlay.unhide(false)
                    }
                } else {
                    if (overlay.parent.children.contains(overlay)) {
                        overlay.hide()
                    }
                }
            }
        }

    init {
        setWidth(16f.pixel)
        setHeight(16f.pixel)

        countState.onSetValue {
            if (it.length > 2) {
                overlay.setTextScale(PixelConstraint(0.5f * this.constraints.getTextScaleValue()))
            } else {
                overlay.setTextScale(PixelConstraint(1f * this.constraints.getTextScaleValue()))
            }
        }

        overlay.constrain {
            x = PixelConstraint(0f, true)
            y = PixelConstraint(-1f, true)
            textScale = (1f * this@UIBlockState.constraints.getTextScaleValue()).pixel
        }
        overlay.parent = this

        setCount(1)
    }

    fun setCount(count: Int) = apply {
        countState.set(if (count > 0) count.toString() else "")
    }

    fun withTooltip() = apply {
        enableEffect(BlockTooltipEffect(::blockState, detailed = detailed))
    }

    fun withOverlay() = apply {
        drawOverlay = true
    }

    override fun afterInitialization() {
        super.afterInitialization()
        overlay.setTextScale(PixelConstraint(1f * this.constraints.getTextScaleValue()))
    }

    override fun draw(matrixStack: UMatrixStack) {
        beforeDrawCompat(matrixStack)

        val color = this.getColor()
        if (color.alpha == 0) {
            return super.draw(matrixStack)
        }

        if(itemStackCache.isEmpty){
            renderBlock(matrixStack.toMC())
        }
        else {
            val scale = color.alpha / 255f
            val width = getWidth()
            val height = getHeight()
            val x = getLeft() + (width * (1f - scale) * 0.5f)
            val y = getTop() + (height * (1f - scale) * 0.5f)
            UIItemStack.drawItemStack(matrixStack, x, y, width * scale, height * scale, itemStackCache)
        }

        matrixStack.push()
        matrixStack.translate(0.0, 0.0, 200.0)
        super.draw(matrixStack)
        matrixStack.pop()
    }

    @Suppress("DEPRECATION")
    private fun renderBlock(ms: MatrixStack){
        val color = this.getColor()
        val scale = color.alpha / 255f
        val oWidth = this.getWidth() - padding * 2
        val oHeight = this.getHeight() - padding * 2
        val width = oWidth * scale
        val height = oHeight * scale
        val x = (this.getLeft() + padding) + (oWidth * (1f - scale) * 0.5f)
        val y = (this.getTop() + padding) + (oHeight * (1f - scale) * 0.5f)
        val buffer = Minecraft.getInstance().renderTypeBuffers.bufferSource
        ms.push()
        ms.translate(x + width * 0.5, y + height * 0.5, 100.0)
        ms.scale(width * 10f / 16f, -height * 10f / 16f, 1f)
        ms.rotate(Quaternion(30f, 0f, 0f, true))
        ms.rotate(Quaternion(0f, (45 + 270).toFloat(), 0f, true))
        ms.translate(-0.5, -0.5, -0.5)

        val blockRenderType: BlockRenderType = blockState.renderType
        val fluidState: FluidState = blockState.fluidState
        if (!fluidState.isEmpty) {
            RenderSystem.pushMatrix()
            RenderSystem.multMatrix(ms.last.matrix)
            val reader = FakeBlockDisplayReader(blockState, fluidState)
            val used: MutableSet<RenderType> = HashSet()
            for (renderType in RenderType.getBlockRenderTypes()) {
                if (RenderTypeLookup.canRenderInLayer(fluidState, renderType)) {
                    used.add(renderType)
                    val vb: IVertexBuilder = buffer.getBuffer(renderType)
                    Minecraft.getInstance().blockRendererDispatcher.renderFluid(BlockPos.ZERO, reader, vb, fluidState)
                }
            }
            for (renderType in used) {
                buffer.finish(renderType)
            }
            RenderSystem.popMatrix()
        }
        if (blockRenderType == BlockRenderType.MODEL) {
            Minecraft.getInstance().blockRendererDispatcher.renderBlock(
                blockState,
                ms,
                buffer,
                0xF0,
                OverlayTexture.NO_OVERLAY,
                EmptyModelData.INSTANCE
            )
        } else {
            if(!blockEntityCached){
                blockEntityCache = blockState.createTileEntity(EmptyBlockReader.INSTANCE)
                blockEntityCache?.setWorldAndPos(FakeWorldInstance, BlockPos.ZERO)
                blockEntityCache?.cachedBlockState = this.blockState
                blockEntityCache?.read(blockState, CompoundNBT())
                blockEntityCached = true
            }
            if (blockEntityCache != null) {
                renderEntity(blockState, blockEntityCache!!, ms, buffer)
            }
        }
        ms.pop()
        buffer.finish()
    }

    private fun <T : TileEntity> renderEntity(state: BlockState, entity: T, ms: MatrixStack, buffer: IRenderTypeBuffer) {
        val renderer = TileEntityRendererDispatcher.instance.getRenderer(entity)
        if (renderer != null && entity.type.isValidBlock(state.block)) {
            renderer.render(entity, Minecraft.getInstance().renderPartialTicks, ms, buffer, 0xF0, OverlayTexture.NO_OVERLAY)
        }
    }

    class FakeBlockDisplayReader(private val blockState: BlockState, private val fluidState: FluidState) : IBlockDisplayReader {
        @OnlyIn(Dist.CLIENT)
        override fun func_230487_a_(direction: Direction, p_230487_2_: Boolean): Float = 1f
        override fun getLightManager(): WorldLightManager = Minecraft.getInstance().world!!.lightManager
        override fun getLightFor(lightType: LightType, pos: BlockPos): Int = 15
        override fun getLightSubtracted(pos: BlockPos, p_226659_2_: Int): Int = 15
        override fun canSeeSky(pos: BlockPos): Boolean = true
        @OnlyIn(Dist.CLIENT)
        override fun getBlockColor(pos: BlockPos, resolver: ColorResolver): Int = fluidState.fluid.getAttributes().color
        override fun getTileEntity(pos: BlockPos): TileEntity? = null
        override fun getBlockState(pos: BlockPos): BlockState {
            return if (pos == BlockPos.ZERO) {
                blockState
            } else {
                Blocks.AIR.defaultState
            }
        }
        override fun getFluidState(pos: BlockPos): FluidState {
            return if (pos == BlockPos.ZERO) {
                fluidState
            } else {
                Fluids.EMPTY.defaultState
            }
        }
    }
}