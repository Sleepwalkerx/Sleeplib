package com.sleepwalker.sleeplib.util.request;

import com.sleepwalker.sleeplib.util.TextUtil;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.Nonnull;

public final class RequestState {

    @Nonnull private final State state;
    @Nonnull private final ITextComponent info;

    public RequestState(@Nonnull State state, @Nonnull ITextComponent info) {
        this.state = state;
        this.info = info;
    }

    public boolean isCompleted(){
        return state == State.COMPLETED;
    }

    public boolean isNoted(){
        return state == State.NOTED;
    }

    public boolean isRejected(){
        return state == State.REJECTED;
    }

    @Nonnull
    public State getState() {
        return state;
    }

    @Nonnull
    public ITextComponent getInfo() {
        return info;
    }

    public void toNetwork(@Nonnull PacketBuffer pf){
        pf.writeEnumValue(state);
        pf.writeTextComponent(info);
    }

    @Nonnull
    public static RequestState completed(@Nonnull ITextComponent info){
        return new RequestState(State.COMPLETED, info);
    }

    @Nonnull
    public static RequestState completed(@Nonnull String info){
        return new RequestState(State.COMPLETED, TextUtil.str(info));
    }

    @Nonnull
    public static RequestState completed(@Nonnull String info, Object... args){
        return new RequestState(State.COMPLETED, TextUtil.str(info, args));
    }

    @Nonnull
    public static RequestState noted(@Nonnull ITextComponent info){
        return new RequestState(State.NOTED, info);
    }

    @Nonnull
    public static RequestState noted(@Nonnull String info){
        return new RequestState(State.NOTED, TextUtil.str(info));
    }

    @Nonnull
    public static RequestState noted(@Nonnull String info, Object... args){
        return new RequestState(State.NOTED, TextUtil.str(info, args));
    }

    @Nonnull
    public static RequestState rejected(@Nonnull ITextComponent info){
        return new RequestState(State.REJECTED, info);
    }

    @Nonnull
    public static RequestState rejected(@Nonnull String info){
        return new RequestState(State.REJECTED, TextUtil.str(info));
    }

    @Nonnull
    public static RequestState rejected(@Nonnull String info, Object... args){
        return new RequestState(State.REJECTED, TextUtil.str(info, args));
    }

    @Nonnull
    public static RequestState fromNetwork(@Nonnull PacketBuffer pf){
        return new RequestState(pf.readEnumValue(State.class), pf.readTextComponent());
    }

    public enum State {
        COMPLETED, NOTED, REJECTED
    }
}
