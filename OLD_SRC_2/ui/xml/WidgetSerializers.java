package com.sleepwalker.sleeplib.ui.xml;

import com.sleepwalker.sleeplib.SleepLib;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;

import javax.annotation.Nonnull;

@SuppressWarnings({"rawtypes", "unchecked"})
public final class WidgetSerializers {

    @Nonnull public static final DeferredRegister<WidgetSerializer<?>>
        REGISTER = DeferredRegister.create((Class)WidgetSerializer.class, SleepLib.MODID);

    @Nonnull public static final RegistryObject<BasicWidgetSerializer> BASIC = REGISTER.register("basic", BasicWidgetSerializer::new);
}
