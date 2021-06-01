package engineTester;

import entities.Camera;
import entities.Entity;
import entities.Light;
import models.TextureModel;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.*;
import models.RawModel;
import shaders.StaticShader;
import terrains.Terrain;
import textures.ModelTexture;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainGameLoop {
    public static void main(String[] args) throws LWJGLException {

        //Display Manger and Loader
        DisplayManager displayManager = new DisplayManager();
        displayManager.createDisplay();
        Loader loader = new Loader();

        //Model (Trees)
        RawModel model = OBJLoader.loadObjModel("cube", loader);
        ModelTexture texture = new ModelTexture(loader.loadTexture("gold"));
        texture.setShineDamper(10);
        texture.setReflectivity(1);
        TextureModel texturedModel = new TextureModel(model, texture);

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

        //Terrain
        Terrain terrain = new Terrain(0,0,loader, new ModelTexture(loader.loadTexture("grass")));
        Terrain terrain2 = new Terrain(1,0,loader, new ModelTexture(loader.loadTexture("grass")));
        Terrain terrain3 = new Terrain(0,1,loader, new ModelTexture(loader.loadTexture("grass")));
        Terrain terrain4 = new Terrain(1,1,loader, new ModelTexture(loader.loadTexture("grass")));

        //Light and Camera
        Light light = new Light(new Vector3f(0,50,0), new Vector3f(1,1,1));
        Camera camera = new Camera();
        //Sensitivity
        //camera.setSensitivity(2f);
        //Camera Speed
        camera.setCameraSpeed(5f);


        //Master Renderer
        MasterRenderer renderer = new MasterRenderer();

        //Mouse
        Mouse.create();
        Mouse.setGrabbed(true);

        while (!Display.isCloseRequested()){

            //game logic

            //entity.increasePosition(0,0,0);
            camera.move();
            camera.mouseDirection();
            light.move();

            //Terrains
            renderer.processTerrain(terrain);
            renderer.processTerrain(terrain2);
            renderer.processTerrain(terrain3);
            renderer.processTerrain(terrain4);

            for(Entity entity:entities){
                renderer.processEntity(entity);
                //entity.increaseRotation(0,1,0);
            }

            //Exit Game
            if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)){
                break;
            }

            //render
            renderer.render(light, camera);

            displayManager.updateDisplay();
        }

        Mouse.destroy();
        renderer.cleanUp();
        loader.cleanUp();
        displayManager.closeDisplay();
    }
}
