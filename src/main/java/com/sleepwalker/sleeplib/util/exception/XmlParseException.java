package com.sleepwalker.sleeplib.util.exception;

public class XmlParseException extends RuntimeException {

    public XmlParseException(String msg) {
        super(msg);
    }

    public XmlParseException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public XmlParseException(Throwable cause) {
        super(cause);
    }
}
