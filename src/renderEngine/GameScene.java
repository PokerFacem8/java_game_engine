package renderEngine;

import entities.Camera;
import entities.Entity;
import entities.Light;
import org.lwjgl.LWJGLException;
import terrains.Terrain;
import toolBox.Maths;

import java.util.List;

//TODO: Create Terrains and Entities

public class GameScene {

    //Scene Id
    private long id;

    //Scene Name
    private String name;

    //Entities
    private List<Entity> entities;

    //Terrain
    private List<Terrain> terrains;

    //Camera
    private Camera camera;

    //Light
    private Light light;

    //Renderer
    private MasterRenderer renderer;

    public GameScene(){
        //Generate Id
        this.id = Maths.generateUniqueId();
    }

    public GameScene(String name, List<Entity> entities, List<Terrain> terrains, Camera camera, Light light, MasterRenderer renderer) {
        //Generate Id
        this.id = Maths.generateUniqueId();
        this.name = name;
        this.entities = entities;
        this.camera = camera;
        this.light = light;
        this.terrains = terrains;
        this.renderer = renderer;
    }

    public void start(){

        //Load Light Movement
        light.move();

        //Load Camera Movement
        camera.move();

        //Load Terrains
        for (Terrain terrain: terrains) {
            renderer.processTerrain(terrain);
        }

        //Load Entities
        for(Entity entity:entities){
            renderer.processEntity(entity);
        }

        //Render Light and Camera
        renderer.render(light, camera);
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public void setEntities(List<Entity> entities) {
        this.entities = entities;
    }

    public List<Terrain> getTerrains() {
        return terrains;
    }

    public void setTerrains(List<Terrain> terrains) {
        this.terrains = terrains;
    }

    public Camera getCamera() {
        return camera;
    }

    public void setCamera(Camera camera) {
        this.camera = camera;
    }

    public Light getLight() {
        return light;
    }

    public void setLight(Light light) {
        this.light = light;
    }

    public MasterRenderer getRenderer() {
        return renderer;
    }

    public void setRenderer(MasterRenderer renderer) {
        this.renderer = renderer;
    }
}
