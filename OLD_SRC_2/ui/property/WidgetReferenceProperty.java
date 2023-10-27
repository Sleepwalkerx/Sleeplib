package com.sleepwalker.sleeplib.ui.property;

import com.sleepwalker.sleeplib.ui.widget.Widget;
import com.sleepwalker.sleeplib.ui.xml.DeserializeContext;
import com.sleepwalker.sleeplib.util.exception.XmlParseException;
import net.minecraftforge.registries.ForgeRegistryEntry;
import org.w3c.dom.Attr;

import javax.annotation.Nonnull;

public class WidgetReferenceProperty extends ForgeRegistryEntry<Property<?, ?>> implements Property<Widget, WidgetReference> {

    @Override
    public void onSet(@Nonnull Widget object, @Nonnull WidgetReference value) { }

    @Nonnull
    @Override
    public WidgetReference getDefaultValue(@Nonnull Widget object) {
        return WidgetReferences.parent();
    }

    @Nonnull
    @Override
    public WidgetReference fromXml(@Nonnull Attr attr, @Nonnull Widget host, @Nonnull DeserializeContext context) throws XmlParseException {
        return fromString(attr.getValue(), host);
    }

    @Nonnull
    @Override
    public Class<Widget> getHostType() {
        return Widget.class;
    }

    @Nonnull
    @Override
    public Class<WidgetReference> getValueType() {
        return WidgetReference.class;
    }

    @Nonnull
    public static WidgetReference fromString(@Nonnull String str, @Nonnull Widget target) throws XmlParseException {

        int valueStart = str.indexOf('(');
        if(valueStart == 0){
            throw new XmlParseException("Format mismatch %registryName%(%value%)");
        }

        String registryName;
        String value;
        if(valueStart == -1){
            registryName = str;
            value = "";
        }
        else {

            int valueEnd = str.lastIndexOf(')');
            if(valueEnd == -1){
                throw new XmlParseException("Format mismatch %registryName%(%value%)");
            }

            registryName = str.substring(0, valueStart);
            value = str.substring(valueStart, valueEnd);
        }

        if(registryName.equals("parent")){

            if(value.isEmpty()){
                return WidgetReferences.parent();
            }
            else if(value.startsWith("#")){
                return new WidgetReferences.ParentChildId(value.substring(1));
            }
            else {
                return new WidgetReferences.ParentChildName(value);
            }
        }
        else {
            throw new XmlParseException("Unknown registryName - " + registryName);
        }
    }
}
