package me.fadingfog.hideandseek.game;

import me.fadingfog.hideandseek.ConfigStorage;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

import static me.fadingfog.hideandseek.commands.CommandManager.prefix;

@SuppressWarnings("DuplicatedCode")
public class Arena {
    private static Arena instance;
    private final ConfigStorage config = ConfigStorage.getInstance();

    private final String configPath = "arena-location";
    private final String configSeekersPath = "seekers-lobby-location";
    private Location location = config.getLocation(configPath);
    private Location seekersLobbyLocation = config.getLocation(configSeekersPath);

    private final List<GamePlayer> gamePlayers = new ArrayList<>();


    public static Arena getInstance() {
        return instance;
    }

    public void setup() {
        instance = this;
    }

    public Location getLocation() {
        return this.location;
    }

    public void setLocation(Location location) {
        config.setLocation(configPath, location);
        this.location = config.getLocation(configPath);
    }

    public Location getSeekersLobbyLocation() {
        return this.seekersLobbyLocation;
    }

    public void setSeekersLobbyLocation(Location location) {
        config.setLocation(configSeekersPath, location);
        this.seekersLobbyLocation = config.getLocation(configSeekersPath);
    }

    public List<GamePlayer> getGamePlayers() {
        return this.gamePlayers;
    }

    public List<Player> getPlayers() {
        return this.gamePlayers.stream().map(GamePlayer::getPlayer).collect(Collectors.toList());
    }

    public void addGamePlayer(GamePlayer gPlayer) {
        this.gamePlayers.add(gPlayer);
    }

    public GamePlayer getGamePlayer(Player player) {
        List<GamePlayer> gamePlayers = this.gamePlayers.stream().filter(p -> p.getPlayer() == player).collect(Collectors.toList());
        if (gamePlayers.size() > 0) {
            return gamePlayers.get(0);
        }
        return null;
    }

    public boolean removeGamePlayer(GamePlayer gPlayer) {
        return this.gamePlayers.remove(gPlayer);
    }

    public boolean removeGamePlayer(Player player) {
        GamePlayer gPlayer = getGamePlayer(player);
        if (gPlayer == null) {
            return false;
        }
        return this.gamePlayers.remove(gPlayer);
    }

    public void clearGamePlayers() {
        this.gamePlayers.clear();
    }

    public List<GamePlayer> getSeekers() {
        return this.gamePlayers.stream().filter(p -> p.getRole() == GamePlayer.Role.SEEKER).collect(Collectors.toList());
    }

    public List<GamePlayer> getHiders() {
        return this.gamePlayers.stream().filter(p -> p.getRole() == GamePlayer.Role.HIDER).collect(Collectors.toList());
    }

    public void sendArenaMessage(String msg) {
        for (GamePlayer gp : gamePlayers) {
            Player player = gp.getPlayer();
            player.sendMessage(prefix + msg);
        }
    }

    public void sendSeekersMessage(String msg) {
        for (GamePlayer seeker : getSeekers()) {
            Player player = seeker.getPlayer();
            player.sendMessage(prefix + msg);
        }
    }

    public void sendHidersMessage(String msg) {
        for (GamePlayer hider : getHiders()) {
            Player player = hider.getPlayer();
            player.sendMessage(prefix + msg);
        }
    }

    public LinkedHashMap<GamePlayer, Integer> getTopSeekers(int total) {
        LinkedHashMap<GamePlayer, Integer> sortedSeekers = getGamePlayers().stream().collect(Collectors.toMap(gPlayer -> gPlayer, GamePlayer::getScore)).entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> b, LinkedHashMap::new));

        LinkedHashMap<GamePlayer, Integer> topSeekers = new LinkedHashMap<>();

        int counter = 0;
        Iterator<GamePlayer> itr = sortedSeekers.keySet().iterator();
        while (itr.hasNext() && counter < total) {
            GamePlayer gPlayer = itr.next();
            topSeekers.put(gPlayer, sortedSeekers.get(gPlayer));
            counter++;
        }

        return topSeekers;
    }

    public LinkedHashMap<GamePlayer, Integer> getTopHiders(int total) {
        LinkedHashMap<GamePlayer, Integer> sortedHiders = getGamePlayers().stream().collect(Collectors.toMap(gPlayer -> gPlayer, GamePlayer::getTotalTimeAsHider)).entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> b, LinkedHashMap::new));

        LinkedHashMap<GamePlayer, Integer> topHiders = new LinkedHashMap<>();

        int counter = 0;
        Iterator<GamePlayer> itr = sortedHiders.keySet().iterator();
        while (itr.hasNext() && counter < total) {
            GamePlayer gPlayer = itr.next();
            topHiders.put(gPlayer, sortedHiders.get(gPlayer));
            counter++;
        }

        return topHiders;
    }

//    private LinkedHashMap<GamePlayer, Integer> sortHashMap(HashMap<GamePlayer, Integer> hashMap) {
//        return hashMap.entrySet().stream()
//                .sorted(Comparator.comparingInt(Map.Entry::getValue))
//                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> b, LinkedHashMap::new));
//    }
//
//    private LinkedHashMap<GamePlayer, Integer> cutHashMap(HashMap<GamePlayer, Integer> hashMap, int total) {
//        LinkedHashMap<GamePlayer, Integer> newHashMap = new LinkedHashMap<>();
//
//        int counter = 0;
//        Iterator<GamePlayer> itr = hashMap.keySet().iterator();
//        while (itr.hasNext() && counter < total) {
//            GamePlayer gPlayer = itr.next();
//            newHashMap.put(gPlayer, hashMap.get(gPlayer));
//            counter++;
//        }
//
//        return newHashMap;
//    }
}
