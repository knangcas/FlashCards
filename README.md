# FlashCard application.
## Currently in progress.

### Last Updated

Updated February 24th, 2025
- Added "offline mode" which is based on JSON implementation.
- Single user supported for offline mode. "Signing in" will still query MySQL database.
- Fixed an issue where a selecting a deck with 0 cards would cause an error.

### About
Personal JavaFX project to get familiar with working with a few technologies.

- Supports multiple users.

- Each user can create decks.

- Each deck can contain a (currently unlimited) number of cards.

- Each card has a question and answer.

- User can skip a card, which goes into a "skipped" stack.

- Upon completion of the whole deck, the user can select a new deck, restart & shuffle the current deck, or load the skipped cards back onto the deck.

- User and FlashCard Data is stored in a DB.

- Currently supports MySQL (mySQL with jdbc driver).

### Current Plans

- Make a "Home Page" which features recommendations on which decks to study, based on date last studied.

- Working on support for MongoDB.



### Screenshots

![Screenshot1](https://github.com/knangcas/FlashCards/blob/main/Screenshots/ss1.png?raw=true)

![Screenshot2](https://github.com/knangcas/FlashCards/blob/main/Screenshots/ss2.png?raw=true)

![Screenshot3](https://github.com/knangcas/FlashCards/blob/main/Screenshots/ss3.png?raw=true)

![Screenshot4](https://github.com/knangcas/FlashCards/blob/main/Screenshots/ss4.png?raw=true)

![Screenshot5](https://github.com/knangcas/FlashCards/blob/main/Screenshots/ss5.png?raw=true)

![Screenshot6](https://github.com/knangcas/FlashCards/blob/main/Screenshots/ss6.png?raw=true)


Uses 
-Java JDK 19
-JavaFX 21.0.5
-Gradle 8.0.2
-Local MySQL Server



