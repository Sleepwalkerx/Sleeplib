package com.sleepwalker.sleeplib.client.wrap.objgrid;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.sleepwalker.sleeplib.client.utils.SLGuiUtils;
import com.sleepwalker.sleeplib.client.widget.base.button.Button;
import com.sleepwalker.sleeplib.client.widget.base.button.DisposableTextButton;
import com.sleepwalker.sleeplib.client.widget.core.ITooltipRenderable;
import com.sleepwalker.sleeplib.client.widget.grouped.NamedSwitcher;
import com.sleepwalker.sleeplib.client.wrap.widget.BaseWrapNestedCanvas;
import com.sleepwalker.sleeplib.client.wrap.widget.WCCloseButton;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.opengl.GL11;
import com.sleepwalker.sleeplib.client.SLSprites;
import com.sleepwalker.sleeplib.client.widget.base.button.SelectableButton;
import com.sleepwalker.sleeplib.client.widget.base.inputfield.TextField;
import com.sleepwalker.sleeplib.client.widget.base.scrollrect.GridLine;
import com.sleepwalker.sleeplib.client.widget.base.scrollrect.GridRectVertical;
import com.sleepwalker.sleeplib.client.widget.base.sprite.ISprite;
import com.sleepwalker.sleeplib.client.widget.core.IExtraNestedGuiEventHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

@OnlyIn(Dist.CLIENT)
public class WrapObjectGrid<T extends Button & IWrapObjectCell> extends BaseWrapNestedCanvas {

    @Nonnull
    protected final SelectableGridRect gridRect;
    @Nonnull
    private final ButtonContentProvider inventoryButton, forgeRegButton;
    @Nonnull
    private final NamedSwitcher chosenFirst;
    @Nonnull
    private final DisposableTextButton confirm;
    @Nonnull
    private final TextField matchField;
    @Nonnull
    private final WCCloseButton closeButton;

    private int maxSelectedCount = Integer.MAX_VALUE;
    private boolean overflowSwitch = false;
    @Nonnull
    private final List<T> selectedObjects;

    @Nullable
    private ITooltipRenderable tooltipRender;

    @Nonnull
    private ButtonContentProvider selectedButton;
    private final List<T> lastMatches = new ArrayList<>();
    @Nonnull
    private final List<T> lastNonMatches = new ArrayList<>();
    private int lastMatchFieldLength;

    @Nonnull
    private final Consumer<WrapObjectGrid<T>> endConsumer;
    @Nonnull
    private final Function<WrapObjectGrid<T>, Collection<T>> providerListener;

    public WrapObjectGrid(@Nonnull Function<WrapObjectGrid<T>, Collection<T>> providerListener, @Nonnull Consumer<WrapObjectGrid<T>> endConsumer){

        this.endConsumer = endConsumer;
        this.providerListener = providerListener;
        this.selectedObjects = new ArrayList<>();

        chosenFirst = new NamedSwitcher(new StringTextComponent("chosen first"));
        chosenFirst.getSwitcher().setClickListener(this::onChosenFirst);

        sortLastMatches();

        setSize(235, 150);

        closeButton = createCloseButton();

        gridRect = new SelectableGridRect();
        gridRect.setSize(222 - gridRect.getSlider().getWidth(), 96);

        inventoryButton = new ButtonContentProvider(ContentProvider.FORGE_REGISTRY);
        forgeRegButton = new ButtonContentProvider(ContentProvider.INVENTORY);

        matchField = new TextField();
        matchField.setSize(130, 18);
        matchField.setResponder(this::onMatchFieldChanged);

        confirm = new DisposableTextButton(new StringTextComponent("ok"));
        confirm.setSize(37, 18);
        confirm.setClickListener(this::onConfirmClick);

        selectedButton = inventoryButton; // ._.
        changeProvider(forgeRegButton);
    }

    public void setMaxSelectedCount(int maxSelectedCount) {
        this.maxSelectedCount = maxSelectedCount;
    }

    public void setOverflowSwitch(boolean overflowSwitch) {
        this.overflowSwitch = overflowSwitch;
    }

