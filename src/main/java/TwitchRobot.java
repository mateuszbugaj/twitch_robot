import GUI.Canvas;
import GUI.GUIConfig;
import Utils.FileReader;
import Utils.GeneralConfig;
import Utils.UserInputInterpreter;
import com.fazecast.jSerialComm.SerialPort;
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

        PApplet.main(Canvas.class);
    }
}
