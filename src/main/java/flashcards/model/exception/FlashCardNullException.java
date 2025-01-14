package flashcards.model.exception;

public class FlashCardNullException extends RuntimeException{

    public FlashCardNullException() {
        this("FlashCard does not exist.");
    }

    public FlashCardNullException(String message) {super(message);}
}
