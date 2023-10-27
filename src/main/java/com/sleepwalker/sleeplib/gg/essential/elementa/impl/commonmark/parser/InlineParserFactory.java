package com.sleepwalker.sleeplib.gg.essential.elementa.impl.commonmark.parser;

/**
 * Factory for custom inline parser.
 */
public interface InlineParserFactory {
    InlineParser create(InlineParserContext inlineParserContext);
}
