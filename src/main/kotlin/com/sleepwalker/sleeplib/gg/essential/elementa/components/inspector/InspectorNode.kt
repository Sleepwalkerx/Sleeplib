package com.sleepwalker.sleeplib.gg.essential.elementa.components.inspector

import com.sleepwalker.sleeplib.gg.essential.elementa.UIComponent
import com.sleepwalker.sleeplib.gg.essential.elementa.components.TreeNode
import com.sleepwalker.sleeplib.gg.essential.elementa.components.UIBlock
import com.sleepwalker.sleeplib.gg.essential.elementa.components.UIText
import com.sleepwalker.sleeplib.gg.essential.elementa.constraints.ChildBasedSizeConstraint
import com.sleepwalker.sleeplib.gg.essential.elementa.constraints.SiblingConstraint
import com.sleepwalker.sleeplib.gg.essential.elementa.constraints.TextAspectConstraint
import com.sleepwalker.sleeplib.gg.essential.elementa.dsl.toConstraint
import com.sleepwalker.sleeplib.gg.essential.elementa.dsl.childOf
import com.sleepwalker.sleeplib.gg.essential.elementa.dsl.constrain
import com.sleepwalker.sleeplib.gg.essential.elementa.dsl.pixels
import java.awt.Color

class InspectorNode(private val inspector: Inspector, val targetComponent: UIComponent) : TreeNode() {
    private val componentClassName = targetComponent.javaClass.simpleName.ifEmpty { "UnknownType" }
    private val componentDisplayName = targetComponent.componentName.let { if (it == componentClassName) it else "$componentClassName: $it" }
    private var wasHidden = false

    private val component: UIComponent = object : UIBlock(Color(0, 0, 0, 0)) {
        private val text = UIText(componentDisplayName).constrain {
            width = TextAspectConstraint()
        } childOf this

        override fun animationFrame() {
            super.animationFrame()

            val isCurrentlyHidden = targetComponent.parent != targetComponent && !targetComponent.parent.children.contains(
                targetComponent
            )
            if (isCurrentlyHidden && !wasHidden) {
                wasHidden = true
                text.setText("§r$componentDisplayName §7§o(Hidden)")
            } else if (!isCurrentlyHidden && wasHidden) {
                wasHidden = false
                text.setText(componentDisplayName)
            }
        }
    }.constrain {
        x = SiblingConstraint()
        y = 2.pixels()
        width = ChildBasedSizeConstraint()
        height = ChildBasedSizeConstraint()
    }.onMouseClick { event ->
        event.stopImmediatePropagation()
        toggleSelection()
    }

    internal fun toggleSelection() {
        inspector.selectedNode?.component?.setColor(Color(0, 0, 0, 0).toConstraint())

        if (inspector.selectedNode == this@InspectorNode) {
            inspector.setSelectedNode(null)
        } else {
            inspector.setSelectedNode(this@InspectorNode)
            component.setColor(Color(32, 78, 138).toConstraint())
        }
    }

    override fun getArrowComponent() = ArrowComponent(targetComponent.children.isEmpty())

    override fun getPrimaryComponent() = component
}
