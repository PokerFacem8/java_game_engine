package terrains;

import models.RawModel;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.Loader;
import textures.ModelTexture;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Terrain {
    private static final float SIZE = 800;
    private static final float MAX_HEIGTH = 40;
    private static final float MAX_PIXEL_COLOUR = 256 * 256 * 256;


    private float x;
    private float z;
    private RawModel model;
    private ModelTexture texture;

    public Terrain(int gridX, int gridZ, Loader loader, ModelTexture texture, String heigthMap){
        this.texture = texture;
        this.x = gridX * SIZE;
        this.z = gridZ * SIZE;
        this.model = generateTerrain(loader, heigthMap);
    }

    public static float getSIZE() {
        return SIZE;
    }

    public float getX() {
        return x;
    }

    public float getZ() {
        return z;
    }

    public RawModel getModel() {
        return model;
    }

    public ModelTexture getTexture() {
        return texture;
    }

    private RawModel generateTerrain(Loader loader, String heightMap){

        //HeigthMap Image
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File("res/" + heightMap + ".png"));
        } catch (IOException e) {
            System.out.println("Error: Failed to load heigthMap.");
            e.printStackTrace();
        }

        int VERTEX_COUNT = image.getHeight();

        int count = VERTEX_COUNT * VERTEX_COUNT;
        float[] vertices = new float[count * 3];
        float[] normals = new float[count * 3];
        float[] textureCoords = new float[count * 2];
        int[] indices = new int[6*(VERTEX_COUNT-1)*(VERTEX_COUNT*1)];
        int vertexPointer = 0;
        for (int i = 0; i < VERTEX_COUNT; i++) {
            for (int j = 0; j < VERTEX_COUNT; j++) {
                vertices[vertexPointer*3] = -(float)j/((float) VERTEX_COUNT - 1) * SIZE;
                vertices[vertexPointer*3+1] = getHeigth(j, i, image);
                vertices[vertexPointer*3+2] = -(float)i/((float)VERTEX_COUNT - 1) * SIZE;
                Vector3f normal = calculateNormal(j, i, image);
                normals[vertexPointer*3] = normal.x;
                normals[vertexPointer*3+1] = normal.y;
                normals[vertexPointer*3+2] = normal.z;
                textureCoords[vertexPointer*2] = (float)j/((float)VERTEX_COUNT - 1);
                textureCoords[vertexPointer*2+1] = (float)i/((float)VERTEX_COUNT - 1);
                vertexPointer++;
            }
        }

        int pointer = 0;
        for (int gz = 0; gz < VERTEX_COUNT - 1; gz++) {
            for (int gx = 0; gx < VERTEX_COUNT - 1; gx++) {
                int topLeft = (gz * VERTEX_COUNT) + gx;
                int topRight = topLeft + 1;
                int bottomLeft = ((gz+1) * VERTEX_COUNT) + gx;
                int bottomRight = bottomLeft + 1;
                indices[pointer++] = topLeft;
                indices[pointer++] = bottomLeft;
                indices[pointer++] = topRight;
                indices[pointer++] = topRight;
                indices[pointer++] = bottomLeft;
                indices[pointer++] = bottomRight;
            }
        }
        return loader.loadToVAO(vertices, textureCoords, normals, indices);
    }

    private Vector3f calculateNormal(int x , int z, BufferedImage image){
        float heigthL = getHeigth(x - 1, z, image);
        float heigthR = getHeigth(x + 1, z, image);
        float heigthD = getHeigth(x, z - 1, image);
        float heigthU = getHeigth(x, z + 1, image);

        Vector3f normal = new Vector3f(heigthL - heigthR, 2f, heigthD - heigthD);
        normal.normalise();
        return normal;
    }

    private float getHeigth(int x, int z, BufferedImage image){
        if(x < 0 || x >= image.getHeight() || z < 0 || z >= image.getHeight()){
            return 0;
        }

        float heigth = image.getRGB(x, z);
        heigth += MAX_PIXEL_COLOUR/2f;
        heigth /= MAX_PIXEL_COLOUR/2f;
        heigth *= MAX_HEIGTH;
        return heigth;
    }
}
