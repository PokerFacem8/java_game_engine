package entities;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;
import org.lwjgl.input.Mouse;

import java.security.Key;

public class Camera {

    private Vector3f position = new Vector3f(0,0,0);
    private Vector3f cameraDirection = new Vector3f(0,0,0);

    //Direction Angles
    private float pitch = 0f;
    private float yaw = 0f;
    private float roll = 0f;

    //Options
    private float sensitivity = 1f;
    private float cameraSpeed = 5f;

    private boolean firstMouse = true;
    private float lastX;
    private float lastY;

    public Camera() throws LWJGLException {
        Mouse.create();
        Mouse.setClipMouseCoordinatesToWindow(false);
        Mouse.setGrabbed(true);
    }

    public void destroy(){
        Mouse.destroy();
    }

    public void move(){

        //Get Camera Direction Vector3f
        mouseDirection();

        if(Keyboard.isKeyDown(Keyboard.KEY_W)){
            position.z -= (cameraDirection.z * 0.15f) * cameraSpeed;
            position.x -= (-cameraDirection.x * 0.15f) * cameraSpeed;
            position.y += (-cameraDirection.y * 0.15f) * cameraSpeed;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_S)){
            position.z += (cameraDirection.z * 0.15f) * cameraSpeed;
            position.x += (-cameraDirection.x * 0.15f) * cameraSpeed;
            position.y -= (-cameraDirection.y * 0.15f) * cameraSpeed;
        }

        if(Keyboard.isKeyDown(Keyboard.KEY_D)){
            position.z += (cameraDirection.x * 0.15f) * cameraSpeed;
            position.x += (cameraDirection.z * 0.15f) * cameraSpeed;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_A)) {
            position.z -= (cameraDirection.x * 0.15f) * cameraSpeed;
            position.x -= (cameraDirection.z * 0.15f) * cameraSpeed;
        }

        if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
            position.y+=0.15f * cameraSpeed;
        }
        if(Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)){
            position.y-=0.15f * cameraSpeed;
        }
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

        cameraDirection.x = (float) Math.sin(Math.toRadians(yaw));
        cameraDirection.y = (float) Math.sin(Math.toRadians(pitch));
        cameraDirection.z = (float) Math.cos(Math.toRadians(yaw));
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
