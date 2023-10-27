package com.sleepwalker.sleeplib.ui.property;

import com.sleepwalker.sleeplib.ui.widget.Widget;
import com.sleepwalker.sleeplib.ui.xml.DeserializeContext;
import com.sleepwalker.sleeplib.util.C;
import com.sleepwalker.sleeplib.util.exception.XmlParseException;
import net.minecraft.util.text.ITextProperties;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.registries.ForgeRegistryEntry;
import org.w3c.dom.Attr;

import javax.annotation.Nonnull;

public class TextProperty extends ForgeRegistryEntry<Property<?, ?>> implements Property<Widget, ITextProperties> {

    @Override
    public void onSet(@Nonnull Widget object, @Nonnull ITextProperties value) { }

    @Nonnull
    @Override
    public ITextProperties getDefaultValue(@Nonnull Widget object) {
        return C.EMPTY;
    }

    @Nonnull
    @Override
    public ITextProperties fromXml(@Nonnull Attr attr, @Nonnull Widget host, @Nonnull DeserializeContext context) throws XmlParseException {
        return new StringTextComponent(attr.getValue());
    }

    @Nonnull
    @Override
    public Class<Widget> getHostType() {
        return Widget.class;
    }

    @Nonnull
    @Override
    public Class<ITextProperties> getValueType() {
        return ITextProperties.class;
    }
}
