package com.sleepwalker.sleeplib.gg.essential.elementa.constraints.resolution

import com.sleepwalker.sleeplib.gg.essential.elementa.constraints.ConstraintType
import com.sleepwalker.sleeplib.gg.essential.elementa.UIComponent
import com.sleepwalker.sleeplib.gg.essential.elementa.UIConstraints
import com.sleepwalker.sleeplib.gg.essential.elementa.components.Window

class ConstraintResolver(window: Window) {
    private val graph = DirectedAcyclicGraph<ResolverNode>()

    init {
        window.forEachChild {
            // Color constraints are not added because they are always resolvable
            graph.addVertices(
                ResolverNode(it, it.constraints.x, ConstraintType.X),
                ResolverNode(it, it.constraints.y, ConstraintType.Y),
                ResolverNode(it, it.constraints.width, ConstraintType.WIDTH),
                ResolverNode(it, it.constraints.height, ConstraintType.HEIGHT),
                ResolverNode(it, it.constraints.textScale, ConstraintType.TEXT_SCALE),
                ResolverNode(it, it.constraints.radius, ConstraintType.RADIUS)
            )
        }

        window.forEachChild {
            val visitor = ConstraintVisitor(graph, it)

            it.constraints.x.visit(visitor, ConstraintType.X)
            it.constraints.y.visit(visitor, ConstraintType.Y)
            it.constraints.width.visit(visitor, ConstraintType.WIDTH)
            it.constraints.height.visit(visitor, ConstraintType.HEIGHT)
            it.constraints.textScale.visit(visitor, ConstraintType.TEXT_SCALE)
            it.constraints.radius.visit(visitor, ConstraintType.RADIUS)
        }
    }

    fun getCyclicNodes() = graph.getCyclicLoop()
}

fun UIConstraints.getConstraint(type: ConstraintType) = when (type) {
    com.sleepwalker.sleeplib.gg.essential.elementa.constraints.ConstraintType.X -> x
    com.sleepwalker.sleeplib.gg.essential.elementa.constraints.ConstraintType.Y -> y
    com.sleepwalker.sleeplib.gg.essential.elementa.constraints.ConstraintType.WIDTH -> width
    com.sleepwalker.sleeplib.gg.essential.elementa.constraints.ConstraintType.HEIGHT -> height
    com.sleepwalker.sleeplib.gg.essential.elementa.constraints.ConstraintType.RADIUS -> radius
    com.sleepwalker.sleeplib.gg.essential.elementa.constraints.ConstraintType.COLOR -> color
    com.sleepwalker.sleeplib.gg.essential.elementa.constraints.ConstraintType.TEXT_SCALE -> textScale
    com.sleepwalker.sleeplib.gg.essential.elementa.constraints.ConstraintType.FONT_PROVIDER -> fontProvider
}

fun Window.forEachChild(action: (UIComponent) -> Unit) {
    fun helper(component: UIComponent) {
        action(component)
        component.children.forEach(::helper)
    }

    helper(this)
}
