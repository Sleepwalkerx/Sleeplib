package com.sleepwalker.sleeplib.ui.graphics.drawable;

import com.sleepwalker.sleeplib.util.exception.XmlParseException;
import net.minecraftforge.registries.IForgeRegistryEntry;
import org.w3c.dom.Element;

import javax.annotation.Nonnull;

public interface DrawableSerializer extends IForgeRegistryEntry<DrawableSerializer> {

    @Nonnull Drawable deserializeFromXml(@Nonnull Element element) throws XmlParseException;
}
