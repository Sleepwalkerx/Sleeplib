package com.sleepwalker.sleeplib.gg.essential.elementa.impl.commonmark.node;

public abstract class CustomNode extends Node {
    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
