package com.sleepwalker.sleeplib.ui.property;

import com.sleepwalker.sleeplib.ui.widget.Widget;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public final class WidgetReferences {

    @Nonnull private static final WidgetReference PARENT = Widget::getParent;

    @Nonnull
    public static WidgetReference parent() {
        return PARENT;
    }

    @Nonnull
    public static WidgetReference parentChildIndex(int index){
        return new ParentChildIndex(index);
    }

    @Nonnull
    public static WidgetReference parentChildName(@Nonnull String name){
        return new ParentChildName(name);
    }

    public static class ParentChildIndex implements WidgetReference {

        private final int index;

        public ParentChildIndex(int index) {
            this.index = index;
        }

        @Nullable
        @Override
        public Widget getFrom(@Nonnull Widget target) {
            return target.getParent().getChildAt(index);
        }
    }

    public static class ParentChildName implements WidgetReference {

        @Nonnull private final String name;

        public ParentChildName(@Nonnull String name) {
            this.name = name;
        }

        @Nullable
        @Override
        public Widget getFrom(@Nonnull Widget target) {
            return target.getParent().getChildByName(name);
        }
    }

    public static class ParentChildId implements WidgetReference {

        @Nonnull private final String id;

        public ParentChildId(@Nonnull String id) {
            this.id = id;
        }

        @Nullable
        @Override
        public Widget getFrom(@Nonnull Widget target) {
            return target.getParent().getChildById(id);
        }
    }
}
