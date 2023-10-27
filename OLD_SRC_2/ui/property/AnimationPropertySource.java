package com.sleepwalker.sleeplib.ui.property;

import com.sleepwalker.sleeplib.ui.animation.DoubleAnimation;
import com.sleepwalker.sleeplib.ui.widget.Widget;
import net.minecraft.util.math.MathHelper;

import javax.annotation.Nonnull;

public class AnimationPropertySource implements PropertySource {

    @Nonnull private final DoubleAnimation animation;
    private long currentTime;

    public AnimationPropertySource(@Nonnull DoubleAnimation animation) {
        this.animation = animation;
    }

    @Nonnull
    @Override
    public <T extends Widget, V> V getPropertyValue(@Nonnull Property<T, V> property, @Nonnull T target) {

        if(currentTime == 0){
            currentTime = System.currentTimeMillis();
        }

        double deltaTime = MathHelper.clamp(System.currentTimeMillis() - currentTime, 0.0, animation.getMs());
        return property.getValueType().cast((float)animation.getValue(deltaTime));
    }

    @Override
    public <T extends Widget, V> boolean hasProperty(@Nonnull Property<T, V> property, @Nonnull T target) {
        return false;
    }
}
