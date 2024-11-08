import java.util.Random;

/**
 * AIplayer2 class represents an AI player in the Wheel of Fortune game.
 * It generates random guesses for the hidden phrase.
 */
public class AIplayer2 extends WheelOfFortuneAIGame implements WheelOfFortunePlayer {

    /**
     * Generates the next random guess for the AI player.
     * The guess is a randomly chosen character within the ASCII range for letters.
     * 
     * @param previousGuesses A string containing all previously guessed characters.
     * @param hiddenPhrase The current state of the hidden phrase.
     * @param charIndex Unused parameter for this implementation.
     * @return A randomly chosen character.
     */
    @Override
    public char nextGuess(String previousGuesses, String hiddenPhrase, String charIndex) {
        Random random = new Random();

        // Generate a random uppercase or lowercase character
        int r = 65 + random.nextInt(57); // Random number in the range of ASCII letters
        return (char) r; // Convert the random number to a character
    }

    /**
     * Returns the identifier for this AI player.
     * 
     * @return A string representing the player ID.
     */
    @Override
    public String playerId() {
        return "AiPlayer 2";
    }

    /**
     * Resets the game state for this AI player.
     * 
     * @param o The game record object to reset.
     * @throws UnsupportedOperationException If the method is not implemented.
     */
    @Override
    public void reset(GameRecord o) {
        // This method is not implemented for AIplayer2
        throw new UnsupportedOperationException("Unimplemented method 'reset'");
    }
}
