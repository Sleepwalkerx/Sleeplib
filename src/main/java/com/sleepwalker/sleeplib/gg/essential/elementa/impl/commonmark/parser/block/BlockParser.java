package com.sleepwalker.sleeplib.gg.essential.elementa.impl.commonmark.parser.block;

import com.sleepwalker.sleeplib.gg.essential.elementa.impl.commonmark.parser.InlineParser;
import com.sleepwalker.sleeplib.gg.essential.elementa.impl.commonmark.parser.SourceLine;
import com.sleepwalker.sleeplib.gg.essential.elementa.impl.commonmark.node.Block;
import com.sleepwalker.sleeplib.gg.essential.elementa.impl.commonmark.node.SourceSpan;

/**
 * Parser for a specific block node.
 * <p>
 * Implementations should subclass {@link AbstractBlockParser} instead of implementing this directly.
 */
public interface BlockParser {

    /**
     * Return true if the block that is parsed is a container (contains other blocks), or false if it's a leaf.
     */
    boolean isContainer();

    /**
     * Return true if the block can have lazy continuation lines.
     * <p>
     * Lazy continuation lines are lines that were rejected by this {@link #tryContinue(ParserState)} but didn't match
     * any other block parsers either.
     * <p>
     * If true is returned here, those lines will get added via {@link #addLine(SourceLine)}. For false, the block is
     * closed instead.
     */
    boolean canHaveLazyContinuationLines();

    boolean canContain(Block childBlock);

    Block getBlock();

    BlockContinue tryContinue(ParserState parserState);

    void addLine(SourceLine line);

    /**
     * Add a source span of the currently parsed block. The default implementation in {@link AbstractBlockParser} adds
     * it to the block. Unless you have some complicated parsing where you need to check source positions, you don't
     * need to override this.
     *
     * @since 0.16.0
     */
    void addSourceSpan(SourceSpan sourceSpan);

    void closeBlock();

    void parseInlines(InlineParser inlineParser);

}
