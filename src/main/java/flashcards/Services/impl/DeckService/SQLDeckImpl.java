package flashcards.Services.impl.DeckService;

import flashcards.Services.DeckService;
import flashcards.Services.impl.SQLVariables;
import flashcards.model.FlashCard;
import flashcards.model.FlashCardDeck;
import flashcards.model.User;
import flashcards.model.exception.FlashCardNullException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLDeckImpl implements DeckService {

    Connection conn;

    public SQLDeckImpl() {}

    private void connect() {
        try {
            conn = DriverManager.getConnection(SQLVariables.SQLADDRESS, SQLVariables.SQLUSER, SQLVariables.SQLPASSWORD);
            conn.setAutoCommit(false);
        } catch (SQLException e) {
            System.out.println("could not connect");
        }
    }



    @Override
    public FlashCardDeck getDeck(int deckID) throws SQLException {

        connect();
        FlashCardDeck rval = null;

        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        ResultSet resultSet = null;
        ResultSet resultSet2 = null;
        //String query = "SELECT * FROM CARDS WHERE deckID = ?";
        String query = "SELECT CARDS.question, CARDS.answer, DECKS.deckID, DECKS.deckName, CARDS.cardID, decks.subject " +
                "FROM DECKS INNER JOIN CARDS ON DECKS.deckID = CARDS.deckID" +
                " WHERE DECKS.deckID = ?";
        String query2 = "SELECT decks.deckName FROM DECKS WHERE decks.deckID = ?";
        try {
            ps1 = conn.prepareStatement(query);
            ps1.setInt(1, deckID);
            resultSet = ps1.executeQuery();
            rval = new FlashCardDeck("");
            rval.setDeckID(deckID);
            //question, answer, deckID (FK), deckName, CARDS.cardID
            boolean firstPass = false;
            while(resultSet.next()) {

                if (!firstPass) {
                    rval.setName(resultSet.getString(4));
                    //rval.setDeckID(resultSet.getInt(3));
                    rval.setSubject(resultSet.getString(6));
                } //small optimization
                FlashCard flashCard;
                flashCard = new FlashCard(resultSet.getString(1), resultSet.getString(2));
                flashCard.setDeckID(deckID);
                flashCard.setCardID(resultSet.getInt(5));
                rval.addFlashCard(flashCard);
                firstPass = true;
            }

            if (rval.getName().equals("")) {
                ps2 = conn.prepareStatement(query2);
                ps2.setInt(1, deckID);
                resultSet2 = ps2.executeQuery();
                while (resultSet2.next()) {
                    rval.setName(resultSet2.getString(1));

                }
            }



        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (ps1 != null) {
                ps1.close();
            }
            if (conn != null) {
                conn.close();
            }
            if (resultSet != null) {
                resultSet.close();
            }
            if (resultSet2 != null) {
                resultSet2.close();
            }
        }


        return rval;
    }



    @Override
    public List<Integer> getDecks(User user) {
        connect();
        List<Integer> rval = new ArrayList<>();


        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        String query = "SELECT decks.deckID, decks.deckName FROM USERS INNER JOIN DECKS " +
                "ON users.username = decks.username WHERE users.username =?";

        try {
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, user.getUsername());
            resultSet = preparedStatement.executeQuery();
            while(resultSet.next()) {
                rval.add((resultSet.getInt(1)));
                //System.out.println("deckID returned: " + resultSet.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
            //TODO throw up callstack
        }

        return rval;
    }

    @Override
    public int createDeck(FlashCardDeck deck, String username) throws SQLException {
        connect();
        String query = "Insert into decks (deckName, username) values (?,?)";
        PreparedStatement preparedStatement = null;
        int result;
        try {
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, deck.getName());
            preparedStatement.setString(2, username);
            result = preparedStatement.executeUpdate();
            conn.commit();
            if (result == 0) {
                //name has to be unique
                //return false;
            }

            if (result > 0) {
                //return true;
            }
        } catch (SQLException e) {
            //name has to be unique
        }   finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (conn != null) {
                conn.close();
            }
        }


        return getDeckByName(deck.getName(), username);
    }

    private int getDeckByName(String deckName, String username) throws SQLException {
        //works because deckNames have a unique constraint with usernames.
        connect();
        int rval = 0;
        String query = "select * from decks where decks.username = ? and  decks.deckName = ?";
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, deckName);
            resultSet = preparedStatement.executeQuery();

            while(resultSet.next()) {
                rval = resultSet.getInt(1);
            }


        } catch (SQLException e) {
            //name has to be unique
        }   finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (conn != null) {
                conn.close();
            }
        }

        return rval;

    }


    @Override
    public boolean deleteDeck(FlashCardDeck deck) throws SQLException {
        int deckID = deck.getDeckID();

        String query = "DELETE FROM CARDS WHERE cards.deckID = ?";
        String query2 = "DELETE FROM DECKS WHERE decks.deckID = ?";

        PreparedStatement preparedStatement = null;
        PreparedStatement preparedStatement2 = null;
        int result, result2;
        connect();
        try {
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setInt(1, deckID);
            result = preparedStatement.executeUpdate();
            if (result > 0) {
                System.out.println(deckID + " cards has been deleted");
            }
            if (result == 0 ) {
                System.out.println("Nothing deleted");
            }

            preparedStatement2 = conn.prepareStatement(query2);
            preparedStatement2.setInt(1, deckID);
            result2 = preparedStatement2.executeUpdate();
            if (result > 0) {
                System.out.println(deckID + " has been deleted");
            }
            if (result == 0 ) {
                System.out.println("Nothing deleted");
            }

            conn.commit();

        } catch (SQLException e) {

        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (conn != null) {
                conn.close();
            }
        }

        return false;
    }


    @Override
    public boolean updateDeck(FlashCardDeck deck) throws SQLException {
        String deckName = deck.getName();
        String deckSubject = deck.getSubject();
        int deckID = deck.getDeckID();


        connect();
        String query;
        int result;

        query = "UPDATE decks Set decks.deckName = ?, decks.subject = ? Where decks.deckID = ?";

        //query = "update decks set decks.deckName = ? where decks.deckID = ?";
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, deckName);
            preparedStatement.setString(2, deckSubject);
            preparedStatement.setInt(3, deckID);
            result = preparedStatement.executeUpdate();
            conn.commit();
            if (result > 0) {
                //return true;
            }

            if (result == 0) {
                //return false;
            }
        } catch (SQLException e) {
            System.out.println("sqlerror");
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (conn != null) {
                conn.close();
            }
        }

        //grab deckID, get list of cards
        //
        return false;
    }

    @Override
    public boolean updateCard(FlashCard card) throws SQLException {
        int cardID = card.getCardID();
        int deckID = card.getDeckID();

        connect();
        String query = "Update cards Set cards.question = ?, cards.answer = ? Where cards.cardID = ?";
        PreparedStatement preparedStatement = null;
        //note, this can also be done with the updateXXX methods with a SELECT statement too.
        //quesiton, answer, deckID, cardID
        try {
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, card.getQuestion());
            preparedStatement.setString(2, card.getAnswer());
            preparedStatement.setInt(3, cardID);


            int result = preparedStatement.executeUpdate();
            conn.commit();
            if (result == 0) {
                throw new FlashCardNullException();
            }
            if (result > 0) {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (conn != null) {
                conn.close();
            }


        }
        return false;
    }

    private void updateCardHelper() {

    }

    @Override
    public boolean addCard(FlashCard card) throws SQLException {
        connect();
        String query = "Insert into cards (question,answer,deckID) values(?,?,?)";
        int result;
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = conn.prepareStatement(query);
            preparedStatement.setString(1, card.getQuestion());
            preparedStatement.setString(2, card.getAnswer());
            preparedStatement.setInt(3, card.getDeckID());
            result = preparedStatement.executeUpdate();

            if (result == 0) {
                System.out.println("insert failed");
            }
            if (result > 0) {
                System.out.println("insert success");
                conn.commit();
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (conn != null) {
                conn.close();
            }

        }
        return false;
    }

    @Override
    public boolean deleteCard(int cardID) throws SQLException {
            connect();
            int result;
            PreparedStatement preparedStatement = null;
            String query = "DELETE from CARDS where cardID = ?";
            try {
                preparedStatement = conn.prepareStatement(query);
                preparedStatement.setInt(1, cardID);
                result = preparedStatement.executeUpdate();
                if (result == 0) {
                    return false;
                }
                if (result > 0) {
                    return true;
                }
            } catch (SQLException e) {

            } finally {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (conn != null) {
                    conn.close();
                }
            }

        return false;
    }
}
