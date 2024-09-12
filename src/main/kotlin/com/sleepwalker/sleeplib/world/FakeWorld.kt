package com.sleepwalker.sleeplib.world

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

@OnlyIn(Dist.CLIENT)
class FakeWorld : World(
    FakeSpawnWorldInfo, OVERWORLD, DimensionType.OVERWORLD_TYPE,
    fakeProfilerProvider, true, false, 0
) {
    override fun getLight(pos: BlockPos): Int = 15
    override fun getLightFor(lightTypeIn: LightType, blockPosIn: BlockPos): Int = 15
    override fun addTileEntity(tile: TileEntity): Boolean = false
    override fun getTileEntity(pos: BlockPos): TileEntity? = null
    override fun getBlockState(pos: BlockPos): BlockState = Blocks.AIR.defaultState
    override fun getFluidState(pos: BlockPos): FluidState = getBlockState(pos).fluidState
    override fun playSound(pPlayer: PlayerEntity?, pX: Double, pY: Double, pZ: Double, pSound: SoundEvent, pCategory: SoundCategory, pVolume: Float, pPitch: Float){}
    override fun playMovingSound(playerIn: PlayerEntity?, entityIn: Entity, eventIn: SoundEvent, categoryIn: SoundCategory, volume: Float, pitch: Float) {}
    override fun getBiome(pos: BlockPos): Biome = BiomeRegistry.THE_VOID
    override fun getNoiseBiomeRaw(x: Int, y: Int, z: Int): Biome = BiomeRegistry.THE_VOID
    override fun func_230487_a_(pDirection: Direction, pIsShade: Boolean): Float {
        val flag: Boolean = FakeDimensionRenderInfo.func_239217_c_()
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

    override fun getEntitiesInAABBexcluding(entityIn: Entity?, boundingBox: AxisAlignedBB, predicate: Predicate<in Entity>?): List<Entity> = emptyList()
    override fun <T : Entity?> getEntitiesWithinAABB(clazz: Class<out T>, aabb: AxisAlignedBB, filter: Predicate<in T>?): List<T> = emptyList()
    override fun getEntityByID(id: Int): Entity? = null
    override fun getMapData(pMapName: String): MapData? = null
    override fun registerMapData(mapDataIn: MapData) {}
    override fun getNextMapId(): Int = 0
    override fun sendBlockBreakProgress(breakerId: Int, pos: BlockPos, progress: Int) {}
    override fun getScoreboard(): Scoreboard = FakeScoreboard
    override fun getPlayers(): List<PlayerEntity> = emptyList()
    override fun getSkylightSubtracted(): Int = 0
    override fun hasBlockState(pos: BlockPos, state: Predicate<BlockState>): Boolean = state.test(getBlockState(pos))
    override fun getRecipeManager(): RecipeManager = FakeRecipeManager
    override fun getTags(): ITagCollectionSupplier = FakeTagCollectionSupplier
    override fun destroyBlock(pos: BlockPos, pDropBlock: Boolean): Boolean = false
    override fun removeBlock(pos: BlockPos, pIsMoving: Boolean): Boolean = false
    fun setBlock(pos: BlockPos, state: BlockState) = setBlockState(pos, state, Constants.BlockFlags.DEFAULT)
    override fun setBlockState(pos: BlockPos, state: BlockState, flags: Int, recursionLeft: Int): Boolean = true
    override fun notifyBlockUpdate(pos: BlockPos, oldState: BlockState, newState: BlockState, flags: Int) {}
    override fun getPendingBlockTicks(): ITickList<Block> = EmptyTickList.get()
    override fun getPendingFluidTicks(): ITickList<Fluid> = EmptyTickList.get()
    override fun getChunkProvider(): AbstractChunkProvider = FakeChunkProvider()
    override fun playEvent(player: PlayerEntity?, type: Int, pos: BlockPos, data: Int) {}
    override fun func_241828_r(): DynamicRegistries = FakeDynamicRegistries

    inner class FakeChunkProvider : AbstractChunkProvider() {
        override fun getWorld(): IBlockReader = this@FakeWorld
        override fun getChunk(p_212849_1_: Int, p_212849_2_: Int, p_212849_3_: ChunkStatus, p_212849_4_: Boolean): IChunk?  = null
        override fun makeString(): String = "bebra"
        override fun getLightManager(): WorldLightManager = this@FakeWorld.lightManager
    }

    companion object {
        val fakeProfilerProvider: Supplier<IProfiler> = Supplier { FakeProfiler }
    }
}

object FakeSpawnWorldInfo : ISpawnWorldInfo {
    override fun getSpawnX(): Int = 0
    override fun getSpawnY(): Int = 0
    override fun getSpawnZ(): Int = 0
    override fun getSpawnAngle(): Float = 0f
    override fun getGameTime(): Long = Minecraft.getInstance().world?.gameTime ?: 0L
    override fun getDayTime(): Long = 0
    override fun isThundering(): Boolean = false
    override fun isRaining(): Boolean = false
    override fun setRaining(p_76084_1_: Boolean) {}
    override fun isHardcore(): Boolean = false
    override fun getGameRulesInstance(): GameRules = GameRules()
    override fun getDifficulty(): Difficulty = Difficulty.EASY
    override fun isDifficultyLocked(): Boolean = false
    override fun setSpawnX(x: Int) {}
    override fun setSpawnY(y: Int) {}
    override fun setSpawnZ(z: Int) {}
    override fun setSpawnAngle(p_241859_1_: Float) {}
}

object FakeProfiler : IProfiler {
    override fun startTick() {}
    override fun endTick() {}
    override fun startSection(name: String) {}
    override fun startSection(nameSupplier: Supplier<String>) {}
    override fun endSection() {}
    override fun endStartSection(name: String) {}
    override fun endStartSection(nameSupplier: Supplier<String>) {}
    override fun func_230035_c_(p_230035_1_: String) {}
    override fun func_230036_c_(p_230036_1_: Supplier<String>) {}
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
    override fun getBlockTags(): ITagCollection<Block> = BlockTags.getCollection()
    override fun getItemTags(): ITagCollection<Item> = ItemTags.getCollection()
    override fun getFluidTags(): ITagCollection<Fluid> = FluidTags.getCollection()
    override fun getEntityTypeTags(): ITagCollection<EntityType<*>> = EntityTypeTags.getCollection()
}

object FakeScoreboard : Scoreboard()