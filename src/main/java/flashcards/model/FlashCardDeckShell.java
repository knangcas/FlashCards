package flashcards.model;

public class FlashCardDeckShell{

    public FlashCardDeckShell(){}

    public FlashCardDeckShell(String name, String subject, String username, int deckID) {
        this.name = name;
        this.subject = subject;
        this.username = username;
        this.deckID = deckID;
    }

    private int deckID;

    private String name;

    private String username;

    private String subject;


    public int getDeckID() {
        return deckID;
    }


    public void setDeckID(int deckID) {
        this.deckID = deckID;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }


    public String getUsername() {
        return username;
    }


    public void setUsername(String username) {
        this.username = username;
    }


    public String getSubject() {
        return subject;
    }


    public void setSubject(String subject) {
        this.subject = subject;
    }
}
