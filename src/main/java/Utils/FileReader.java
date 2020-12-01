package Utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileReader {
    public static String read(String fileName){
        String output = "";

        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()){
                String data = scanner.nextLine();
                output = output.concat(data).concat("\n");
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            return "File " + fileName + " not found";
        }

        return output;
    }
}
