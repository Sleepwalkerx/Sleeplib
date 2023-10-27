package com.sleepwalker.sleeplib.ui.graphics.drawable.shape;

import com.sleepwalker.sleeplib.ui.GlfwUtil;
import com.sleepwalker.sleeplib.ui.graphics.Canvas;
import com.sleepwalker.sleeplib.util.exception.XmlParseException;
import net.minecraftforge.registries.ForgeRegistryEntry;
import org.w3c.dom.Element;

import javax.annotation.Nonnull;

public class OvalShape extends BaseShape {

    public OvalShape(@Nonnull ShapePadding padding, @Nonnull ShapeSize size, @Nonnull ShapeSolid solid, @Nonnull ShapeStroke stroke) {
        super(padding, size, solid, stroke);
    }

    @Override
    public void draw(@Nonnull Canvas canvas) {

        float width = size.getWidth(canvas);
        float height = size.getHeight(canvas);
        float pX = computeX(canvas, width);
        float pY = computeY(canvas, height);

        if(solid.isDrawable()){
            GlfwUtil.drawEllipse(canvas.getMatrix(),
                pX + stroke.getWidth(), pY + stroke.getWidth(), canvas.getZ(),
                width - stroke.getWidth() * 2, height - stroke.getWidth() * 2,
                solid.getColor()
            );
        }

        if(stroke.isDrawable()){
            GlfwUtil.drawHollowEllipse(canvas.getMatrix(), pX, pY, canvas.getZ(), width, height, stroke.getWidth(), stroke.getColor());
        }
    }

    @Override
    public int getWidth() {
        return (int)(size.getWidthValue());
    }

    @Override
    public int getHeight() {
        return (int)(size.getHeightValue());
    }

    public static class Serializer extends ForgeRegistryEntry<ShapeSerializer> implements ShapeSerializer {

        @Nonnull
        @Override
        public Shape deserialize(@Nonnull Element element) throws XmlParseException {
            return new OvalShape(
                ShapePadding.deserialize(element),
                ShapeSize.deserialize(element),
                ShapeSolid.deserialize(element),
                ShapeStroke.deserialize(element)
            );
        }
    }
}
