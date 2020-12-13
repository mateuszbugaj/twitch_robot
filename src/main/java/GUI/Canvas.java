package GUI;

import Managment.CommandManager;
import Managment.UserCommand;
import Utils.FileReader;
import processing.core.PApplet;
import processing.core.PFont;

import java.util.stream.Collectors;
import java.util.stream.IntStream;
import static Utils.DisableAccessWarnings.disableAccessWarnings;

public class Canvas extends PApplet {
    private Simulation simulation;
    private CommandManager commandManager;
    private String helpMessage, logo;
    private Window helpWindow;

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
                1400,
                100,
                400,
                200,
                "HELP")
                .setContent(helpMessage, true);

        Simulation.p = this;
        simulation = new Simulation();
        commandManager = new CommandManager();

//        SerialCom serialCom = new SerialCom(null);
//        SendSerialMessageAction serialMessageAction = new SendSerialMessageAction(serialCom);
//        commandManager = new CommandManager(serialMessageAction);
//
//        String channelName = System.getenv("TWITCH_CHANNEL");
//        String twitchToken = System.getenv("TWITCH_TOKEN");
//
//        TwitchClient twitchClient = TwitchClientBuilder
//                .builder()
//                .withEnableHelix(true)
//                .withEnableChat(true)
//                .withChatAccount(new OAuth2Credential(System.getenv("TWITCH_CHANNEL"), twitchToken))
//                .build();
//
//        TwitchService twitchService = new TwitchService(twitchClient, channelName);
//
//        twitchClient.getEventManager().onEvent(IRCMessageEvent.class,
//                new TwitchChatMessageEventHandler(
//                        new SaveCommandAction(commandManager),
//                        new SendChatMessageAction(twitchService))
//        );
    }

    @Override
    public void draw() {
        background(177, 157, 216);

        showConsole();
        showLogo();
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

    private void showConsole(){
        fill(GUIConfig.ubuntuBlack);
        rect(0, 0, 350, height);

        textFont(GUIConfig.consoleFont);

        String lastUser = "";
        int lineYPos = 270;
        for(UserCommand command:commandManager.getCommandList()){

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

}
