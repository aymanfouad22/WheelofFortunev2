import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;

public class Mastermind {

    // Define color codes
    private static final String COLORS = "RGBYOP"; // Red, Green, Blue, Yellow, Orange, Purple
    private static final int MAX_MISSES = 10; // Max allowed incorrect guesses (misses)

    // Player class to store player information and their score
    static class Player {
        String playerName;
        int numberOfGuesses;
        boolean hasGuessedCorrectly;
        int misses;

        Player(String playerName) {
            this.playerName = playerName;
            this.numberOfGuesses = 0;
            this.hasGuessedCorrectly = false;
            this.misses = 0; // Initialize misses to 0
        }

        void incrementGuesses() {
            this.numberOfGuesses++;
        }

        void setGuessedCorrectly(boolean status) {
            this.hasGuessedCorrectly = status;
        }

        void incrementMisses() {
            this.misses++;
        }

        @Override
        public String toString() {
            return playerName + " - Guesses: " + numberOfGuesses + ", Misses: " + misses + (hasGuessedCorrectly ? " (Correct)" : " (Incorrect)");
        }
    }

    // AllGamesRecord class to track all players and their results
    static class AllGamesRecord {
        ArrayList<Player> players;

        AllGamesRecord() {
            players = new ArrayList<>();
        }

        void addPlayer(Player player) {
            players.add(player);
        }

        void displayScores() {
            for (Player player : players) {
                System.out.println(player);
            }
        }

        int getTotalGamesPlayed() {
            return players.size();
        }
    }

    public static void main(String[] args) {
        // Generate a secret code
        String secret = generateSecret();
        Scanner scanner = new Scanner(System.in);

        // Create an AllGamesRecord instance to keep track of players
        AllGamesRecord allGamesRecord = new AllGamesRecord();

        // Add a single player to the game
        System.out.print("Enter your player name: ");
        String playerName = scanner.nextLine();
        Player player = new Player(playerName);
        allGamesRecord.addPlayer(player);

        boolean gameOver = false;
        int numberOfGuesses = 0;

        System.out.println("Welcome to Mastermind!");
        System.out.println("Try to guess the secret code.");
        System.out.println("Valid colors: R (Red), G (Green), B (Blue), Y (Yellow), O (Orange), P (Purple)");
        System.out.println("Enter a 4-letter guess, e.g., RGRY.");

        // Game loop
        while (!gameOver) {
            System.out.print("Enter your guess: ");
            String guess = scanner.nextLine().toUpperCase();

            // Validate the guess
            if (!isValidGuess(guess)) {
                System.out.println("Invalid guess! Please use only R, G, B, Y, O, P and enter exactly 4 characters.");
                continue;
            }

            // Compute exact and partial matches
            int exactMatches = computeExacts(secret, guess);
            int partialMatches = computePartials(secret, guess);

            numberOfGuesses++;
            player.incrementGuesses(); // Increment the number of guesses for the player
            System.out.println(exactMatches + " exact, " + partialMatches + " partial.");

            // If no exact or partial matches, increment misses
            if (exactMatches == 0 && partialMatches == 0) {
                player.incrementMisses();
                System.out.println("Misses: " + player.misses);
            }

            // Check if the guess is correct
            if (exactMatches == 4) {
                player.setGuessedCorrectly(true);
                gameOver = true;
                System.out.println("Congratulations, " + playerName + "! You guessed the secret code in " + numberOfGuesses + " guesses.");
            }

            // Check if the player has exceeded the maximum number of misses
            if (player.misses >= MAX_MISSES) {
                gameOver = true;
                System.out.println("Game Over! You've exceeded the maximum number of misses. The secret code was: " + secret);
            }
        }

        // Display the scores for all players (currently, only one player)
        System.out.println("\nGame Over! Scores: ");
        allGamesRecord.displayScores();

        scanner.close();
    }

    // Method to generate a random 4-letter secret code
    private static String generateSecret() {
        Random random = new Random();
        StringBuilder secret = new StringBuilder();
        
        // Generate a secret code of 4 characters
        for (int i = 0; i < 4; i++) {
            char color = COLORS.charAt(random.nextInt(COLORS.length()));
            secret.append(color);
        }
        
        return secret.toString();
    }

    // Method to check if a guess is valid
    private static boolean isValidGuess(String guess) {
        if (guess.length() != 4) {
            return false; // Guess must have exactly 4 characters
        }
        
        // Check if each character in the guess is one of the allowed colors
        for (char c : guess.toCharArray()) {
            if (COLORS.indexOf(c) == -1) {
                return false; // Invalid color character
            }
        }
        
        return true;
    }

    // Method to compute exact matches (correct color in the correct position)
    private static int computeExacts(String secret, String guess) {
        int exactMatches = 0;
        
        // Loop through the guess and secret to count exact matches
        for (int i = 0; i < 4; i++) {
            if (secret.charAt(i) == guess.charAt(i)) {
                exactMatches++;
            }
        }
        
        return exactMatches;
    }

    // Method to compute partial matches (correct color in the wrong position)
    private static int computePartials(String secret, String guess) {
        int partialMatches = 0;
        
        // We need to check for colors in the guess that exist in the secret but not in the same position
        // Make a copy of the secret and guess to track unmatched characters
        StringBuilder secretBuilder = new StringBuilder(secret);
        StringBuilder guessBuilder = new StringBuilder(guess);

        // Remove exact matches first (since we don't want to count them as partials)
        for (int i = 0; i < 4; i++) {
            if (secret.charAt(i) == guess.charAt(i)) {
                secretBuilder.setCharAt(i, ' ');
                guessBuilder.setCharAt(i, ' ');
            }
        }

        // Now count partial matches
        for (int i = 0; i < 4; i++) {
            if (guessBuilder.charAt(i) != ' ' && secretBuilder.indexOf(String.valueOf(guessBuilder.charAt(i))) != -1) {
                partialMatches++;
                secretBuilder.setCharAt(secretBuilder.indexOf(String.valueOf(guessBuilder.charAt(i))), ' ');
            }
        }

        return partialMatches;
    }
}
