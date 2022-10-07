package me.fadingfog.hideandseek.game;

import me.fadingfog.hideandseek.HideAndSeek;
import org.bukkit.configuration.file.FileConfiguration;

public class Game {
    public enum GameState {
        Closed, WarmUp, Ingame, End;
    }
    private static Game instance;
    private final HideAndSeek plugin = HideAndSeek.getInstance();
    private final Arena arena = Arena.getInstance();

    private GameState gameState = GameState.Closed;

    public static Game getInstance() {
        return instance;
    }

    public void setup() {
        instance = this;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public void start() {
        setGameState(GameState.WarmUp);


    }

}
