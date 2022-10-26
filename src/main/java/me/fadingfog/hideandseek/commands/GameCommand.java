package me.fadingfog.hideandseek.commands;

import me.fadingfog.hideandseek.ConfigStorage;
import me.fadingfog.hideandseek.I18n;
import me.fadingfog.hideandseek.game.Arena;
import me.fadingfog.hideandseek.game.Game;
import me.fadingfog.hideandseek.game.Lobby;
import org.bukkit.entity.Player;


public class GameCommand extends SubCommand {
    private String resultMessage;
    private final ConfigStorage config = ConfigStorage.getInstance();
    private final Lobby lobby = Lobby.getInstance();
    private final Arena arena = Arena.getInstance();
    private final Game game = Game.getInstance();


    @Override
    public String getName() {
        return "game";
    }

    @Override
    public String getResultMessage() {
        return resultMessage;
    }

    @Override
    public void perform(Player player, String[] args) {
        if (args.length > 0) {
            switch (args[0].toLowerCase()) {
                case ("start"):
                    if (game.getGameState() != Game.GameState.Closed) {
                        resultMessage = I18n.tl("gameAlreadyStarted");
                        break;
                    }
                    if (arena.getLocation() == null || arena.getSeekersLobbyLocation() == null) {
                        resultMessage = I18n.tl("arenaOrSeekersLocNotSet");
                        break;
                    }
                    if (config.getMinNumberOfPlayers() <= config.getNumberOfSeekers()) {
                        resultMessage = I18n.tl("gameMinPlayersLessSeekers");
                        break;
                    }
                    if (lobby.getMembers().size() < config.getMinNumberOfPlayers() || lobby.getMembers().size() < config.getNumberOfSeekers() * 2) {
                        resultMessage = I18n.tl("gameNotEnoughPlayers");
                        break;
                    }
                    if (game.start()) {
                        resultMessage = I18n.tl("gameStarted");
                    } else {
                        resultMessage = I18n.tl("gameAlreadyStarted");
                    }

                    break;
                case ("stop"):
                    if (game.stop()) {
                        resultMessage = I18n.tl("gameStopped");
                        lobby.sendLobbyMessage(I18n.tl("gameStoppedEarly"));
                    } else {
                        resultMessage = I18n.tl("gameNotStarted");
                    }

                    break;
                case ("forcestop"):
                    game.forceStop();
                    resultMessage = I18n.tl("gameNotStarted");

                    break;
                default:
                    resultMessage = I18n.tl("commandNotFound");
                    break;
            }
        } else resultMessage = I18n.tl("commandNotFound");

    }

}
