import java.util.Random;

public class AIplayer3 extends WheelOfFortuneAIGame implements WheelOfFortunePlayer {
    int index = 0;

    @Override
    public char nextGuess(String previousGuesses, String hiddenPhrase, String charIndex) {
        Random random = new Random();

        // Ensure you're working with the correct hidden phrase
        String currentHiddenPhrase = hiddenPhrase;
        int count = 0;

        // Count the non-whitespace characters in the hidden phrase
        for (int i = index; i < currentHiddenPhrase.length(); i++) {
            if (!Character.isWhitespace(currentHiddenPhrase.charAt(i))) {
                count++;
            }
        }

        // Increment index to move to the next position
        if (index < currentHiddenPhrase.length()) {
            index++;
        }

        // Determine which set of characters to guess based on count
        if (count <= 3) {
            // Guess from a limited set of common letters
            char[] commonChars = {'t', 'e', 'o', 'r', 'i', 'f', 's', 'n', 'h'};
            int r1 = random.nextInt(commonChars.length);
            return commonChars[r1];
        } else {
            // Guess from the full alphabet
            String alphabet = "abcdefghijklmnopqrstuvwxyz";
            int r1 = random.nextInt(alphabet.length());
            return alphabet.charAt(r1);
        }
    }

    @Override
    public String playerId() {
        return "AI Player 3";
    }

    @Override
    public void reset(GameRecord o) {
        // Reset the index for a new game (if needed)
        index = 0;
        // Additional reset logic if required
    }
}
