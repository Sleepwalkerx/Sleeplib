package com.sleepwalker.sleeplib.gg.essential.elementa.impl.commonmark.node;

public class Emphasis extends Node implements Delimited {

    private String delimiter;

    public Emphasis() {
    }

    public Emphasis(String delimiter) {
        this.delimiter = delimiter;
    }

    public void setDelimiter(String delimiter) {
        this.delimiter = delimiter;
    }

    @Override
    public String getOpeningDelimiter() {
        return delimiter;
    }

    @Override
    public String getClosingDelimiter() {
        return delimiter;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
