package com.sleepwalker.sleeplib.ui.property;

import com.sleepwalker.sleeplib.ui.widget.Widget;
import com.sleepwalker.sleeplib.ui.xml.DeserializeContext;
import com.sleepwalker.sleeplib.util.exception.XmlParseException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraftforge.registries.ForgeRegistryEntry;
import org.w3c.dom.Attr;

import javax.annotation.Nonnull;

public class FontProperty extends ForgeRegistryEntry<Property<?, ?>> implements Property<Widget, FontRenderer> {

    @Override
    public void onSet(@Nonnull Widget object, @Nonnull FontRenderer value) { }

    @Nonnull
    @Override
    public FontRenderer getDefaultValue(@Nonnull Widget object) {
        return Minecraft.getInstance().font;
    }

    @Nonnull
    @Override
    public FontRenderer fromXml(@Nonnull Attr attr, @Nonnull Widget host, @Nonnull DeserializeContext context) throws XmlParseException {
        //TODO: develop
        return Minecraft.getInstance().font;
    }

    @Nonnull
    @Override
    public Class<Widget> getHostType() {
        return Widget.class;
    }

    @Nonnull
    @Override
    public Class<FontRenderer> getValueType() {
        return FontRenderer.class;
    }
}
