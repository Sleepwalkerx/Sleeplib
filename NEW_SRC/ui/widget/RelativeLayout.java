package com.sleepwalker.sleeplib.ui.widget;

import com.sleepwalker.sleeplib.ui.property.Properties;

public class RelativeLayout extends BaseLayoutGroupWidget {

    @Override
    public void updateLayout() {

        for(Widget child : getChildren()){

            child.updateSize();

            float biasX = child.getPropertyValue(Properties.BIAS_X);
            float biasY = child.getPropertyValue(Properties.BIAS_Y);

            float pX = getX() + (biasX * getWidth()) - (biasX * child.getWidth());
            float pY = getY() + (biasY * getHeight()) - (biasY * child.getHeight());

            child.setPosition(
                pX + child.getPropertyValue(Properties.MARGIN_LEFT) - child.getPropertyValue(Properties.MARGIN_RIGHT),
                pY + child.getPropertyValue(Properties.MARGIN_TOP) - child.getPropertyValue(Properties.MARGIN_BOTTOM)
            );
        }
    }
}
