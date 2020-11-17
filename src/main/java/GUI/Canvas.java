package GUI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import processing.core.PApplet;
import processing.core.PFont;

public class Canvas extends PApplet {
    Logger log = LoggerFactory.getLogger(Canvas.class);
    public static PFont ubuntuFont;

    @Override
    public void settings() {
        fullScreen();
    }

    @Override
    public void setup() {
        textSize(15);
        textAlign(LEFT, TOP);
//        ubuntuFont = createFont("D:\\GithubRepos\\twitch_robot\\src\\main\\resources\\UbuntuMono-Regular.ttf", 15);
        ubuntuFont = createFont("UbuntuMono-Regular.ttf", 15);
        textFont(ubuntuFont);
    }

    @Override
    public void draw() {
        background(180);
        fill(12, 12 ,12);
        rect(0, 0, 350, height);
        showConsoleText();
        showHelp();
    }

    private void showConsoleText(){
        fill(204, 204, 204);

        String logo = """
                 ______               __          __    \s
                /_  __/ _    __   _  / /_  ____  / /    \s
                 / /   | |/|/ / / / / __/ / __/ / _ \\   \s
                /_/    |__,__/ /_/  \\__/  \\__/_/_//_/   \s
                  ____  ___    / /    ___    / /_       \s
                 / __/ / _ \\  / _ \\  / _ \\  / __/       \s
                /_/    \\___/ /_.__/  \\___/  \\__/        \s
                https://twitch.com/boogieman002""";

        String consoleLog = logo.concat("");

        text(consoleLog, 10, 0);
    }

    private void showHelp(){
        String content = """
                To send command type '!'
                and one of the following:
                 x#
                 y#
                 z#
                where # means distance in millimeters
                (6.21371e-7 mile) on the x/y/z axis.
                You can combine commands for the axes
                to move them at the same time.
                Just separate them with space.
                You can also combine commands for
                separate movement using semicolon ';'

                Here are examples:
                !x10 (moves head 10mm on the x axis)
                !y-55 (moves -55mm on the y axis)
                !x15 y15
                (moves on the diagonal to the x15 y15)
                !x15; y10
                (moves first to the x=15, then y=10)
                There are limitations to the range of
                motion but you can try to brake them.
                Have fun.""";

        fill(12, 12, 12);
        textSize(30);
        text("HELP", 1600, 10);
        textSize(15);
        text(content, 1600, 50);
    }

}
