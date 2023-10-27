package com.sleepwalker.sleeplib.ui.property;

import com.sleepwalker.sleeplib.ui.widget.Widget;
import com.sleepwalker.sleeplib.ui.xml.DeserializeContext;
import com.sleepwalker.sleeplib.util.exception.XmlParseException;
import org.w3c.dom.Attr;

import javax.annotation.Nonnull;
import java.util.Locale;

public class EnumProperty<T extends Widget, V extends Enum<V>> extends BaseProperty<T, V> {

    @Nonnull private final Class<V> valueType;

    public EnumProperty(@Nonnull DefaultValueSupplier<T, V> defaultValue, @Nonnull Class<T> hostType, @Nonnull Class<V> valueType, @Nonnull Listener<T, V> listener) {
        super(defaultValue, hostType, listener);
        this.valueType = valueType;
    }

    @Nonnull
    @Override
    public V fromXml(@Nonnull Attr attr, @Nonnull T host, @Nonnull DeserializeContext context) throws XmlParseException {
        return Enum.valueOf(getValueType(), attr.getValue().toUpperCase(Locale.ROOT));
    }

    @Nonnull
    @Override
    public Class<V> getValueType() {
        return valueType;
    }
}
