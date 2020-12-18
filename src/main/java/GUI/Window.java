package GUI;

import org.jetbrains.annotations.NotNull;
import processing.core.PApplet;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Window {
    public static PApplet p;
    private final int xPos, yPos, windowWidth, windowHeight;
    private final String name;
    private List<String > contents = new ArrayList<>();

    public Window(int xPos, int yPos, int windowWidth, int windowHeight, String name) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        this.name = name;
    }

    /**
     * Split text content into paragraphs based on empty lines.
     * There can be multiple empty lines in between.
     * List of paragraphs is returned and also stored as private field of Window.
     * Ending line characters are kept in place as in original String
     * @param content original text containing paragraphs divided by empty lines
     * @return a list of Strings containing individual paragraphs
     */
    public List<String> setContent(String content){
        List<String> paragraphs = new ArrayList<>();
        List<String> lines = content.lines().collect(Collectors.toList());

        String paragraph = "";
        for(String line:lines){
            if(line.isEmpty()){
                if(!paragraph.isEmpty()){
                    paragraphs.add(paragraph);
                    paragraph = "";
                }
            } else {
                paragraph = paragraph.concat(line).concat("\n");
            }
        }

        if(!paragraph.isEmpty()){
            paragraphs.add(paragraph);
        }

        contents.addAll(paragraphs);
        return paragraphs;
    }

    public void show(){
        p.pushMatrix();
        p.translate(xPos, yPos);
        p.noStroke();

        // Window Frame
        p.fill(GUIConfig.twitchDarkPurple);
        p.rect(-5, -30, windowWidth + 5*2, windowHeight + 30 + 5, 10);

        // Window name
        p.fill(GUIConfig.ubuntuBlack);
        p.textFont(GUIConfig.logoFont);
        p.textSize(30);
        p.text(name, 10, -30);

        // Content space
        p.fill(GUIConfig.ubuntuBlack);
        p.rect(0, 0, windowWidth, windowHeight, 0, 0, 10, 10);

        // Content text
        if(!contents.isEmpty()){
            int k = (p.frameCount/300) % contents.size();

            // Slide number
            if(contents.size() > 1){
                p.fill(GUIConfig.ubuntuBlack);
                p.textFont(GUIConfig.logoFont);
                p.textSize(30);
                String slideNumberText = k+1 + "/" + contents.size();
                p.text(slideNumberText, windowWidth - p.textWidth(slideNumberText) - 10, -30);
            }

            p.fill(GUIConfig.ubuntuWhite);
            p.textFont(GUIConfig.consoleFont);
            p.textSize(20);
            p.text(contents.get(k), 10, 10);

        }

        p.popMatrix();
    }
}
