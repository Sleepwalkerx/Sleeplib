package com.sleepwalker.sleeplib.ui.graphics.drawable.shape;

import com.sleepwalker.sleeplib.ui.GlfwUtil;
import com.sleepwalker.sleeplib.ui.graphics.Canvas;
import com.sleepwalker.sleeplib.util.exception.XmlParseException;
import com.sleepwalker.sleeplib.util.serialization.XmlUtil;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.registries.ForgeRegistryEntry;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;

import javax.annotation.Nonnull;

public class RectShape extends BaseShape {

    public RectShape(@Nonnull ShapePadding padding, @Nonnull ShapeSize size, @Nonnull ShapeSolid solid, @Nonnull ShapeStroke stroke) {
        super(padding, size, solid, stroke);
    }

    @Override
    public void draw(@Nonnull Canvas canvas) {

        float width = size.getWidth(canvas);
        float height = size.getHeight(canvas);
        float pX = computeX(canvas, width);
        float pY = computeY(canvas, height);

        if(solid.isDrawable()){
            GlfwUtil.drawRect(
                canvas.getMatrix(), pX + stroke.getWidth(), pY + stroke.getWidth(), canvas.getZ(),
                width - stroke.getWidth() * 2, height - stroke.getWidth() * 2, solid.getColor()
            );
        }

        if(stroke.isDrawable()){
            GlfwUtil.drawHollowRect(canvas.getMatrix(), pX, pY, canvas.getZ(), width, height, stroke.getWidth(), stroke.getColor());
        }
    }

    @Nonnull
    public ShapeSize getSize() {
        return size;
    }

    @Nonnull
    public ShapeSolid getSolid() {
        return solid;
    }

    @Nonnull
    public ShapeStroke getStroke() {
        return stroke;
    }

    @Override
    public int getWidth() {
        return INDEFINABLE;
    }

    @Override
    public int getHeight() {
        return INDEFINABLE;
    }

    public static class Serializer extends ForgeRegistryEntry<ShapeSerializer> implements ShapeSerializer {

        @Nonnull
        @Override
        public Shape deserialize(@Nonnull Element element) {

            ShapeSize size = ShapeSize.deserialize(element);
            ShapeSolid solid = ShapeSolid.deserialize(element);
            ShapeStroke stroke = ShapeStroke.deserialize(element);
            ShapePadding padding = ShapePadding.deserialize(element);

            Element corners = XmlUtil.getChildElement("corners", element, null);
            if(corners != null){

                CornerRadius leftTopRadius = CornerRadius.EMPTY;
                CornerRadius rightTopRadius = CornerRadius.EMPTY;
                CornerRadius rightBottomRadius = CornerRadius.EMPTY;
                CornerRadius leftBottomRadius = CornerRadius.EMPTY;

                if(corners.hasAttribute("radius")){

                    String attrValue = corners.getAttributeNode("radius").getValue();
                    String[] array = attrValue.split(" ", 4);
                    CornerRadius[] values = new CornerRadius[array.length];

                    for(int i = 0; i < array.length; i++){

                        String value = array[i];

                        if(value.endsWith("%")){
                            float percent;
                            try {
                                percent = Float.parseFloat(value.substring(0, value.length() - 1));
                            }
                            catch (NumberFormatException e){
                                throw new XmlParseException(e);
                            }
                            values[i] = new CornerRadius.Percent(MathHelper.clamp(percent / 100f, 0f, 1f));
                        }
                        else {
                            float absolute;
                            try {
                                absolute = Float.parseFloat(value);
                            }
                            catch (NumberFormatException e){
                                throw new XmlParseException(e);
                            }
                            values[i] = new CornerRadius.Absolute(absolute);
                        }
                    }

                    if(values.length == 1){
                        leftTopRadius = values[0];
                        rightBottomRadius = values[0];
                        rightTopRadius = values[0];
                        leftBottomRadius = values[0];
                    }
                    else if(values.length == 2){
                        leftTopRadius = values[0];
                        rightBottomRadius = values[0];
                        rightTopRadius = values[1];
                        leftBottomRadius = values[1];
                    }
                    else if(values.length == 4){
                        leftTopRadius = values[0];
                        rightBottomRadius = values[1];
                        rightTopRadius = values[2];
                        leftBottomRadius = values[3];
                    }
                }

                return new RoundedRectShape(padding, size, solid, stroke, leftTopRadius, rightTopRadius, rightBottomRadius, leftBottomRadius);
            }
            else {
                return new RectShape(padding, size, solid, stroke);
            }
        }

        private float tryParseRadius(@Nonnull Attr attr){
            try {
                return Float.parseFloat(attr.getValue());
            }
            catch (NumberFormatException e){
                throw new XmlParseException(e);
            }
        }
    }
}
