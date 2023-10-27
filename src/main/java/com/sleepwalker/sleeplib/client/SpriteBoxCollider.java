package com.sleepwalker.sleeplib.client;

import com.sleepwalker.sleeplib.client.drawable.Drawable;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SpriteBoxCollider {

    @Nonnull private final List<Point> points;

    public SpriteBoxCollider(@Nonnull List<Point> points) {
        this.points = points;
    }

    public boolean isMouseOver(double mX, double mY, double pX, double pY, double width, double height) {

        for (Point point : points){

            double pointX = pX + width * point.u0;
            double pointY = pY + height * point.v0;

            if(mX >= pointX && (pointX + width * point.w) > mX && mY >= pointY && (pointY + height * point.h) > mY){
                return true;
            }
        }

        return false;
    }

    public static class Point {

        public final float u0, v0, w, h;

        public Point(float u0, float v0, float w, float h) {
            this.u0 = u0;
            this.v0 = v0;
            this.w = w;
            this.h = h;
        }

        @Nonnull
        public static Point of(@Nonnull Drawable sprite, int x, int y, int w, int h){
            return of(x / (float)sprite.getWidth(), y / (float)sprite.getHeight(), w / (float)sprite.getWidth(), h / (float)sprite.getHeight());
        }

        @Nonnull
        public static Point of(float x, float y, float w, float h){
            return new Point(x, y, w, h);
        }
    }

    public static class PointListBuilder {

        @Nonnull private final Drawable sprite;
        @Nonnull private final List<Point> points;

        private PointListBuilder(@Nonnull Drawable sprite) {
            this.sprite = sprite;
            points = new ArrayList<>();
        }

        @Nonnull
        public static PointListBuilder builder(@Nonnull Drawable sprite){
            return new PointListBuilder(sprite);
        }

        @Nonnull
        public PointListBuilder add(int x, int y, int w, int h){
            points.add(Point.of(sprite, x, y, w, h));
            return this;
        }

        @Nonnull
        public List<Point> build(){
            return Collections.unmodifiableList(points);
        }
    }
}
