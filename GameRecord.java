import java.util.ArrayList;

public class GameRecord extends WheelofFortune implements Comparable<GameRecord> {
    private long startTime;
    public int score;
    public int misses = 0;
    public String playerId;
    public int numberOfGames;
    public int phraseLength; // fixed typo in the field name

    // Constructor
    public GameRecord(int score, String playerId, int numberOfGames, int phraseLength) {
        this.startTime = System.currentTimeMillis(); // Capture start time per instance
        this.score = score;
        this.playerId = playerId;
        this.numberOfGames = numberOfGames;
        this.phraseLength = phraseLength;
    }

    // Implement compareTo to compare by score in descending order
    @Override
    public int compareTo(GameRecord other) {
        return Integer.compare(other.score, this.score);
    }

    // Override toString for readable output
    @Override
    public String toString() {
        return this.playerId + "\n" + " -----------------" + "\n" + 
               "Score : " + this.score + "\n" + 
               "Misses : " + this.misses + "\n" + 
               "Number of games played : " + this.numberOfGames + "\n" + 
               "-----------------";
    }

    // Optional: Add setters or methods to modify fields like misses if needed
    public void incrementMisses() {
        this.misses++;
    }

    // Optional: Getter for startTime if needed for reporting or debugging
    public long getStartTime() {
        return this.startTime;
    }

    @Override
    public char getGuess(String previousGuess) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getGuess'");
    }
}
