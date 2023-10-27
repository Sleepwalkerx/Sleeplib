package com.sleepwalker.sleeplib.ui.widget;

import com.sleepwalker.sleeplib.ui.layout.RectSide;
import com.sleepwalker.sleeplib.ui.property.*;
import com.sleepwalker.sleeplib.ui.xml.DeserializeContext;
import com.sleepwalker.sleeplib.util.exception.XmlParseException;
import org.w3c.dom.Attr;

import javax.annotation.Nonnull;
import java.util.Locale;

public class ConstraintLayout extends BaseLayoutGroupWidget {

    @Nonnull public static final BaseProperty.DefaultValueSupplier<Widget, SideAttach> DEFAULT_ATTACH_LEFT = BaseProperty.constDefaultValue(new SideAttach(WidgetReferences.parent(), RectSide.LEFT));
    @Nonnull public static final BaseProperty.DefaultValueSupplier<Widget, SideAttach> DEFAULT_ATTACH_RIGHT = BaseProperty.constDefaultValue(new SideAttach(WidgetReferences.parent(), RectSide.RIGHT));
    @Nonnull public static final BaseProperty.DefaultValueSupplier<Widget, SideAttach> DEFAULT_ATTACH_TOP = BaseProperty.constDefaultValue(new SideAttach(WidgetReferences.parent(), RectSide.TOP));
    @Nonnull public static final BaseProperty.DefaultValueSupplier<Widget, SideAttach> DEFAULT_ATTACH_BOTTOM = BaseProperty.constDefaultValue(new SideAttach(WidgetReferences.parent(), RectSide.BOTTOM));

    @Override
    public void updateLayout() {

        for(Widget child : getChildren()){

            child.updateSize();

            SideAttach leftSideAttach = child.getPropertyValue(Properties.CONSTRAINT_ATTACH_LEFT);
            SideAttach rightSideAttach = child.getPropertyValue(Properties.CONSTRAINT_ATTACH_RIGHT);
            Widget leftAttach = leftSideAttach.getReference().getFrom(child);
            Widget rightAttach = rightSideAttach.getReference().getFrom(child);

            float pX;
            if(leftAttach != null && rightAttach != null){

                float biasH = child.getPropertyValue(Properties.BIAS_X);
                float leftPos = getSidePos(leftAttach, leftSideAttach.getSide());
                float rightPos = getSidePos(rightAttach, rightSideAttach.getSide());

                float bX = leftPos + Math.abs(rightPos - leftPos) * biasH - (child.getWidth() * biasH);
                pX = bX + child.getPropertyValue(Properties.MARGIN_LEFT) - child.getPropertyValue(Properties.MARGIN_RIGHT);
            }
            else if(leftAttach != null) {
                pX = getSidePos(leftAttach, leftSideAttach.getSide()) + child.getPropertyValue(Properties.MARGIN_LEFT);
            }
            else if(rightAttach != null) {
                pX = getSidePos(rightAttach, rightSideAttach.getSide()) - child.getPropertyValue(Properties.MARGIN_RIGHT);
            }
            else {
                pX = getX();
            }

            SideAttach topSideAttach = child.getPropertyValue(Properties.CONSTRAINT_ATTACH_TOP);
            SideAttach bottomSideAttach = child.getPropertyValue(Properties.CONSTRAINT_ATTACH_BOTTOM);
            Widget topAttach = topSideAttach.getReference().getFrom(child);
            Widget bottomAttach = bottomSideAttach.getReference().getFrom(child);

            float pY;
            if(topAttach != null && bottomAttach != null){

                float biasV = child.getPropertyValue(Properties.BIAS_Y);
                float topPos = getSidePos(topAttach, topSideAttach.getSide());
                float bottomPos = getSidePos(bottomAttach, bottomSideAttach.getSide());

                float bY = topPos + Math.abs(bottomPos - topPos) * biasV + - (child.getHeight() * biasV);
                pY = bY + child.getPropertyValue(Properties.MARGIN_TOP) - child.getPropertyValue(Properties.MARGIN_BOTTOM);
            }
            else if(topAttach != null) {
                pY = getSidePos(topAttach, topSideAttach.getSide()) + child.getPropertyValue(Properties.MARGIN_TOP);
            }
            else if(bottomAttach != null) {
                pY = getSidePos(bottomAttach, bottomSideAttach.getSide()) - child.getPropertyValue(Properties.MARGIN_BOTTOM);
            }
            else {
                pY = getY();
            }

            child.setPosition(pX, pY);
        }
    }

    private float getSidePos(@Nonnull Widget attach, @Nonnull RectSide side){
        switch (side){
            case LEFT:
                return attach.getX();
            case RIGHT:
                return attach.getX() + attach.getWidth();
            case TOP:
                return attach.getY();
            case BOTTOM:
                return attach.getY() + attach.getHeight();
        }
        return 0;
    }

    public static class SideAttach {

        @Nonnull private final WidgetReference reference;
        @Nonnull private final RectSide side;

        public SideAttach(@Nonnull WidgetReference reference, @Nonnull RectSide side) {
            this.reference = reference;
            this.side = side;
        }

        @Nonnull
        public WidgetReference getReference() {
            return reference;
        }

        @Nonnull
        public RectSide getSide() {
            return side;
        }
    }

    public static class SideAttachProperty extends BaseProperty<Widget, SideAttach> {

        public SideAttachProperty(@Nonnull DefaultValueSupplier<Widget, SideAttach> defaultValueSupplier) {
            super(defaultValueSupplier, Widget.class, Properties.updateLayout());
        }

        @Nonnull
        @Override
        public SideAttach fromXml(@Nonnull Attr attr, @Nonnull Widget host, @Nonnull DeserializeContext context) throws XmlParseException {
            String[] args = attr.getValue().split(" ", 2);
            WidgetReference reference = WidgetReferenceProperty.fromString(args[0], host);
            RectSide side = RectSide.valueOf(args[1].toUpperCase(Locale.ROOT));
            return new SideAttach(reference, side);
        }

        @Nonnull
        @Override
        public Class<SideAttach> getValueType() {
            return SideAttach.class;
        }
    }
}
