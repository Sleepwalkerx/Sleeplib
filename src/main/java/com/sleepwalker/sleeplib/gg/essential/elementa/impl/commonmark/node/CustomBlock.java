package com.sleepwalker.sleeplib.gg.essential.elementa.impl.commonmark.node;

public abstract class CustomBlock extends Block {

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
