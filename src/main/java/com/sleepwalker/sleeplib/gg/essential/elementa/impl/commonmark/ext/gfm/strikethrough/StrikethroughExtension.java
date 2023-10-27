package com.sleepwalker.sleeplib.gg.essential.elementa.impl.commonmark.ext.gfm.strikethrough;

import com.sleepwalker.sleeplib.gg.essential.elementa.impl.commonmark.Extension;
import com.sleepwalker.sleeplib.gg.essential.elementa.impl.commonmark.ext.gfm.strikethrough.internal.StrikethroughDelimiterProcessor;
import com.sleepwalker.sleeplib.gg.essential.elementa.impl.commonmark.parser.Parser;
import com.sleepwalker.sleeplib.gg.essential.elementa.impl.commonmark.renderer.NodeRenderer;
import com.sleepwalker.sleeplib.gg.essential.elementa.impl.commonmark.renderer.html.HtmlNodeRendererContext;
import com.sleepwalker.sleeplib.gg.essential.elementa.impl.commonmark.renderer.html.HtmlNodeRendererFactory;
import com.sleepwalker.sleeplib.gg.essential.elementa.impl.commonmark.renderer.html.HtmlRenderer;
import com.sleepwalker.sleeplib.gg.essential.elementa.impl.commonmark.renderer.text.TextContentNodeRendererContext;
import com.sleepwalker.sleeplib.gg.essential.elementa.impl.commonmark.renderer.text.TextContentNodeRendererFactory;
import com.sleepwalker.sleeplib.gg.essential.elementa.impl.commonmark.renderer.text.TextContentRenderer;
import com.sleepwalker.sleeplib.gg.essential.elementa.impl.commonmark.ext.gfm.strikethrough.internal.StrikethroughHtmlNodeRenderer;
import com.sleepwalker.sleeplib.gg.essential.elementa.impl.commonmark.ext.gfm.strikethrough.internal.StrikethroughTextContentNodeRenderer;

/**
 * Extension for GFM strikethrough using {@code ~} or {@code ~~} (GitHub Flavored Markdown).
 * <p>Example input:</p>
 * <pre>{@code ~foo~ or ~~bar~~}</pre>
 * <p>Example output (HTML):</p>
 * <pre>{@code <del>foo</del> or <del>bar</del>}</pre>
 * <p>
 * Create the extension with {@link #create()} and then add it to the parser and renderer builders
 * ({@link Parser.Builder#extensions(Iterable)},
 * {@link HtmlRenderer.Builder#extensions(Iterable)}).
 * </p>
 * <p>
 * The parsed strikethrough text regions are turned into {@link Strikethrough} nodes.
 * </p>
 * <p>
 * If you have another extension that only uses a single tilde ({@code ~}) syntax, you will have to configure this
 * {@link StrikethroughExtension} to only accept the double tilde syntax, like this:
 * </p>
 * <pre>
 *     {@code
 *     StrikethroughExtension.builder().requireTwoTildes(true).build();
 *     }
 * </pre>
 * <p>
 * If you don't do that, there's a conflict between the two extensions and you will get an
 * {@link IllegalArgumentException} when constructing the parser.
 * </p>
 */
public class StrikethroughExtension implements Parser.ParserExtension, HtmlRenderer.HtmlRendererExtension,
    TextContentRenderer.TextContentRendererExtension {

    private final boolean requireTwoTildes;

    private StrikethroughExtension(Builder builder) {
        this.requireTwoTildes = builder.requireTwoTildes;
    }

    /**
     * @return the extension with default options
     */
    public static Extension create() {
        return builder().build();
    }

    /**
     * @return a builder to configure the behavior of the extension
     */
    public static Builder builder() {
        return new Builder();
    }

    @Override
    public void extend(Parser.Builder parserBuilder) {
        parserBuilder.customDelimiterProcessor(new StrikethroughDelimiterProcessor(requireTwoTildes));
    }

    @Override
    public void extend(HtmlRenderer.Builder rendererBuilder) {
        rendererBuilder.nodeRendererFactory(new HtmlNodeRendererFactory() {
            @Override
            public NodeRenderer create(HtmlNodeRendererContext context) {
                return new StrikethroughHtmlNodeRenderer(context);
            }
        });
    }

    @Override
    public void extend(TextContentRenderer.Builder rendererBuilder) {
        rendererBuilder.nodeRendererFactory(new TextContentNodeRendererFactory() {
            @Override
            public NodeRenderer create(TextContentNodeRendererContext context) {
                return new StrikethroughTextContentNodeRenderer(context);
            }
        });
    }

    public static class Builder {

        private boolean requireTwoTildes = false;

        /**
         * @param requireTwoTildes Whether two tilde characters ({@code ~~}) are required for strikethrough or whether
         * one is also enough. Default is {@code false}; both a single tilde and two tildes can be used for strikethrough.
         * @return {@code this}
         */
        public Builder requireTwoTildes(boolean requireTwoTildes) {
            this.requireTwoTildes = requireTwoTildes;
            return this;
        }

        /**
         * @return a configured extension
         */
        public Extension build() {
            return new StrikethroughExtension(this);
        }
    }
}
