package com.sleepwalker.sleeplib.ui.property;

import com.sleepwalker.sleeplib.ui.widget.Widget;
import com.sleepwalker.sleeplib.ui.xml.DeserializeContext;
import com.sleepwalker.sleeplib.util.exception.XmlParseException;
import org.w3c.dom.Attr;

import javax.annotation.Nonnull;

public class IntProperty<T extends Widget> extends BaseProperty<T, Integer> {

    public IntProperty(@Nonnull DefaultValueSupplier<T, Integer> defaultValue, @Nonnull Class<T> hostType, @Nonnull Listener<T, Integer> listener) {
        super(defaultValue, hostType, listener);
    }

    @Nonnull
    @Override
    public Integer fromXml(@Nonnull Attr attr, @Nonnull T host, @Nonnull DeserializeContext context) throws XmlParseException {
        int value;
        try {
            value = Integer.parseInt(attr.getValue());
        }
        catch (NumberFormatException e){
            throw new XmlParseException(e);
        }
        return value;
    }

    @Nonnull
    @Override
    public Class<Integer> getValueType() {
        return Integer.class;
    }
}
