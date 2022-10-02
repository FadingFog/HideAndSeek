package me.fadingfog.hideandseek;

import me.fadingfog.hideandseek.commands.CommandManager;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class HideAndSeek extends JavaPlugin {
    private static HideAndSeek instance;
    private BukkitAudiences adventure;

    public static HideAndSeek getInstance() {
        return HideAndSeek.instance;
    }

    public @NonNull BukkitAudiences adventure() {
        if(this.adventure == null) {
            throw new IllegalStateException("Tried to access Adventure when the plugin was disabled!");
        }
        return this.adventure;
    }

    @Override
    public void onEnable() {
        instance = this;
        this.adventure = BukkitAudiences.create(this);

        getCommand("hns").setExecutor(new CommandManager());
    }

    @Override
    public void onDisable() {
        if(this.adventure != null) {
            this.adventure.close();
            this.adventure = null;
        }
    }
}
