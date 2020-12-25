import GUI.Canvas;
import Utils.FileReader;
import processing.core.PApplet;


public class TwitchRobot{
    public static void main(String[] args) {
        String logo = FileReader.read("src/main/resources/text_files/logo.txt");
        System.out.println(logo);

        PApplet.main(Canvas.class); }
}
