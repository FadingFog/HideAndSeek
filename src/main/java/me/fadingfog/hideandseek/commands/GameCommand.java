package me.fadingfog.hideandseek.commands;

import me.fadingfog.hideandseek.HideAndSeek;
import me.fadingfog.hideandseek.game.Game;
import org.bukkit.entity.Player;

public class GameCommand extends SubCommand {
    private String resultMessage;
    private final HideAndSeek plugin = HideAndSeek.getInstance();
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
