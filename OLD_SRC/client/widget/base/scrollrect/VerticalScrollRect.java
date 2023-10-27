package com.sleepwalker.sleeplib.client.widget.base.scrollrect;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.sleepwalker.sleeplib.client.utils.ISavable;
import com.sleepwalker.sleeplib.client.utils.SLGuiUtils;
import com.sleepwalker.sleeplib.client.widget.base.slider.VerticalSlider;
import com.sleepwalker.sleeplib.client.widget.core.BaseNestedWidget;
import com.sleepwalker.sleeplib.client.widget.core.IExtraNestedGuiEventHandler;
import com.sleepwalker.sleeplib.client.widget.core.IWidget;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.DoubleNBT;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public class VerticalScrollRect<T extends IWidget> extends BaseNestedWidget implements ISavable<CompoundNBT>, Iterable<T> {

    @Nonnull
    protected final Minecraft mc;

    private int scrollPanelHeight;

    @Nonnull
    protected final List<T> sortedElements;
    protected int focusY1, focusY2;

    @Nonnull
    protected final VerticalSlider slider;

    public VerticalScrollRect(@Nonnull List<T> sortedElements, @Nonnull VerticalSlider slider) {

        this.sortedElements = sortedElements;

        this.mc = Minecraft.getInstance();

        this.slider = slider;
        slider.setSliderListener(this::onSliderMove);
        slider.setBlocked(true);
    }

    public VerticalScrollRect() {
        this(new ArrayList<>(), new VerticalSlider());
    }

    public void clear(){

        slider.setValue(0);
        slider.setBlocked(true);

        scrollPanelHeight = 0;
        focusY1 = 0;
        focusY2 = 0;

        clearSortedElements();
    }

    protected void clearSortedElements(){
        sortedElements.forEach(t -> t.forceRemove(this));
        sortedElements.clear();
    }

    protected void defaultInitOnScreen(int posX, int posY, int width, int height, @Nonnull IExtraNestedGuiEventHandler parent){
        super.initOnScreen(posX, posY, width, height, parent);
    }

    @Override
    public void initOnScreen(int posX, int posY, int width, int height, @Nonnull IExtraNestedGuiEventHandler parent) {
        defaultInitOnScreen(posX, posY, width, height, parent);
        init(posX, posY, width, height, parent);
    }

    protected void init(int posX, int posY, int width, int height, @Nonnull IExtraNestedGuiEventHandler parent){

        slider.initSliderOnScreen(posX + width, posY, posEndY, posY, parent);

        sortedElements.forEach(t -> t.initOnScreen(this));

        recalculatePanel();
        recalculateElements();
    }

    @Nonnull
    public VerticalSlider getSlider() {
        return slider;
    }

    @Override
    public void forceRemove(@Nonnull IExtraNestedGuiEventHandler parent) {
        super.forceRemove(parent);
        slider.forceRemove(parent);
    }

    @Override
    public void setPosX(int posX) {
        super.setPosX(posX);

        recalculatePanel();
        recalculateElements();
    }

    @Override
    public void setPosY(int posY) {
        super.setPosY(posY);

        recalculatePanel();
        recalculateElements();
    }

    public void addScrollElement(int index, @Nonnull T element){

        sortedElements.add(index, element);

        recalculatePanel();
        recalculateElements();

        element.initOnScreen(this);
    }

    public void addScrollElement(@Nonnull T element){

        sortedElements.add(element);

        recalculatePanel();
        recalculateElements();

        element.initOnScreen(this);
    }

    public void addScrollElements(int index, @Nonnull Collection<? extends T> collection){

        sortedElements.addAll(index, collection);

        recalculatePanel();
        recalculateElements();

        collection.forEach(t -> t.initOnScreen(this));
    }

    public void addScrollElements(@Nonnull Collection<? extends T> collection){

        sortedElements.addAll(collection);

        recalculatePanel();
        recalculateElements();

        collection.forEach(t -> t.initOnScreen(this));
    }

    public void removeElement(@Nonnull T element){

        lastMouseFocused = null;

        if(sortedElements.remove(element)){
            element.forceRemove(this);
        }

        recalculatePanel();
        recalculateElements();
    }

    public void removeElements(@Nonnull Collection<T> elements){

        lastMouseFocused = null;

        sortedElements.removeAll(elements);

        recalculatePanel();
        recalculateElements();
    }

    @Override
    public boolean mouseScrolled(double mX, double mY, double delta) {

        if(super.mouseScrolled(mX, mY, delta)){
            return true;
        }

        if(slider.isBlocked()){
            return false;
        }

        int maxHeight = getScrollPanelHeight() - height;
        slider.setValue((slider.getValue() * maxHeight + -2 * delta) / maxHeight);

        return true;
    }

    protected void recalculatePanel(){

        scrollPanelHeight = sortedElements.stream().mapToInt(IWidget::getHeight).sum();

        slider.setBlocked(scrollPanelHeight <= height);

        for(T element : sortedElements){
            element.setPosX(posX);
        }
    }

    protected void onSliderMove(double value){
        recalculateElements();
    }

    protected void recalculateElements(){

        int relativeY = posY + (int)(-slider.getValue() * (getScrollPanelHeight() - height));

        boolean findY1 = false, findY2 = false;
        int i = 0;
        for(T element : sortedElements){

            element.setPosY(relativeY);

            relativeY += element.getHeight();

            if(!findY1){
                if(posY < element.getPosEndY()){
                    findY1 = true;
                    focusY1 = i;
                }
            }
            else if(!findY2 && posEndY < element.getPosY()){
                findY2 = true;
                focusY2 = i;
            }

            i++;
        }

        if(!findY2){
            focusY2 = sortedElements.size();
        }
    }

    @Override
    public void renderWidget(@Nonnull MatrixStack ms, int mX, int mY, float pt) {

        SLGuiUtils.glScissor(posX, posY, width, height, mc.getWindow());

        renderElements(ms, mX, mY, pt);

        GL11.glDisable(GL11.GL_SCISSOR_TEST);

        slider.render(ms, mX, mY, pt);
    }

    protected void renderElements(@Nonnull MatrixStack ms, int mouseX, int mouseY, float pt){

        for(int i = focusY1; i < focusY2; i++){
            sortedElements.get(i).render(ms, mouseX, mouseY, pt);
        }
    }

    @Nonnull
    public List<T> getSortedElements() {
        return sortedElements;
    }

    public int getScrollPanelHeight() {
        return scrollPanelHeight;
    }

    @Override
    public void readData(@Nonnull CompoundNBT nbt) {

        if(nbt.contains("slider")){
            slider.readData((DoubleNBT)nbt.get("slider"));
        }
    }

    @Nonnull
    @Override
    public CompoundNBT saveData() {

        CompoundNBT compoundNBT = new CompoundNBT();

        compoundNBT.put("slider", slider.saveData());

        return compoundNBT;
    }

    @Override
    public Iterator<T> iterator() {
        return sortedElements.iterator();
    }
}
