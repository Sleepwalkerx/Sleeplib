package com.sleepwalker.sleeplib.ui.listener;

import com.sleepwalker.sleeplib.ui.event.Event;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ListenerStack<E extends Event> {

    @Nonnull
    private List<ListenerHolder> listeners;

    private ListenerStack(){
        listeners = Collections.emptyList();
    }

    @Nonnull
    public static <E extends Event> ListenerStack<E> newStack(){
        return new ListenerStack<>();
    }

    public void post(@Nonnull E event){
        for(ListenerHolder holder : listeners){
            holder.listener.onEvent(event);
        }
    }

    public void addListener(@Nonnull EventListener<E> listener){
        addListener(listener, 0);
    }

    public void addListener(@Nonnull EventListener<E> listener, int priority){

        if(listeners == Collections.EMPTY_LIST){
            listeners = new ArrayList<>();
        }

        listeners.add(new ListenerHolder(priority, listener));
        Collections.sort(listeners);
    }

    public void removeListener(@Nonnull EventListener<E> listener){

        if(!listeners.isEmpty() && listeners.removeIf(l -> l.listener == listener)){

            if(listeners.size() == 0){
                listeners = Collections.emptyList();
            }
            else {
                Collections.sort(listeners);
            }
        }
    }

    private class ListenerHolder implements Comparable<ListenerHolder> {

        private final int priority;
        private final EventListener<E> listener;

        private ListenerHolder(int priority, EventListener<E> listener) {
            this.priority = priority;
            this.listener = listener;
        }

        @Override
        public int compareTo(@Nonnull ListenerHolder o) {
            return o.priority - priority;
        }
    }
}
