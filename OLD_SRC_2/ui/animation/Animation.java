package com.sleepwalker.sleeplib.ui.animation;

import com.sleepwalker.sleeplib.ui.animation.function.AnimationFunction;

import javax.annotation.Nonnull;

public interface Animation {

    @Nonnull AnimationFunction getAnimationFunction();
    double getDuration();
}
