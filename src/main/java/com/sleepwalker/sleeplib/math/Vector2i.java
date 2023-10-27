package com.sleepwalker.sleeplib.math;

import net.minecraft.util.math.vector.Vector2f;

import javax.annotation.Nonnull;
import java.util.Objects;

public class Vector2i {

    public static Vector2i ZERO = new Vector2i(0, 0);

    public final int x, y;

    public Vector2i(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector2i vector2i = (Vector2i) o;
        return x == vector2i.x && y == vector2i.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Nonnull
    public static Vector2i cast(@Nonnull Vector2f vector2f){
        return new Vector2i((int)vector2f.x, (int)vector2f.y);
    }
}
