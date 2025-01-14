package flashcards.model;

import java.util.*;

public class FlashCardDeck{


    private List<FlashCard> cards;

    private Stack<FlashCard> deck;

    private Stack<FlashCard> shuffle1;

    private Stack<FlashCard> shuffle2;

    private Stack<FlashCard> correct;

    private Stack<FlashCard> incorrect;

    private Queue<FlashCard> skipped;

    private int size;

    private int totalCards;

    private int deckID;

    private String name;

    public int getDeckID() {
        return deckID;
    }

    public void setDeckID(int deckID) {
        this.deckID = deckID;
    }

    public void addFlashCard(FlashCard card) {
        deck.push(card);
        FlashCard cardCopy = new FlashCard(card.getQuestion(), card.getAnswer());
        cardCopy.setCardID(card.getCardID());
        cardCopy.setDeckID(card.getDeckID());

        cards.add(cardCopy);

    }

    public List<FlashCard> getCardList() {
        return cards;
    }

    public void removeCard(int cardID) {
        if (deck.size() != totalCards) {
            resetDeck();
        }

        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i).getCardID() == cardID) {
                cards.remove(i);
                i = cards.size();
            }
        }

        for (int i = 0; i < totalCards; i++) {
            shuffle1.push(deck.pop());
            if (shuffle1.peek().getCardID()==cardID) {
                shuffle1.pop();
                i = totalCards;
            }
        }

        resetDeck();
        totalCards--;
    }

    public void updateCard(int cardID, String question, String answer) {
        if (deck.size() != totalCards) {
            resetDeck();
        }

        for (int i = 0; i < cards.size(); i++) {
            if (cards.get(i).getCardID() == cardID) {
                cards.get(i).setQuestion(question);
                cards.get(i).setAnswer(answer);
            }
        }


        for (int i = 0; i < totalCards; i++) {

            shuffle1.push(deck.pop());

            if (shuffle1.peek().getCardID() == cardID) {
                shuffle1.peek().setQuestion(question);
                shuffle1.peek().setAnswer(answer);
                i = totalCards;
            }
        }
        resetDeck();

    }

    public FlashCardDeck(String name) {
        this.name = name;

        deck = new Stack<>();

        shuffle1 = new Stack<>();

        shuffle2 = new Stack<>();

        correct = new Stack<>();

        incorrect = new Stack<>();

        skipped = new LinkedList<>();

        cards = new ArrayList<>();
    }

    public FlashCard getCard() {
        if (!deck.isEmpty()) {
            return deck.peek();
        }

        return null;
    }


    public void skipCard() {
        skipped.add(deck.pop());
    }

    public void correctChoice() {
        correct.push(deck.pop());
    }

    public void incorrectChoice() {
        incorrect.push(deck.pop());
    }


    public boolean isEmpty() {
        return deck.isEmpty();
    }


    public int getTotalCards(){
        return deck.size() + shuffle1.size() + shuffle2.size() + correct.size() + incorrect.size() + skipped.size();
    }

    public int getSize() {
        return deck.size();
    }

    public void shuffle() {
        resetDeck();
        Random rand = new Random();
        while (deck.size() > 0) {
            int n1 = rand.nextInt(100);
            int n2 = rand.nextInt(100);

            if (n1>n2) {
                shuffle1.push(deck.pop());
            } else if (n2<n1) {
                shuffle2.push(deck.pop());
            } else {
                correct.push(deck.pop());
            }
        }
        resetDeck();
    }

    private void resetDeck() {
        if (shuffle1.size() > 0) {
            resetDeckHelper(shuffle1);
        }
        if (shuffle2.size() > 0) {
            resetDeckHelper(shuffle2);
        }
        if (correct.size() > 0) {
            resetDeckHelper(correct);
        }
        if (incorrect.size() > 0 ) {
            resetDeckHelper(incorrect);
        }
        if (skipped.size() > 0) {
            while (skipped.size()>0) {
                deck.push(skipped.remove());
            }
        }
    }

    private void resetDeckHelper(Stack<FlashCard> cards) {
        for (int i = 0; i < cards.size() ; i++) {
            deck.push(cards.pop());
        }
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
