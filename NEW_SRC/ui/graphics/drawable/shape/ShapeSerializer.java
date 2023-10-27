package com.sleepwalker.sleeplib.ui.graphics.drawable.shape;

import com.sleepwalker.sleeplib.util.exception.XmlParseException;
import net.minecraftforge.registries.IForgeRegistryEntry;
import org.w3c.dom.Element;

import javax.annotation.Nonnull;

public interface ShapeSerializer extends IForgeRegistryEntry<ShapeSerializer> {

    @Nonnull Shape deserialize(@Nonnull Element element) throws XmlParseException;
}
