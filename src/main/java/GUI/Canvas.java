package GUI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import processing.core.PApplet;

public class Canvas extends PApplet {
    Logger log = LoggerFactory.getLogger(Canvas.class);

    @Override
    public void settings() {
        fullScreen();
    }

    @Override
    public void setup() {

    }

    @Override
    public void draw() {
        background(180);
    }

}
