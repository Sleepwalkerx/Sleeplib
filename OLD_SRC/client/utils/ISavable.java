package com.sleepwalker.sleeplib.client.utils;

import net.minecraft.nbt.INBT;

import javax.annotation.Nonnull;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public interface ISavable<T extends INBT> {

    @Nonnull
    T saveData();

    void readData(@Nonnull T nbt);

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.SOURCE)
    @interface DefaultDepended {
    }
}
