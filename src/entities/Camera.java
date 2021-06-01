package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.input.Mouse;

import java.text.Normalizer;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Camera {

    private Vector3f position = new Vector3f(0,0,0);
    private Vector3f cameraFront = new Vector3f(0,0,0);

    private float pitch;
    private float yaw;
    private float roll;

    private float sensitivity;
    private float cameraSpeed = 1f;
    private boolean firstMouse = true;
    private float lastX;
    private float lastY;

    public Camera() {}

    public void move(){

        System.out.println(cameraFront.x);
        System.out.println(cameraFront.y);

        //CameraFront 1 <-> -1

        if(Keyboard.isKeyDown(Keyboard.KEY_W)){
            position.z-= 0.15f * cameraSpeed;
            //position = new Vector3f(position.x, position.y, cameraFront.z * cameraSpeed);

        }
        if(Keyboard.isKeyDown(Keyboard.KEY_S)){
            position.z+= 0.15f * cameraSpeed;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_D)){
            position.x+=0.15f * cameraSpeed;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_A)){
            position.x-=0.15f * cameraSpeed;
        }
        /*if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
            position.y+=0.15f * cameraSpeed;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)){
            position.y-=0.15f * cameraSpeed;
        }*/



    }

    public void mouseDirection(){
        float mouseX = Mouse.getX();
        float mouseY = Mouse.getY();

        if (firstMouse)
        {
            lastX = mouseX;
            lastY = mouseY;
            firstMouse = false;
        }

        float xoffset = mouseX - lastX;
        float yoffset = lastY - mouseY;
        lastX = mouseX;
        lastY = mouseY;

        float sensitivity = 0.1f;
        xoffset *= sensitivity;
        yoffset *= sensitivity;

        yaw   += xoffset;
        pitch += yoffset;

        if(pitch > 89.0f)
            pitch = 89.0f;
        if(pitch < -89.0f)
            pitch = -89.0f;

        Vector3f direction = new Vector3f(0,0,0);

        direction.x = (float) (cos(Math.toRadians(yaw)) * cos(Math.toRadians(pitch)));
        direction.y = (float) sin(Math.toRadians(pitch));
        direction.z = (float) (sin(Math.toRadians(yaw)) * cos(Math.toRadians(pitch)));
        cameraFront = (Vector3f) direction.normalise();
    }

    public Vector3f getCameraFront() {
        return cameraFront;
    }

    public void setCameraFront(Vector3f cameraFront) {
        this.cameraFront = cameraFront;
    }

    public float getSensitivity() {
        return sensitivity;
    }

    public void setSensitivity(float sensitivity) {
        this.sensitivity = sensitivity;
    }

    public float getCameraSpeed() {
        return cameraSpeed;
    }

    public void setCameraSpeed(float cameraSpeed) {
        this.cameraSpeed = cameraSpeed;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public float getPitch() {
        return pitch;
    }

    public void setPitch(float pitch) {
        this.pitch = pitch;
    }

    public float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public float getRoll() {
        return roll;
    }

    public void setRoll(float roll) {
        this.roll = roll;
    }
}
