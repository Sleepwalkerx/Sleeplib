package com.sleepwalker.architectsdream.world

import com.google.gson.JsonElement
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.client.Minecraft
import net.minecraft.client.world.DimensionRenderInfo
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityType
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.fluid.Fluid
import net.minecraft.fluid.FluidState
import net.minecraft.item.Item
import net.minecraft.item.crafting.RecipeManager
import net.minecraft.profiler.IProfiler
import net.minecraft.resources.IResourceManager
import net.minecraft.scoreboard.Scoreboard
import net.minecraft.tags.*
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.Direction
import net.minecraft.util.ResourceLocation
import net.minecraft.util.SoundCategory
import net.minecraft.util.SoundEvent
import net.minecraft.util.math.AxisAlignedBB
import net.minecraft.util.math.BlockPos
import net.minecraft.util.registry.DynamicRegistries
import net.minecraft.world.*
import net.minecraft.world.biome.Biome
import net.minecraft.world.biome.BiomeRegistry
import net.minecraft.world.chunk.AbstractChunkProvider
import net.minecraft.world.chunk.ChunkStatus
import net.minecraft.world.chunk.IChunk
import net.minecraft.world.lighting.WorldLightManager
import net.minecraft.world.storage.ISpawnWorldInfo
import net.minecraft.world.storage.MapData
import net.minecraftforge.api.distmarker.Dist
import net.minecraftforge.api.distmarker.OnlyIn
import net.minecraftforge.common.util.Constants
import java.util.function.Predicate
import java.util.function.Supplier
import javax.annotation.Nonnull

@OnlyIn(Dist.CLIENT)
class FakeWorld : World(
    FakeSpawnWorldInfo, OVERWORLD, DimensionType.DEFAULT_OVERWORLD,
    fakeProfilerProvider, true, false, 0
) {
    override fun getBrightness(type: LightType, pos: BlockPos): Int = 15
    override fun getRawBrightness(pos: BlockPos, p_226659_2_: Int): Int = 15
    override fun addFreshEntity(entityIn: Entity): Boolean = false
    override fun getBlockEntity(@Nonnull pos: BlockPos): TileEntity? = null
    override fun getBlockState(pos: BlockPos): BlockState = Blocks.AIR.defaultBlockState()
    override fun getFluidState(pos: BlockPos): FluidState = getBlockState(pos).fluidState
    override fun playSound(pPlayer: PlayerEntity?, pX: Double, pY: Double, pZ: Double, pSound: SoundEvent, pCategory: SoundCategory, pVolume: Float, pPitch: Float){}
    override fun playSound(pPlayer: PlayerEntity?, pEntity: Entity, pEvent: SoundEvent, pCategory: SoundCategory, pVolume: Float, pPitch: Float) {}
    override fun getBiome(pos: BlockPos): Biome = BiomeRegistry.THE_VOID
    override fun getUncachedNoiseBiome(pX: Int, pY: Int, pZ: Int): Biome = BiomeRegistry.THE_VOID
    override fun getShade(pDirection: Direction, pIsShade: Boolean): Float {
        val flag: Boolean = FakeDimensionRenderInfo.constantAmbientLight()
        return if (!pIsShade) {
            if (flag) 0.9f else 1.0f
        } else {
            when (pDirection) {
                Direction.DOWN -> if (flag) 0.9f else 0.5f
                Direction.UP -> if (flag) 0.9f else 1.0f
                Direction.NORTH, Direction.SOUTH -> 0.8f
                Direction.WEST, Direction.EAST -> 0.6f
                else -> 1.0f
            }
        }
    }
    override fun getEntities(entity: Entity?, area: AxisAlignedBB, filter: Predicate<in Entity>?): List<Entity> = emptyList()
    override fun <T : Entity> getEntitiesOfClass(pClazz: Class<out T>, pArea: AxisAlignedBB, pFilter: Predicate<in T>?): List<T> = emptyList()
    override fun getEntity(pId: Int): Entity? = null
    override fun getMapData(pMapName: String): MapData? = null
    override fun setMapData(mapData: MapData) {}
    override fun getFreeMapId(): Int = 0
    override fun destroyBlockProgress(pBreakerId: Int, pPos: BlockPos, pProgress: Int) {}
    override fun getScoreboard(): Scoreboard = FakeScoreboard
    override fun players(): List<PlayerEntity> = emptyList()
    override fun getSkyDarken(): Int = 0
    override fun isStateAtPosition(pos: BlockPos, predicate: Predicate<BlockState>): Boolean = predicate.test(getBlockState(pos))
    override fun getRecipeManager(): RecipeManager = FakeRecipeManager
    override fun getTagManager(): ITagCollectionSupplier = FakeTagCollectionSupplier
    override fun destroyBlock(pos: BlockPos, pDropBlock: Boolean): Boolean = false
    override fun removeBlock(pos: BlockPos, pIsMoving: Boolean): Boolean = false
    fun setBlock(pos: BlockPos, state: BlockState) = setBlock(pos, state, Constants.BlockFlags.DEFAULT)
    override fun setBlock(pos: BlockPos, state: BlockState, flags: Int): Boolean = true
    override fun sendBlockUpdated(pos: BlockPos, oldState: BlockState, newState: BlockState, flags: Int) {}
    override fun getBlockTicks(): ITickList<Block> = EmptyTickList.empty()
    override fun getLiquidTicks(): ITickList<Fluid> = EmptyTickList.empty()
    override fun getChunkSource(): AbstractChunkProvider = FakeChunkProvider()
    override fun levelEvent(pPlayer: PlayerEntity?, pType: Int, pPos: BlockPos, pData: Int) {}
    override fun registryAccess(): DynamicRegistries = FakeDynamicRegistries

    inner class FakeChunkProvider : AbstractChunkProvider() {
        override fun getLevel(): IBlockReader = this@FakeWorld
        override fun getChunk(p_212849_1_: Int, p_212849_2_: Int, p_212849_3_: ChunkStatus, p_212849_4_: Boolean): IChunk?  = null
        override fun gatherStats(): String = "bebra"
        override fun getLightEngine(): WorldLightManager = this@FakeWorld.lightEngine
    }

    companion object {
        val fakeProfilerProvider: Supplier<IProfiler> = Supplier { FakeProfiler }
    }
}

