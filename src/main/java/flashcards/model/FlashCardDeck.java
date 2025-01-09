package flashcards.model;

import java.util.Stack;

public class FlashCardDeck{

    private Stack<FlashCard> deck = new Stack<>();

    private Stack<FlashCard> shuffle1;

    private Stack<FlashCard> shuffle2;

    private Stack<FlashCard> correct;

    private Stack<FlashCard> incorrect;

    private int size;



    private String name;

    public void addFlashCard(FlashCard card) {
        deck.push(card);
    }

    public FlashCardDeck(String name) {
        this.name = name;

        deck = new Stack<>();

        shuffle1 = new Stack<>();

        shuffle2 = new Stack<>();

        correct = new Stack<>();

        incorrect = new Stack<>();
    }

    public int getSize() {
        return deck.size();
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
