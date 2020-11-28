package GUI;

import Managment.CommandManager;
import Managment.SaveCommandAction;
import Managment.UserCommand;
import Robot.SendSerialMessageAction;
import Robot.SerialCom;
import Twitch.SendChatMessageAction;
import Twitch.TwitchChatMessageEventHandler;
import Twitch.TwitchService;
import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.TwitchClientBuilder;
import com.github.twitch4j.chat.events.channel.IRCMessageEvent;
import processing.core.PApplet;
import processing.core.PFont;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Canvas extends PApplet {
    private Simulation simulation;
    private CommandManager commandManager;
    private PFont ubuntuLogoFont;
    private PFont ubuntuConsoleFont;


    @Override
    public void settings() {
        fullScreen(P3D);
    }

    @Override
    public void setup() {
        textSize(15);
        textAlign(LEFT, TOP);
        ubuntuLogoFont = createFont("UbuntuMono-Regular.ttf", 15);
        ubuntuConsoleFont = createFont("UbuntuMono-Regular.ttf", 20);

        Simulation.p = this;
        simulation = new Simulation();

        SerialCom serialCom = new SerialCom(null);
        SendSerialMessageAction serialMessageAction = new SendSerialMessageAction(serialCom);
        commandManager = new CommandManager(serialMessageAction);

        String channelName = System.getenv("TWITCH_CHANNEL");
        String twitchToken = System.getenv("TWITCH_TOKEN");

        TwitchClient twitchClient = TwitchClientBuilder
                .builder()
                .withEnableHelix(true)
                .withEnableChat(true)
                .withChatAccount(new OAuth2Credential("boogieman002", twitchToken))
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
        background(180);

        showConsole();
        showLogo();
        showHelp();
        simulation.show(1500, 800, -0.2f, 0.3f);
    }

    private void showLogo(){
        fill(204, 204, 204);
        String logo = """
                 ______               __          __    \s
                /_  __/ _    __   _  / /_  ____  / /    \s
                 / /   | |/|/ / / / / __/ / __/ / _ \\   \s
                /_/    |__,__/ /_/  \\__/  \\__/_/_//_/   \s
                  ____  ___    / /    ___    / /_       \s
                 / __/ / _ \\  / _ \\  / _ \\  / __/       \s
                /_/    \\___/ /_.__/  \\___/  \\__/        \s
                https://twitch.com/boogieman002\n""";

        textFont(ubuntuLogoFont);
        text(logo, 10, 0);
    }

    private void showConsole(){
        fill(12, 12 ,12);
        rect(0, 0, 350, height);

        textFont(ubuntuConsoleFont);

        String lastUser = "";
        int lineYPos = 200;
        for(UserCommand command:commandManager.getCommandList()){

            String userName;
            if(command.getUserName().equals(lastUser)){
                userName = IntStream.range(0, command.getUserName().length()).mapToObj(i -> " ").collect(Collectors.joining());
                text(userName, 10, lineYPos);
            } else {
                lineYPos += ubuntuConsoleFont.getSize()*0.3;
                fill(22, 198, 12);
                lastUser = command.getUserName();
                userName = command.getUserName();
                text(userName, 10, lineYPos);

                fill(204, 204, 204);
                text("$ ", 10 + textWidth(userName), lineYPos);
            }

            String content = command.getContent().concat("\n");

            // rect in case command don't fit in console
            fill(12, 12 ,12);
            rect(10 + textWidth(userName) + textWidth("$ "), lineYPos, 10 + textWidth(content), ubuntuConsoleFont.getSize());

            fill(204, 204, 204);
            text(content, 10 + textWidth(userName) + textWidth("$ "), lineYPos);
            lineYPos += ubuntuConsoleFont.getSize()*1.2;
        }
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
        text("HELP", 1550, 10);
        textSize(18);
        text(content, 1550, 50);
    }

}
