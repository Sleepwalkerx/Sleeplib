package com.sleepwalker.sleeplib.elementa.components

import com.sleepwalker.sleeplib.elementa.drawable.Drawables
import com.sleepwalker.sleeplib.gg.essential.elementa.UIComponent
import com.sleepwalker.sleeplib.gg.essential.elementa.components.ScrollComponent
import com.sleepwalker.sleeplib.gg.essential.elementa.components.UIContainer
import com.sleepwalker.sleeplib.gg.essential.elementa.components.UIText
import com.sleepwalker.sleeplib.gg.essential.elementa.components.input.AbstractTextInput
import com.sleepwalker.sleeplib.gg.essential.elementa.components.input.UITextInput
import com.sleepwalker.sleeplib.gg.essential.elementa.constraints.*
import com.sleepwalker.sleeplib.gg.essential.elementa.constraints.animation.Animations
import com.sleepwalker.sleeplib.gg.essential.elementa.dsl.*
import com.sleepwalker.sleeplib.gg.essential.universal.UMatrixStack
import com.sleepwalker.sleeplib.integration.IntegrationHelper
import com.sleepwalker.sleeplib.integration.jei.SleepLibJeiPlugin
import com.sleepwalker.sleeplib.translations.SleepLibTranslations
import com.sleepwalker.sleeplib.util.text.getStringWithFormatting
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.client.Minecraft
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.item.Items
import net.minecraftforge.registries.ForgeRegistries
import java.awt.Color
import kotlin.math.ceil

