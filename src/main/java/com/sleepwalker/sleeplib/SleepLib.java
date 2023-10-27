package com.sleepwalker.sleeplib;

import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;

@Mod(SleepLib.MODID)
public final class SleepLib {

    @Nonnull public static final String MODID = "sleeplib";
    @Nonnull public static final Logger LOGGER = LogManager.getLogger();

    public SleepLib(){}
}
