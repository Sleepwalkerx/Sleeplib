package com.sleepwalker.sleeplib.util.exception

class XmlParseException : RuntimeException {
    constructor(msg: String) : super(msg)
    constructor(cause: Throwable) : super(cause)
    constructor(msg: String, cause: Throwable) : super(msg, cause)
}