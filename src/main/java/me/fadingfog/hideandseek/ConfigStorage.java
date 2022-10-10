package me.fadingfog.hideandseek;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;

@SuppressWarnings({"unchecked", "DuplicatedCode"})
public class ConfigStorage {

    private final HideAndSeek plugin;
    private static ConfigStorage instance;
    private static File configFile;
    private static FileConfiguration config;

    public boolean LOAD_DEFAULT = false;

    public ConfigStorage(HideAndSeek plugin) {
        this.plugin = plugin;
    }

    public static ConfigStorage getInstance() {
        return ConfigStorage.instance;
    }

    public void setup() {
        ConfigStorage.instance = this;
        configFile = new File(Bukkit.getServer().getPluginManager().getPlugin("HideAndSeek").getDataFolder(), "config.yml");

        config = YamlConfiguration.loadConfiguration(configFile);
        if (LOAD_DEFAULT) loadDefaults();
        save();
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public void save() {
        try {
            config.save(configFile);
        } catch (IOException e) {
            System.out.println("Can't save storage file");
        }
    }

    public static void reload() {
        config = YamlConfiguration.loadConfiguration(configFile);
    }

    public void loadDefaults() {
//        config.addDefault("anchor", ""); // TODO
        config.options().copyDefaults(true);
    }

    public Location getLocation(String path) {
        reload();
        if (config.isSet(path)) {
            HashMap<String, String> loc = (HashMap<String, String>) (Object) config.getConfigurationSection(path).getValues(false);
            return serializeToLocation(loc);
        } else return null;
    }

    public void setLocation(String path, Location location) {
        reload();
        config.set(path, serializeFromLocation(location));
        save();
    }

    private Location serializeToLocation(HashMap<String, String> loc) {
        World world = plugin.getServer().getWorld(loc.get("world"));
        int x = Integer.parseInt(loc.get("x"));
        int y = Integer.parseInt(loc.get("y"));
        int z = Integer.parseInt(loc.get("z"));
        float yaw = Float.parseFloat(loc.get("yaw"));
        float pitch = Float.parseFloat(loc.get("pitch"));
        return new Location(world, x, y, z, yaw, pitch);
    }

    private HashMap<String, String> serializeFromLocation(Location location) {
        String world = location.getWorld().getName();
        String xCoord = Integer.toString((int) location.getX());
        String zCoord = Integer.toString((int) location.getZ());
        String yCoord = Integer.toString((int) location.getY());
        String yaw = Float.toString(location.getYaw());
        String pitch = Float.toString(location.getPitch());

        return new HashMap<String, String>() {{
            put("world", world);
            put("x", xCoord);
            put("y", yCoord);
            put("z", zCoord);
            put("yaw", yaw);
            put("pitch", pitch);
        }};
    }

    String pathNumberOfSeekers = "number-of-seekers";
    String pathMinNumberOfPlayers = "min-players";
    String pathTimeToStart = "time-to-start";
    String pathTimeToHide = "time-to-hide";
    String pathGameDuration = "game-duration";

    public void setNumberOfSeekers(int count) {
        config.set(pathNumberOfSeekers, count);
    }

    public int getNumberOfSeekers() {
        return config.getInt(pathNumberOfSeekers);
    }

    public void setMinNumberOfPlayers(int count) {
        config.set(pathMinNumberOfPlayers, count);
    }

    public int getMinNumberOfPlayers() {
        return config.getInt(pathMinNumberOfPlayers);
    }

    public void setTimeToStart(int dur) {
        config.set(pathTimeToStart, dur); // TODO
    }

    public void setTimeToHide(int dur) {
        config.set(pathTimeToHide, dur); // TODO
    }

    public void setGameDuration(int dur) {
        config.set(pathGameDuration, dur); // TODO
    }

}