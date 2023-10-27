package com.sleepwalker.sleeplib.shader;

import com.sleepwalker.sleeplib.SleepLib;
import net.minecraft.client.Minecraft;
import net.minecraft.client.shader.IShaderManager;
import net.minecraft.client.shader.ShaderLinkHelper;
import net.minecraft.client.shader.ShaderLoader;
import net.minecraft.resources.IReloadableResourceManager;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.resource.IResourceType;
import net.minecraftforge.resource.ISelectiveResourceReloadListener;
import org.lwjgl.system.MemoryUtil;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.FloatBuffer;
import java.util.EnumMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.Predicate;

public enum SLShader implements ISelectiveResourceReloadListener {

    BLUR("shader/blur.vert", "shader/blur.frag");

    SLShader(String vertexShaderPath, String fragmentShaderPath) {
        this.vertexShaderPath = vertexShaderPath;
        this.fragmentShaderPath = fragmentShaderPath;
    }

    public final String vertexShaderPath;
    public final String fragmentShaderPath;

    @Nonnull public static final FloatBuffer FLOAT_BUF = MemoryUtil.memAllocFloat(1);
    @Nonnull private static final Map<SLShader, ShaderProgram> PROGRAMS = new EnumMap<>(SLShader.class);

    @Override
    public void onResourceManagerReload(@Nonnull IResourceManager manager, @Nonnull Predicate<IResourceType> predicate) {
        PROGRAMS.values().forEach(ShaderLinkHelper::releaseProgram);
        PROGRAMS.clear();
        loadShaders(manager);
    }

    public static void initShaders() {
        IResourceManager resourceManager = Minecraft.getInstance().getResourceManager();
        if(resourceManager instanceof IReloadableResourceManager){
            ((IReloadableResourceManager)resourceManager).registerReloadListener(SLShader.BLUR);
        }
    }

    private static void loadShaders(IResourceManager manager) {

        for (SLShader shader : SLShader.values()){
            createProgram(manager, shader);
        }
    }

    public static void useShader(@Nonnull SLShader shader, @Nullable ShaderCallback callback) {

        ShaderProgram shaderProgram = PROGRAMS.get(shader);
        if (shaderProgram == null){
            return;
        }

        int program = shaderProgram.getId();
        ShaderLinkHelper.glUseProgram(program);

        //int time = GlStateManager._glGetUniformLocation(program, "time");
        //GlStateManager._glUniform1i(time, ClientTickHandler.ticksInGame);

        if (callback != null){
            callback.call(program);
        }
    }

    public static void useShader(@Nonnull SLShader shader) {
        useShader(shader, null);
    }

    public static void releaseShader() {
        ShaderLinkHelper.glUseProgram(0);
    }

    private static void createProgram(@Nonnull IResourceManager manager, @Nonnull SLShader shader) {
        try {
            ShaderLoader vert = createShader(manager, shader.vertexShaderPath, ShaderLoader.ShaderType.VERTEX);
            ShaderLoader frag = createShader(manager, shader.fragmentShaderPath, ShaderLoader.ShaderType.FRAGMENT);
            int program = ShaderLinkHelper.createProgram();
            ShaderProgram shaderProgram = new ShaderProgram(program, vert, frag);
            ShaderLinkHelper.linkProgram(shaderProgram);
            PROGRAMS.put(shader, shaderProgram);
        }
        catch (IOException ex) {
            SleepLib.LOGGER.error("Failed to load program {}", shader.name(), ex);
        }
    }

    @Nonnull
    private static ShaderLoader createShader(IResourceManager manager, String filename, ShaderLoader.ShaderType shaderType) throws IOException {
        ResourceLocation loc = new ResourceLocation(SleepLib.MODID, "shader/" + filename);
        try (InputStream inputStream = new BufferedInputStream(manager.getResource(loc).getInputStream())) {
            return ShaderLoader.compileShader(shaderType, loc.toString(), inputStream, shaderType.name().toLowerCase(Locale.ROOT));
        }
    }

    private static class ShaderProgram implements IShaderManager {

        private final int program;

        private final ShaderLoader vert;

        private final ShaderLoader frag;

        private ShaderProgram(int program, ShaderLoader vert, ShaderLoader frag) {
            this.program = program;
            this.vert = vert;
            this.frag = frag;
        }

        @Override
        public int getId() {
            return this.program;
        }

        @Override
        public void markDirty() { }

        @Override
        @Nonnull
        public ShaderLoader getVertexProgram() {
            return this.vert;
        }

        @Override
        @Nonnull
        public ShaderLoader getFragmentProgram() {
            return this.frag;
        }
    }
}
