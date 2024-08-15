package com.sleepwalker.sleeplib.elementa.components

import com.sleepwalker.sleeplib.elementa.drawable.Shape
import com.sleepwalker.sleeplib.elementa.events.UIColorPickEvent
import com.sleepwalker.sleeplib.gg.essential.elementa.UIComponent
import com.sleepwalker.sleeplib.gg.essential.elementa.components.UIBlock
import com.sleepwalker.sleeplib.gg.essential.elementa.components.UIContainer
import com.sleepwalker.sleeplib.gg.essential.elementa.constraints.*
import com.sleepwalker.sleeplib.gg.essential.elementa.dsl.*
import com.sleepwalker.sleeplib.gg.essential.elementa.state.BasicState
import com.sleepwalker.sleeplib.gg.essential.elementa.state.toConstraint
import com.sleepwalker.sleeplib.gg.essential.universal.UGraphics
import com.sleepwalker.sleeplib.gg.essential.universal.UMatrixStack
import com.sleepwalker.sleeplib.util.HSVtoRGB
import com.sleepwalker.sleeplib.util.RGBtoHSV
import com.sleepwalker.sleeplib.util.fromHSV
import com.sleepwalker.sleeplib.util.toHSV
import org.lwjgl.opengl.GL11
import java.awt.Color

class UIColorPicker : UIContainer() {

    private var hue: Float = 0f
    private var saturation: Float = 1f
    private var lightness: Float = 1f
    private val colorState = BasicState(Color.RED)
    private val hueColorState = BasicState(Color.RED)

    private val slPicker: SlideComponent
    private val huePicker: SlideComponent

    private val colorPickListeners = mutableListOf<UIComponent.(UIColorPickEvent) -> Unit>()

    init {
        val colorSlide: UIComponent
        val sliderPanel = UIContainer()
            .constrain {
                y = 0.pixel(true)
                width = RelativeConstraint(1f)
                height = 14.pixel
            }
            .addChild(UIDrawable(COLOR_SLIDER)
                .constrain {
                    val padding = 8.pixel
                    x = CenterConstraint()
                    y = CenterConstraint()
                    width = RelativeConstraint(1f) - padding
                    height = RelativeConstraint(1f) - padding
                }
                .also { colorSlide = it }
            ) childOf this

        val slider: UIComponent
        UIContainer()
            .constrain {
                x = CenterConstraint()
                y = CenterConstraint()
                width = RelativeConstraint(1f).to(colorSlide) as WidthConstraint
                height = RelativeConstraint(1f).to(colorSlide) + 2.pixel
            }
            .addChild(UIBlock(Color.WHITE)
                .constrain {
                    y = CenterConstraint()
                    width = AspectConstraint()
                    height = RelativeConstraint(1f)
                }
                .addChild(UIBlock(hueColorState).constrain {
                    x = CenterConstraint()
                    y = CenterConstraint()
                    width = RelativeConstraint(1f) - 2.pixel
                    height = RelativeConstraint(1f) - 2.pixel
                })
                .also { slider = it }
            ) childOf sliderPanel

        SlideComponent(SlideComponent.Mode.HORIZONTAL)
            .constrain {
                x = CenterConstraint()
                y = CenterConstraint()
                width = RelativeConstraint(1f)
                height = RelativeConstraint(1f)
            }
            .onSlide {
                hue = it.xValue
                updateColor()
            }
            .also {
                huePicker = it
                it.setSlider(slider)
                colorSlide.addChild(it)
            }

        val colorPickerSlider = UIBlock(Color.WHITE)
            .constrain {
                x = 0.pixel(true)
                width = AspectConstraint()
                height = RelativeConstraint(1f).to(slider) as HeightConstraint
            }
            .addChild(UIBlock(colorState).constrain {
                x = CenterConstraint()
                y = CenterConstraint()
                width = RelativeConstraint(1f) - 2.pixel
                height = RelativeConstraint(1f) - 2.pixel
            })

        UIContainer()
            .constrain {
                width = RelativeConstraint(1f)
                height = FillConstraint(true)
            }
            .addChild(UIBlock()
                .constrain {
                    width = RelativeConstraint(0.3f)
                    height = RelativeConstraint(1f)
                    color = colorState.toConstraint()
                }
            )
            .addChild(UIDrawable(COLOR_RECT)
                .constrain {
                    x = SiblingConstraint()
                    width = FillConstraint(true)
                    height = RelativeConstraint(1f)
                    color = hueColorState.toConstraint()
                }
                .addChild(SlideComponent(SlideComponent.Mode.HORIZONTAL_AND_VERTICAL)
                    .constrain {
                        width = RelativeConstraint(1f)
                        height = RelativeConstraint(1f)
                    }
                    .onSlide {
                        saturation = it.xValue
                        lightness = 1f - it.yValue
                        updateColor()
                    }
                    .also {
                        slPicker = it
                        it.addChild(colorPickerSlider)
                        it.setSlider(colorPickerSlider)
                    }
                )
            ) childOf this

        setColorPick(Color.RED)
    }

