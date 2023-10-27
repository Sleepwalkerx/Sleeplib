package com.sleepwalker.sleeplib.ui.property;

import com.sleepwalker.sleeplib.ui.layout.AbsoluteSize;
import com.sleepwalker.sleeplib.ui.layout.ParentPercentSize;
import com.sleepwalker.sleeplib.ui.layout.WidgetSize;
import com.sleepwalker.sleeplib.ui.widget.Widget;
import com.sleepwalker.sleeplib.ui.xml.DeserializeContext;
import com.sleepwalker.sleeplib.util.exception.XmlParseException;
import net.minecraftforge.registries.ForgeRegistryEntry;
import org.w3c.dom.Attr;

import javax.annotation.Nonnull;

public class DimensionMatchProperty extends ForgeRegistryEntry<Property<?, ?>> implements Property<Widget, WidgetSize> {

    @Override
    public void onSet(@Nonnull Widget object, @Nonnull WidgetSize value) {
        object.getParent().updateLayout();
    }

    @Nonnull
    @Override
    public WidgetSize getDefaultValue(@Nonnull Widget object) {
        return WidgetSize.ZERO;
    }

    @Nonnull
    @Override
    public WidgetSize fromXml(@Nonnull Attr attr, @Nonnull Widget host, @Nonnull DeserializeContext context) throws XmlParseException {

        if(attr.getValue().endsWith("%")){
            float percent;
            try {
                percent = Float.parseFloat(attr.getValue().substring(0, attr.getValue().length() - 1));
            }
            catch (NumberFormatException e){
                throw new XmlParseException(e);
            }
            return new ParentPercentSize(percent / 100f);
        }
        else {
            float value;
            try {
                value = Float.parseFloat(attr.getValue());
            }
            catch (NumberFormatException e){
                throw new XmlParseException(e);
            }
            return new AbsoluteSize(value);
        }
    }

    @Nonnull
    @Override
    public Class<Widget> getHostType() {
        return Widget.class;
    }

    @Nonnull
    @Override
    public Class<WidgetSize> getValueType() {
        return WidgetSize.class;
    }
}
