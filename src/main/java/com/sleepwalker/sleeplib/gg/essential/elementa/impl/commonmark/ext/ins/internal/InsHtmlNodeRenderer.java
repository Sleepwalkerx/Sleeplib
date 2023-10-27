package com.sleepwalker.sleeplib.gg.essential.elementa.impl.commonmark.ext.ins.internal;

import com.sleepwalker.sleeplib.gg.essential.elementa.impl.commonmark.node.Node;
import com.sleepwalker.sleeplib.gg.essential.elementa.impl.commonmark.renderer.html.HtmlNodeRendererContext;
import com.sleepwalker.sleeplib.gg.essential.elementa.impl.commonmark.renderer.html.HtmlWriter;

import java.util.Collections;
import java.util.Map;

public class InsHtmlNodeRenderer extends InsNodeRenderer {

    private final HtmlNodeRendererContext context;
    private final HtmlWriter html;

    public InsHtmlNodeRenderer(HtmlNodeRendererContext context) {
        this.context = context;
        this.html = context.getWriter();
    }

    @Override
    public void render(Node node) {
        Map<String, String> attributes = context.extendAttributes(node, "ins", Collections.<String, String>emptyMap());
        html.tag("ins", attributes);
        renderChildren(node);
        html.tag("/ins");
    }

    private void renderChildren(Node parent) {
        Node node = parent.getFirstChild();
        while (node != null) {
            Node next = node.getNext();
            context.render(node);
            node = next;
        }
    }
}
