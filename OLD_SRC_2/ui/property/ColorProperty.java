package com.sleepwalker.sleeplib.ui.property;

import com.sleepwalker.sleeplib.ui.widget.Widget;
import com.sleepwalker.sleeplib.ui.xml.DeserializeContext;
import com.sleepwalker.sleeplib.util.exception.XmlParseException;
import org.w3c.dom.Attr;

import javax.annotation.Nonnull;

public class ColorProperty<T extends Widget> extends BaseProperty<T, Integer> {

    public ColorProperty(@Nonnull DefaultValueSupplier<T, Integer> defaultValueSupplier, @Nonnull Class<T> hostType, @Nonnull Listener<T, Integer> listener) {
        super(defaultValueSupplier, hostType, listener);
    }

    @Nonnull
    @Override
    public Integer fromXml(@Nonnull Attr attr, @Nonnull T host, @Nonnull DeserializeContext context) throws XmlParseException {
        return parseColor(attr);
    }

    public static int parseColor(@Nonnull Attr attr) throws XmlParseException {

        String value = attr.getValue();

        if(value.startsWith("#")){
            try {
                return Integer.parseInt(value.substring(1), 16);
            }
            catch (NumberFormatException e){
                throw new XmlParseException(e);
            }
        }

        return 0;
    }

    @Nonnull
    @Override
    public Class<Integer> getValueType() {
        return Integer.class;
    }
}
