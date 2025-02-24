package flashcards.model;

public class FlashCardShell{

    public FlashCardShell(){}

    public FlashCardShell(String question, String answer, int deckID, int cardID) {
        this.question = question;
        this.answer = answer;
        this.deckID = deckID;
        this.cardID = cardID;
    }

    private String question;

    private String answer;

    private int deckID;

    private int cardID;


    public String getQuestion() {
        return question;
    }


    public void setQuestion(String question) {
        this.question = question;
    }


    public String getAnswer() {
        return answer;
    }


    public void setAnswer(String answer) {
        this.answer = answer;
    }


    public int getDeckID() {
        return deckID;
    }


    public void setDeckID(int deckID) {
        this.deckID = deckID;
    }


    public int getCardID() {
        return cardID;
    }


    public void setCardID(int cardID) {
        this.cardID = cardID;
    }
}
