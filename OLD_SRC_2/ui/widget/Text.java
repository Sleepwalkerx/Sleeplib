package com.sleepwalker.sleeplib.ui.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import com.sleepwalker.sleeplib.ui.graphics.Canvas;
import com.sleepwalker.sleeplib.ui.property.Properties;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.IReorderingProcessor;

import javax.annotation.Nonnull;
import java.util.List;

public class Text extends BaseClickable {

    @SuppressWarnings("deprecation")
    @Override
    public void draw(@Nonnull Canvas canvas) {

        setupCanvas(canvas);
        getPropertyValue(Properties.BACKGROUND).draw(canvas);

        FontRenderer font = getPropertyValue(Properties.FONT);
        List<IReorderingProcessor> lines = getLinesFromValue(font, canvas);
        if(lines.isEmpty()){
            return;
        }

        float biasX = getPropertyValue(Properties.TEXT_BIAS_X);
        float biasY = getPropertyValue(Properties.TEXT_BIAS_Y);
        boolean shadow = getPropertyValue(Properties.TEXT_SHADOW);

        float pX = canvas.getWidth() * biasX + canvas.getX() + getPropertyValue(Properties.TEXT_OFFSET_X);
        float lineHeight = getPropertyValue(Properties.LINE_HEIGHT);
        int linesCount = getLinesCount(lines, lineHeight, canvas);
        float pY = (canvas.getY() + canvas.getHeight() * biasY) - (linesCount * lineHeight * biasY) + (lineHeight * biasY - (font.lineHeight) * biasY);
        pY += getPropertyValue(Properties.TEXT_OFFSET_Y);

        int color = getPropertyValue(Properties.TEXT_COLOR);
        RenderSystem.color4f(1f, 1f, 1f, getPropertyValue(Properties.TEXT_ALPHA));

        if (shadow) {
            for(int i = 0; i < linesCount; i++){
                IReorderingProcessor line = lines.get(i);
                font.drawShadow(canvas.getMatrix(), line, pX - (font.width(line) * biasX), pY, color);
                pY += lineHeight;
            }
        }
        else {
            for(int i = 0; i < linesCount; i++){
                IReorderingProcessor line = lines.get(i);
                font.draw(canvas.getMatrix(), line, pX - (font.width(line) * biasX), pY, color);
                pY += lineHeight;
            }
        }
    }

    private int getLinesCount(@Nonnull List<IReorderingProcessor> lines, float lineHeight, @Nonnull Canvas canvas){
        return getPropertyValue(Properties.HIDE_OVERFLOW) ? Math.min(lines.size(), (int)(canvas.getHeight() / lineHeight)) : lines.size();
    }

    @Nonnull
    public List<IReorderingProcessor> getLinesFromValue(@Nonnull FontRenderer font, @Nonnull Canvas canvas){
        int maxLines = getPropertyValue(Properties.MAX_LINES);
        List<IReorderingProcessor> lines = font.split(getPropertyValue(Properties.TEXT), canvas.getIntWidth());
        if(lines.size() > maxLines){
            lines = lines.subList(0, maxLines - 1);
        }
        return lines;
    }
}
