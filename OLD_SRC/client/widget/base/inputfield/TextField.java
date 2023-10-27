package com.sleepwalker.sleeplib.client.widget.base.inputfield;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.sleepwalker.sleeplib.client.SLSprites;
import com.sleepwalker.sleeplib.client.utils.ISavable;
import com.sleepwalker.sleeplib.client.widget.base.sprite.ISprite;
import com.sleepwalker.sleeplib.client.widget.core.BaseWidget;
import com.sleepwalker.sleeplib.client.widget.core.IExtraNestedGuiEventHandler;
import com.sleepwalker.sleeplib.client.widget.core.ILostFocusListener;
import com.sleepwalker.sleeplib.client.widget.core.ITickable;
import com.sleepwalker.sleeplib.client.widget.core.event.MouseAdapter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TranslationTextComponent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Predicate;

public class TextField extends BaseWidget implements ISavable<StringNBT>, ILostFocusListener, ITickable {

    protected final FontRenderer font;
    protected String value = "";
    private int maxLength = Integer.MAX_VALUE;
    protected int frame;
    private boolean bordered = true;
    private boolean canLoseFocus = true;
    protected boolean isEditable = true;
    private boolean shiftPressed;
    protected int displayPos;
    protected int cursorPos;
    protected int highlightPos;
    protected int textColor = 14737632;
    protected int textColorUneditable = 7368816;
    protected String suggestion;
    private Consumer<String> responder;
    private Predicate<String> filter = Objects::nonNull;
    protected BiFunction<String, Integer, IReorderingProcessor> formatter = (p_195610_0_, p_195610_1_) -> IReorderingProcessor.forward(p_195610_0_, Style.EMPTY);

    @Nonnull
    private final MouseAdapter mouseAdapter;

    protected long nextNarration = Long.MAX_VALUE;
    private ITextComponent message;
    private boolean focused;

    @Nonnull
    protected ISprite background = SLSprites.RECT_5;

    public TextField() {
        this.font = Minecraft.getInstance().font;
        mouseAdapter = new MouseAdapter(){
            @Override
            public void onMouseClicked(double mX, double mY, int bId) {
                if(canLoseFocus()){
                    setFocus(false);
                }
            }
        };
    }

    protected boolean canLoseFocus(){
        return isFocused() && !isMouseFocused();
    }

    @Override
    public void initOnScreen(int posX, int posY, int width, int height, @Nonnull IExtraNestedGuiEventHandler parent) {
        super.initOnScreen(posX, posY, width, height, parent);

        displayPos = 0;
        cursorPos = 0;
        highlightPos = 0;
        setValue(value);

        if(parent.getRoot() != null){
            parent.getRoot().getMouseListener().addListenerIfNot(mouseAdapter);
        }
    }

    @Override
    public void forceRemove(@Nonnull IExtraNestedGuiEventHandler parent) {
        super.forceRemove(parent);
        if(parent.getRoot() != null){
            parent.getRoot().getMouseListener().removeListener(mouseAdapter);
        }
    }

    @Override
    public void renderWidget(@Nonnull MatrixStack ms, int mX, int mY, float pt) {
        background.render(ms, posX, posY, width, height);
        renderText(ms, mX, mY, pt);
    }

    public void renderText(@Nonnull MatrixStack ms, int mX, int mY, float pt){
        int i2 = isEditable ? textColor : textColorUneditable;
        int j = cursorPos - displayPos;
        int k = highlightPos - displayPos;
        String s = font.plainSubstrByWidth(value.substring(displayPos), getInnerWidth());
        boolean flag = j >= 0 && j <= s.length();
        boolean flag1 = isFocused() && frame / 6 % 2 == 0 && flag;
        int l = posX + 4;
        int i1 = posY + (height - 8) / 2;
        int j1 = l;
        if (k > s.length()) {
            k = s.length();
        }

        if (!s.isEmpty()) {
            String s1 = flag ? s.substring(0, j) : s;
            j1 = font.drawShadow(ms, formatter.apply(s1, displayPos), (float)l, (float)i1, i2);
        }

        boolean flag2 = cursorPos < value.length() || value.length() >= getMaxLength();
        int k1 = j1;
        if (!flag) {
            k1 = j > 0 ? l + width : l;
        } else if (flag2) {
            k1 = j1 - 1;
            --j1;
        }

        if (!s.isEmpty() && flag && j < s.length()) {
            font.drawShadow(ms, this.formatter.apply(s.substring(j), cursorPos), (float)j1, (float)i1, i2);
        }

        if (!flag2 && suggestion != null) {
            font.drawShadow(ms, suggestion, (float)(k1 - 1), (float)i1, -8355712);
        }

        if (flag1) {
            if (flag2) {
                AbstractGui.fill(ms, k1, i1 - 1, k1 + 1, i1 + 1 + 9, -3092272);
            } else {
                font.drawShadow(ms, "_", (float)k1, (float)i1, i2);
            }
        }

        if (k != j) {
            int l1 = l + font.width(s.substring(0, k));
            renderHighlight(k1, i1 - 1, l1 - 1, i1 + 1 + 9);
        }

        narrate();
    }

