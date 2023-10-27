package com.sleepwalker.sleeplib.client.widget.core.event;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public final class ListenerStack<L extends IEventListener> {

    @Nonnull
    private List<L> listeners;

    private ListenerStack(){
        listeners = Collections.emptyList();
    }

    @Nonnull
    public static <L extends IEventListener> ListenerStack<L> newStack(){
        return new ListenerStack<>();
    }

    public void post(@Nonnull Consumer<L> consumer){
        listeners.forEach(consumer);
    }

    public void addListener(@Nonnull L listener){
        if(listeners == Collections.EMPTY_LIST){
            listeners = new ArrayList<>();
        }
        listeners.add(listener);
    }

    public void removeListener(@Nonnull L listener){
        if(listeners == Collections.EMPTY_LIST){
            return;
        }
        listeners.remove(listener);
        if(listeners.size() == 0){
            listeners = Collections.emptyList();
        }
    }

    public void addListenerIfNot(@Nonnull L listener){
        if(listeners == Collections.EMPTY_LIST){
            listeners = new ArrayList<>();
            listeners.add(listener);
        }
        if(!listeners.contains(listener)){
            listeners.add(listener);
        }
    }
}
