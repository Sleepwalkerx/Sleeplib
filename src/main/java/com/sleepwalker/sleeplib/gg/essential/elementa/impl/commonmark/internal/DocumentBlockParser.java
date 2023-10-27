package com.sleepwalker.sleeplib.gg.essential.elementa.impl.commonmark.internal;

import com.sleepwalker.sleeplib.gg.essential.elementa.impl.commonmark.node.Block;
import com.sleepwalker.sleeplib.gg.essential.elementa.impl.commonmark.node.Document;
import com.sleepwalker.sleeplib.gg.essential.elementa.impl.commonmark.parser.SourceLine;
import com.sleepwalker.sleeplib.gg.essential.elementa.impl.commonmark.parser.block.AbstractBlockParser;
import com.sleepwalker.sleeplib.gg.essential.elementa.impl.commonmark.parser.block.BlockContinue;
import com.sleepwalker.sleeplib.gg.essential.elementa.impl.commonmark.parser.block.ParserState;

public class DocumentBlockParser extends AbstractBlockParser {

    private final Document document = new Document();

    @Override
    public boolean isContainer() {
        return true;
    }

    @Override
    public boolean canContain(Block block) {
        return true;
    }

    @Override
    public Document getBlock() {
        return document;
    }

    @Override
    public BlockContinue tryContinue(ParserState state) {
        return BlockContinue.atIndex(state.getIndex());
    }

    @Override
    public void addLine(SourceLine line) {
    }

}
