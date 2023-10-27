package com.sleepwalker.sleeplib.gg.essential.elementa.impl.commonmark.node;

public class SoftLineBreak extends Node {

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
