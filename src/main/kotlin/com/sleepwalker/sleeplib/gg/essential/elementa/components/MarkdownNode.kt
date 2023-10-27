package com.sleepwalker.sleeplib.gg.essential.elementa.components

import com.sleepwalker.sleeplib.gg.essential.elementa.components.inspector.ArrowComponent
import com.sleepwalker.sleeplib.gg.essential.elementa.constraints.SiblingConstraint
import com.sleepwalker.sleeplib.gg.essential.elementa.dsl.constrain
import com.sleepwalker.sleeplib.gg.essential.elementa.dsl.pixels
import com.sleepwalker.sleeplib.gg.essential.elementa.markdown.drawables.Drawable
import com.sleepwalker.sleeplib.gg.essential.elementa.markdown.drawables.TextDrawable

internal class MarkdownNode(private val targetDrawable: Drawable) : TreeNode() {
    private val componentClassName = targetDrawable.javaClass.simpleName.ifEmpty { "UnknownType" }
    private val componentDisplayName =
        componentClassName + if (targetDrawable is TextDrawable) " \"${targetDrawable.formattedText}\"" else ""

    private val component = UIText(componentDisplayName).constrain {
        x = SiblingConstraint()
        y = 2.pixels
    }

    init {
        targetDrawable.children.forEach {
            addChild(MarkdownNode(it))
        }
    }
    override fun getArrowComponent() = ArrowComponent(targetDrawable.children.isEmpty())

    override fun getPrimaryComponent() = component
}
