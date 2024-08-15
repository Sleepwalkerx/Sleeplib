package com.sleepwalker.sleeplib

import net.minecraftforge.fml.common.Mod
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import javax.annotation.Nonnull

@Mod(SleepLib.MODID)
class SleepLib {

    companion object {
        const val MODID = "sleeplib"
        val LOGGER: Logger = LogManager.getLogger()
    }
}