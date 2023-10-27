package com.sleepwalker.sleeplib.ui;

import com.sleepwalker.sleeplib.SleepLib;
import com.sleepwalker.sleeplib.ui.widget.WidgetGroup;
import com.sleepwalker.sleeplib.ui.widget.Window;
import com.sleepwalker.sleeplib.ui.xml.LayoutSerializer;
import com.sleepwalker.sleeplib.ui.xml.LayoutSerializers;
import com.sleepwalker.sleeplib.ui.xml.RuntimeDeserializerContext;
import com.sleepwalker.sleeplib.util.exception.XmlParseException;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.annotation.Nonnull;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public final class UIManager {

    public static void tryInitLayoutFromXml(@Nonnull ResourceLocation location, @Nonnull Window window){
        try {
            initWindowFromXml(new ResourceLocation(location.getNamespace(), location.getPath() + ".xml"), window);
        }
        catch (Exception e){
            SleepLib.LOGGER.error("Can't init layout " + location, e);
        }
    }

    public static void initWindowFromXml(@Nonnull ResourceLocation location, @Nonnull Window window)
        throws ParserConfigurationException, IOException, SAXException, XmlParseException {

        IResourceManager manager = Minecraft.getInstance().getResourceManager();

        if(!manager.hasResource(location)){
            throw new IOException("Can't find Screen - " + location);
        }

        try(IResource resource = manager.getResource(location)) {

            WidgetGroup layout = loadLayoutFromXml(resource, LayoutSerializers.SIMPLE.get(), window);
            layout.setRoot(window);
            layout.setParent(window);

            window.setContent(layout);
            window.updateLayout();
        }
    }

    @Nonnull
    public static WidgetGroup loadLayoutFromXml(@Nonnull IResource resource, @Nonnull LayoutSerializer serializer, @Nonnull Root root)
        throws ParserConfigurationException, IOException, SAXException, XmlParseException {

        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        builderFactory.setNamespaceAware(true);
        DocumentBuilder builder = builderFactory.newDocumentBuilder();
        Document document = builder.parse(resource.getInputStream());

        RuntimeDeserializerContext context = new RuntimeDeserializerContext();
        return serializer.deserialize(document.getDocumentElement(), root, context);
    }
}
