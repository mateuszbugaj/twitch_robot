package GUI;

import Managment.*;
import Robot.SendSerialMessageAction;
import Robot.SerialCom;
import Robot.SerialComEventHandler;
import Twitch.SendChatMessageAction;
import Twitch.TwitchChatMessageEventHandler;
import Twitch.TwitchService;
import Utils.FileReader;
import Utils.GeneralConfig;
import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.TwitchClientBuilder;
import com.github.twitch4j.chat.events.channel.IRCMessageEvent;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.stream.Collectors;
import java.util.stream.IntStream;
import static Utils.DisableAccessWarnings.disableAccessWarnings;

public class Canvas extends PApplet {
    private Simulation simulation;
    private CommandManager commandManager;
    private String helpMessage, logo;
    private Window helpWindow;
    private Camera webcam;

    @Override
    public void settings() {
        fullScreen(P3D);
//        size(1920, 1080, P3D);
        disableAccessWarnings();
    }

    @Override
    public void setup() {
        textAlign(LEFT, TOP);
        GUIConfig.consoleFont = createFont("UbuntuMono-Regular.ttf", 20);
        GUIConfig.logoFont = createFont("UbuntuMono-Bold.ttf", 20);

        helpMessage = FileReader.read("src/main/resources/text_files/help.txt");
        logo = FileReader.read("src/main/resources/text_files/logo.txt");

        Window.p = this;
        helpWindow = new Window(
                1500,
                150,
                400,
                200,
                "HELP");

        helpWindow.setContent(helpMessage);

        Simulation.p = this;
        simulation = new Simulation();

        Camera.p = this;
        webcam = new Camera(370, 40, (int) (640 * 2),(int) (480 * 2));

        SerialCom serialCom = new SerialCom(GeneralConfig.serialPort);
        SendSerialMessageAction serialMessageAction = new SendSerialMessageAction(serialCom);
        commandManager = new CommandManager(serialMessageAction);
        SaveRobotLogAction saveRobotLogAction = new SaveRobotLogAction(commandManager);
        SerialComEventHandler listener = new SerialComEventHandler(saveRobotLogAction);
        GeneralConfig.serialPort.addDataListener(listener);

        listener.addPoseSubscribers(commandManager);
        listener.addPoseSubscribers(simulation);

        TerminalMessageEventHandler terminalHandler = new TerminalMessageEventHandler(System.in,
                new SaveCommandAction(commandManager).setSaveAsFirst(true)
        );

        Thread terminalThread = new Thread(terminalHandler);
        terminalThread.start();

        String channelName = System.getenv("TWITCH_CHANNEL");
        String twitchToken = System.getenv("TWITCH_TOKEN");

        TwitchClient twitchClient = TwitchClientBuilder
                .builder()
                .withEnableHelix(true)
                .withEnableChat(true)
                .withChatAccount(new OAuth2Credential(System.getenv("TWITCH_CHANNEL"), twitchToken))
                .build();

        TwitchService twitchService = new TwitchService(twitchClient, channelName);

        twitchClient.getEventManager().onEvent(IRCMessageEvent.class,
                new TwitchChatMessageEventHandler(
                        new SaveCommandAction(commandManager),
                        new SendChatMessageAction(twitchService))
        );
    }

    @Override
    public void draw() {
        background(177, 157, 216);

        showConsole();
        showLogo();
        webcam.show();
        simulation.show(1500, 800, -0.2f, 0.3f);
        helpWindow.show();
    }

