package com.sleepwalker.sleeplib.ui.xml;

import com.sleepwalker.sleeplib.ui.property.PropertySource;
import com.sleepwalker.sleeplib.util.exception.XmlParseException;
import net.minecraftforge.registries.IForgeRegistryEntry;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;

import javax.annotation.Nonnull;

public interface PropertySourceSerializer extends IForgeRegistryEntry<PropertySourceSerializer> {

    @Nonnull PropertySource deserialize(@Nonnull Attr attr, @Nonnull Document document, @Nonnull DeserializeContext context) throws XmlParseException;
}
