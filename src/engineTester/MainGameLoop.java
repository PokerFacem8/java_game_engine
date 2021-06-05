package engineTester;

import entities.Camera;
import entities.Entity;
import entities.Light;
import models.TextureModel;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.*;
import models.RawModel;
import terrains.Terrain;
import textures.ModelTexture;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainGameLoop {
    public static void main(String[] args) throws LWJGLException {

        //TODO: Create Game Scene Manager

        //DisplayManager, Loader and MasterRenderer
        DisplayManager displayManager = new DisplayManager();
        displayManager.createDisplay();
        Loader loader = new Loader();
        MasterRenderer renderer = new MasterRenderer();

        //Create Models
        RawModel model = OBJLoader.loadObjModel("cube", loader);
        ModelTexture texture = new ModelTexture(loader.loadTexture("gold", "png"));
        texture.setShineDamper(10);
        texture.setReflectivity(1);
        TextureModel texturedModel = new TextureModel(model, texture);

        //Create Entities
        List<Entity> entities = new ArrayList<Entity>();
        for(int i = 0; i <= 5000; i++){
            Random r = new Random();
            int low = -800;
            int high = 800;
            int resultX = r.nextInt(high-low) + low;
            int resultZ = r.nextInt(high-low) + low;

            Entity entity = new Entity(texturedModel, new Vector3f( resultX ,1f ,resultZ),0,0,0,1);
            entities.add(entity);
        }

        //Create Terrain
        List<Terrain> terrains = new ArrayList<Terrain>();
        Terrain terrain = new Terrain(0,0,loader, new ModelTexture(loader.loadTexture("grass", "png")));
        terrains.add(terrain);
        Terrain terrain2 = new Terrain(1,0,loader, new ModelTexture(loader.loadTexture("brick_ground", "png")));
        terrains.add(terrain2);
        Terrain terrain3 = new Terrain(0,1,loader, new ModelTexture(loader.loadTexture("grass","png")));
        terrains.add(terrain3);
        Terrain terrain4 = new Terrain(1,1,loader, new ModelTexture(loader.loadTexture("grass", "png")));
        terrains.add(terrain4);


        //Create Light and Camera
        Light light = new Light(new Vector3f(0,50,0), new Vector3f(1,1,1));
        Camera camera = new Camera();


        //Create GameScene Manager
        GameSceneManager gameSceneManager = new GameSceneManager();

        //Create GameScene
        GameScene gameScene = new GameScene("GameScene1",entities, terrains, camera, light, renderer);

        //Add GameScene to GameSceneManager
        gameSceneManager.getGameScenes().add(gameScene);

        //Game Main Loop
        while (!Display.isCloseRequested()){

            //GameScene Start //TODO: Start specific scene by Id
            gameScene.start();
            System.out.println(gameScene.getId());

            //game logic

            //Exit Game
            if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
                break;
            }

            displayManager.updateDisplay();
        }

        gameScene.getCamera().destroy();
        renderer.cleanUp();
        loader.cleanUp();
        displayManager.closeDisplay();
    }
}