    private void sortLastMatches(){
        lastMatches.sort((obj1, obj2) -> {
            if(chosenFirst.isEnabled()){
                if(obj1.isSelected() == obj2.isSelected()){
                    return obj1.getSortedID() - obj2.getSortedID();
                }
                else return obj1.isSelected() ? -1 : 1;
            }
            else return obj1.getSortedID() - obj2.getSortedID();
        });
    }

    protected void changeProvider(@Nonnull ButtonContentProvider buttonProvider){

        buttonProvider.selected = true;
        selectedButton.selected = false;
        selectedButton = buttonProvider;

        Collection<T> newObjects = providerListener.apply(this);

        lastNonMatches.clear();
        lastMatches.clear();

        List<T> oldSelected = new ArrayList<>(selectedObjects);

        newObjects.forEach(t -> {

            int index = selectedObjects.indexOf(t);

            if(index != -1){
                oldSelected.remove(t);
                selectedObjects.set(index, t);
                t.setSelected(true);
            }
        });
        lastMatches.addAll(newObjects);
        lastMatches.addAll(oldSelected);

        gridRect.clear();
        gridRect.addGridElements(lastMatches);

        onMatchFieldChanged(matchField.getValue());
    }

    private void onMatchFieldChanged(@Nonnull String text){

        if(text.length() != 0 && (text.length() != 1 || text.charAt(0) != '@')){

            String tx;
            BiFunction<T, String, Boolean> function;

            if(text.charAt(0) == '@'){
                tx = text.substring(1);
                function = ((cell, str) -> cell.tagMatches(str));
            }
            else {
                tx = text;
                function = ((cell, str) -> cell.textMatches(str));
            }

            int lastMatchesSize = lastMatches.size();

            if(lastMatchFieldLength < text.length() || lastMatchFieldLength == text.length()){

                lastMatches.removeIf(cell -> {
                    if(!cell.isSelected() && !function.apply(cell, tx)){
                        lastNonMatches.add(cell);
                        cell.setActive(false);
                        return true;
                    }
                    else return false;
                });
            }
            else {

                lastNonMatches.removeIf(cell -> {
                    if(cell.isSelected() || function.apply(cell, tx)){
                        lastMatches.add(cell);
                        cell.setActive(true);
                        return true;
                    }
                    else return false;
                });
            }

            if(lastMatchesSize != lastMatches.size()){
                sortLastMatches();
                gridRect.clear();
                gridRect.addGridElements(lastMatches);
            }
        }
        else if(lastNonMatches.size() != 0) {

            lastNonMatches.forEach(t -> t.setActive(true));
            sortLastMatches();
            lastMatches.addAll(lastNonMatches);
            lastNonMatches.clear();

            gridRect.clear();
            gridRect.addGridElements(lastMatches);
        }

        lastMatchFieldLength = text.length();
    }

    private void onConfirmClick(Button button, int id){
        endConsumer.accept(this);
        wrapHandler.deactivateWrapCanvas();
    }

    private void onChosenFirst(Button button, int id){
        resortMatches();
    }

    private void resortMatches(){

        sortLastMatches();

        double lastSliderValue = gridRect.getSlider().getValue();
        gridRect.clear();
        gridRect.addGridElements(lastMatches);
        gridRect.getSlider().setValue(lastSliderValue);
    }

