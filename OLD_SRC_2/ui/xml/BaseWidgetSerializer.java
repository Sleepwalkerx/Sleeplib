package com.sleepwalker.sleeplib.ui.xml;

import com.sleepwalker.sleeplib.SleepLib;
import com.sleepwalker.sleeplib.registries.SLRegistries;
import com.sleepwalker.sleeplib.ui.property.DynamicPropertyManager;
import com.sleepwalker.sleeplib.ui.property.Property;
import com.sleepwalker.sleeplib.ui.widget.Widget;
import com.sleepwalker.sleeplib.util.exception.XmlParseException;
import com.sleepwalker.sleeplib.util.serialization.XmlUtil;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistryEntry;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import javax.annotation.Nonnull;

public abstract class BaseWidgetSerializer<T extends Widget> extends ForgeRegistryEntry<WidgetSerializer<?>> implements WidgetSerializer<T> {

    @Override
    public void deserialize(@Nonnull T instance, @Nonnull Element element, @Nonnull DeserializeContext context) throws XmlParseException {

        instance.setPropertyManager(new DynamicPropertyManager());

        NamedNodeMap attrs = element.getAttributes();
        for(int i = 0; i < attrs.getLength(); i++){

            Node node = attrs.item(i);
            if(!(node instanceof Attr)){
                continue;
            }

            Attr attr = (Attr) node;

            ResourceLocation propertyId = XmlUtil.getNodeResourceLocationOrDefault(attr, SleepLib.MODID);
            Property<?, ?> property = SLRegistries.PROPERTIES.get().getValue(propertyId);

            if(property != null){
                setProperty(property, attr, instance, context);
                //throw new XmlParseException();
            }
            else {
                SleepLib.LOGGER.warn("Unknown Property - '" + propertyId + "' Element:" + attr.getOwnerElement().getTagName());
            }
        }
    }

    protected <TT extends Widget, V> void setProperty(@Nonnull Property<TT, V> property, @Nonnull Attr attr, @Nonnull T widget, @Nonnull DeserializeContext context){

        if (!property.getHostType().isInstance(widget)) {
            throw new XmlParseException("Property '" + property.getRegistryName() + "' cannot be applied to " + widget.getClass().getSimpleName());
        }

        TT host = property.getHostType().cast(widget);
        V value = property.fromXml(attr, host, context);
        widget.getPropertyManager().setPropertyValue(property, host, value);
    }
}
