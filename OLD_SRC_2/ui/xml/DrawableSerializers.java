package com.sleepwalker.sleeplib.ui.xml;

import com.sleepwalker.sleeplib.SleepLib;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

import javax.annotation.Nonnull;

public final class DrawableSerializers {

    @Nonnull public static final DeferredRegister<DrawableSerializer> REGISTER = DeferredRegister.create(DrawableSerializer.class, SleepLib.MODID);

    @Nonnull public static final RegistryObject<ShapeDrawableSerializer> SHAPE = REGISTER.register("shape", ShapeDrawableSerializer::new);
}
