package com.sleepwalker.sleeplib.util;

import javax.annotation.Nonnull;

public final class ColorUtil {

    @Nonnull
    public static float[] HSVtoRGB(float h, float s, float v) {
        h *= 6;
        float m, n, f;
        int i;

        float[] hsv = new float[3];
        float[] rgb = new float[3];

        hsv[0] = h;
        hsv[1] = s;
        hsv[2] = v;

        if (hsv[0] == -1) {
            rgb[0] = rgb[1] = rgb[2] = hsv[2];
            return rgb;
        }
        i = (int) (Math.floor(hsv[0]));
        f = hsv[0] - i;
        if (i % 2 == 0) {
            f = 1 - f;
        }
        m = hsv[2] * (1 - hsv[1]);
        n = hsv[2] * (1 - hsv[1] * f);
        switch (i) {
            case 6:
            case 0:
                rgb[0] = hsv[2];
                rgb[1] = n;
                rgb[2] = m;
                break;
            case 1:
                rgb[0] = n;
                rgb[1] = hsv[2];
                rgb[2] = m;
                break;
            case 2:
                rgb[0] = m;
                rgb[1] = hsv[2];
                rgb[2] = n;
                break;
            case 3:
                rgb[0] = m;
                rgb[1] = n;
                rgb[2] = hsv[2];
                break;
            case 4:
                rgb[0] = n;
                rgb[1] = m;
                rgb[2] = hsv[2];
                break;
            case 5:
                rgb[0] = hsv[2];
                rgb[1] = m;
                rgb[2] = n;
                break;
        }

        return rgb;
    }

    @Nonnull
    public static float[] RGBtoHSV(float r, float g, float b) {
        float h, s, v;
        float min, max, delta;

        min = Math.min(Math.min(r, g), b);
        max = Math.max(Math.max(r, g), b);

        v = max;

        delta = max - min;

        if (max != 0)
            s = delta / max;
        else {
            s = 0;
            h = -1;
            return new float[] { h, s, v };
        }

        if (r == max)
            h = delta == 0 ? 0 : (g - b) / delta;
        else if (g == max)
            h = delta == 0 ? 0 : 2 + (b - r) / delta;
        else
            h = delta == 0 ? 0 : 4 + (r - g) / delta;

        h *= 60;

        if (h < 0)
            h += 360;

        return new float[] { h / 360f, s, v };
    }
}
