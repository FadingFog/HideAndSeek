package me.fadingfog.hideandseek;

import me.fadingfog.hideandseek.commands.CommandManager;
import me.fadingfog.hideandseek.game.Arena;
import me.fadingfog.hideandseek.game.Game;
import me.fadingfog.hideandseek.game.Lobby;
import me.fadingfog.hideandseek.placeholder.HnsExpansion;
import org.bukkit.Bukkit;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.plugin.java.JavaPlugin;

public final class HideAndSeek extends JavaPlugin {
    private static HideAndSeek instance;
    private final ConfigStorage configStorage = new ConfigStorage(this);

    public static HideAndSeek getInstance() {
        return HideAndSeek.instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        setupParts();

        getConfig().options().copyDefaults();
        saveDefaultConfig();

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new HnsExpansion(this).register();
        } else {
            getLogger().warning("Could not find PlaceholderAPI! This plugin is required.");
            Bukkit.getPluginManager().disablePlugin(this);
        }

        getCommand("hns").setExecutor(new CommandManager());
    }

    @Override
    public void onDisable() {

    }

    public void setupParts() {
        configStorage.LOAD_DEFAULT = true;
        configStorage.setup();

        final Lobby lobby = new Lobby();
        lobby.setup();

        final Arena arena = new Arena();
        arena.setup();

        final Game game = new Game();
        game.setup();
    }
}
