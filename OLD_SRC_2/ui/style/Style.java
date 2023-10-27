package com.sleepwalker.sleeplib.ui.style;

import com.sleepwalker.sleeplib.ui.property.Property;
import com.sleepwalker.sleeplib.ui.widget.Widget;

import javax.annotation.Nonnull;
import java.util.Set;

public interface Style {

    void apply(@Nonnull Widget widget);

    @Nonnull Set<Property<?, ?>> getProperties();
}
