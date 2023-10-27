package com.sleepwalker.sleeplib.ui.graphics.drawable.shape;

import com.sleepwalker.sleeplib.ui.graphics.Canvas;
import com.sleepwalker.sleeplib.ui.layout.Dimension;
import com.sleepwalker.sleeplib.util.exception.XmlParseException;
import com.sleepwalker.sleeplib.util.serialization.XmlUtil;
import net.minecraft.util.math.MathHelper;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;

import javax.annotation.Nonnull;

public class ShapeSize {

    @Nonnull public static final ShapeSize FIT_CANVAS = new ShapeSize(new CanvasPercent(1f), new CanvasPercent(1f));

    @Nonnull protected final Size width;
    @Nonnull protected final Size height;

    public ShapeSize(@Nonnull Size width, @Nonnull Size height) {
        this.width = width;
        this.height = height;
    }

    public float getWidth(@Nonnull Canvas canvas) {
        return width.calculate(canvas, Dimension.WIDTH);
    }

    public float getHeight(@Nonnull Canvas canvas) {
        return height.calculate(canvas, Dimension.HEIGHT);
    }

    @Nonnull
    public static ShapeSize deserialize(@Nonnull Element parentElement){

        Element sizeElem = XmlUtil.getChildElement("size", parentElement, null);
        if(sizeElem == null){
            return FIT_CANVAS;
        }
        else {

            Size width = null;
            Size height = null;

            if(sizeElem.hasAttribute("width")){
                width = parseSize(sizeElem.getAttributeNode("width"));
            }

            if(sizeElem.hasAttribute("height")){
                height = parseSize(sizeElem.getAttributeNode("height"));
            }

            if(width == null && height == null){
                return FIT_CANVAS;
            }
            else {

                if(width == null){
                    width = new CanvasPercent(1);
                }
                else if(height == null) {
                    height = new CanvasPercent(1);
                }

                return new ShapeSize(width, height);
            }
        }
    }

    @Nonnull
    private static Size parseSize(@Nonnull Attr attr){

        String attrValue = attr.getValue();

        if(attrValue.endsWith("%")){

            float percent;
            try {
                percent = Float.parseFloat(attrValue.substring(0, attrValue.length() - 1));
            }
            catch (NumberFormatException e){
                throw new XmlParseException(e);
            }

            return new CanvasPercent(MathHelper.clamp(percent / 100f, 0f, 1f));
        }
        else {

            float value;
            try {
                value = Float.parseFloat(attrValue);
            }
            catch (NumberFormatException e){
                throw new XmlParseException(e);
            }

            return new Absolute(value);
        }
    }

    public interface Size {

        float calculate(@Nonnull Canvas canvas, @Nonnull Dimension dimension);
    }

    public static class Absolute implements Size {

        private final float value;

        public Absolute(float value) {
            this.value = value;
        }

        @Override
        public float calculate(@Nonnull Canvas canvas, @Nonnull Dimension dimension) {
            return value;
        }
    }

    public static class CanvasPercent implements Size {

        private final float percent;

        public CanvasPercent(float percent) {
            this.percent = percent;
        }

        @Override
        public float calculate(@Nonnull Canvas canvas, @Nonnull Dimension dimension) {
            return dimension == Dimension.HEIGHT ? canvas.getHeight() * percent : canvas.getWidth() * percent;
        }
    }
}
