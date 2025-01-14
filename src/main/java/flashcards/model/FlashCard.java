package flashcards.model;

public class FlashCard {

    private String question;
    private String answer;

    private int deckID;

    private int cardID;

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

    public boolean isQuestionSide() {
        return questionSide;
    }

    public void setQuestionSide(boolean questionSide) {
        this.questionSide = questionSide;
    }

    private boolean questionSide;

    public FlashCard(String question, String answer) {
        this.question = question;
        this.answer = answer;
        this.questionSide = true;
    }

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
}
