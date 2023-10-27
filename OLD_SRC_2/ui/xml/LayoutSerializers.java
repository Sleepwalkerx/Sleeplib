package com.sleepwalker.sleeplib.ui.xml;

import com.sleepwalker.sleeplib.SleepLib;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

import javax.annotation.Nonnull;

public final class LayoutSerializers {

    @Nonnull
    public static final DeferredRegister<LayoutSerializer> REGISTER = DeferredRegister.create(LayoutSerializer.class, SleepLib.MODID);

    @Nonnull
    public static final RegistryObject<SimpleLayoutSerializer> SIMPLE = REGISTER.register("simple", SimpleLayoutSerializer::new);
}
