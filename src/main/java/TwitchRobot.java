import GUI.Canvas;
import GUI.GUIConfig;
import Twitch.TwitchService;
import Utils.FileReader;
import Utils.GeneralConfig;
import Utils.UserInputInterpreter;
import com.fazecast.jSerialComm.SerialPort;
import com.github.philippheuer.credentialmanager.domain.OAuth2Credential;
import com.github.twitch4j.TwitchClient;
import com.github.twitch4j.TwitchClientBuilder;
import exception.InvalidOptionChoice;
import processing.core.PApplet;
import processing.video.Capture;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TwitchRobot{
    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        String logo = FileReader.read("src/main/resources/text_files/logo.txt");
        System.out.println(logo);
        List<String> camDevices = Arrays.asList(Capture.list());
        List<SerialPort> serialPorts = Arrays.asList(SerialPort.getCommPorts());

        // Choose cam device
        System.out.println("\nAvailable webcam devices: ");
        boolean successful = false;
        while (!successful){
            try {
                GUIConfig.camDeviceName = UserInputInterpreter.choice(camDevices);
                successful = true;
            } catch (InvalidOptionChoice e){
                System.out.println(e.getMessage());
            }
        }

        // Choose serial port
        System.out.println("\nAvailable serial ports: ");
        successful = false;
        while (!successful){
            try {
                GeneralConfig.serialPort = UserInputInterpreter.choice(serialPorts);
                successful = true;
            } catch (InvalidOptionChoice e){
                System.out.println(e.getMessage());
            }
        }

        // Enable twitch.tv connection
        System.out.println("\nEnable twitch.tv connection?: ");
        successful = false;
        List<Boolean> booleanList = Arrays.asList(true, false);
        Boolean enableTwitch = null;
        while (!successful){
            try {
                enableTwitch = UserInputInterpreter.choice(booleanList);
                successful = true;
            } catch (InvalidOptionChoice e){
                System.out.println(e.getMessage());
            }
        }

        if(enableTwitch != null && enableTwitch){
            String channelName = System.getenv("TWITCH_CHANNEL");
            String twitchToken = System.getenv("TWITCH_TOKEN");

            System.out.println(channelName);
            System.out.println(twitchToken);

            GeneralConfig.twitchClient = TwitchClientBuilder
                    .builder()
                    .withEnableHelix(true)
                    .withEnableChat(true)
                    .withChatAccount(new OAuth2Credential(System.getenv("TWITCH_CHANNEL"), twitchToken))
                    .build();

            GeneralConfig.twitchService = new TwitchService(GeneralConfig.twitchClient, channelName);

        }

        PApplet.main(Canvas.class);
    }
}


