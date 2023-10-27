package com.sleepwalker.sleeplib.util.request;

import com.mojang.authlib.GameProfile;
import com.sleepwalker.sleeplib.util.ServerUtil;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Util;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.server.ServerLifecycleHooks;
import net.minecraftforge.server.permission.PermissionAPI;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.UUID;

public class PlayerRequestSender implements IRequestSender {

    @Nonnull private final GameProfile profile;

    public PlayerRequestSender(@Nonnull GameProfile profile) {
        this.profile = profile;
    }

    public PlayerRequestSender(@Nonnull ServerPlayerEntity player) {
        this.profile = player.getGameProfile();
    }

    @Override
    public boolean hasPermission(@Nonnull String node) {
        return PermissionAPI.hasPermission(profile, node, null);
    }

    @Override
    public void sendMessage(@Nonnull ITextComponent message) {

    }

    @Override
    public void sendMessage(@Nonnull ITextComponent message, boolean notifyAdmins) {
        ServerPlayerEntity player = getPlayer();
        if(player != null){
            player.sendMessage(message, Util.DUMMY_UUID);
        }
        if(notifyAdmins){
            ServerUtil.broadcastToAdmins(this, message);
        }
    }

    @Nonnull
    @Override
    public String getName() {
        return profile.getName();
    }

    @Nonnull
    @Override
    public UUID getUUID() {
        return profile.getId();
    }

    @Nonnull
    public GameProfile getProfile() {
        return profile;
    }

    @Nullable
    public ServerPlayerEntity getPlayer(){
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        if(server == null){
            return null;
        }
        else {
            return server.getPlayerList().getPlayerByUUID(profile.getId());
        }
    }
}
