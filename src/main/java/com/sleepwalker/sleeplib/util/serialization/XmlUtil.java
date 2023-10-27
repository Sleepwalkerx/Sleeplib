package com.sleepwalker.sleeplib.util.serialization;

import com.sleepwalker.sleeplib.util.exception.XmlParseException;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.ResourceLocationException;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.RegistryManager;
import org.w3c.dom.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Iterator;

public final class XmlUtil {

    @Nonnull
    public static ResourceLocation getNodeResourceLocationOrDefault(@Nonnull Node node, @Nonnull String defaultNamespace){
        String prefix = node.getPrefix();
        if(prefix == null || prefix.isEmpty()){
            prefix = defaultNamespace;
        }
        return new ResourceLocation(prefix, node.getLocalName());
    }

    @Nonnull
    public static <V extends IForgeRegistryEntry<V>> V getAttrAsRegistry(@Nonnull String name, @Nonnull Element element, @Nonnull Class<V> type, @Nonnull V def){

        if(!element.hasAttribute(name)){
            return def;
        }

        Attr attr = element.getAttributeNode(name);

        ResourceLocation loc;
        try {
            loc = new ResourceLocation(attr.getValue());
        }
        catch (ResourceLocationException e){
            throw new XmlParseException(e);
        }

        IForgeRegistry<V> registry = RegistryManager.ACTIVE.getRegistry(type);
        V value = registry.getValue(loc);
        if(value == null){
            throw new XmlParseException("Can't find " + registry.getRegistryName() + " value " + loc);
        }

        return value;
    }

    @Nonnull
    public static Element getFirstElement(@Nonnull Element parent){

        Element element = findFirstElement(parent.getChildNodes());
        if(element == null){
            throw new XmlParseException(parent.getTagName() + " does not have a single element");
        }

        return element;
    }

    public static Element getFirstElementByNameOrDef(@Nonnull String name, @Nonnull Element parent, Element def){

        Element element = findFirstElement(parent.getElementsByTagName(name));
        if(element == null){
            return def;
        }
        return element;
    }

    @Nonnull
    public static Element getFirstElementByName(@Nonnull String name, @Nonnull Element parent){

        Element element = findFirstElement(parent.getElementsByTagName(name));
        if(element == null){
            throw new XmlParseException("Can't find Element - " + name);
        }
        return element;
    }

    @Nullable
    private static Element findFirstElement(@Nullable NodeList list){

        if(list == null || list.getLength() == 0){
            return null;
        }

        for(int i = 0; i < list.getLength(); i++){

            if(list.item(i) instanceof Element){
                return (Element) list.item(i);
            }
        }

        return null;
    }

    @Nonnull
    public static Iterable<Attr> getAttrsAsIterable(@Nonnull Node node) {
        NamedNodeMap namedNodeMap = node.getAttributes();
        return new AttrIterator(namedNodeMap);
    }

    private static class AttrIterator implements Iterator<Attr>, Iterable<Attr> {

        @Nonnull private final NamedNodeMap namedNodeMap;
        private Attr attr;
        private int index;

        private AttrIterator(@Nonnull NamedNodeMap namedNodeMap) {
            this.namedNodeMap = namedNodeMap;
        }

        @Override
        public boolean hasNext() {

            attr = null;
            for (; index < namedNodeMap.getLength(); index++){
                Node node = namedNodeMap.item(index);
                if(node instanceof Attr){
                    attr = (Attr) node;
                }
            }

            return attr != null;
        }

        @Override
        @Nonnull
        public Attr next() {
            return attr;
        }

        @Override
        public Iterator<Attr> iterator() {
            return this;
        }
    }
}
