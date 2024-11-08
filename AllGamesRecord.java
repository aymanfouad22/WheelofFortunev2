import java.beans.EventHandler;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class AllGamesRecord extends WheelofFortune {
    ArrayList <GameRecord> gameRecords;
@Override
   public char getGuess(String playerId){
    return ' ';
   }
   
    public AllGamesRecord(){
        gameRecords = new ArrayList<>();
    }
    public void add(GameRecord gameRecord){
        this.gameRecords.add(gameRecord);
    }
   public int average(){
    int scoreAverage = 0;
    for(int i =0;i<gameRecords.size();i++){
        scoreAverage =  scoreAverage +gameRecords.get(i).score;
    }
    scoreAverage =  scoreAverage/(gameRecords.size());
    return scoreAverage;
   }

  public int playerAverage(String playerIde){
    int scoreAverage=0;
    int count =0;
      for(int i =0;i < gameRecords.size();i++){
        if(gameRecords.get(i).playerId.equals(playerIde)){
            scoreAverage = scoreAverage + gameRecords.get(i).score;
            count=i;
        };
      }
      System.err.println(count);
      scoreAverage =  scoreAverage/gameRecords.get(count).numberOfGames;
      return scoreAverage;
   }
   public ArrayList<GameRecord> highGameList(int n){
    ArrayList<GameRecord> nSorteGameRecords = new ArrayList<GameRecord>();
    Collections.sort(gameRecords);
   int i = 0;
nSorteGameRecords.add(gameRecords.get(i));
    i++;
    return nSorteGameRecords;
   }
   public ArrayList<GameRecord> highGameList(String playerIde ,int n){
    ArrayList<GameRecord> playerSortedGameRecords = new ArrayList<GameRecord>();
    Collections.sort(gameRecords);
    for(int i =0;i<=n;i++){
        if(gameRecords.get(i).playerId.equals(playerIde)){
playerSortedGameRecords.add(gameRecords.get(i));}
    }
    return playerSortedGameRecords;
   }
   public String toString(){
    return gameRecords.toString();
    }
  
}
