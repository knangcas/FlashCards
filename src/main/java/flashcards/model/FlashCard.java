package flashcards.model;

public class FlashCard {

    private String question;
    private String answer;

    private String deckID;

    private String cardID;

    public String getDeckID() {
        return deckID;
    }

    public void setDeckID(String deckID) {
        this.deckID = deckID;
    }

    public String getCardID() {
        return cardID;
    }

    public void setCardID(String cardID) {
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
