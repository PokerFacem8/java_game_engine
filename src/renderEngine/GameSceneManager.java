package renderEngine;


import java.util.ArrayList;
import java.util.List;

public class GameSceneManager {

    //Game Scenes
    private List<GameScene> gameScenes;

    public GameSceneManager(){
        gameScenes = new ArrayList<GameScene>();
    }

    //TODO: Start/Stop Specific Scene by Id

    public List<GameScene> getGameScenes() {
        return gameScenes;
    }

    public void setGameScenes(List<GameScene> gameScenes) {
        this.gameScenes = gameScenes;
    }
}
