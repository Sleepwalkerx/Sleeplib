package com.sleepwalker.sleeplib.ui.property;

import com.sleepwalker.sleeplib.ui.widget.Widget;
import com.sleepwalker.sleeplib.ui.xml.DeserializeContext;
import com.sleepwalker.sleeplib.util.exception.XmlParseException;
import org.w3c.dom.Attr;

import javax.annotation.Nonnull;

public class FloatProperty<T extends Widget> extends BaseProperty<T, Float> {

    public FloatProperty(@Nonnull DefaultValueSupplier<T, Float> defaultValueSupplier, @Nonnull Class<T> hostType, @Nonnull Listener<T, Float> listener) {
        super(defaultValueSupplier, hostType, listener);
    }

    @Nonnull
    @Override
    public Float fromXml(@Nonnull Attr attr, @Nonnull T host, @Nonnull DeserializeContext context) throws XmlParseException {
        float value;
        try {
            value = Float.parseFloat(attr.getValue());
        }
        catch (NumberFormatException e){
            throw new XmlParseException(e);
        }
        return value;
    }

    @Nonnull
    @Override
    public Class<Float> getValueType() {
        return Float.class;
    }
}
