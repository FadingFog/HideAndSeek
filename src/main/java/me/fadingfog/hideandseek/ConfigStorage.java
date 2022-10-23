package me.fadingfog.hideandseek;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        config.addDefault("locale", "en");
        config.addDefault("number-of-seekers", 3);
        config.addDefault("min-players", 6);
        config.addDefault("time-to-start", "10S");
        config.addDefault("time-to-hide", "10M");
        config.addDefault("time-to-seek", "20M");
        config.options().copyDefaults(true);
    }

    public String getLocale() {
        return config.getString("locale");
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
    String pathGameDuration = "time-to-seek";

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

    public void setTimeToStart(String dur) {
        config.set(pathTimeToStart, dur.toUpperCase());
    }

    public long getTimeToStart() {
        String time = (String) config.get(pathTimeToStart);
        Duration dur = Duration.parse("PT" + time.toUpperCase());
        return dur.getSeconds();
    }

    public void setTimeToHide(String dur) {
        config.set(pathTimeToHide, dur.toUpperCase());
    }

    public long getTimeToHide() {
        String time = (String) config.get(pathTimeToHide);
        Duration dur = Duration.parse("PT" + time.toUpperCase());
        return dur.getSeconds();
    }

    public void setTimeToSeek(String dur) {
        config.set(pathGameDuration, dur.toUpperCase());
    }

    public long getTimeToSeek() {
        String time = (String) config.get(pathGameDuration);
        Duration dur = Duration.parse("PT" + time.toUpperCase());
        return dur.getSeconds();
    }

    public boolean isDuration(String dur) {
        return Pattern.matches("^(\\d{1,2}[HhMmSs])*$", dur);
    }

    public static String formatDuration(final int totalSeconds) {

        Duration d = Duration.parse("PT" + totalSeconds + "S");
        final long h = d.toHours();
        d = d.minusHours(h);
        final long m = d.toMinutes();
        d = d.minusMinutes(m);
        final long s = d.getSeconds();

        final String hoursPattern = I18n.tl("hoursPattern");
        final String minutesPattern = I18n.tl("minutesPattern");
        final String secondsPattern = I18n.tl("secondsPattern");

        final String hours = (h == 0) ? "" :  I18n.formatPlural(hoursPattern, h);
        final String minutes = (m ==  0) ? "" : I18n.formatPlural(minutesPattern, m);
        final String seconds = (s == 0) ? "" : I18n.formatPlural(secondsPattern, s);

        List<String> time = Stream.of(hours, minutes, seconds).filter(i -> !i.isEmpty()).collect(Collectors.toList());
        return StringUtils.join(time, " ");
    }

    public static String formatDurationDigits(final int totalSeconds) {
        Duration d = Duration.parse("PT" + totalSeconds + "S");
        final long hours = d.toHours();
        d = d.minusHours(hours);
        final long minutes = d.toMinutes();
        d = d.minusMinutes(minutes);
        final long seconds = d.getSeconds();
        if (hours != 0) {
            return String.format("%02d:%02d:%02d", hours, minutes, seconds);
        } else {
            return String.format("%d:%02d", minutes, seconds);
        }
    }

}
