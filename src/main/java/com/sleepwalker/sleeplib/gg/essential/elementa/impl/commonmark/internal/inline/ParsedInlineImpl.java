package com.sleepwalker.sleeplib.gg.essential.elementa.impl.commonmark.internal.inline;

import com.sleepwalker.sleeplib.gg.essential.elementa.impl.commonmark.node.Node;

public class ParsedInlineImpl extends ParsedInline {
    private final Node node;
    private final Position position;

    ParsedInlineImpl(Node node, Position position) {
        this.node = node;
        this.position = position;
    }

    public Node getNode() {
        return node;
    }

    public Position getPosition() {
        return position;
    }
}