open class UIElementSelector<T> @JvmOverloads constructor(
    private val provider: ObjectProvider<T>,
    private val maxLineItems: Int = 9,
    private val maxSelectCount: Int = 1,
    private val instantChoice: Boolean = true
) : UIDrawable(Drawables.WINDOW_BACKGROUND) {

    private val sources: Set<UISource>
    private val search: AbstractTextInput
    private val scrollPanel: ScrollComponent
    private val cells: MutableMap<UICell, T> = LinkedHashMap()
    private val selectButton: UIComponent?

    private val selectHandlers = mutableListOf<(List<T>) -> Unit>()

    init {
        constrain {
            width = (19 + 18 * maxLineItems).pixel
            height = 150.pixel
        }

        val header = UIContainer().constrain {
            x = 4.pixel
            y = 4.pixel
            width = 100.percent - 2.pixel
            height = 18.pixel
        } childOf this

        val sources = mutableSetOf<UISource>()
        for (source in ObjectSource.entries){
            if(source.supported){
                UISource(source).constrain {
                    x = SiblingConstraint()
                    width = AspectConstraint()
                    height = 100.percent
                }.apply {
                    sources.add(this)
                }.onMouseClick {
                    changeSource((this as UISource).source)
                } childOf header
            }
        }
        this.sources = sources.toSet()

        UIDrawable(Drawables.FIELD_BACKGROUND).constrain {
            x = SiblingConstraint(2f)
            width = FillConstraint() - 8.pixel
            height = 100.percent
        }.addChild(UITextInput().constrain {
            x = CenterConstraint()
            y = CenterConstraint()
            width = 100.percent - 8.pixel
        }.onUpdate {
            onSearch()
        }.apply {
            search = this
        }.onMouseClick {
            this.grabWindowFocus()
        }) childOf header

        val scrollBackground = UIDrawable(Drawables.SCROLL_BACKGROUND).constrain {
            x = CenterConstraint()
            y = SiblingConstraint(2f)
            width = RelativeConstraint(1f) - 8.pixel
            height = FillConstraint() - 10.pixel
        } childOf this

        val scrollBody = UIContainer().constrain {
            x = CenterConstraint()
            y = CenterConstraint()
            width = 100.percent - 4.pixel
            height = 100.percent - 4.pixel
        } childOf scrollBackground

        val sliderPanel = UIContainer().constrain {
            x = PixelConstraint(0f, true)
            width = 6.pixel
            height = 100.percent
        } childOf scrollBody

        scrollPanel = ScrollComponent().constrain {
            width = FillConstraint()
            height = 100.percent
        } childOf scrollBody

        scrollPanel.setVerticalScrollBarComponent(UIButton(Styles.VERTICAL_SLIDER) childOf sliderPanel)
        scrollPanel.verticalScrollBarMaxSize = Drawables.VERTICAL_SLIDER_ENABLED.height.pixel

        if(!instantChoice){
            selectButton = UIDisposableButton(Styles.DISPOSABLE_BUTTON, SleepLibTranslations.widget("element_selector.select").getStringWithFormatting()).constrain {
                x = CenterConstraint()
                y = PixelConstraint(4f, true)
                width = 100.percent - 8.pixel
                height = 20.pixel
            }.onMouseClick {
                val selectedList = cells.keys.filter { it.selected }.map { it.value }
                if(selectedList.isNotEmpty()){
                    onSelectedInternal(selectedList)
                }
            } childOf this
            selectButton.active = false
        }
        else {
            selectButton = null
        }

        changeSource(ObjectSource.GAME_REGISTRY)
    }

    protected open fun onSelectedInternal(list: List<T>){
        for (handler in selectHandlers){
            handler(list)
        }
    }

    fun onSelected(handler: (List<T>) -> Unit) = apply {
        selectHandlers.add(handler)
    }

    private var searchDelayTimer: Int? = null
    private fun onSearch() {
        if (searchDelayTimer != null) {
            stopDelay(searchDelayTimer!!)
        }
        searchDelayTimer = null
        searchDelayTimer = startDelay(400) { startSearch() }
    }

    private fun changeSource(source: ObjectSource){
        sources.forEach { it.setSelected(false) }
        sources.find { it.source === source }?.let {
            it.setSelected(true)
            cells.clear()
            scrollPanel.clearChildren()
            val elements = provider.getObjects(source)
            var holder: UIItemLine? = null
            for (element in elements){
                if (holder == null || holder.childrenList.size == (maxLineItems * 32)) {
                    holder?.bake()
                    holder = UIItemLine()
                }

                val cell = UICell(element, holder)
                cell.hide(true)
                holder.childrenList.add(cell)
                cells[cell] = element
            }
            holder?.bake()
        }
        startSearch()
    }

    private fun startSearch() {
        scrollPanel.clearChildren()
        val name: String = search.getText().lowercase()
        var holder: UIItemLine? = null
        for ((widget, element) in cells) {
            val elementName = provider.getName(element)
            if (elementName.lowercase().contains(name)) {
                if (holder == null || holder.childrenList.size == maxLineItems * 32) {
                    holder?.bake()
                    holder = UIItemLine()
                }
                widget.constrain {
                    x = (holder.childrenList.size % maxLineItems * 18).pixel
                    y = (holder.childrenList.size / maxLineItems * 18).pixel
                } childOf holder
                holder.childrenList.add(widget)
            }
        }
        holder?.bake()
    }

    private inner class UICell(val value: T, holder: UIItemLine) : UIDrawable(Drawables.CELL) {
        private var selectIcon: UIDrawable? = null
        var selected: Boolean = false
            private set(value) {
                if(field != value){
                    field = value
                    if(value){
                        selectIcon = object : UIDrawable(Drawables.CHECK_MARK){
                            override fun draw(matrixStack: UMatrixStack) {
                                matrixStack.push()
                                matrixStack.translate(0.0, 0.0, 200.0)
                                super.draw(matrixStack)
                                matrixStack.pop()
                            }
                        }.constrain {
                            x = PixelConstraint(1f, true)
                            y = PixelConstraint(1f, true)
                        } childOf this
                    }
                    else {
                        selectIcon?.hide(true)
                        selectIcon = null
                    }
                }
            }

        init {
            constrain {
                x = (holder.childrenList.size % maxLineItems * 18).pixel
                y = (holder.childrenList.size / maxLineItems * 18).pixel
                width = 18.pixel
                height = 18.pixel
            }.addChild(UIHollowBlock(Color(255, 255, 255, 0), 1f).constrain {
                width = 100.percent
                height = 100.percent
            }.onMouseEnter {
                this.makeAnimation().setColorAnimation(
                    Animations.OUT_QUART,
                    0.225f,
                    AlphaAspectColorConstraint(this.getColor(), 0.8f)
                ).begin()
            }.onMouseLeave {
                this.makeAnimation().setColorAnimation(
                    Animations.OUT_QUART,
                    0.225f,
                    AlphaAspectColorConstraint(this.getColor(), 0f)
                ).begin()
            }).addChild(provider.createUI(value)
                .constrain {
                    x = CenterConstraint()
                    y = CenterConstraint()
                }.setSmartHoverHandler(true)
            ).onMouseClick {
                if(!selected){
                    if(maxSelectCount == 1){
                        cells.keys.filter { it.selected }.forEach { it.selected = false }
                    }
                    else if(maxSelectCount != -1 && cells.keys.filter { it.selected }.size >= maxSelectCount){
                        return@onMouseClick
                    }
                }
                selected = !selected
                if(instantChoice){
                    onSelectedInternal(listOf(value))
                } else {
                    selectButton?.active = selected || cells.keys.any { it.selected }
                }
            } childOf holder
        }
    }

    private class UISource(val source: ObjectSource) : UIDrawable(Drawables.DECOR_CELL) {

        private var selected: Boolean = false

        init {
            UIContainer().constrain {
                x = CenterConstraint()
                y = CenterConstraint()
                width = 100.percent - 4.pixel
                height = 100.percent - 4.pixel
            }.addChild(source.icon().constrain {
                x = CenterConstraint()
                y = CenterConstraint()
            }) childOf this

            UIHollowBlock(Color(255, 255, 255, 0), 1f).constrain {
                width = 100.percent
                height = 100.percent
            }.onMouseEnter {
                this.makeAnimation().setColorAnimation(
                    Animations.OUT_QUART,
                    0.225f,
                    AlphaAspectColorConstraint(this.getColor(), 0.8f)
                ).begin()
            }.onMouseLeave {
                this.makeAnimation().setColorAnimation(
                    Animations.OUT_QUART,
                    0.225f,
                    AlphaAspectColorConstraint(this.getColor(), 0f)
                ).begin()
            } childOf this
        }

        fun setSelected(selected: Boolean){
            this.selected = selected
            drawable = if(selected) Drawables.DECOR_CELL_SELECTED else Drawables.DECOR_CELL
        }
    }

    private inner class UIItemLine : UIContainer() {
        private var unhiden = true
        val childrenList: MutableList<UIComponent> = ArrayList()

        init {
            constrain {
                y = SiblingConstraint()
                width = (18 * maxLineItems).pixel
                height = 18.pixel
            } childOf scrollPanel
        }

        fun bake(){
            setHeight((ceil(childrenList.size / maxLineItems.toFloat()).toInt() * 18).pixel)
        }

        override fun animationFrame() {
            if (getTop() > parent.parent.getBottom() || getBottom() < parent.parent.getTop()) {
                if (unhiden) {
                    unhiden = false
                    childrenList.forEach { it.hide(false) }
                }
            } else {
                if (!unhiden) {
                    unhiden = true
                    childrenList.forEach { it.unhide(false) }
                }
            }
            super.animationFrame()
        }
    }

    enum class ObjectSource {
        GAME_REGISTRY {
            override val icon: () -> UIComponent = { UIDrawable(Drawables.BOOK) }
            override val supported: Boolean = true
        },
        JEI {
            override val icon: () -> UIComponent = { UIText("J").constrain {
                textScale = 1.25.pixel
                color = Color(0x2D8F28).toConstraint()
            } }
            override val supported: Boolean
                get() = IntegrationHelper.hasJei()
        },
        INVENTORY {
            override val icon: () -> UIComponent = { UIDrawable(Drawables.CHEST) }
            override val supported: Boolean
                get() = true
        };
        abstract val icon: () -> UIComponent
        abstract val supported: Boolean
    }

    interface ObjectProvider<T> {
        fun getName(obj: T): String
        fun getObjects(source: ObjectSource): Collection<T>
        fun createUI(obj: T): UIComponent
    }

    object ItemProvider : ObjectProvider<Item> {
        override fun getName(obj: Item): String = obj.getDisplayName(obj.defaultInstance).getStringWithFormatting()
        override fun getObjects(source: ObjectSource): Collection<Item> = when(source) {
            ObjectSource.GAME_REGISTRY -> ForgeRegistries.ITEMS.values
            ObjectSource.JEI -> SleepLibJeiPlugin.getAllItems().map { it.item }.toSet()
            ObjectSource.INVENTORY -> Minecraft.getInstance().player?.inventory?.mainInventory
                ?.map { it.item }?.filter { it.item !== Items.AIR }?.toSet() ?: emptySet()
        }.sortedBy { Item.getIdFromItem(it) }
        override fun createUI(obj: Item): UIComponent = UIItemStack(obj.defaultInstance, 1f).constrain {
            width = 100.percent
            height = 100.percent
        }.withTooltip()
    }

    object ItemStackProvider : ObjectProvider<ItemStack> {
        override fun getName(obj: ItemStack): String = obj.displayName.getStringWithFormatting()
        override fun getObjects(source: ObjectSource): Collection<ItemStack> = when(source) {
            ObjectSource.GAME_REGISTRY -> ForgeRegistries.ITEMS.values.map { it.defaultInstance }
            ObjectSource.JEI -> SleepLibJeiPlugin.getAllItems().toSet()
            ObjectSource.INVENTORY -> Minecraft.getInstance().player?.inventory?.mainInventory
                ?.filter { !it.isEmpty }?.toSet() ?: emptySet()
        }.sortedBy { Item.getIdFromItem(it.item) }
        override fun createUI(obj: ItemStack): UIComponent = UIItemStack(obj, 1f).constrain {
            width = 100.percent
            height = 100.percent
        }.withTooltip()
    }

    object BlockProvider : ObjectProvider<Block> {
        override fun getName(obj: Block): String = obj.translatedName.getStringWithFormatting()
        override fun getObjects(source: ObjectSource): Collection<Block> = when(source) {
            ObjectSource.GAME_REGISTRY -> ForgeRegistries.BLOCKS.values
            ObjectSource.JEI -> SleepLibJeiPlugin.getAllItems()
                .map { Block.getBlockFromItem(it.item) }.filter { it !== Blocks.AIR }.toSet()
            ObjectSource.INVENTORY -> Minecraft.getInstance().player?.inventory?.mainInventory
                ?.map { Block.getBlockFromItem(it.item) }?.filter { it !== Blocks.AIR }?.toSet() ?: emptySet()
        }.sortedBy { Block.getStateId(it.defaultState) }
        override fun createUI(obj: Block): UIComponent  = UIBlockState(obj.defaultState).withTooltip()
    }

    object BlockStateProvider : ObjectProvider<BlockState> {
        override fun getName(obj: BlockState): String = obj.block.translatedName.getStringWithFormatting()
        override fun getObjects(source: ObjectSource): Collection<BlockState> = when(source) {
            ObjectSource.GAME_REGISTRY -> ForgeRegistries.BLOCKS.values.flatMap { it.stateContainer.validStates }
            ObjectSource.JEI -> SleepLibJeiPlugin.getAllItems()
                .map { Block.getBlockFromItem(it.item) }.map { it.defaultState }.filter { it !== Blocks.AIR }.toSet()
            ObjectSource.INVENTORY -> Minecraft.getInstance().player?.inventory?.mainInventory
                ?.map { Block.getBlockFromItem(it.item) }?.map { it.defaultState }?.filter { it !== Blocks.AIR }?.toSet() ?: emptySet()
        }.sortedBy { Block.getStateId(it) }
        override fun createUI(obj: BlockState): UIComponent = UIBlockState(obj, canDisplayItem = false, detailed = true, padding = 1f).withTooltip().constrain {
            width = 100.percent
            height = 100.percent
        }
    }
}