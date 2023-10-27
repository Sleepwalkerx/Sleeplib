package com.sleepwalker.sleeplib.ui.graphics.drawable.shape;

import com.sleepwalker.sleeplib.ui.property.ColorProperty;
import com.sleepwalker.sleeplib.util.exception.XmlParseException;
import com.sleepwalker.sleeplib.util.serialization.XmlUtil;
import org.w3c.dom.Element;

import javax.annotation.Nonnull;

public class ShapeStroke {

    @Nonnull public static final ShapeStroke EMPTY = new ShapeStroke(0, 0xFFFFFFFF);

    private final float width;
    private final int color;

    public ShapeStroke(float width, int color) {
        this.width = width;
        this.color = color;
    }

    public float getWidth() {
        return width;
    }

    public int getColor() {
        return color;
    }

    public boolean isDrawable(){
        return this != EMPTY;
    }

    @Nonnull
    public static ShapeStroke deserialize(@Nonnull Element parentElement) throws XmlParseException {

        Element stokeElem = XmlUtil.getChildElement("stroke", parentElement, null);
        if(stokeElem == null){
            return EMPTY;
        }
        else {

            float width = 1;
            int color = 0xFFFFFFFF;

            if(stokeElem.hasAttribute("width")){
                try {
                    width = Float.parseFloat(stokeElem.getAttributeNode("width").getValue());
                }
                catch (NumberFormatException e){
                    throw new XmlParseException(e);
                }
            }

            if(stokeElem.hasAttribute("color")){
                color = ColorProperty.parseColor(stokeElem.getAttributeNode("color"));
            }

            return new ShapeStroke(width, color);
        }
    }
}
