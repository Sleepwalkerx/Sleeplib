package com.sleepwalker.sleeplib.gg.essential.elementa.impl.commonmark.ext.ins;

import com.sleepwalker.sleeplib.gg.essential.elementa.impl.commonmark.Extension;
import com.sleepwalker.sleeplib.gg.essential.elementa.impl.commonmark.parser.Parser;
import com.sleepwalker.sleeplib.gg.essential.elementa.impl.commonmark.renderer.NodeRenderer;
import com.sleepwalker.sleeplib.gg.essential.elementa.impl.commonmark.renderer.html.HtmlNodeRendererContext;
import com.sleepwalker.sleeplib.gg.essential.elementa.impl.commonmark.renderer.html.HtmlNodeRendererFactory;
import com.sleepwalker.sleeplib.gg.essential.elementa.impl.commonmark.renderer.html.HtmlRenderer;
import com.sleepwalker.sleeplib.gg.essential.elementa.impl.commonmark.renderer.text.TextContentNodeRendererContext;
import com.sleepwalker.sleeplib.gg.essential.elementa.impl.commonmark.renderer.text.TextContentNodeRendererFactory;
import com.sleepwalker.sleeplib.gg.essential.elementa.impl.commonmark.renderer.text.TextContentRenderer;
import com.sleepwalker.sleeplib.gg.essential.elementa.impl.commonmark.ext.ins.internal.InsDelimiterProcessor;
import com.sleepwalker.sleeplib.gg.essential.elementa.impl.commonmark.ext.ins.internal.InsHtmlNodeRenderer;
import com.sleepwalker.sleeplib.gg.essential.elementa.impl.commonmark.ext.ins.internal.InsTextContentNodeRenderer;

/**
 * Extension for ins using ++
 * <p>
 * Create it with {@link #create()} and then configure it on the builders
 * ({@link Parser.Builder#extensions(Iterable)},
 * {@link HtmlRenderer.Builder#extensions(Iterable)}).
 * </p>
 * <p>
 * The parsed ins text regions are turned into {@link Ins} nodes.
 * </p>
 */
public class InsExtension implements Parser.ParserExtension,
        HtmlRenderer.HtmlRendererExtension,
    TextContentRenderer.TextContentRendererExtension {

    private InsExtension() {
    }

    public static Extension create() {
        return new InsExtension();
    }

    @Override
    public void extend(Parser.Builder parserBuilder) {
        parserBuilder.customDelimiterProcessor(new InsDelimiterProcessor());
    }

    @Override
    public void extend(HtmlRenderer.Builder rendererBuilder) {
        rendererBuilder.nodeRendererFactory(new HtmlNodeRendererFactory() {
            @Override
            public NodeRenderer create(HtmlNodeRendererContext context) {
                return new InsHtmlNodeRenderer(context);
            }
        });
    }

    @Override
    public void extend(TextContentRenderer.Builder rendererBuilder) {
        rendererBuilder.nodeRendererFactory(new TextContentNodeRendererFactory() {
            @Override
            public NodeRenderer create(TextContentNodeRendererContext context) {
                return new InsTextContentNodeRenderer(context);
            }
        });
    }
}
