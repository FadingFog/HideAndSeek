package me.fadingfog.hideandseek;

import me.fadingfog.hideandseek.commands.CommandManager;
import me.fadingfog.hideandseek.commands.LobbyExitCommand;
import me.fadingfog.hideandseek.commands.LobbyJoinCommand;
import me.fadingfog.hideandseek.game.Arena;
import me.fadingfog.hideandseek.game.Game;
import me.fadingfog.hideandseek.game.Lobby;
import me.fadingfog.hideandseek.listeners.HitPlayerListener;
import me.fadingfog.hideandseek.listeners.PlayerQuitListener;
import me.fadingfog.hideandseek.placeholder.HnsExpansion;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class HideAndSeek extends JavaPlugin {
    private static HideAndSeek instance;
    private final ConfigStorage configStorage = new ConfigStorage(this);
    private transient I18n i18n;
    private Game game;

    public static HideAndSeek getInstance() {
        return HideAndSeek.instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        this.i18n = new I18n(this);
        this.i18n.onEnable();

        configStorage.LOAD_DEFAULT = true;
        configStorage.setup();

        this.i18n.updateLocale(configStorage.getLocale());

        setupParts();


        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new HnsExpansion(this).register();
        } else {
            getLogger().warning("Could not find PlaceholderAPI! This plugin is required.");
            Bukkit.getPluginManager().disablePlugin(this);
        }

        if (Bukkit.getPluginManager().getPlugin("Essentials") == null){
            getLogger().warning("Could not find Essentials! This plugin is required.");
            Bukkit.getPluginManager().disablePlugin(this);
        }

        getCommand("hns").setExecutor(new CommandManager());
        getCommand("ejoin").setExecutor(new LobbyJoinCommand());
        getCommand("eleave").setExecutor(new LobbyExitCommand());

        getServer().getPluginManager().registerEvents(new PlayerQuitListener(), this);
        getServer().getPluginManager().registerEvents(new HitPlayerListener(), this);
    }

    @Override
    public void onDisable() {
        game.forceStop();
        if (i18n != null) {
            i18n.onDisable();
        }
    }

    public I18n getI18n() {
        return i18n;
    }

    public void setupParts() {
        final Lobby lobby = new Lobby();
        lobby.setup();

        final Arena arena = new Arena();
        arena.setup();

        game = new Game();
        game.setup();
    }
}
