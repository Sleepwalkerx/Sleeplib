package com.sleepwalker.sleeplib.ui.property;

import com.sleepwalker.sleeplib.SleepLib;
import com.sleepwalker.sleeplib.registries.SLRegistries;
import com.sleepwalker.sleeplib.ui.graphics.drawable.Drawable;
import com.sleepwalker.sleeplib.ui.widget.Widget;
import com.sleepwalker.sleeplib.ui.xml.DeserializeContext;
import com.sleepwalker.sleeplib.ui.xml.DrawableSerializer;
import com.sleepwalker.sleeplib.util.MinecraftUtil;
import com.sleepwalker.sleeplib.util.exception.XmlParseException;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.ResourceLocationException;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.annotation.Nonnull;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class DrawableProperty<T extends Widget> extends BaseProperty<T, Drawable> {

    public DrawableProperty(@Nonnull DefaultValueSupplier<T, Drawable> defaultValue, @Nonnull Class<T> hostType, @Nonnull Listener<T, Drawable> listener) {
        super(defaultValue, hostType, listener);
    }

    @Nonnull
    @Override
    public Drawable fromXml(@Nonnull Attr attr, @Nonnull T host, @Nonnull DeserializeContext context) throws XmlParseException {
        return deserialize(attr.getValue(), context);
    }

    @Nonnull
    @Override
    public Class<Drawable> getValueType() {
        return Drawable.class;
    }

    @Nonnull
    public static Drawable deserialize(@Nonnull String value, @Nonnull DeserializeContext context) throws XmlParseException {

        ResourceLocation location;
        try {
            location = new ResourceLocation(value + ".xml");
        }
        catch (ResourceLocationException e){
            throw new XmlParseException(e);
        }

        Drawable cache = context.findDrawableCache(location);
        if(cache != null){
            return cache;
        }

        IResourceManager manager = Minecraft.getInstance().getResourceManager();
        if(!manager.hasResource(location)){
            throw new XmlParseException("Can't find drawable -> " + location);
        }

        try(IResource resource = manager.getResource(location)) {

            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            builderFactory.setNamespaceAware(true);
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document document = builder.parse(resource.getInputStream());

            Element root = document.getDocumentElement();
            ResourceLocation typeId = MinecraftUtil.parseResourceLocation(root.getNodeName(), SleepLib.MODID);
            DrawableSerializer serializer = SLRegistries.DRAWABLE_SERIALIZERS.get().getValue(typeId);

            if(serializer == null){
                throw new XmlParseException("Can't find drawableSerializer -> " + typeId);
            }

            Drawable drawable = serializer.deserialize(root, context);
            context.addDrawableToCache(location, drawable);
            return drawable;
        }
        catch (IOException | ParserConfigurationException | SAXException | ResourceLocationException e){
            throw new XmlParseException(e);
        }
    }
}
