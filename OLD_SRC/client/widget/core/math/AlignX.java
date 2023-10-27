package com.sleepwalker.sleeplib.client.widget.core.math;

public enum AlignX {

    LEFT {
        @Override
        public float math(int elementWidth, int parentWidth) {
            return 0;
        }
    },
    RIGHT {
        @Override
        public float math(int elementWidth, int parentWidth) {
            return parentWidth - elementWidth;
        }
    },
    CENTER {
        @Override
        public float math(int elementWidth, int parentWidth) {
            return parentWidth / 2f - elementWidth / 2f;
        }
    };

    public abstract float math(int elementWidth, int parentWidth);
}
