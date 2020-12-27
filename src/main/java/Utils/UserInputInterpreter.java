package Utils;

import exception.InvalidOptionChoice;

import java.util.List;
import java.util.Scanner;

public class UserInputInterpreter {
    public static <T> T choice(List<T> options) throws InvalidOptionChoice {
        if(options.size() == 0){
            System.out.println("No option to choose from.");
            return null;
        }

        System.out.println("Press enter to omit.");
        for (int i = 0; i < options.size(); i++) {
            System.out.printf("(%d) %s \n", i, options.get(i).toString());
        }

        System.out.print("Type number of choice: ");
        String input = new Scanner(System.in).nextLine();
        try{
            if(input.isEmpty()){
                return null;
            }
            int numberOfChoice = Integer.parseInt(input);
            return options.get(numberOfChoice);
        } catch (NumberFormatException | IndexOutOfBoundsException e){
            throw new InvalidOptionChoice(input, options.size()-1);
        }
    }
}
