package flashcards;

import flashcards.Services.impl.JsonLoadSave;

import java.sql.SQLException;

public class MainWrapper {
    final public static String SERVICE = "SQL";
    //ideally swappable between SQL and MONGO, however mongo is not implemented yet.
    public static void main(String[] args) throws SQLException {

        HelloApplication.main(args);
    }
}
