package flashcards;

import flashcards.Services.impl.JsonLoadSave;

import java.sql.SQLException;

public class MainWrapper {
    final public static String SERVICE = "JSON";
    public static void main(String[] args) throws SQLException {


        HelloApplication.main(args);
    }
}
