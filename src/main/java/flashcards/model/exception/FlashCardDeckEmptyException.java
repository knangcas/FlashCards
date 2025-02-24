package flashcards.model.exception;

public class FlashCardDeckEmptyException extends RuntimeException{

    public FlashCardDeckEmptyException() {this("There are no cards in the selected deck");}

    public FlashCardDeckEmptyException(String message) { super(message);};
}
