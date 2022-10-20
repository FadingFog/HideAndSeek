package me.fadingfog.hideandseek.placeholder;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.fadingfog.hideandseek.HideAndSeek;
import me.fadingfog.hideandseek.game.Arena;
import me.fadingfog.hideandseek.game.Game;
import me.fadingfog.hideandseek.game.GamePlayer;
import me.fadingfog.hideandseek.game.Lobby;
import org.bukkit.OfflinePlayer;

import static me.fadingfog.hideandseek.ConfigStorage.formatDurationDigits;

@SuppressWarnings("NullableProblems")
public class HnsExpansion extends PlaceholderExpansion {
    private final HideAndSeek plugin;
    private static HnsExpansion instance;

    private final Lobby lobby = Lobby.getInstance();
    private final Arena arena = Arena.getInstance();
    private final Game game = Game.getInstance();
    public int timer;


    public HnsExpansion(HideAndSeek plugin) {
        this.plugin = plugin;
        instance = this;
    }

    public static HnsExpansion getInstance() {
        return instance;
    }

    @Override
    public String getAuthor() {
        return "FadingFog";
    }

    @Override
    public String getIdentifier() {
        return "hns";
    }

    @Override
    public String getVersion() {
        return "1.0.0";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onRequest(OfflinePlayer player, String identifier) {
        switch (identifier) {
            case "lobby":
                return String.valueOf(lobby.getMembers().size());
            case "role":
                if (game.getGameState() != Game.GameState.Closed) {
                    GamePlayer gPlayer = arena.getGamePlayer(player.getPlayer());
                    return gPlayer.getRole().getName();
                }
                return "-1";
            case "score":
                if (game.getGameState() != Game.GameState.Closed) {
                    GamePlayer gPlayer = arena.getGamePlayer(player.getPlayer());
                    return String.valueOf(gPlayer.getScore());
                }
                return "0";
            case "hiders":
                return String.valueOf(arena.getHiders().size());
            case "seekers":
                return String.valueOf(arena.getSeekers().size());
            case "state":
                return game.getGameState().getName();
            case "timer":
                if (game.getGameState() == Game.GameState.Preparing) {
                    return "Game will start in " + timer;
                } else if (game.getGameState() == Game.GameState.Hiding) {
                    return "Time left " + formatDurationDigits(timer);
                } else if (game.getGameState() == Game.GameState.Seeking) {
                    return "Time left " + formatDurationDigits(timer);
                } else {
                    return "-1";
                }
        }

        return null;
    }
}
