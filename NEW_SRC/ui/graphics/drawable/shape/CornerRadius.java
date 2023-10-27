package com.sleepwalker.sleeplib.ui.graphics.drawable.shape;

import javax.annotation.Nonnull;

public interface CornerRadius {

    @Nonnull CornerRadius EMPTY = new Absolute(0);

    float compute(float maxRadius);

    class Absolute implements CornerRadius {

        private final float value;

        public Absolute(float value) {
            this.value = value;
        }

        @Override
        public float compute(float maxRadius) {
            return value;
        }
    }

    class Percent implements CornerRadius {

        private final float percent;

        public Percent(float percent) {
            this.percent = percent;
        }

        @Override
        public float compute(float maxRadius) {
            return maxRadius * percent;
        }
    }
}
