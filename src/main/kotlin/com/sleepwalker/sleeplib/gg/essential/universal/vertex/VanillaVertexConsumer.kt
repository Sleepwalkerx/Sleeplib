package com.sleepwalker.sleeplib.gg.essential.universal.vertex

import com.sleepwalker.sleeplib.gg.essential.universal.UMatrixStack

//#if MC>=11602
import com.mojang.blaze3d.vertex.IVertexBuilder
//#else
//$$ import net.minecraft.client.renderer.Matrix3f
//$$ import net.minecraft.client.renderer.Matrix4f
//$$ import net.minecraft.client.renderer.Vector3f
//$$ import net.minecraft.client.renderer.Vector4f
//#endif

internal class VanillaVertexConsumer(
    //#if MC>=11600
    private val inner: IVertexBuilder,
    //#else
    //$$ private val inner: BufferBuilder,
    //#endif
) : UVertexConsumer {

    override fun pos(stack: UMatrixStack, x: Double, y: Double, z: Double): UVertexConsumer = apply {
        if (stack === UMatrixStack.UNIT) {
            inner.pos(x, y, z)
            return@apply
        }
        //#if MC>=11602
        inner.pos(stack.peek().model, x.toFloat(), y.toFloat(), z.toFloat())
        //#else
        //$$ val vec = Vector4f(x.toFloat(), y.toFloat(), z.toFloat(), 1f)
        //#if MC>=11400
        //$$ vec.transform(stack.peek().model);
        //#else
        //$$ Matrix4f.transform(stack.peek().model, vec, vec)
        //#endif
        //$$ inner.pos(vec.getX().toDouble(), vec.getY().toDouble(), vec.getZ().toDouble())
        //#endif
    }

    override fun color(red: Int, green: Int, blue: Int, alpha: Int): UVertexConsumer = apply {
        inner.color(red, green, blue, alpha)
    }

    override fun tex(u: Double, v: Double): UVertexConsumer = apply {
        //#if MC>=11502
        inner.tex(u.toFloat(), v.toFloat());
        //#else
        //$$ inner.tex(u, v)
        //#endif
    }

    override fun overlay(u: Int, v: Int): UVertexConsumer = apply {
        //#if MC>=11502
        inner.overlay(u, v);
        //#else
        //$$ inner.tex(u.toDouble(), v.toDouble())
        //#endif
    }

    override fun light(u: Int, v: Int): UVertexConsumer = apply {
        inner.lightmap(u, v)
    }

    override fun norm(stack: UMatrixStack, x: Float, y: Float, z: Float): UVertexConsumer = apply {
        if (stack === UMatrixStack.UNIT) {
            inner.normal(x, y, z)
            return@apply
        }
        //#if MC>=11602
        inner.normal(stack.peek().normal, x, y, z);
        //#else
        //$$ val vec = Vector3f(x, y, z)
        //#if MC>=11400
        //$$ vec.transform(stack.peek().normal);
        //#else
        //$$ Matrix3f.transform(stack.peek().normal, vec, vec)
        //#endif
        //$$ inner.normal(vec.getX(), vec.getY(), vec.getZ())
        //#endif
    }

    override fun endVertex(): UVertexConsumer = apply {
        inner.endVertex()
    }
}
