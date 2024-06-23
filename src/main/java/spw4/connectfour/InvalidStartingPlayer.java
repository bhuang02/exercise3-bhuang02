package spw4.connectfour;

public class InvalidStartingPlayer extends IllegalArgumentException{
    public InvalidStartingPlayer(String message) {
        super(message);
    }
}
