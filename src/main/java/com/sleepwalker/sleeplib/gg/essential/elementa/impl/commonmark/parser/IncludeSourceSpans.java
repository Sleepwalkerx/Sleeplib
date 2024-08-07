package com.sleepwalker.sleeplib.gg.essential.elementa.impl.commonmark.parser;

import com.sleepwalker.sleeplib.gg.essential.elementa.impl.commonmark.node.Block;
import com.sleepwalker.sleeplib.gg.essential.elementa.impl.commonmark.node.SourceSpan;

/**
 * Whether to include {@link SourceSpan} or not while parsing,
 * see {@link Parser.Builder#includeSourceSpans(IncludeSourceSpans)}.
 *
 * @since 0.16.0
 */
public enum IncludeSourceSpans {
    /**
     * Do not include source spans.
     */
    NONE,
    /**
     * Include source spans on {@link Block} nodes.
     */
    BLOCKS,
    /**
     * Include source spans on block nodes and inline nodes.
     */
    BLOCKS_AND_INLINES,
}
