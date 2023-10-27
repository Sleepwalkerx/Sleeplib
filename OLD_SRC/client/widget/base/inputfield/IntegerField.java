package com.sleepwalker.sleeplib.client.widget.base.inputfield;

import net.minecraft.client.gui.screen.Screen;
import org.apache.commons.lang3.math.NumberUtils;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class IntegerField extends TextField {

    public static final String INFINITY_SYMBOL = "∞";
    private int number;

    private int maxValue = Integer.MAX_VALUE, minValue = Integer.MIN_VALUE;
    private boolean infinite;

    @Nullable
    private Consumer<Integer> numberConsumer;

    public IntegerField(){
        super();

        setFilter(s -> NumberUtils.isParsable(s) || s.length() == 0 || (infinite && s.equals(INFINITY_SYMBOL)));
        setResponder(s -> {

            if(s.length() == 0){
                setNewNumber(minValue);
                return;
            }
            else if(infinite && s.equals(INFINITY_SYMBOL)){
                setNewNumber(minValue);
                return;
            }

            try {

                int number = Integer.parseInt(s);

                if(number < minValue || number > maxValue){

                    if(infinite){
                        setValue(INFINITY_SYMBOL);
                    }
                    else {
                        setValue(String.valueOf(number < minValue ? minValue : maxValue));
                    }
                }
                else {
                    setNewNumber(number);
                }
            }
            catch (NumberFormatException ignore){
            }
        });
    }

    public boolean isInfinite() {
        return getValue().equals(INFINITY_SYMBOL);
    }

    private void setNewNumber(int number){

        this.number = number;

        if(numberConsumer != null){
            numberConsumer.accept(number);
        }
    }

    @Override
    public boolean mouseScrolled(double x, double y, double delta) {
        setValue(String.valueOf((number + (int)delta) * (Screen.hasControlDown() ? 10 : 1)));
        return true;
    }

    public void setNumberConsumer(@Nullable Consumer<Integer> numberConsumer) {
        this.numberConsumer = numberConsumer;
    }

    public void setNumber(int number) {
        setValue(String.valueOf(number));
    }

    public void setBoundaries(int maxValue, int minValue){
        this.maxValue = maxValue;
        this.minValue = minValue;
    }

    public void setInfinite(boolean infinite) {
        this.infinite = infinite;
    }

    public int getNumber() {
        return number;
    }
}
