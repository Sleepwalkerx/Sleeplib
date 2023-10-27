package com.sleepwalker.sleeplib.gg.essential.elementa.impl.commonmark.node;

public class Heading extends Block {

    private int level;

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }
}
