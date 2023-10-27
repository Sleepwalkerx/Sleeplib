package com.sleepwalker.sleeplib.ui.xml;

import com.sleepwalker.sleeplib.SleepLib;
import com.sleepwalker.sleeplib.registries.SLRegistries;
import com.sleepwalker.sleeplib.ui.graphics.drawable.Drawable;
import com.sleepwalker.sleeplib.util.MinecraftUtil;
import com.sleepwalker.sleeplib.util.exception.XmlParseException;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.ResourceLocationException;
import net.minecraftforge.registries.ForgeRegistryEntry;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;

import javax.annotation.Nonnull;

public class ShapeDrawableSerializer extends ForgeRegistryEntry<DrawableSerializer> implements DrawableSerializer {

    @Nonnull
    @Override
    public Drawable deserialize(@Nonnull Element element, @Nonnull DeserializeContext context) {

        if(!element.hasAttribute("type")){
            throw new XmlParseException("Shape must be have type");
        }

        Attr typeAttr = element.getAttributeNode("type");
        ResourceLocation typeId;
        try {
            typeId = MinecraftUtil.parseResourceLocation(typeAttr.getValue(), SleepLib.MODID);
        }
        catch (ResourceLocationException e){
            throw new XmlParseException(e);
        }

        ShapeSerializer shapeSerializer = SLRegistries.SHAPE_SERIALIZERS.get().getValue(typeId);
        if(shapeSerializer == null){
            throw new XmlParseException("Unknown ShapeSerializer -> " + typeId);
        }

        return shapeSerializer.deserialize(element, context);
    }
}
