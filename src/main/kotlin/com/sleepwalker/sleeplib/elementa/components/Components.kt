package com.sleepwalker.sleeplib.elementa.components

import com.sleepwalker.sleeplib.gg.essential.elementa.components.UIContainer
import com.sleepwalker.sleeplib.gg.essential.elementa.components.UIText
import com.sleepwalker.sleeplib.gg.essential.elementa.constraints.CenterConstraint
import com.sleepwalker.sleeplib.gg.essential.elementa.constraints.RelativeConstraint
import com.sleepwalker.sleeplib.gg.essential.elementa.dsl.childOf
import com.sleepwalker.sleeplib.gg.essential.elementa.dsl.constrain
import com.sleepwalker.sleeplib.gg.essential.elementa.dsl.minus
import com.sleepwalker.sleeplib.gg.essential.elementa.dsl.pixel

object Components {

    fun disposableButton(style: Styles.Button, text: String): UIButton {
        val button = UIButton(style)
        val panel = UIContainer().constrain {
            width = RelativeConstraint()
            height = RelativeConstraint() - 3.pixel
        } childOf button

        button
            .onMouseClickConsumer {
                panel.setY(1.pixel)
            }
            .onMouseReleaseRunnable {
                panel.setY(0.pixel)
            }

        UIText(text).constrain {
            x = CenterConstraint()
            y = CenterConstraint()
        } childOf panel

        return button
    }
}