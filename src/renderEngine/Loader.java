package renderEngine;

import jdk.swing.interop.SwingInterOpUtils;
import models.RawModel;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import javax.xml.transform.Source;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

//Load 3D models into memory by storing the position data about the model in a VAO
public class Loader {

    private List<Integer> vaos = new ArrayList<Integer>();
    private List<Integer> vbos = new ArrayList<Integer>();
    private List<Integer> textures = new ArrayList<Integer>();

    //Store position data about the model in a VAO
    public RawModel loadToVAO(float[] positions, float[] textureCoords, float[] normals, int[] indices){
        int vaoID = createVAO();
        bindIndicesBuffer(indices);
        storeDataInAttributeList(0,3,positions); //Vertex Coords in VAO
        storeDataInAttributeList(1,2,textureCoords); //Texture Vertex Coords in VAO
        storeDataInAttributeList(2,3,normals); //Texture Vertex Coords in VAO
        unbindVAO();
        return new RawModel(vaoID, indices.length);
    }

    public int loadTexture(String filename, String extension){
        Texture texture = null;
        try {
            texture = TextureLoader.getTexture("PNG",new FileInputStream("res/textures/" + filename + "." + extension));
        } catch (IOException e) {
            System.out.println("Warning: Texture with name " + filename + "." + extension + " is not loading!");
            try{
                texture = TextureLoader.getTexture("PNG", new FileInputStream("res/textures/missing_texture.png"));
            }catch (IOException e2){
                e2.printStackTrace();
            }
        }
        int textureID = texture.getTextureID();
        textures.add(textureID);
        return  textureID;
    }

    //Create a VAO
    private int createVAO(){
        int vaoID = GL30.glGenVertexArrays();
        vaos.add(vaoID);
        GL30.glBindVertexArray(vaoID);
        return vaoID;
    }

    //Create a VBO with position data about the model
    private void storeDataInAttributeList(int attributeNumber, int coordinateSize, float[] data){
        int vboID = GL15.glGenBuffers();
        vbos.add(vboID);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
        FloatBuffer buffer = storeDataInFloatBuffer(data);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(attributeNumber,coordinateSize, GL11.GL_FLOAT, false, 0 ,0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER,0);
    }

    private void unbindVAO(){
        GL30.glBindVertexArray(0);
    }

    private void bindIndicesBuffer(int[] indices){
        int vboID = GL15.glGenBuffers();
        vbos.add(vboID);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
        IntBuffer buffer = storeDataInIntBuffer(indices);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
    }

    private IntBuffer storeDataInIntBuffer(int[] data){
        IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    //Data to Buffer
    private FloatBuffer storeDataInFloatBuffer(float[] data){
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        return buffer;
    }

    public void cleanUp(){
        for(int vao:vaos){
           GL30.glDeleteVertexArrays(vao);
        }
        for(int vbo:vbos){
            GL15.glDeleteBuffers(vbo);
        }
        for(int texture:textures){
            GL11.glDeleteTextures(texture);
        }
    }
}
