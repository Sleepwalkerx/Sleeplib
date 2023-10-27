package com.sleepwalker.sleeplib.gg.essential.elementa.impl.commonmark.ext.ins.internal;

import com.sleepwalker.sleeplib.gg.essential.elementa.impl.commonmark.ext.ins.Ins;
import com.sleepwalker.sleeplib.gg.essential.elementa.impl.commonmark.node.Node;
import com.sleepwalker.sleeplib.gg.essential.elementa.impl.commonmark.node.Nodes;
import com.sleepwalker.sleeplib.gg.essential.elementa.impl.commonmark.node.SourceSpans;
import com.sleepwalker.sleeplib.gg.essential.elementa.impl.commonmark.node.Text;
import com.sleepwalker.sleeplib.gg.essential.elementa.impl.commonmark.parser.delimiter.DelimiterProcessor;
import com.sleepwalker.sleeplib.gg.essential.elementa.impl.commonmark.parser.delimiter.DelimiterRun;

public class InsDelimiterProcessor implements DelimiterProcessor {

    @Override
    public char getOpeningCharacter() {
        return '+';
    }

    @Override
    public char getClosingCharacter() {
        return '+';
    }

    @Override
    public int getMinLength() {
        return 2;
    }

    @Override
    public int process(DelimiterRun openingRun, DelimiterRun closingRun) {
        if (openingRun.length() >= 2 && closingRun.length() >= 2) {
            // Use exactly two delimiters even if we have more, and don't care about internal openers/closers.

            Text opener = openingRun.getOpener();

            // Wrap nodes between delimiters in ins.
            Node ins = new Ins();

            SourceSpans sourceSpans = new SourceSpans();
            sourceSpans.addAllFrom(openingRun.getOpeners(2));

            for (Node node : Nodes.between(opener, closingRun.getCloser())) {
                ins.appendChild(node);
                sourceSpans.addAll(node.getSourceSpans());
            }

            sourceSpans.addAllFrom(closingRun.getClosers(2));
            ins.setSourceSpans(sourceSpans.getSourceSpans());

            opener.insertAfter(ins);

            return 2;
        } else {
            return 0;
        }
    }
}
