package com.sleepwalker.sleeplib.ui.widget;

import com.sleepwalker.sleeplib.ui.GlfwUtil;
import com.sleepwalker.sleeplib.ui.graphics.Canvas;
import com.sleepwalker.sleeplib.ui.property.Properties;
import com.sleepwalker.sleeplib.ui.xml.BaseWidgetSerializer;
import com.sleepwalker.sleeplib.ui.xml.WidgetSerializer;
import com.sleepwalker.sleeplib.ui.xml.WidgetSerializers;
import com.sleepwalker.sleeplib.util.C;
import com.sleepwalker.sleeplib.util.exception.XmlParseException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.text.ITextProperties;
import net.minecraft.util.text.StringTextComponent;
import org.w3c.dom.Element;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

public class Text extends Pinchable {

    @Nonnull private ITextProperties value = C.EMPTY;
    @Nonnull private List<IReorderingProcessor> lines = Collections.emptyList();

    @Override
    public void draw(@Nonnull Canvas canvas) {

        if(lines.isEmpty()){
            return;
        }

        GlfwUtil.enableScissorTest(getX(), getY(), getWidth(), getHeight());

        canvas.scale(getWidth(), getHeight());
        canvas.translate(getX(), getY(), getLayer());
        getPropertyValue(Properties.BACKGROUND).draw(canvas);

        FontRenderer fr = Minecraft.getInstance().font;

        float biasX = getPropertyValue(Properties.TEXT_BIAS_X);
        float biasY = getPropertyValue(Properties.TEXT_BIAS_Y);
        float lineHeight = getPropertyValue(Properties.LINE_HEIGHT);

        float pX = getWidth() * biasX + getX() + getPropertyValue(Properties.TEXT_OFFSET_X);
        int linesCount = getLinesCount(lineHeight);
        float pY = (getY() + getHeight() * biasY) - (linesCount * lineHeight * biasY) + (lineHeight * biasY - (fr.lineHeight) * biasY);
        pY += getPropertyValue(Properties.TEXT_OFFSET_Y);
        int color = getPropertyValue(Properties.TEXT_COLOR);

        if (getPropertyValue(Properties.TEXT_SHADOW)) {
            for(int i = 0; i < linesCount; i++){
                IReorderingProcessor line = lines.get(i);
                fr.drawShadow(canvas.getMatrix(), line, pX - (fr.width(line) * biasX), pY, color);
                pY += lineHeight;
            }
        }
        else {
            for(int i = 0; i < linesCount; i++){
                IReorderingProcessor line = lines.get(i);
                fr.draw(canvas.getMatrix(), line, pX - (fr.width(line) * biasX), pY, color);
                pY += lineHeight;
            }
        }

        GlfwUtil.disableScissorTest();
    }

    private int getLinesCount(float lineHeight){
        return getPropertyValue(Properties.HIDE_OVERFLOW) ? Math.min(lines.size(), getIntHeight() / (int)(lineHeight)) : lines.size();
    }

    public void setValue(@Nonnull ITextProperties value) {
        this.value = value;
        updateLines();
    }

    public void updateLines(){
        FontRenderer fr = Minecraft.getInstance().font;
        lines = fr.split(value, getIntWidth());
        int maxLines = getPropertyValue(Properties.MAX_LINES);
        if(maxLines > 0 && lines.size() > maxLines){
            lines = lines.subList(0, maxLines - 1);
        }
    }

    @Override
    public void updateSize() {
        super.updateSize();
        updateLines();
    }

    @Nonnull
    @Override
    public WidgetSerializer<?> getSerializer() {
        return WidgetSerializers.TEXT.get();
    }

    public static class Serializer extends BaseWidgetSerializer<Text> {

        @Override
        public void deserialize(@Nonnull Text instance, @Nonnull Element element) throws XmlParseException {
            super.deserialize(instance, element);

            String value = element.getTextContent();
            if(value != null && !value.isEmpty()){
                instance.setValue(new StringTextComponent(value));
            }
        }

        @Nonnull
        @Override
        public Class<Text> getWidgetType() {
            return Text.class;
        }
    }
}
