package Utils;

import Twitch.TwitchService;
import com.fazecast.jSerialComm.SerialPort;
import com.github.twitch4j.TwitchClient;

import java.util.ArrayList;
import java.util.List;

public class GeneralConfig {
    public static SerialPort serialPort;
    public static List<String > bannedForSession = new ArrayList<>();
    public static TwitchClient twitchClient;
    public static TwitchService twitchService;
}
