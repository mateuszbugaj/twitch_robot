package GUI;

import processing.core.PApplet;
import processing.video.Capture;

public class Camera {
    public static PApplet p;
    private final int xPos, yPos, windowWidth, windowHeight;
    private Capture camDevice;

    public Camera(int xPos, int yPos, int windowWidth, int windowHeight) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;

        if(GUIConfig.camDeviceName != null){
            camDevice = new Capture(p, GUIConfig.camDeviceName);
            camDevice.start();
        }
    }

    public void show(){
        p.pushMatrix();
        p.translate(xPos, yPos);
        p.noStroke();

        // Window Frame
        p.fill(GUIConfig.twitchDarkPurple);
        p.rect(0, 0, windowWidth + 5*2, windowHeight + 30 + 5, 10);

        // Window name
        p.fill(GUIConfig.ubuntuBlack);
        p.textFont(GUIConfig.logoFont);
        p.textSize(30);
        p.text("Webcam", 10, 0);

        // Content
        if(camDevice != null){
            camDevice.read();
            p.image(camDevice, 5, 30, windowWidth, windowHeight);
        }

        p.popMatrix();
    }
}
