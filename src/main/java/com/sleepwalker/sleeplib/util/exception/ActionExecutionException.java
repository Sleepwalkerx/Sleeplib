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
import net.minecraft.util.text.StringTextComponent;

import javax.annotation.Nonnull;

public class ActionExecutionException extends Exception {

    @Nonnull private final ITextComponent reason;
    private final boolean serverSideException;

    public ActionExecutionException(Throwable cause) {
        this(C.UNEXPECTED_ERROR, cause, true);
    }

    public ActionExecutionException(@Nonnull ITextComponent reason, Throwable cause, boolean serverSideException) {
        super(cause);
        this.reason = reason;
        this.serverSideException = serverSideException;
    }

    public ActionExecutionException(@Nonnull ITextComponent reason, boolean serverSideException) {
        super();
        this.reason = reason;
        this.serverSideException = serverSideException;
    }

    public ActionExecutionException(@Nonnull ITextComponent reason, Throwable cause) {
        this(reason, cause, true);
    }

    public ActionExecutionException(@Nonnull ITextComponent reason) {
        this(reason, false);
    }

    public ActionExecutionException(@Nonnull String reason, Throwable cause, boolean serverSideException) {
        this(new StringTextComponent(reason), cause, serverSideException);
    }

    public ActionExecutionException(@Nonnull String reason, boolean serverSideException) {
        this(new StringTextComponent(reason), serverSideException);
    }

    public ActionExecutionException(@Nonnull String reason, Throwable cause) {
        this(new StringTextComponent(reason), cause, true);
    }

    public ActionExecutionException(@Nonnull String reason) {
        this(new StringTextComponent(reason), false);
    }

    @Nonnull
    public ITextComponent getReason() {
        return reason;
    }

    public boolean isServerSideException() {
        return serverSideException;
    }
}
