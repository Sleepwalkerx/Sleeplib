package com.sleepwalker.sleeplib.client.widget.core.math;

public enum AlignY {

    TOP {
        @Override
        public float math(int elementHeight, int parentHeight) {
            return 0;
        }
    },
    BOTTOM {
        @Override
        public float math(int elementHeight, int parentHeight) {
            return parentHeight - elementHeight;
        }
    },
    MIDDLE {
        @Override
        public float math(int elementHeight, int parentHeight) {
            return parentHeight / 2f - elementHeight / 2f;
        }
    };

    public abstract float math(int elementHeight, int parentHeight);
}
