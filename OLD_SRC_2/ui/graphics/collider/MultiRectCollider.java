package com.sleepwalker.sleeplib.ui.graphics.collider;

import com.sleepwalker.sleeplib.ui.graphics.drawable.Drawable;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MultiRectCollider {

    @Nonnull
    private final List<Rect> rectList;

    public MultiRectCollider(@Nonnull List<Rect> rectList) {
        this.rectList = rectList;
    }

    public boolean isMouseOver(int mX, int mY, int pX, int pY, int width, int height) {

        for (Rect point : rectList){

            float pointX = pX + width * point.u0;
            float pointY = pY + height * point.v0;

            if(mX >= pointX && (pointX + width * point.w) > mX && mY >= pointY && (pointY + height * point.h) > mY){
                return true;
            }
        }

        return false;
    }

    public static class Rect {

        public final float u0, v0, w, h;

        public Rect(float u0, float v0, float w, float h) {
            this.u0 = u0;
            this.v0 = v0;
            this.w = w;
            this.h = h;
        }
    }

    public static class PointListBuilder {

        @Nonnull
        private final Drawable drawable;
        @Nonnull
        private final List<Rect> points;

        private PointListBuilder(@Nonnull Drawable drawable) {
            this.drawable = drawable;
            points = new ArrayList<>();
        }

        @Nonnull
        public static PointListBuilder builder(@Nonnull Drawable drawable){
            return new PointListBuilder(drawable);
        }

        @Nonnull
        public PointListBuilder add(int x, int y, int w, int h){
            /*points.add(new Rect(
                x / (float) drawable.getWidth(),
                y / (float) drawable.getHeight(),
                w / (float) drawable.getWidth(),
                h / (float) drawable.getHeight()
            ));*/
            return this;
        }

        @Nonnull
        public List<Rect> build(){
            return Collections.unmodifiableList(points);
        }
    }
}
