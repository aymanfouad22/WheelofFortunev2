import java.util.Random;

/**
 * AIplayer1 class represents an AI player in the Wheel of Fortune game.
 * It extends the main game class and implements the player interface.
 */
public class AIplayer1 extends WheelOfFortuneAIGame implements WheelOfFortunePlayer {
    int count = 0; // Tracks the number of guesses made by this AI

    /**
     * Generates the next guess for the AI player based on the hidden phrase and previous guesses.
     * Alternates between vowels and non-vowels, starting with vowels.
     * 
     * @param hiddenPhrase The current state of the hidden phrase.
     * @param previousGuesses A string containing all previous guesses made.
     * @param charIndex Unused parameter for this implementation.
     * @return The next character guessed by the AI.
     */
    @Override
    public char nextGuess(String hiddenPhrase, String previousGuesses, String charIndex) {
        hiddenPhrase = updatedHiddenPhrase.toString(); // Update the hidden phrase state
        Random random = new Random();

        // Define sets of vowels and non-vowels
        String nonVowel = "qwrtpsdfghjklmzxcvbnQWRTPSDFGHJKLZXCCVBNM";
        String vowel = "euyioaEUIOA";

        // Generate random indices for selecting characters
        int r1 = random.nextInt(nonVowel.length());
        int r2 = random.nextInt(vowel.length());

        count++; // Increment the guess counter

        // Alternate between guessing vowels and non-vowels
        if (count < vowel.length()) {
            return vowel.charAt(r2); // Return a random vowel if count is within vowel limit
        } else {
            return nonVowel.charAt(r1); // Otherwise, return a random non-vowel
        }
    }

    /**
     * Returns the identifier for this AI player.
     * 
     * @return A string representing the player ID.
     */
    @Override
    public String playerId() {
        return "AiPlayer 1";
    }

    /**
     * Resets the game state for this AI player.
     * 
     * @param o The game record object to reset.
     * @throws UnsupportedOperationException If the method is not implemented.
     */
    @Override
    public void reset(GameRecord o) {
        // This method is not implemented for AIplayer1
        throw new UnsupportedOperationException("Unimplemented method 'reset'");
    }
}
