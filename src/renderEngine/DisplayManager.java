package renderEngine;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.*;

import java.io.IOException;
import java.sql.Time;

import static org.lwjgl.Sys.getTime;

public class DisplayManager {

    private static final int WIDTH = 1280;
    private static final int HEIGTH = 720;
    private static final int FPS_CAP = 120;
    private static final int SHOW_FPS = 1;
    private static float framesPerSecond = 0;
    private static float lastTime = 0.0f;

    public DisplayManager() {}

    public static void CalculateFrameRate(){
        if(lastTime == 0.0f){
            lastTime = getTime() ;
        }

        float currentTime = getTime();
        framesPerSecond++;
        if( currentTime - lastTime > 1000)
        {
            lastTime = currentTime;
            if(SHOW_FPS == 1){
                Display.setTitle("GameEngine FPS: " + framesPerSecond);
            }
            framesPerSecond = 0;
        }
    }

    public static long getTime() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }

    public static void createDisplay() throws LWJGLException {
        ContextAttribs attribs = new ContextAttribs(3,2)
                .withForwardCompatible(true)
                .withProfileCore(true);
        try{
            Display.setDisplayMode(new DisplayMode(WIDTH, HEIGTH));
            Display.create(new PixelFormat(), attribs);
        }catch (LWJGLException e){
            e.printStackTrace();
        }

        GL11.glViewport(0,0,WIDTH, HEIGTH);

    }

    public  static  void updateDisplay(){
        Display.sync(FPS_CAP);
        CalculateFrameRate();
        Display.update();
    }

    public  static  void closeDisplay(){
        Display.destroy();
    }

}
