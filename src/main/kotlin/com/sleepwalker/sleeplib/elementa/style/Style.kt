package com.sleepwalker.sleeplib.elementa.style

import com.google.common.collect.ImmutableMap
import com.sleepwalker.sleeplib.elementa.drawable.Drawable

val ENABLED = StyleProperty(Drawable::class.java, "enabled")
val FOCUSED = StyleProperty(Drawable::class.java, "focused")
val PRESSED = StyleProperty(Drawable::class.java, "pressed")
val DISABLED = StyleProperty(Drawable::class.java, "disabled")

class Style(private val properties: Map<StyleProperty<*>, *>) {

    constructor(vararg values: StyleValue<*>) : this(ImmutableMap.copyOf(values.associate { it.property to it.value }))

    fun <T> getValueOrNull(property: StyleProperty<T>): T? {
        return property.classType.cast(properties[property])
    }

    fun <T> getValueOrThrow(property: StyleProperty<T>): T {
        val value = properties[property]
        return if(value == null) throw IllegalArgumentException("Property ${property.name} is missing") else property.classType.cast(value)
    }

    class Builder {

        private val properties: MutableMap<StyleProperty<*>, Any?> = hashMapOf()

        fun <T> put(property: StyleProperty<T>, value: T) = apply {
            properties[property] = value
        }

        fun build(): Style = Style(ImmutableMap.copyOf(properties))
    }
}

data class StyleProperty<T>(val classType: Class<T>, val name: String)
data class StyleValue<T>(val property: StyleProperty<T>, val value: T)

infix fun <T> StyleProperty<T>.to(that: T): StyleValue<T> = StyleValue(this, that)