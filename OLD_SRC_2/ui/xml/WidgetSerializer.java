package com.sleepwalker.sleeplib.ui.xml;

import com.sleepwalker.sleeplib.ui.widget.Widget;
import com.sleepwalker.sleeplib.util.exception.XmlParseException;
import net.minecraftforge.registries.IForgeRegistryEntry;
import org.w3c.dom.Element;

import javax.annotation.Nonnull;

public interface WidgetSerializer<T extends Widget> extends IForgeRegistryEntry<WidgetSerializer<?>> {

    void deserialize(@Nonnull T instance, @Nonnull Element element, @Nonnull DeserializeContext context) throws XmlParseException;

    @Nonnull Class<T> getWidgetType();
}
