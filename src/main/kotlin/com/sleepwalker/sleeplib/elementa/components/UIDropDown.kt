package com.sleepwalker.sleeplib.elementa.components

import com.sleepwalker.sleeplib.client.SLSprites
import com.sleepwalker.sleeplib.client.drawable.Drawable
import com.sleepwalker.sleeplib.gg.essential.elementa.UIComponent
import com.sleepwalker.sleeplib.gg.essential.elementa.components.UIContainer
import com.sleepwalker.sleeplib.gg.essential.elementa.components.UIText
import com.sleepwalker.sleeplib.gg.essential.elementa.constraints.*
import com.sleepwalker.sleeplib.gg.essential.elementa.constraints.animation.Animations
import com.sleepwalker.sleeplib.gg.essential.elementa.dsl.*
import com.sleepwalker.sleeplib.gg.essential.elementa.effects.ScissorEffect
import com.sleepwalker.sleeplib.gg.essential.universal.UMatrixStack
import com.sleepwalker.sleeplib.gg.essential.universal.USound
import java.awt.Color
import javax.annotation.Nonnull

class UIDropDown(
    private val valueMap: Map<Any, String>,
    private var selectedValue: Any,
    private val sprite: Drawable
) : UIContainer() {

    private var valueSelectedListeners = mutableListOf<UIDropDown.() -> Unit>()
    private var clickSound: UIComponent.() -> Unit = { USound.playButtonPress() }
    private val titleButton: UIButton
    private val pointingTriangleIcon: UIPointingTriangleIcon
    private var expansion: UIContainer? = null

    init {
        var mainTitle: String? = valueMap[selectedValue]
        if (mainTitle == null) {
            mainTitle = "null"
        }

        titleButton = UIButton(mainTitle, selectedValue, false) childOf this
        pointingTriangleIcon = UIPointingTriangleIcon().constrain {
            x = PixelConstraint(4f, true)
            y = CenterConstraint()
        } childOf this

        onMouseClickConsumer {
            if (hasFocus()) {
                releaseWindowFocus()
            }
            else {
                grabWindowFocus()
            }
        }

        onFocus {
            pointingTriangleIcon.apply {
                ::angle.animate(Animations.OUT_QUART, 0.35f, 180f, 0f)
            }

            expansion = UIContainer().constrain {
                x = PixelConstraint(0f).to(this@UIDropDown) as XConstraint
                y = PixelConstraint(0f, alignOpposite = true, true).to(this@UIDropDown) as YConstraint
                width = ChildBasedMaxSizeConstraint()
                height = 0f.pixel
            } childOf parent

            expansion?.let {
                it.enableEffect(ScissorEffect())

                for ((key, value) in valueMap) {
                    if (key !== selectedValue) {
                        UIButton(value, key, true)
                            .setX(PixelConstraint(0f))
                            .setY(SiblingConstraint())
                            .setChildOf(it)
                    }
                }

                it.makeAnimation()
                    .setHeightAnimation(Animations.OUT_QUART, 0.25f, ChildBasedSizeConstraint())
                    .begin()
            }
        }

        onFocusLost {
            pointingTriangleIcon.apply {
                ::angle.animate(Animations.OUT_QUART, 0.35f, 0f, 0f)
            }

            expansion?.let {
                it.makeAnimation()
                    .setHeightAnimation(Animations.OUT_QUART, 0.25f, PixelConstraint(0f))
                    .onCompleteRunnable {
                        it.parent.removeChild(it)
                        expansion = null
                    }
                    .begin()
            }
        }
    }

    fun setClickSound(clickSound: UIComponent.() -> Unit) = apply {
        this.clickSound = clickSound
    }

    fun onValueSelected(listener: UIDropDown.() -> Unit) = apply {
        valueSelectedListeners.add(listener)
    }

    fun setValue(@Nonnull value: Any) {
        selectedValue = value
        var title: String? = valueMap[value]
        if (title == null) {
            title = "null"
        }
        titleButton.update(title, value)
        valueSelectedListeners.forEach { it.invoke(this) }
    }

    fun getRawValue(): Any {
        return selectedValue
    }

    fun <T> getValue(@Nonnull type: Class<T>): T {
        return type.cast(selectedValue)
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> getValue(): T {
        return selectedValue as T
    }

    inner class UIButton(
        title: String,
        private var value: Any,
        private val selectable: Boolean
    ) : UIDrawable(sprite) {

        private val title: UIText

        init {
            constrain {
                width = RelativeConstraint().to(this@UIDropDown) as WidthConstraint
                height = RelativeConstraint().to(this@UIDropDown) as HeightConstraint
            }
            setColor(CopyConstraintColor().to(this@UIDropDown) as ColorConstraint)

            UIHollowBlock(Color(255, 255, 255, 0), 1f)
                .setWidth(RelativeConstraint())
                .setHeight(RelativeConstraint())
                .onMouseEnter {
                    this.makeAnimation()
                        .setColorAnimation(
                            Animations.OUT_QUART,
                            0.225f,
                            AlphaAspectColorConstraint(this.getColor(), 1f)
                        )
                        .begin()
                }
                .onMouseLeave {
                    this.makeAnimation()
                        .setColorAnimation(
                            Animations.OUT_QUART,
                            0.225f,
                            AlphaAspectColorConstraint(this.getColor(), 0f)
                        )
                        .begin()
                } childOf this

            this.title = UIText(title).constrain {
                x = 4f.pixel
                y = CenterConstraint()
            } childOf this

            onMouseClick {
                clickSound.invoke(this)
                if (selectable) {
                    setValue(value)
                    if (this@UIDropDown.hasFocus()) {
                        this@UIDropDown.releaseWindowFocus()
                    }
                }
            }
        }

        fun update(@Nonnull title: String, @Nonnull value: Any) {
            this.title.setText(title)
            this.value = value
        }
    }

    inner class UIPointingTriangleIcon : UIDrawable(SLSprites.DOWN_POINTING_TRIANGLE) {

        var angle = 0f

        init {
            setWidth(SLSprites.DOWN_POINTING_TRIANGLE.width.pixel)
            setHeight(SLSprites.DOWN_POINTING_TRIANGLE.height.pixel)
        }

        override fun draw(@Nonnull matrixStack: UMatrixStack) {
            matrixStack.push()
            val x = getLeft() + getWidth() / 2f
            val y = getTop() + getHeight() / 2f
            matrixStack.translate(x, y, 0f)
            matrixStack.rotate(angle, 0f, 0f, 1f, true)
            matrixStack.translate(-x, -y, 0f)
            super.draw(matrixStack)
            matrixStack.pop()
        }
    }
}