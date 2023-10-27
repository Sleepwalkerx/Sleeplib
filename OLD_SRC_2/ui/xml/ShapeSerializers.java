package com.sleepwalker.sleeplib.ui.xml;

import com.sleepwalker.sleeplib.SleepLib;
import com.sleepwalker.sleeplib.ui.graphics.drawable.shape.OvalShape;
import com.sleepwalker.sleeplib.ui.graphics.drawable.shape.RectShape;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

import javax.annotation.Nonnull;

public final class ShapeSerializers {

    @Nonnull public static final DeferredRegister<ShapeSerializer> REGISTER = DeferredRegister.create(ShapeSerializer.class, SleepLib.MODID);

    @Nonnull public static final RegistryObject<RectShape.Serializer> RECT = REGISTER.register("rect", RectShape.Serializer::new);
    @Nonnull public static final RegistryObject<OvalShape.Serializer> OVAL = REGISTER.register("oval", OvalShape.Serializer::new);
}
