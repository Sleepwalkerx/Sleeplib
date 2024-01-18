package com.sleepwalker.sleeplib.elementa.components

import com.sleepwalker.sleeplib.elementa.events.UISlideEvent
import com.sleepwalker.sleeplib.gg.essential.elementa.UIComponent
import com.sleepwalker.sleeplib.gg.essential.elementa.components.UIBlock
import com.sleepwalker.sleeplib.gg.essential.elementa.constraints.XConstraint
import com.sleepwalker.sleeplib.gg.essential.elementa.constraints.YConstraint
import com.sleepwalker.sleeplib.gg.essential.elementa.constraints.animation.Animations
import com.sleepwalker.sleeplib.gg.essential.elementa.dsl.basicXConstraint
import com.sleepwalker.sleeplib.gg.essential.elementa.dsl.basicYConstraint
import com.sleepwalker.sleeplib.gg.essential.elementa.dsl.pixel
import com.sleepwalker.sleeplib.gg.essential.universal.UMatrixStack
import net.minecraft.util.math.MathHelper
import javax.annotation.Nonnull

class SlideComponent(private val mode: Mode) : UIComponent() {

    private val slideListeners = mutableListOf<UIComponent.(UISlideEvent) -> Unit>()

    var xValue = 0f
        set(value) {
            val realValue = value.coerceIn(0f, 1f)
            if(field != realValue){
                needsUpdate = true
                field = realValue
            }
        }
    var yValue = 0f
        set(value) {
            val realValue = value.coerceIn(0f, 1f)
            if(field != realValue){
                needsUpdate = true
                field = realValue
            }
        }

    private var needsUpdate = false
    private var dragStartPosX: Float? = null
    private var dragStartPosY: Float? = null
    private var slider: UIComponent = UIBlock()
        .setWidth(10.pixel)
        .setHeight(10.pixel)
        .setChildOf(this)

    init {
        onMouseScroll {
            recalculateBySlider(
                if(mode.horizontal) it.delta.toFloat() else null,
                if(mode.vertical) it.delta.toFloat() else null
            )
        }
    }

    override fun draw(matrixStack: UMatrixStack) {
        beforeDrawCompat(matrixStack)

        if (needsUpdate) {
            needsUpdate = false
            if (mode.horizontal) {
                slider.makeAnimation().setXAnimation(Animations.IN_SIN, 0.1f, makeSliderXConstraint()).begin()
            }
            if(mode.vertical) {
                slider.makeAnimation().setYAnimation(Animations.IN_SIN, 0.1f, makeSliderYConstraint()).begin()
            }
        }

        super.draw(matrixStack)
    }

    fun onSlide(listener: UIComponent.(UISlideEvent) -> Unit) = apply {
        slideListeners.add(listener)
    }

    fun setSlider(component: UIComponent) = apply {
        slider.hide(true)
        slider = component
        component.onMouseScroll {
            recalculateBySlider(
                if(mode.horizontal) it.delta.toFloat() else null,
                if(mode.vertical) it.delta.toFloat() else null
            )
        }
        component.onMouseDrag { mouseX, mouseY, _ ->
            recalculateBySlider(
                if(!mode.horizontal) null else dragStartPosX?.let { mouseX - it },
                if(!mode.vertical) null else dragStartPosY?.let { mouseY - it }
            )
        }
        component.onMouseRelease {
            dragStartPosX = null
            dragStartPosY = null
        }
        component.onMouseClick {
            if(mode.horizontal){
                dragStartPosX = it.relativeX
            }
            if(mode.vertical){
                dragStartPosY = it.relativeY
            }
        }
        component.parent.onMouseClick {
            if(it.target == it.currentTarget){
                if(mode.horizontal){
                    val width = it.target.getWidth()
                    xValue = if(width == 0f) 0f else it.relativeX / width
                }
                if(mode.vertical){
                    val height = it.target.getHeight()
                    yValue = if(height == 0f) 0f else it.relativeY / height
                }
            }
        }
    }

    @Nonnull
    private fun makeSliderXConstraint(): XConstraint {
        return basicXConstraint { component: UIComponent ->
            val offset = (component.parent.getWidth() - component.getWidth()) * xValue
            component.parent.getLeft() + offset
        }
    }

    @Nonnull
    private fun makeSliderYConstraint(): YConstraint {
        return basicYConstraint { component: UIComponent ->
            val offset = (component.parent.getHeight() - component.getHeight()) * yValue
            component.parent.getTop() + offset
        }
    }

    fun forceUpdate() {
        needsUpdate = false
        if (mode.horizontal) {
            slider.setX(makeSliderXConstraint())
        }
        if (mode.vertical) {
            slider.setY(makeSliderYConstraint())
        }
    }

    private fun recalculateBySlider(dragDeltaX: Float?, dragDeltaY: Float?) {
        dragDeltaX?.let {
            val min = slider.parent.getLeft()
            val max = slider.parent.getRight()
            val newPos = slider.getLeft() + it - min
            xValue = MathHelper.clamp(newPos / (max - min - slider.getWidth()), 0f, 1f)
        }

        dragDeltaY?.let {
            val min = slider.parent.getTop()
            val max = slider.parent.getBottom()
            val newPos = slider.getTop() + it - min
            yValue = MathHelper.clamp(newPos / (max - min - slider.getHeight()), 0f, 1f)
        }

        needsUpdate = true

        val event = UISlideEvent(if (mode.horizontal) xValue else yValue, xValue, yValue, this, this)
        for (listener in slideListeners) {
            this.listener(event)
        }
    }

    enum class Mode(val horizontal: Boolean, val vertical: Boolean) {
        HORIZONTAL(true, false),
        VERTICAL(false, true),
        HORIZONTAL_AND_VERTICAL(true, true);
    }
}