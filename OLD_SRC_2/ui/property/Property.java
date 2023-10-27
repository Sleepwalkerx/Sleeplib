package com.sleepwalker.sleeplib.ui.property;

import com.sleepwalker.sleeplib.ui.widget.Widget;
import com.sleepwalker.sleeplib.ui.xml.DeserializeContext;
import com.sleepwalker.sleeplib.util.exception.XmlParseException;
import net.minecraftforge.registries.IForgeRegistryEntry;
import org.w3c.dom.Attr;

import javax.annotation.Nonnull;

public interface Property<T extends Widget, V> extends IForgeRegistryEntry<Property<?, ?>> {

    void onSet(@Nonnull T object, @Nonnull V value);

    @Nonnull V getDefaultValue(@Nonnull T object);

    @Nonnull V fromXml(@Nonnull Attr attr, @Nonnull T host, @Nonnull DeserializeContext context) throws XmlParseException;

    @Nonnull Class<T> getHostType();
    @Nonnull Class<V> getValueType();
}