    private void showLogo(){
        String channelURL = "twitch.com/" + System.getenv("TWITCH_CHANNEL");

        // add specific number of white signs to channelURL to utilize whole console width
        int numberOfSpacesToConcat = (int) ((350 - textWidth(channelURL)) / textWidth(" ")) - 1;
        channelURL = (IntStream
                .range(0, numberOfSpacesToConcat)
                .mapToObj(i -> " ")
                .collect(Collectors.joining())).concat(channelURL);

        int k = (frameCount % ((channelURL.length() * 2 + 1) * 10)) /10;
        if(k > channelURL.length()){
            channelURL = IntStream.
                    range(0, k - channelURL.length())
                    .mapToObj(i -> " ")
                    .collect(Collectors.joining())
                    .concat(channelURL.substring(0, channelURL.length()*2 - k));
        } else {
            channelURL = channelURL.substring(channelURL.length() - k);
        }

        fill(GUIConfig.twitchDarkPurple);
        String logoWithChannel = logo.concat(channelURL);

        textFont(GUIConfig.logoFont);
        text(logoWithChannel, 10, 0);
    }

    private int rootInfo(String infoName, int lineYPos, String content){
        String root = "root";

        fill(GUIConfig.ubuntuGreen);
        text(root, 10, lineYPos);

        fill(GUIConfig.ubuntuWhite);
        text(":", 10 + textWidth(root), lineYPos);

        fill(GUIConfig.simulationBaseColor);
        text(infoName, 10 + textWidth(root) + textWidth(":"), lineYPos);

        fill(GUIConfig.ubuntuWhite);
        text("$ " + content, 10 + textWidth(root) + textWidth(":") + textWidth(infoName), lineYPos);

        return (int)(lineYPos + GUIConfig.consoleFont.getSize() * 1.2);
    }

    private void showConsole(){
        fill(GUIConfig.ubuntuBlack);
        rect(0, 0, 350, height);

        textFont(GUIConfig.consoleFont);

        int lineYPos = 270;
        lineYPos = rootInfo(
                "point-pos",
                lineYPos,
                String.format("[%.0f, %.0f, %.0f]",
                        commandManager.currentPose.x,
                        commandManager.currentPose.y,
                        commandManager.currentPose.z));

        if (commandManager.currentlyExecuting != null){
            lineYPos = rootInfo("now-exe", lineYPos, commandManager.currentlyExecuting.getContent());
        } else {
            lineYPos = rootInfo("now-exe", lineYPos, "None");
        }

        // draw line underneath
        stroke(GUIConfig.ubuntuWhite);
        line(20,
                lineYPos + 5,
                330,
                lineYPos + 5);

        noStroke();
        String lastUser = "";
        for(int commandIndex = 0; commandIndex < commandManager.getCommandList().size(); commandIndex++){
            UserCommand command = commandManager.getCommandList().get(commandIndex);
            if (lineYPos > height - 100){
                fill(GUIConfig.ubuntuWhite);
                text(String.format("And more... (%d)", commandManager.getCommandList().size() - commandIndex), 10, lineYPos);

                break;
            }

            String userName;
            if(command.getUserName().equals(lastUser)){

                // add specific number of white signs to indent
                userName = IntStream
                        .range(0, command.getUserName().length())
                        .mapToObj(i -> " ")
                        .collect(Collectors.joining());

                text(userName, 10, lineYPos);
            } else {
                lineYPos += GUIConfig.consoleFont.getSize()*0.3;
                fill(GUIConfig.ubuntuGreen);
                lastUser = command.getUserName();
                userName = command.getUserName();
                text(userName, 10, lineYPos);

                fill(GUIConfig.ubuntuWhite);
                text("$ ", 10 + textWidth(userName), lineYPos);
            }

            String content = command.getContent().concat("\n");

            // rect in case command don't fit in console
            fill(GUIConfig.ubuntuBlack);
            rect(10 + textWidth(userName) + textWidth("$ "),
                    lineYPos,
                    10 + textWidth(content),
                    GUIConfig.consoleFont.getSize());

            fill(GUIConfig.ubuntuWhite);
            text(content, 10 + textWidth(userName) + textWidth("$ "), lineYPos);
            lineYPos += GUIConfig.consoleFont.getSize()*1.2;

        }
    }

    public void showChat(){

    }

}
