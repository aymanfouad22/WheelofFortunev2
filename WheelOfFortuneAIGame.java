import java.util.ArrayList;
import java.util.Scanner;

public class WheelOfFortuneAIGame extends WheelofFortune implements WheelOfFortunePlayer {
    private String restart = ""; // Variable to track whether the game should restart
    public String phrase = ""; // Current phrase being guessed
    private int numberOfGames = 1; // Tracks the number of games played
    private int currentPlayerIndex = 0; // Tracks the current player's turn
    private int currentPhraseIndex = 0; // Tracks the current phrase being guessed
    private int remainingAttempts = 5; // Remaining attempts for the current game
    String updatedHiddenPhrase = ""; // Stores the current state of the hidden phrase
    private AllGamesRecord recordTracker = new AllGamesRecord(); // Tracks all game records
    private ArrayList<WheelOfFortuneAIGame> AiPlayers; // List of AI players

    /**
     * Initializes the list of AI players participating in the game.
     */
    public void initializeAIPlayers() {
        AiPlayers = new ArrayList<>();
        AiPlayers.add(new AIplayer1());
        AiPlayers.add(new AIplayer2());
        AiPlayers.add(new AIplayer3());
    }

    /**
     * Generates a unique identifier for the current player.
     * @return A string representing the player ID.
     */
    public String playerId() {
        return "Player " + (currentPlayerIndex + 1);
    }

    /**
     * Calculates the length of the given phrase, excluding whitespace.
     * @param phrase The phrase to analyze.
     * @return The number of non-whitespace characters in the phrase.
     */
    public int getPhraseLength(String phrase) {
        int phraseLength = 0;
        for (char c : phrase.toCharArray()) {
            if (!Character.isWhitespace(c)) {
                phraseLength++;
            }
        }
        return phraseLength;
    }

    /**
     * Executes the main game logic for a single player, allowing them to guess the phrase.
     * @return A GameRecord representing the outcome of the current game session.
     */
    @Override
    public GameRecord play() {
        System.out.println("****** Hello and Welcome to the Wheel of Fortune! ******");
        System.out.println("Instructions: A hidden phrase will be displayed, and you will guess its characters.\n"
                + "Type 1 character at a time. You have " + remainingAttempts + " failed attempts.");

        if (currentPhraseIndex >= phraseList.size()) {
            throw new IllegalStateException("No more phrases available.");
        }

        // Get the current phrase
        phrase = phraseList.get(currentPhraseIndex);
        int phraseLength = getPhraseLength(phrase);

        if (currentPlayerIndex >= AiPlayers.size()) {
            throw new IllegalStateException("Invalid player index.");
        }

        // Create a new game record
        GameRecord gameRecord = new GameRecord(0, AiPlayers.get(currentPlayerIndex).playerId(), numberOfGames, phraseLength);
        recordTracker.add(gameRecord);

        hiddenPhrase = new StringBuilder(generateHiddenPhrase(phrase));
        StringBuilder previousGuesses = new StringBuilder();
        boolean gameOver = false;

        // Game loop
        while (remainingAttempts > 0 && count > 0) {
            char guess1 = AiPlayers.get(currentPlayerIndex).nextGuess(hiddenPhrase.toString(), previousGuesses.toString(), phrase);
            char guess2 = Character.isUpperCase(guess1) ? Character.toLowerCase(guess1) : Character.toUpperCase(guess1);

            updatedHiddenPhrase = processGuess(hiddenPhrase, phrase, guess1, guess2, gameRecord);
            System.out.println("AI Guess: " + guess1);
            System.out.println("Current Hidden Phrase: " + updatedHiddenPhrase);

            // Update guesses and attempts
            if (hiddenPhrase.indexOf("*") == -1) {
                System.out.println("Congratulations! The AI won the game!");
                gameOver = true;
                break;
            } else if (Character.isLetter(guess1) && hiddenPhrase.toString().indexOf(guess1) == -1) {
                remainingAttempts--;
                System.out.println("Attempts left: " + remainingAttempts);
            }

            previousGuesses.append(guess1).append(guess2);
        }

        // End-of-game messages
        if (!gameOver && remainingAttempts == 0) {
            System.out.println("The AI lost! The phrase was: " + phrase);
        }

        System.out.println(gameRecord);
        System.out.println("Average score for " + AiPlayers.get(currentPlayerIndex).playerId() + ": " +
                recordTracker.playerAverage(AiPlayers.get(currentPlayerIndex).playerId()));

        // Prepare for the next game
        numberOfGames++;
        reset(gameRecord);
        return gameRecord;
    }

    /**
     * Determines whether to proceed to the next game.
     * @return True if the next game should start, false otherwise.
     */
    @Override
    public boolean playnext() {
        return true;
    }

    /**
     * Plays all phrases in the phrase list for all AI players sequentially.
     * @return An AllGamesRecord object summarizing all game sessions.
     */
    @Override
    public AllGamesRecord playAll() {
        for (currentPlayerIndex = 0; currentPlayerIndex < AiPlayers.size(); currentPlayerIndex++) {
            System.out.println("Player " + AiPlayers.get(currentPlayerIndex).playerId() + " is playing.");

            for (currentPhraseIndex = 0; currentPhraseIndex < phraseList.size(); currentPhraseIndex++) {
                play();
            }
            currentPhraseIndex = 0;

            // Display leaderboard for the current player
            System.out.println("Leaderboard for " + AiPlayers.get(currentPlayerIndex).playerId() + ": " +
                    recordTracker.highGameList(AiPlayers.get(currentPlayerIndex).playerId(), numberOfGames - 1));
        }

        // Display overall stats
        System.out.println("Overall average for all players: " + recordTracker.average());
        return recordTracker;
    }

    /**
     * Resets the game state for the next game.
     * @param gameRecord The current game record being reset.
     */
    @Override
    public void reset(GameRecord gameRecord) {
        remainingAttempts = 5; // Reset the number of attempts
        count = getPhraseLength(phrase); // Reset count to the length of the phrase
        hiddenPhrase = new StringBuilder(generateHiddenPhrase(phrase)); // Regenerate the hidden phrase
        gameRecord = new GameRecord(0, AiPlayers.get(currentPlayerIndex).playerId(), numberOfGames, getPhraseLength(phrase));
        recordTracker.add(gameRecord); // Add the new game record
    }

    /**
     * Gets a character guess from the AI or user (not implemented here).
     * @param previousGuess The previous guesses made.
     * @return The next guessed character.
     */
    @Override
    public char getGuess(String previousGuess) {
        throw new UnsupportedOperationException("Unimplemented method 'getGuess'");
    }

    /**
     * Determines the AI's next guess based on the game state (not implemented here).
     * @param hiddenPhrase The current state of the hidden phrase.
     * @param previousGuesses The string of previous guesses.
     * @param charIndex Unused in this context.
     * @return The AI's next guessed character.
     */
    @Override
    public char nextGuess(String hiddenPhrase, String previousGuesses, String charIndex) {
        throw new UnsupportedOperationException("Unimplemented method 'nextGuess'");
    }

    /**
     * The main entry point to start the AI-based Wheel of Fortune game.
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        WheelOfFortuneAIGame newGame = new WheelOfFortuneAIGame();
        newGame.initializeAIPlayers();
        AllGamesRecord record = newGame.playAll();
        System.out.println(record);
    }
}