    fun onColorPick(listener: UIComponent.(UIColorPickEvent) -> Unit) = apply {
        colorPickListeners.add(listener)
    }

    fun getPickedColor(): Color {
        return colorState.get()
    }

    fun setColorPick(color: Color) {
        val (hue, saturation, lightness) = color.toHSV()
        slPicker.xValue = saturation
        slPicker.yValue = 1f - lightness
        slPicker.forceUpdate()
        huePicker.xValue = hue
        huePicker.forceUpdate()
        updateColor()
    }

    private fun updateColor(){
        colorState.set(Triple(hue, saturation, lightness).fromHSV())
        hueColorState.set(Triple(hue, 1f, 1f).fromHSV())

        val event = UIColorPickEvent(colorState.get())
        for(listener in colorPickListeners){
            this.listener(event)
        }
    }

    companion object {

        val COLOR_SLIDER = object : Shape {
            override fun drawImage(matrixStack: UMatrixStack, x: Double, top: Double, width: Double, height: Double, color: Color) {
                UGraphics.enableBlend()
                UGraphics.disableAlpha()
                UGraphics.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO)
                UGraphics.shadeModel(GL11.GL_SMOOTH)

                val buffer = UGraphics.getFromTessellator()
                val bottom: Double = top + height
                val step = 6
                val wStep = width / step
                var left = x
                var right: Double = left + wStep
                val alpha = color.alpha / 255f
                for (i in 0 until step) {
                    val (r1, g1, b1) = HSVtoRGB(i.toFloat() / step, 1f, 1f)
                    val (r2, g2, b2) = HSVtoRGB((i + 1f) / step, 1f, 1f)
                    buffer.beginWithDefaultShader(UGraphics.DrawMode.QUADS, UGraphics.CommonVertexFormats.POSITION_COLOR)
                    buffer.pos(matrixStack, right, top, 0.0).color(r2, g2, b2, alpha).endVertex()
                    buffer.pos(matrixStack, left, top, 0.0).color(r1, g1, b1, alpha).endVertex()
                    buffer.pos(matrixStack, left, bottom, 0.0).color(r1, g1, b1, alpha).endVertex()
                    buffer.pos(matrixStack, right, bottom, 0.0).color(r2, g2, b2, alpha).endVertex()
                    buffer.drawDirect()
                    left += wStep
                    right += wStep
                }

                UGraphics.shadeModel(GL11.GL_FLAT)
                UGraphics.disableBlend()
                UGraphics.enableAlpha()
            }
        }

        val COLOR_RECT = object : Shape {
            override fun drawImage(
                matrixStack: UMatrixStack,
                left: Double,
                top: Double,
                width: Double,
                height: Double,
                color: Color
            ) {
                UGraphics.enableBlend()
                UGraphics.disableAlpha()
                UGraphics.tryBlendFuncSeparate(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA, GL11.GL_ONE, GL11.GL_ZERO)
                UGraphics.shadeModel(GL11.GL_SMOOTH)

                val right: Double = left + width
                val bottom: Double = top + height
                val red = color.red / 255f
                val green = color.green / 255f
                val blue = color.blue / 255f
                val alpha = color.alpha / 255f

                val buffer = UGraphics.getFromTessellator()
                buffer.beginWithDefaultShader(UGraphics.DrawMode.QUADS, UGraphics.CommonVertexFormats.POSITION_COLOR)
                buffer.pos(matrixStack, right, top, 0.0).color(red, green, blue, 1f).endVertex()
                buffer.pos(matrixStack, left, top, 0.0).color(1f, 1f, 1f, alpha).endVertex()
                buffer.pos(matrixStack, left, bottom, 0.0).color(0f, 0f, 0f, alpha).endVertex()
                buffer.pos(matrixStack, right, bottom, 0.0).color(0f, 0f, 0f, alpha).endVertex()
                buffer.drawDirect()

                UGraphics.shadeModel(GL11.GL_FLAT)
                UGraphics.disableBlend()
                UGraphics.enableAlpha()
            }
        }
    }
}