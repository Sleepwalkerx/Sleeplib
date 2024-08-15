package com.sleepwalker.sleeplib.util.serialization

import com.sleepwalker.sleeplib.util.exception.XmlParseException
import net.minecraft.util.ResourceLocation
import net.minecraft.util.ResourceLocationException
import net.minecraftforge.registries.IForgeRegistryEntry
import net.minecraftforge.registries.RegistryManager
import org.w3c.dom.*

fun getNodeResourceLocationOrDefault(node: Node, defaultNamespace: String?): ResourceLocation {
    var prefix = node.prefix
    if (prefix == null || prefix.isEmpty()) {
        prefix = defaultNamespace
    }
    return ResourceLocation(prefix, node.localName)
}

fun <V : IForgeRegistryEntry<V>?> getAttrAsRegistry(name: String, element: Element, type: Class<V>, def: V): V {
    if (!element.hasAttribute(name)) {
        return def
    }
    val attr = element.getAttributeNode(name)
    val loc: ResourceLocation = try {
        ResourceLocation(attr.value)
    } catch (e: ResourceLocationException) {
        throw XmlParseException(e)
    }
    val registry = RegistryManager.ACTIVE.getRegistry(type)
    return registry.getValue(loc) ?: throw XmlParseException("Can't find " + registry.registryName + " value " + loc)
}

fun getFirstElement(parent: Element): Element {
    return findFirstElement(parent.childNodes) ?: throw XmlParseException(parent.tagName + " does not have a single element")
}

fun getFirstElementByNameOrDef(name: String, parent: Element, def: Element): Element {
    return findFirstElement(parent.getElementsByTagName(name)) ?: return def
}

fun getFirstElementByName(name: String, parent: Element): Element {
    return findFirstElement(parent.getElementsByTagName(name)) ?: throw XmlParseException("Can't find Element - $name")
}

private fun findFirstElement(list: NodeList?): Element? {
    if (list == null || list.length == 0) {
        return null
    }
    for (i in 0 until list.length) {
        if (list.item(i) is Element) {
            return list.item(i) as Element
        }
    }
    return null
}

fun getAttrsAsIterable(node: Node): Iterable<Attr> {
    val namedNodeMap = node.attributes
    return AttrIterator(namedNodeMap)
}

private class AttrIterator(private val namedNodeMap: NamedNodeMap) : Iterator<Attr>, Iterable<Attr> {

    private var attr: Attr? = null
    private var index = 0
    override fun hasNext(): Boolean {
        attr = null
        while (index < namedNodeMap.length) {
            val node = namedNodeMap.item(index)
            if (node is Attr) {
                attr = node
            }
            index++
        }
        return attr != null
    }

    override fun next(): Attr {
        return attr!!
    }

    override fun iterator(): Iterator<Attr> {
        return this
    }
}
