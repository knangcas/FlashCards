package flashcards.model.exception;

public class FlashcardsConnectionException extends RuntimeException{

    public FlashcardsConnectionException() {this("There was an connection error");}

    public FlashcardsConnectionException(String message) { super(message);};


}
