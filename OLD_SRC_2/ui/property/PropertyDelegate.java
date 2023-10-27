package com.sleepwalker.sleeplib.ui.property;

import com.sleepwalker.sleeplib.ui.widget.Widget;
import com.sleepwalker.sleeplib.ui.xml.DeserializeContext;
import com.sleepwalker.sleeplib.util.exception.XmlParseException;
import net.minecraftforge.registries.ForgeRegistryEntry;
import org.w3c.dom.Attr;

import javax.annotation.Nonnull;

public class PropertyDelegate<T extends Widget, V> extends ForgeRegistryEntry<Property<?, ?>> implements Property<T, V> {

    @Nonnull private final Property<T, V> property;

    public PropertyDelegate(@Nonnull Property<T, V> property) {
        this.property = property;
    }

    @Override
    public void onSet(@Nonnull T object, @Nonnull V value) {
        property.onSet(object, value);
    }

    @Nonnull
    @Override
    public V getDefaultValue(@Nonnull T object) {
        return property.getDefaultValue(object);
    }

    @Nonnull
    @Override
    public V fromXml(@Nonnull Attr attr, @Nonnull T host, @Nonnull DeserializeContext context) throws XmlParseException {
        return property.fromXml(attr, host, context);
    }

    @Nonnull
    @Override
    public Class<T> getHostType() {
        return property.getHostType();
    }

    @Nonnull
    @Override
    public Class<V> getValueType() {
        return property.getValueType();
    }
}
