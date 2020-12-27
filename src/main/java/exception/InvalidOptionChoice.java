package exception;

public class InvalidOptionChoice extends Exception{
    public InvalidOptionChoice(String input, int upperRange){
        super(String.format("Input '%s' is invalid. Must be an integer in range from 0 to %d", input, upperRange));
    }
}
