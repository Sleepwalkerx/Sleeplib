package com.sleepwalker.sleeplib.ui.graphics.drawable.shape;

import com.sleepwalker.sleeplib.ui.property.ColorProperty;
import com.sleepwalker.sleeplib.util.exception.XmlParseException;
import com.sleepwalker.sleeplib.util.serialization.XmlUtil;
import org.w3c.dom.Element;

import javax.annotation.Nonnull;

public class ShapeSolid {

    @Nonnull public static final ShapeSolid EMPTY = new ShapeSolid(0xFFFFFFFF);

    private final int color;

    public ShapeSolid(int color) {
        this.color = color;
    }

    public int getColor() {
        return color;
    }

    public boolean isDrawable(){
        return this != EMPTY;
    }

    @Nonnull
    public static ShapeSolid deserialize(@Nonnull Element parentElement) throws XmlParseException {

        Element solidElem = XmlUtil.getChildElement("solid", parentElement, null);
        if(solidElem == null){
            return EMPTY;
        }
        else {

            int color = 0xFFFFFFFF;

            if(solidElem.hasAttribute("color")){
                color = ColorProperty.parseColor(solidElem.getAttributeNode("color"));
            }

            return new ShapeSolid(color);
        }
    }
}
