package com.sleepwalker.sleeplib.gg.essential.elementa.impl.commonmark.node;

public class IndentedCodeBlock extends Block {

    private String literal;

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public String getLiteral() {
        return literal;
    }

    public void setLiteral(String literal) {
        this.literal = literal;
    }
}