    @Override
    public void initOnScreen(int posX, int posY, int width, int height, @Nonnull IExtraNestedGuiEventHandler parent) {
        super.initOnScreen(posX, posY, width, height, parent);

        gridRect.initOnScreen(posX + 6, posY + 26, this);

        inventoryButton.initOnScreen(posX + 4, posY + 4, this);
        forgeRegButton.initOnScreen(inventoryButton.getPosEndX() + 2, posY + 4, this);
        matchField.initOnScreen(forgeRegButton.getPosEndX() + 2, posY + 4, this);

        chosenFirst.initOnScreen(posX + 4, posY + 127, this);

        confirm.initOnScreen(posEndX - confirm.getWidth() - 7, posEndY - confirm.getHeight() - 5, this);
        closeButton.defaultInitOnScreen(this);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void renderWidget(@Nonnull MatrixStack ms, int mX, int mY, float pt) {

        SLSprites.RECT_3.render(ms, posX, posY, width, height);
        SLSprites.SCROLL_RECT_BACKGROUND.render(ms, gridRect.getPosX() - 2, gridRect.getPosY() - 2,
            gridRect.getWidth() + 4 + gridRect.getSlider().getWidth(),
            gridRect.getHeight() + 4
        );

        GL11.glClear(GL11.GL_DEPTH_BUFFER_BIT);
        gridRect.render(ms, mX, mY, pt);

        if(gridRect.getLastMouseFocused() != null && ((GridLine<T>)gridRect.getLastMouseFocused()).getLastMouseFocused() instanceof ITooltipRenderable){
            tooltipRender = (ITooltipRenderable) ((GridLine<T>)gridRect.getLastMouseFocused()).getLastMouseFocused();
        }
        else if(tooltipRender != null){
            tooltipRender = null;
        }

        inventoryButton.render(ms, mX, mY, pt);
        forgeRegButton.render(ms, mX, mY, pt);
        chosenFirst.render(ms, mX, mY, pt);
        confirm.render(ms, mX, mY, pt);
        matchField.render(ms, mX, mY, pt);
        closeButton.render(ms, mX, mY, pt);

        if(tooltipRender != null){
            tooltipRender.renderTooltips(ms, mX, mY, pt);
        }
    }

    @Nonnull
    public ContentProvider contentProvider(){
        return selectedButton.provider;
    }

    @Nonnull
    public List<T> getSelectedObjects() {
        return selectedObjects;
    }

    private class ButtonContentProvider extends SelectableButton {

        private boolean selected;

        @Nonnull
        private final ContentProvider provider;

        public ButtonContentProvider(@Nonnull ContentProvider provider) {
            this.provider = provider;

            setSize(SLSprites.MODE_SWITCHER_CELL.getWidth(), SLSprites.MODE_SWITCHER_CELL.getHeight());
        }

        @Override
        public boolean mouseReleased(double x, double y, int button) {

            super.mouseReleased(x, y, button);

            if(!selected){
                changeProvider(this);
            }

            return true;
        }

        @Override
        public void renderWidget(@Nonnull MatrixStack ms, int x, int y, float pt) {
            super.renderWidget(ms, x, y, pt);

            SLSprites.MODE_SWITCHER_CELL.render(ms, posX, posY, selected ? 1 : 0);

            if(isMouseFocused()){
                SLGuiUtils.drawHoleRect(ms.last().pose(), 0, posX, posY, width, height, 1, 0xeeffffff);
            }

            provider.icon.render(ms, posX + 3, posY + 3);
        }

        @Override
        public void setSelected(boolean selected) {
            this.selected = selected;
        }

        @Override
        public boolean isSelected() {
            return selected;
        }
    }

    private class SelectableGridRect extends GridRectVertical<T> {

        @Override
        public void addGridElements(@Nonnull Iterable<? extends T> iterable) {
            super.addGridElements(iterable);

            for (T t : iterable){
                t.setClickListener(this::onCellClick);
            }
        }

        @Override
        public void addGridElement(@Nonnull T element) {
            super.addGridElement(element);
            element.setClickListener(this::onCellClick);
        }

        @SuppressWarnings("unchecked")
        private void onCellClick(@Nonnull Button button, int id){

            if(id != 0){
                return;
            }

            T buttonType = (T) button;

            if(maxSelectedCount <= selectedObjects.size()){

                if(overflowSwitch){

                    if(!selectedObjects.isEmpty()){
                        selectedObjects.remove(selectedObjects.size() - 1).setSelected(false);
                    }
                }
                else {
                    return;
                }
            }

            buttonType.setSelected(!buttonType.isSelected());

            if(buttonType.isSelected()){
                selectedObjects.add(buttonType);
            }
            else {
                selectedObjects.remove(buttonType);
            }

            if(chosenFirst.isEnabled()){
                resortMatches();
            }
        }
    }

    public enum ContentProvider {
        FORGE_REGISTRY(SLSprites.BOOK_ICON),
        INVENTORY(SLSprites.CHEST_ICON);

        public final ISprite icon;

        ContentProvider(ISprite icon) {
            this.icon = icon;
        }
    }
}
