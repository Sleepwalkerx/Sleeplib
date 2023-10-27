package com.sleepwalker.sleeplib.ui.graphics.drawable;

import com.sleepwalker.sleeplib.SleepLib;
import com.sleepwalker.sleeplib.ui.graphics.drawable.shape.ShapeDrawableSerializer;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

import javax.annotation.Nonnull;

public final class DrawableSerializers {

    @Nonnull public static final DeferredRegister<DrawableSerializer> REGISTER = DeferredRegister.create(DrawableSerializer.class, SleepLib.MODID);

    @Nonnull public static final RegistryObject<ShapeDrawableSerializer> SHAPE = REGISTER.register("shape", ShapeDrawableSerializer::new);
}
