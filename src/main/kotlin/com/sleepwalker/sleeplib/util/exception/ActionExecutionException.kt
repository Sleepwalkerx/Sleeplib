package com.sleepwalker.sleeplib.util.exception

import com.sleepwalker.sleeplib.util.C
import com.sleepwalker.sleeplib.util.text.literal
import net.minecraft.util.text.ITextComponent

class ActionExecutionException(val reason: ITextComponent, serverSide: Boolean) : Exception() {
    constructor(cause: Throwable) : this(C.UNEXPECTED_ERROR, true){ initCause(cause) }
    constructor(reason: ITextComponent) : this(reason, false)
    constructor(reason: String) : this(reason.literal())
}