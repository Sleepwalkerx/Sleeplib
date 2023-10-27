package com.sleepwalker.sleeplib.ui.xml;

import com.sleepwalker.sleeplib.ui.Root;
import com.sleepwalker.sleeplib.ui.widget.Widget;
import com.sleepwalker.sleeplib.ui.widget.WidgetGroup;
import com.sleepwalker.sleeplib.util.exception.XmlParseException;
import net.minecraftforge.registries.ForgeRegistryEntry;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

public class SimpleLayoutSerializer extends ForgeRegistryEntry<LayoutSerializer> implements LayoutSerializer {

    @Nonnull public static final String DEFAULT_NAME_PREFIX = "com.sleepwalker.sleeplib.ui.widget.";

    @Nonnull
    @Override
    public WidgetGroup deserialize(@Nonnull Element rootElement, @Nonnull Root root, @Nonnull DeserializeContext context) {

        Widget preContent = createWidgetByClassName(rootElement.getLocalName());
        if(!(preContent instanceof WidgetGroup)){
            throw new XmlParseException("Content widget must be a group widget.");
        }

        WidgetGroup content = (WidgetGroup) preContent;
        content.setRoot(root);
        initWidget(content.getSerializer(), content, rootElement, context);

        Map<Element, Widget> allWidgets = new HashMap<>();
        allWidgets.put(rootElement, content);

        NodeList childList = rootElement.getChildNodes();
        for(int i = 0; i < childList.getLength(); i++){

            Node node = childList.item(i);

            if(node instanceof Element){

                Element element = (Element) node;
                Widget widget = deserializeWidget(element, root, context, allWidgets);
                allWidgets.put(element, widget);
            }
        }

        return content;
    }

    @Nonnull
    private Widget deserializeWidget(@Nonnull Element element, @Nonnull Root root, @Nonnull DeserializeContext context, @Nonnull Map<Element, Widget> allWidgets){

        Node parentNode = element.getParentNode();
        if(!(parentNode instanceof Element)){
            throw new XmlParseException("Widget parent must be an element child:" + element.getNodeName() + " parent:" + parentNode.getNodeName());
        }

        Element parentElem = (Element) parentNode;
        Widget parentWidget = allWidgets.get(parentElem);

        if(!(parentWidget instanceof WidgetGroup)){
            throw new XmlParseException("Only WidgetGroup can have child elements child:" + element.getNodeName() + " parent:" + parentNode.getNodeName());
        }

        WidgetGroup parent = (WidgetGroup) parentWidget;
        Widget widget = createWidgetByClassName(element.getLocalName());
        widget.setRoot(root);
        widget.setParent(parent);
        initWidget(widget.getSerializer(), widget, element, context);
        parent.addChild(widget);

        return widget;
    }

    private <T extends Widget> void initWidget(@Nonnull WidgetSerializer<T> serializer, @Nonnull Widget instance, @Nonnull Element element, @Nonnull DeserializeContext context){
        serializer.deserialize(serializer.getWidgetType().cast(instance), element, context);
    }

    @Nonnull
    private Widget createWidgetByClassName(@Nonnull String name) {

        String canonicalName = name.contains(".") ? name : DEFAULT_NAME_PREFIX + name;

        Class<?> clazz;
        try {
            clazz = Class.forName(canonicalName);
        }
        catch (ClassNotFoundException e){
            throw new XmlParseException(e);
        }

        if(!Widget.class.isAssignableFrom(clazz)){
            throw new XmlParseException("Class - " + clazz.getCanonicalName() + "  must be inherited from Widget");
        }

        try {
            return (Widget) clazz.newInstance();
        }
        catch (InstantiationException | IllegalAccessException | ClassCastException e){
            throw new XmlParseException("Can't create Widget instance", e);
        }
    }
}
