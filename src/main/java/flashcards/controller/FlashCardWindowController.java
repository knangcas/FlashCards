package flashcards.controller;

import flashcards.model.FlashCard;
import flashcards.model.FlashCardDeck;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class FlashCardWindowController {

    @FXML
    private Label progressLabel;

    @FXML
    private Label contentLabel;

    @FXML
    private Label deckNameLabel;

    private FlashCardDeck deck;

    private FlashCard currentCard;


    public void initializeDeck(FlashCardDeck deck) {
        //this.deck = deck;
        // temporary

        deck = new FlashCardDeck("TestDeck");
        FlashCard card1 = new FlashCard("What is a pointer?", "A pointer is a reference to an address in memory.");
        FlashCard card2 = new FlashCard("Does python need semicolons?", "No");

        deckNameLabel.setText(deck.getName());
        contentLabel.setText(card1.getQuestion());
        currentCard = card1;
    }
    public void test(MouseEvent mouseEvent) {
        flip();
        System.out.println("clicked");
    }

    private void flip() {
        if (currentCard.isQuestionSide()) {
            contentLabel.setText(currentCard.getAnswer());
            currentCard.setQuestionSide(false);
        } else {
            contentLabel.setText(currentCard.getQuestion());
            currentCard.setQuestionSide(true);
        }

    }
}
