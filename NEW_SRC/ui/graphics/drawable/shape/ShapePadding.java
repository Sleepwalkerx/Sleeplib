package com.sleepwalker.sleeplib.ui.graphics.drawable.shape;

import com.sleepwalker.sleeplib.util.exception.XmlParseException;
import com.sleepwalker.sleeplib.util.serialization.XmlUtil;
import net.minecraft.util.math.MathHelper;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;

import javax.annotation.Nonnull;

public class ShapePadding {

    @Nonnull public static final ShapePadding EMPTY = new ShapePadding(0, 0, 0, 0);

    protected final float x;
    protected final float y;
    protected final float biasX;
    protected final float biasY;

    public ShapePadding(float x, float y, float biasX, float biasY) {
        this.x = x;
        this.y = y;
        this.biasX = biasX;
        this.biasY = biasY;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getBiasX() {
        return biasX;
    }

    public float getBiasY() {
        return biasY;
    }

    @Nonnull
    public static ShapePadding deserialize(@Nonnull Element parentElement){

        Element element = XmlUtil.getChildElement("padding", parentElement, null);

        if(element == null){
            return ShapePadding.EMPTY;
        }
        else {

            float x = 0;
            float y = 0;
            float biasX = 0;
            float biasY = 0;

            if(element.hasAttribute("x")){
                x = parseIndentValue(element.getAttributeNode("x"));
            }

            if(element.hasAttribute("y")){
                y = parseIndentValue(element.getAttributeNode("y"));
            }

            if(element.hasAttribute("bias-x")){
                biasX = parseBiasValue(element.getAttributeNode("bias-x"));
            }

            if(element.hasAttribute("bias-y")){
                biasY = parseBiasValue(element.getAttributeNode("bias-y"));
            }

            return new ShapePadding(x, y, biasX, biasY);
        }
    }

    private static float parseIndentValue(@Nonnull Attr attr){
        try {
            return Float.parseFloat(attr.getValue());
        }
        catch (NumberFormatException e){
            throw new XmlParseException(e);
        }
    }

    private static float parseBiasValue(@Nonnull Attr attr){

        String attrValue = attr.getValue();

        float percent;
        if(attrValue.endsWith("%")){
            try {
                percent = Float.parseFloat(attrValue.substring(0, attrValue.length() - 1)) / 100f;
            }
            catch (NumberFormatException e){
                throw new XmlParseException(e);
            }
        }
        else {
            try {
                percent = Float.parseFloat(attrValue);
            }
            catch (NumberFormatException e){
                throw new XmlParseException(e);
            }
        }

        return MathHelper.clamp(percent, 0f, 1f);
    }
}
