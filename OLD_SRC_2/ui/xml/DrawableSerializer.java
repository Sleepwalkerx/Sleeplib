package com.sleepwalker.sleeplib.ui.xml;

import com.sleepwalker.sleeplib.ui.graphics.drawable.Drawable;
import com.sleepwalker.sleeplib.util.exception.XmlParseException;
import net.minecraftforge.registries.IForgeRegistryEntry;
import org.w3c.dom.Element;

import javax.annotation.Nonnull;

public interface DrawableSerializer extends IForgeRegistryEntry<DrawableSerializer> {

    @Nonnull Drawable deserialize(@Nonnull Element element, @Nonnull DeserializeContext context) throws XmlParseException;
}
