package com.sleepwalker.sleeplib.ui.property;

import com.sleepwalker.sleeplib.ui.widget.Widget;
import com.sleepwalker.sleeplib.ui.xml.DeserializeContext;
import com.sleepwalker.sleeplib.util.exception.XmlParseException;
import org.w3c.dom.Attr;

import javax.annotation.Nonnull;

public class BoolProperty<T extends Widget> extends BaseProperty<T, Boolean> {

    @SuppressWarnings("rawtypes")
    @Nonnull private static final DefaultValueSupplier ALWAYS_TRUE = o -> true;
    @SuppressWarnings("rawtypes")
    @Nonnull private static final DefaultValueSupplier ALWAYS_FALSE = o -> false;

    public BoolProperty(@Nonnull DefaultValueSupplier<T, Boolean> defaultValue, @Nonnull Class<T> hostType, @Nonnull Listener<T, Boolean> listener) {
        super(defaultValue, hostType, listener);
    }

    @Nonnull
    @Override
    public Boolean fromXml(@Nonnull Attr attr, @Nonnull T host, @Nonnull DeserializeContext context) throws XmlParseException {
        return Boolean.parseBoolean(attr.getValue());
    }

    @Nonnull
    @Override
    public Class<Boolean> getValueType() {
        return Boolean.class;
    }

    @SuppressWarnings("unchecked")
    public static <T, V> DefaultValueSupplier<T, V> alwaysTrue(){
        return (DefaultValueSupplier<T, V>) ALWAYS_TRUE;
    }

    @SuppressWarnings("unchecked")
    public static <T, V> DefaultValueSupplier<T, V> alwaysFalse(){
        return (DefaultValueSupplier<T, V>) ALWAYS_FALSE;
    }
}
