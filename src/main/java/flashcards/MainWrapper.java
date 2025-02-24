package flashcards;

import flashcards.Services.impl.JsonLoadSave;

import java.sql.SQLException;

public class MainWrapper {
    final public static String SERVICE = "SQL";
    public static void main(String[] args) throws SQLException {

        JsonLoadSave.loadFlashCardDecks();
        //HelloApplication.main(args);
    }
}