    public boolean isFocused() {
        return this.focused;
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public void setActive(boolean active) {
        this.active = active;
    }

    private boolean mouseFocused;
    @Override
    public void setMouseFocused(boolean mouseFocused) {
        this.mouseFocused = mouseFocused;
    }

    @Override
    public boolean isMouseFocused() {
        return mouseFocused;
    }

    public void setBackground(@Nonnull ISprite background) {
        this.background = background;
    }

    @Nonnull
    @Override
    public StringNBT saveData() {
        return StringNBT.valueOf(value);
    }

    @Override
    public void readData(@Nonnull StringNBT nbt) {
        setValue(nbt.getAsString());
    }

    @Override
    public void onLostFocus() {
        setFocus(false);
    }

    public void setResponder(Consumer<String> pResponder) {
        this.responder = pResponder;
    }

    public void setFormatter(BiFunction<String, Integer, IReorderingProcessor> pTextFormatter) {
        this.formatter = pTextFormatter;
    }

    @Override
    public void tick(double mouseX, double mouseY) {
        ++this.frame;
    }

    protected IFormattableTextComponent createNarrationMessage() {
        ITextComponent itextcomponent = message;
        return new TranslationTextComponent("gui.narrate.editBox", itextcomponent, this.value);
    }

    public void setValue(String pText) {
        if (this.filter.test(pText)) {
            if (pText.length() > this.maxLength) {
                this.value = pText.substring(0, this.maxLength);
            } else {
                this.value = pText;
            }

            this.moveCursorToEnd();
            this.setHighlightPos(this.cursorPos);
            this.onValueChange(pText);
        }
    }

    public String getValue() {
        return this.value;
    }

    public String getHighlighted() {
        int i = this.cursorPos < this.highlightPos ? this.cursorPos : this.highlightPos;
        int j = this.cursorPos < this.highlightPos ? this.highlightPos : this.cursorPos;
        return this.value.substring(i, j);
    }

    public void setFilter(Predicate<String> pValidator) {
        this.filter = pValidator;
    }

    public void insertText(String pTextToWrite) {
        int i = this.cursorPos < this.highlightPos ? this.cursorPos : this.highlightPos;
        int j = this.cursorPos < this.highlightPos ? this.highlightPos : this.cursorPos;
        int k = this.maxLength - this.value.length() - (i - j);
        String s = SharedConstants.filterText(pTextToWrite);
        int l = s.length();
        if (k < l) {
            s = s.substring(0, k);
            l = k;
        }

        String s1 = (new StringBuilder(this.value)).replace(i, j, s).toString();
        if (this.filter.test(s1)) {
            this.value = s1;
            this.setCursorPosition(i + l);
            this.setHighlightPos(this.cursorPos);
            this.onValueChange(this.value);
        }
    }

    private void onValueChange(String pNewText) {
        if (this.responder != null) {
            this.responder.accept(pNewText);
        }

        this.nextNarration = Util.getMillis() + 500L;
    }

    private void deleteText(int p_212950_1_) {
        if (Screen.hasControlDown()) {
            this.deleteWords(p_212950_1_);
        } else {
            this.deleteChars(p_212950_1_);
        }

    }

    public void deleteWords(int pNum) {
        if (!this.value.isEmpty()) {
            if (this.highlightPos != this.cursorPos) {
                this.insertText("");
            } else {
                this.deleteChars(this.getWordPosition(pNum) - this.cursorPos);
            }
        }
    }

    public void deleteChars(int pNum) {
        if (!this.value.isEmpty()) {
            if (this.highlightPos != this.cursorPos) {
                this.insertText("");
            } else {
                int i = this.getCursorPos(pNum);
                int j = Math.min(i, this.cursorPos);
                int k = Math.max(i, this.cursorPos);
                if (j != k) {
                    String s = (new StringBuilder(this.value)).delete(j, k).toString();
                    if (this.filter.test(s)) {
                        this.value = s;
                        this.moveCursorTo(j);
                    }
                }
            }
        }
    }

    public int getWordPosition(int pNumWords) {
        return this.getWordPosition(pNumWords, this.getCursorPosition());
    }

    private int getWordPosition(int pN, int pPos) {
        return this.getWordPosition(pN, pPos, true);
    }

    private int getWordPosition(int pN, int pPos, boolean pSkipWs) {
        int i = pPos;
        boolean flag = pN < 0;
        int j = Math.abs(pN);

        for(int k = 0; k < j; ++k) {
            if (!flag) {
                int l = this.value.length();
                i = this.value.indexOf(32, i);
                if (i == -1) {
                    i = l;
                } else {
                    while(pSkipWs && i < l && this.value.charAt(i) == ' ') {
                        ++i;
                    }
                }
            } else {
                while(pSkipWs && i > 0 && this.value.charAt(i - 1) == ' ') {
                    --i;
                }

                while(i > 0 && this.value.charAt(i - 1) != ' ') {
                    --i;
                }
            }
        }

        return i;
    }

    public void moveCursor(int pDelta) {
        this.moveCursorTo(this.getCursorPos(pDelta));
    }

    private int getCursorPos(int pDelta) {
        return Util.offsetByCodepoints(this.value, this.cursorPos, pDelta);
    }

    public void moveCursorTo(int pPos) {
        this.setCursorPosition(pPos);
        if (!this.shiftPressed) {
            this.setHighlightPos(this.cursorPos);
        }

        this.onValueChange(this.value);
    }

    public void setCursorPosition(int pPos) {
        this.cursorPos = MathHelper.clamp(pPos, 0, this.value.length());
    }

    public void moveCursorToStart() {
        this.moveCursorTo(0);
    }

    public void moveCursorToEnd() {
        this.moveCursorTo(this.value.length());
    }

    public boolean keyPressed(int pKeyCode, int pScanCode, int pModifiers) {
        if (!this.canConsumeInput()) {
            return false;
        } else {
            this.shiftPressed = Screen.hasShiftDown();
            if (Screen.isSelectAll(pKeyCode)) {
                this.moveCursorToEnd();
                this.setHighlightPos(0);
                return true;
            } else if (Screen.isCopy(pKeyCode)) {
                Minecraft.getInstance().keyboardHandler.setClipboard(this.getHighlighted());
                return true;
            } else if (Screen.isPaste(pKeyCode)) {
                if (this.isEditable) {
                    this.insertText(Minecraft.getInstance().keyboardHandler.getClipboard());
                }

                return true;
            } else if (Screen.isCut(pKeyCode)) {
                Minecraft.getInstance().keyboardHandler.setClipboard(this.getHighlighted());
                if (this.isEditable) {
                    this.insertText("");
                }

                return true;
            } else {
                switch(pKeyCode) {
                    case 259:
                        if (this.isEditable) {
                            this.shiftPressed = false;
                            this.deleteText(-1);
                            this.shiftPressed = Screen.hasShiftDown();
                        }

                        return true;
                    case 260:
                    case 264:
                    case 265:
                    case 266:
                    case 267:
                    default:
                        return false;
                    case 261:
                        if (this.isEditable) {
                            this.shiftPressed = false;
                            this.deleteText(1);
                            this.shiftPressed = Screen.hasShiftDown();
                        }

                        return true;
                    case 262:
                        if (Screen.hasControlDown()) {
                            this.moveCursorTo(this.getWordPosition(1));
                        } else {
                            this.moveCursor(1);
                        }

                        return true;
                    case 263:
                        if (Screen.hasControlDown()) {
                            this.moveCursorTo(this.getWordPosition(-1));
                        } else {
                            this.moveCursor(-1);
                        }

                        return true;
                    case 268:
                        this.moveCursorToStart();
                        return true;
                    case 269:
                        this.moveCursorToEnd();
                        return true;
                }
            }
        }
    }

    public boolean canConsumeInput() {
        return isActive() && this.isFocused() && this.isEditable();
    }

    public boolean charTyped(char pCodePoint, int pModifiers) {
        if (!this.canConsumeInput()) {
            return false;
        } else if (SharedConstants.isAllowedChatCharacter(pCodePoint)) {
            if (this.isEditable) {
                this.insertText(Character.toString(pCodePoint));
            }

            return true;
        } else {
            return false;
        }
    }

    public boolean mouseClicked(double pMouseX, double pMouseY, int pButton) {
        if (!isActive()) {
            return false;
        } else {
            boolean flag = pMouseX >= (double)posX && pMouseX < (double)(posX + this.width) && pMouseY >= (double)posY && pMouseY < (double)(posY + this.height);
            if (this.canLoseFocus) {
                this.setFocus(flag);
            }

            if (this.isFocused() && flag && pButton == 0) {
                int i = MathHelper.floor(pMouseX) - posX;
                if (this.bordered) {
                    i -= 4;
                }

                String s = this.font.plainSubstrByWidth(this.value.substring(this.displayPos), this.getInnerWidth());
                this.moveCursorTo(this.font.plainSubstrByWidth(s, i).length() + this.displayPos);
                return true;
            } else {
                return false;
            }
        }
    }

    public void setFocus(boolean pIsFocused) {
        focused = pIsFocused;
    }

    protected void narrate() {
        if (this.active && isMouseFocused() && Util.getMillis() > this.nextNarration) {
            String s = this.createNarrationMessage().getString();
            if (!s.isEmpty()) {
                NarratorChatListener.INSTANCE.sayNow(s);
                this.nextNarration = Long.MAX_VALUE;
            }
        }

    }

    protected void renderHighlight(int pStartX, int pStartY, int pEndX, int pEndY) {
        if (pStartX < pEndX) {
            int i = pStartX;
            pStartX = pEndX;
            pEndX = i;
        }

        if (pStartY < pEndY) {
            int j = pStartY;
            pStartY = pEndY;
            pEndY = j;
        }

        if (pEndX > posX + this.width) {
            pEndX = posX + this.width;
        }

        if (pStartX > posX + this.width) {
            pStartX = posX + this.width;
        }

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuilder();
        RenderSystem.color4f(0.0F, 0.0F, 255.0F, 255.0F);
        RenderSystem.disableTexture();
        RenderSystem.enableColorLogicOp();
        RenderSystem.logicOp(GlStateManager.LogicOp.OR_REVERSE);
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION);
        bufferbuilder.vertex((double)pStartX, (double)pEndY, 0.0D).endVertex();
        bufferbuilder.vertex((double)pEndX, (double)pEndY, 0.0D).endVertex();
        bufferbuilder.vertex((double)pEndX, (double)pStartY, 0.0D).endVertex();
        bufferbuilder.vertex((double)pStartX, (double)pStartY, 0.0D).endVertex();
        tessellator.end();
        RenderSystem.disableColorLogicOp();
        RenderSystem.enableTexture();
    }

