package com.sleepwalker.sleeplib.client.wrap;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.util.text.ITextComponent;
import com.sleepwalker.sleeplib.client.widget.base.inputfield.TextField;
import com.sleepwalker.sleeplib.client.widget.core.IExtraNestedGuiEventHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class WrapSearchCollectionSelector<T> extends WrapCollectionSelector<T> {

    protected final TextField searchField;

    protected final Map<String, WrapElement> match, noMatch;

    protected int lastSearchSize;

    public WrapSearchCollectionSelector(
        @Nonnull ITextComponent title, @Nonnull Collection<T> values,
        @Nonnull Function<T, ITextComponent> displayNameSupplier, @Nullable T selected,
        @Nonnull BiConsumer<WrapCollectionSelector<T>, T> confirmConsumer
    ) {
        super(title, values, displayNameSupplier, selected, confirmConsumer);

        searchField = new TextField();
        searchField.setSize(scrollRect.getWidth() + scrollRect.getSlider().getWidth(), 20);

        scrollRect.setHeight(scrollRect.getHeight() - searchField.getHeight() - 4);

        match = new HashMap<>(values.size());
        scrollRect.forEach(wrapElement -> match.put(wrapElement.text.getText().getString().toLowerCase(Locale.ROOT), wrapElement));
        noMatch = new HashMap<>();

        searchField.setResponder(s -> {

            String str = s.toLowerCase(Locale.ROOT);

            if(str.length() > lastSearchSize){

                match.entrySet().removeIf(entry -> {

                    if(entry.getKey().contains(str)){
                        return false;
                    }
                    else {
                        noMatch.put(entry.getKey(), entry.getValue());
                        scrollRect.removeElement(entry.getValue());
                        return true;
                    }
                });
            }
            else {

                noMatch.entrySet().removeIf(entry -> {

                    if(entry.getKey().contains(str)){
                        match.put(entry.getKey(), entry.getValue());
                        scrollRect.addScrollElement(entry.getValue());
                        return true;
                    }
                    else {
                        return false;
                    }
                });
            }

            lastSearchSize = str.length();
        });
    }

    @Override
    public void initOnScreen(int posX, int posY, int width, int height, @Nonnull IExtraNestedGuiEventHandler parent) {
        super.initOnScreen(posX, posY, width, height, parent);

        searchField.initOnScreen(scrollRect.getPosX(), scrollRect.getPosY() - searchField.getHeight() - 4, this);
    }

    @Override
    public void renderWidget(@Nonnull MatrixStack ms, int mX, int mY, float pt) {
        super.renderWidget(ms, mX, mY, pt);

        searchField.render(ms, mX, mY, pt);
    }
}
