package me.fadingfog.hideandseek.commands;

import me.fadingfog.hideandseek.ConfigStorage;
import me.fadingfog.hideandseek.game.Arena;
import me.fadingfog.hideandseek.game.Game;
import me.fadingfog.hideandseek.game.Lobby;
import org.bukkit.entity.Player;

public class GameCommand extends SubCommand {
    private String resultMessage;
    private final ConfigStorage config = ConfigStorage.getInstance();
    private final Game game = Game.getInstance();
    private final Arena arena = Arena.getInstance();
    private final Lobby lobby = Lobby.getInstance();


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
                    if (arena.getLocation() == null || arena.getSeekersLobbyLocation() == null) {
                        resultMessage = "Arena or seekers lobby location is not set";
                        break;
                    }
                    if (lobby.getMembers().size() < config.getMinNumberOfPlayers() || lobby.getMembers().size() < config.getNumberOfSeekers() * 2) {
                        resultMessage = "Not enough players for start game";
                        break;
                    }
                    this.game.start();

                    break;
                case ("stop"):

                    break;
                case ("test"):
//                    ScoreboardManager manager = getServer().getScoreboardManager();
//                    Scoreboard board = manager.getMainScoreboard();
//                    Team team = board.getTeam("Aboba");
//                    team.setPrefix("Ebobo");

                    break;
                default:
                    resultMessage = "Command not found";
                    break;
            }
        } else resultMessage = "Command not found";

    }

}
