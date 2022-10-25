package me.fadingfog.hideandseek.placeholder;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.fadingfog.hideandseek.HideAndSeek;
import me.fadingfog.hideandseek.I18n;
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
        GamePlayer gPlayer;
        switch (identifier) {
            case "cond_state":
                return String.valueOf(game.getGameState().ordinal());

            case "cond_role":
                if (game.getGameState() != Game.GameState.Closed && game.getGameState() != Game.GameState.End) {
                    gPlayer = arena.getGamePlayer(player.getPlayer());
                    if (gPlayer != null) {
                        return String.valueOf(gPlayer.getRole().ordinal());
                    } else {
                        return I18n.tl("admin");
                    }
                }
                return "-1";

            case "state":
                Game.GameState gameState = game.getGameState();
                switch (gameState) {
                    case Closed:
                    case Preparing:
                    case End:
                        return gameState.getName();
                    case Hiding:
                    case Seeking:
                        return I18n.tl("stage", gameState.getName());
                    default:
                        return "-1";
                }

            case "role":
                if (game.getGameState() != Game.GameState.Closed && game.getGameState() != Game.GameState.End) {
                    gPlayer = arena.getGamePlayer(player.getPlayer());
                    if (gPlayer != null) {
                        return I18n.tl("role", gPlayer.getRole().getName());
                    } else {
                        return I18n.tl("admin");
                    }
                }
                return "-1";

            case "score":
                if (game.getGameState() != Game.GameState.Closed && game.getGameState() != Game.GameState.End) {
                    gPlayer = arena.getGamePlayer(player.getPlayer());
                    if (gPlayer != null) {
                        return I18n.tl("score", gPlayer.getScore());
                    } else {
                        return "-1";
                    }
                }
                return "-1";

            case "members":
                return I18n.tl("members", lobby.getMembers().size());

            case "hiders":
                if (game.getGameState() != Game.GameState.Closed && game.getGameState() != Game.GameState.End) {
                    return I18n.tl("hiders", arena.getHiders().size());
                }
                return "-1";

            case "seekers":
                if (game.getGameState() != Game.GameState.Closed && game.getGameState() != Game.GameState.End) {
                return I18n.tl("seekers", arena.getSeekers().size());
                }
                return "-1";

            case "timer":
                if (game.getGameState() == Game.GameState.Preparing) {
                    return I18n.tl("timerGameStart", timer);
                } else if (game.getGameState() == Game.GameState.Hiding || game.getGameState() == Game.GameState.Seeking) {
                    return I18n.tl("timerLeft", formatDurationDigits(timer));
                } else {
                    return "-1";
                }
        }

        return null;
    }
}
