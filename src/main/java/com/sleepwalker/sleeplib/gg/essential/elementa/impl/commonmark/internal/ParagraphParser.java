package com.sleepwalker.sleeplib.gg.essential.elementa.impl.commonmark.internal;

import com.sleepwalker.sleeplib.gg.essential.elementa.impl.commonmark.node.Block;
import com.sleepwalker.sleeplib.gg.essential.elementa.impl.commonmark.node.LinkReferenceDefinition;
import com.sleepwalker.sleeplib.gg.essential.elementa.impl.commonmark.node.Paragraph;
import com.sleepwalker.sleeplib.gg.essential.elementa.impl.commonmark.node.SourceSpan;
import com.sleepwalker.sleeplib.gg.essential.elementa.impl.commonmark.parser.InlineParser;
import com.sleepwalker.sleeplib.gg.essential.elementa.impl.commonmark.parser.SourceLine;
import com.sleepwalker.sleeplib.gg.essential.elementa.impl.commonmark.parser.SourceLines;
import com.sleepwalker.sleeplib.gg.essential.elementa.impl.commonmark.parser.block.AbstractBlockParser;
import com.sleepwalker.sleeplib.gg.essential.elementa.impl.commonmark.parser.block.BlockContinue;
import com.sleepwalker.sleeplib.gg.essential.elementa.impl.commonmark.parser.block.ParserState;

import java.util.List;

public class ParagraphParser extends AbstractBlockParser {

    private final Paragraph block = new Paragraph();
    private final LinkReferenceDefinitionParser linkReferenceDefinitionParser = new LinkReferenceDefinitionParser();

    @Override
    public boolean canHaveLazyContinuationLines() {
        return true;
    }

    @Override
    public Block getBlock() {
        return block;
    }

    @Override
    public BlockContinue tryContinue(ParserState state) {
        if (!state.isBlank()) {
            return BlockContinue.atIndex(state.getIndex());
        } else {
            return BlockContinue.none();
        }
    }

    @Override
    public void addLine(SourceLine line) {
        linkReferenceDefinitionParser.parse(line);
    }

    @Override
    public void addSourceSpan(SourceSpan sourceSpan) {
        // Some source spans might belong to link reference definitions, others to the paragraph.
        // The parser will handle that.
        linkReferenceDefinitionParser.addSourceSpan(sourceSpan);
    }

    @Override
    public void closeBlock() {
        if (linkReferenceDefinitionParser.getParagraphLines().isEmpty()) {
            block.unlink();
        } else {
            block.setSourceSpans(linkReferenceDefinitionParser.getParagraphSourceSpans());
        }
    }

    @Override
    public void parseInlines(InlineParser inlineParser) {
        SourceLines lines = linkReferenceDefinitionParser.getParagraphLines();
        if (!lines.isEmpty()) {
            inlineParser.parse(lines, block);
        }
    }

    public SourceLines getParagraphLines() {
        return linkReferenceDefinitionParser.getParagraphLines();
    }

    public List<LinkReferenceDefinition> getDefinitions() {
        return linkReferenceDefinitionParser.getDefinitions();
    }
}
