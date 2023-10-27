package com.sleepwalker.sleeplib.client.widget.core;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import com.sleepwalker.sleeplib.client.widget.core.event.IMouseListener;
import com.sleepwalker.sleeplib.client.widget.core.event.ListenerStack;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public interface IRoot {

    @Nonnull
    ListenerStack<IMouseListener> getMouseListener();
}