    public void setMaxLength(int pLength) {
        this.maxLength = pLength;
        if (this.value.length() > pLength) {
            this.value = this.value.substring(0, pLength);
            this.onValueChange(this.value);
        }

    }

    protected int getMaxLength() {
        return this.maxLength;
    }

    public int getCursorPosition() {
        return this.cursorPos;
    }

    private boolean isBordered() {
        return this.bordered;
    }

    public void setBordered(boolean pEnableBackgroundDrawing) {
        this.bordered = pEnableBackgroundDrawing;
    }

    public void setTextColor(int pColor) {
        this.textColor = pColor;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColorUneditable(int pColor) {
        this.textColorUneditable = pColor;
    }

    public int getTextColorUneditable() {
        return textColorUneditable;
    }

    public boolean changeFocus(boolean pFocus) {
        return isActive() && this.isEditable ? super.changeFocus(pFocus) : false;
    }

    public boolean isMouseOver(double pMouseX, double pMouseY) {
        return isActive() && pMouseX >= (double)posX && pMouseX < (double)(posX + this.width) && pMouseY >= (double)posY && pMouseY < (double)(posY + this.height);
    }

    protected void onFocusedChanged(boolean pFocused) {
        if (pFocused) {
            this.frame = 0;
        }

    }

    private boolean isEditable() {
        return this.isEditable;
    }

    public void setEditable(boolean pEnabled) {
        this.isEditable = pEnabled;
    }

    public int getInnerWidth() {
        return this.isBordered() ? this.width - 8 : this.width;
    }

    public void setHighlightPos(int pPosition) {
        int i = this.value.length();
        this.highlightPos = MathHelper.clamp(pPosition, 0, i);
        if (this.font != null) {
            if (this.displayPos > i) {
                this.displayPos = i;
            }

            int j = this.getInnerWidth();
            String s = this.font.plainSubstrByWidth(this.value.substring(this.displayPos), j);
            int k = s.length() + this.displayPos;
            if (this.highlightPos == this.displayPos) {
                this.displayPos -= this.font.plainSubstrByWidth(this.value, j, true).length();
            }

            if (this.highlightPos > k) {
                this.displayPos += this.highlightPos - k;
            } else if (this.highlightPos <= this.displayPos) {
                this.displayPos -= this.displayPos - this.highlightPos;
            }

            this.displayPos = MathHelper.clamp(this.displayPos, 0, i);
        }

    }

    public void setCanLoseFocus(boolean pCanLoseFocus) {
        this.canLoseFocus = pCanLoseFocus;
    }

    public void setSuggestion(@Nullable String pSuggestion) {
        this.suggestion = pSuggestion;
    }

    public int getScreenX(int p_195611_1_) {
        return p_195611_1_ > this.value.length() ? posX : posY + this.font.width(this.value.substring(0, p_195611_1_));
    }
}
