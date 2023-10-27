/*
 * Copyright (c)
 *
 * All rights reserved
 * Any copying and reproduction of the text, including partial and in any form,
 * without the written permission of the copyright holder is prohibited.
 * Any use of photos, audio, video and graphic materials, including partial,
 * without the written permission of the copyright holder is prohibited.
 *
 * © Салтыков Д.А., 2022
 */

package com.sleepwalker.sleeplib.util.exception;

import com.sleepwalker.sleeplib.SleepLib;
import com.sleepwalker.sleeplib.util.C;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.Nonnull;

public class ActionExecutionException extends Exception {

    @Nonnull private final ITextComponent reason;
    private final boolean serverSideException;

    public ActionExecutionException(@Nonnull ITextComponent reason) {
        this(reason, false);
    }

    public ActionExecutionException(@Nonnull ITextComponent reason, boolean serverSideException) {
        this.reason = reason;
        this.serverSideException = serverSideException;
    }

    public ActionExecutionException(Throwable cause) {
        this(C.UNEXPECTED_ERROR, cause, true);
    }

    public ActionExecutionException(@Nonnull ITextComponent reason, Throwable cause) {
        this(reason, cause, true);
    }

    public ActionExecutionException(@Nonnull ITextComponent reason, Throwable cause, boolean serverSideException) {
        super(cause);
        this.reason = reason;
        this.serverSideException = serverSideException;

        if(isServerSideException()){
            SleepLib.LOGGER.throwing(getCause());
        }
    }

    @Nonnull
    public ITextComponent getReason() {
        return reason;
    }

    public boolean isServerSideException() {
        return serverSideException;
    }

    public enum Initiator {
        SERVER,

    }
}