object FakeSpawnWorldInfo : ISpawnWorldInfo {
    override fun getXSpawn(): Int = 0
    override fun getYSpawn(): Int = 0
    override fun getZSpawn(): Int = 0
    override fun getSpawnAngle(): Float = 0f
    override fun getGameTime(): Long = Minecraft.getInstance().level?.gameTime ?: 0L
    override fun getDayTime(): Long = 0
    override fun isThundering(): Boolean = false
    override fun isRaining(): Boolean = false
    override fun setRaining(p_76084_1_: Boolean) {}
    override fun isHardcore(): Boolean = false
    override fun getGameRules(): GameRules = GameRules()
    override fun getDifficulty(): Difficulty = Difficulty.EASY
    override fun isDifficultyLocked(): Boolean = false
    override fun setXSpawn(p_76058_1_: Int) {}
    override fun setYSpawn(p_76056_1_: Int) {}
    override fun setZSpawn(p_76087_1_: Int) {}
    override fun setSpawnAngle(p_241859_1_: Float) {}
}

object FakeProfiler : IProfiler {
    override fun startTick() {}
    override fun endTick() {}
    override fun push(p_76320_1_: String) {}
    override fun push(p_194340_1_: Supplier<String>) {}
    override fun pop() {}
    override fun popPush(p_219895_1_: String) {}
    override fun popPush(p_194339_1_: Supplier<String>) {}
    override fun incrementCounter(p_230035_1_: String) {}
    override fun incrementCounter(p_230036_1_: Supplier<String>) {}
}

object FakeRecipeManager : RecipeManager() {
    override fun apply(
        p_212853_1_: MutableMap<ResourceLocation, JsonElement>,
        p_212853_2_: IResourceManager,
        p_212853_3_: IProfiler
    ) {}
}

val FakeDynamicRegistries = DynamicRegistries.Impl()
val FakeDimensionRenderInfo = DimensionRenderInfo.Overworld()
val FakeWorldInstance = FakeWorld()

object FakeTagCollectionSupplier : ITagCollectionSupplier {
    override fun getBlocks(): ITagCollection<Block> = BlockTags.getAllTags()
    override fun getItems(): ITagCollection<Item> = ItemTags.getAllTags()
    override fun getFluids(): ITagCollection<Fluid> = FluidTags.getAllTags()
    override fun getEntityTypes(): ITagCollection<EntityType<*>> = EntityTypeTags.getAllTags()
}

object FakeScoreboard : Scoreboard()