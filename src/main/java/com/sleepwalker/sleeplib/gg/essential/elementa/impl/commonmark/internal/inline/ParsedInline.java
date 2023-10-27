package com.sleepwalker.sleeplib.gg.essential.elementa.impl.commonmark.internal.inline;

import com.sleepwalker.sleeplib.gg.essential.elementa.impl.commonmark.node.Node;

public abstract class ParsedInline {

    protected ParsedInline() {
    }

    public static ParsedInline none() {
        return null;
    }

    public static ParsedInline of(Node node, Position position) {
        if (node == null) {
            throw new NullPointerException("node must not be null");
        }
        if (position == null) {
            throw new NullPointerException("position must not be null");
        }
        return new ParsedInlineImpl(node, position);
    }
}
