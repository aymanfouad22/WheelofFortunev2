import java.util.Scanner;

public class WheelOfFortuneUserGame extends WheelofFortune implements WheelOfFortunePlayer {

    private String restart = ""; // Variable to control game restart
    public String phrase = ""; // Current phrase to be guessed
    private int numberOfGames = 1; // Tracks the number of games played
    private int playerNum = 1; // Player number for tracking
    private int n = 5; // Number of failed attempts allowed
    private int phraseLength = 0; // Length of the current phrase
    AllGamesRecord o = new AllGamesRecord(); // Record object to track game stats

    /**
     * Returns the unique player identifier.
     * @return Player identifier as a string.
     */
    @Override
    public String playerId() {
        return "Player " + playerNum;
    }

    /**
     * Calculates the length of the phrase, excluding whitespace characters.
     * @param phrase The phrase to evaluate.
     * @return The length of the phrase without spaces.
     */
    public int getPhraseLength(String phrase) {
        int phraseLength = 0;
        for (int i = 0; i < phrase.length(); i++) {
            if (!Character.isWhitespace(phrase.charAt(i))) {
                phraseLength++;
            }
        }
        return phraseLength;
    }

    /**
     * Handles the main game logic for the current player.
     * @return The game record of the current play session.
     */
    @Override
    public GameRecord play() {
        System.out.println("****** Hello and Welcome to the Wheel of Fortune! ******");
        System.out.println("Instructions: A hidden phrase will be prompted, and you will have to guess the characters of this phrase.");
        System.out.println("Only type 1 character at a time. You have " + n + " failed attempts possible.");

        // Select a random phrase for the current game
        phrase = o.randomPhrase();
        System.out.println("Phrase Length: " + getPhraseLength(phrase));
        GameRecord gameRecord = new GameRecord(0, playerId(), numberOfGames, getPhraseLength(phrase));
        o.add(gameRecord);

        StringBuilder hiddenPhrase = new StringBuilder(generateHiddenPhrase(phrase));
        boolean gameOver = false;
        Scanner in = new Scanner(System.in);

        // Main game loop
        while (n > 0 && count > 0) {
            char guess1 = getGuess(phrase);
            char guess2 = Character.isUpperCase(guess1) ? Character.toLowerCase(guess1) : Character.toUpperCase(guess1);
            String updatedHiddenPhrase = processGuess(hiddenPhrase, phrase, guess1, guess2, gameRecord);

            // Display progress
            System.out.println("Current Hidden Phrase: " + updatedHiddenPhrase);

            // Check and update attempts or completion status
            if (hiddenPhrase.indexOf("*") == -1) {
                System.out.println("Congratulations! You won the game!");
                gameOver = true;
                break;
            } else if (n == 0) {
                System.out.println("You lost! The phrase was: " + phrase);
                gameOver = true;
            }

            if (!Character.isLetter(guess1)) {
                System.out.println("Warning: The character should be a letter.");
            }

            if (previousGuesses.toString().indexOf(guess1) != -1 || previousGuesses.toString().indexOf(guess2) != -1) {
                System.out.println("Warning: You have already made this guess!");
            }

            previousGuesses.append(guess1).append(guess2);
        }

        // Display game stats and options
        System.out.println(gameRecord);
        System.out.println("Your average: " + o.playerAverage(playerId()));
        System.out.println("Overall average for all players: " + o.average());
        System.out.println("Leaderboard: " + o.highGameList(playerId(), numberOfGames - 1));
        System.out.println("To restart the game, type Y. To finish, type N.");

        numberOfGames++;
        restart = in.next();
        reset(gameRecord);

        if (!gameOver) {
            System.out.println("Game Over!");
        }

        return o.gameRecords.get(numberOfGames - 1);
    }

    /**
     * Checks if the next game should proceed.
     * @return True if the next game should continue; otherwise false.
     */
    @Override
    public boolean playnext() {
        return true;
    }

    /**
     * Plays all phrases in the phrase list for a single player.
     * @return The record of all games played.
     */
    @Override
    public AllGamesRecord playAll() {
        for (int i = 0; i < o.phraseList.size(); i++) {
            if (playnext()) {
                play();
            }
        }
        return o;
    }

    /**
     * Resets the game state for a new game or player.
     * @param gameRecord The current game record being reset.
     */
    @Override
    public void reset(GameRecord gameRecord) {
        if (restart.equalsIgnoreCase("Y")) {
            n = 5;
            count = getPhraseLength(phrase);
            previousGuesses = new StringBuilder("");
            hiddenPhrase = new StringBuilder(generateHiddenPhrase(phrase));
            gameRecord = new GameRecord(0, playerId(), numberOfGames, getPhraseLength(phrase));
            o.add(gameRecord);
        } else if (restart.equalsIgnoreCase("N")) {
            playerNum++;
            o.add(new GameRecord(0, playerId(), 0, getPhraseLength(phrase)));
            n = 5;
            count = 0;
            previousGuesses = new StringBuilder("");
            hiddenPhrase = new StringBuilder(generateHiddenPhrase(phrase));
        }
    }

    /**
     * Collects the user's next character guess.
     * @param previousGuess Previous guesses made by the user.
     * @return The next guessed character.
     */
    @Override
    public char getGuess(String previousGuess) {
        Scanner in = new Scanner(System.in);
        System.out.print("Enter your guess: ");
        String str = in.nextLine();
        return str.charAt(0);
    }

    /**
     * Main entry point to start the game.
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        WheelOfFortuneUserGame newGame = new WheelOfFortuneUserGame();
        AllGamesRecord record = newGame.playAll();
        System.out.println(record);
    }

    @Override
    public char nextGuess(String hiddenPhrase, String previousGuesses, String charIndex) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'nextGuess'");
    }
}
