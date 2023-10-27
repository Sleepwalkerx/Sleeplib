package com.sleepwalker.sleeplib.gg.essential.elementa.impl.commonmark.ext.gfm.strikethrough.internal;

import com.sleepwalker.sleeplib.gg.essential.elementa.impl.commonmark.node.Node;
import com.sleepwalker.sleeplib.gg.essential.elementa.impl.commonmark.node.Nodes;
import com.sleepwalker.sleeplib.gg.essential.elementa.impl.commonmark.node.SourceSpans;
import com.sleepwalker.sleeplib.gg.essential.elementa.impl.commonmark.node.Text;
import com.sleepwalker.sleeplib.gg.essential.elementa.impl.commonmark.parser.delimiter.DelimiterProcessor;
import com.sleepwalker.sleeplib.gg.essential.elementa.impl.commonmark.parser.delimiter.DelimiterRun;
import com.sleepwalker.sleeplib.gg.essential.elementa.impl.commonmark.ext.gfm.strikethrough.Strikethrough;

public class StrikethroughDelimiterProcessor implements DelimiterProcessor {

    private final boolean requireTwoTildes;

    public StrikethroughDelimiterProcessor() {
        this(false);
    }

    public StrikethroughDelimiterProcessor(boolean requireTwoTildes) {
        this.requireTwoTildes = requireTwoTildes;
    }

    @Override
    public char getOpeningCharacter() {
        return '~';
    }

    @Override
    public char getClosingCharacter() {
        return '~';
    }

    @Override
    public int getMinLength() {
        return requireTwoTildes ? 2 : 1;
    }

    @Override
    public int process(DelimiterRun openingRun, DelimiterRun closingRun) {
        if (openingRun.length() == closingRun.length() && openingRun.length() <= 2) {
            // GitHub only accepts either one or two delimiters, but not a mix or more than that.

            Text opener = openingRun.getOpener();

            // Wrap nodes between delimiters in strikethrough.
            Node strikethrough = new Strikethrough();

            SourceSpans sourceSpans = new SourceSpans();
            sourceSpans.addAllFrom(openingRun.getOpeners(openingRun.length()));

            for (Node node : Nodes.between(opener, closingRun.getCloser())) {
                strikethrough.appendChild(node);
                sourceSpans.addAll(node.getSourceSpans());
            }

            sourceSpans.addAllFrom(closingRun.getClosers(closingRun.length()));
            strikethrough.setSourceSpans(sourceSpans.getSourceSpans());

            opener.insertAfter(strikethrough);

            return openingRun.length();
        } else {
            return 0;
        }
    }
}
