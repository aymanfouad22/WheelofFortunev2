public interface WheelOfFortunePlayer {
    public abstract char nextGuess(String hiddenPhrase,String previousGuesses,String charIndex);
    public abstract String playerId();
   public abstract void reset(GameRecord o);
}
