package com.sleepwalker.sleeplib.ui.xml;

import com.sleepwalker.sleeplib.ui.Root;
import com.sleepwalker.sleeplib.ui.widget.WidgetGroup;
import com.sleepwalker.sleeplib.util.exception.XmlParseException;
import net.minecraftforge.registries.IForgeRegistryEntry;
import org.w3c.dom.Element;

import javax.annotation.Nonnull;

public interface LayoutSerializer extends IForgeRegistryEntry<LayoutSerializer> {

    @Nonnull WidgetGroup deserialize(@Nonnull Element rootElement, @Nonnull Root root, @Nonnull DeserializeContext context) throws XmlParseException;
}
