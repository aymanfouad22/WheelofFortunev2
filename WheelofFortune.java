import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public abstract class WheelofFortune extends Game {
    public StringBuilder hiddenPhrase;
    public String phrase ="";
    public  StringBuilder previousGuesses;
    public  int count;
    protected  boolean found;
    protected  int WOFscore = 0;
    List<String> phraseList = new LinkedList<>();
    public WheelofFortune() {
    this.count = 0 ;
    this.found =false;
    previousGuesses = new StringBuilder();
    try {
        phraseList = Files.readAllLines(Paths.get("/Users/ayman/IdeaProjects/Wheel of Fortune v2/src/Phrases.txt"));
    } catch (IOException e) {
        System.out.println(e);}
    }
   
    public  String  randomPhrase() {
            
          
            Random rand = new Random();
            int r = rand.nextInt(phraseList.size()); // Updated to handle any list size
            return phraseList.get(r);
        }
    
        public  String generateHiddenPhrase(String phrase) {
            StringBuilder hiddenPhrase = new StringBuilder();
            GameRecord o =  new GameRecord(0, null, 0,phrase.length());
            count = 0; // Reset count
            for (int i = 0; i < phrase.length(); i++) {
                char ch = phrase.charAt(i);
                if (Character.isLetter(ch)) {
                    hiddenPhrase.append('*');
                    count++;
                } else {
                    hiddenPhrase.append(ch);
                }
            }
            return hiddenPhrase.toString();
        }
         
         public  String processGuess(StringBuilder hiddenPhrase, String phrase, char guess1, char guess2,GameRecord scoreRecord) {
           if(phrase.indexOf(guess1)!=-1 || phrase.indexOf(guess2)!=-1){
            for (int i = 0; i < phrase.length(); i++) {
                char currentChar = phrase.charAt(i);
                if (currentChar == guess1 || currentChar == guess2) {
                    hiddenPhrase.setCharAt(i, currentChar);
                    if(previousGuesses.toString().indexOf(guess1)==-1 || previousGuesses.toString().indexOf(guess1)==-1)
                    scoreRecord.score++;

                }
            }
            scoreRecord.score =  scoreRecord.score-scoreRecord.misses;
        }else {scoreRecord.misses++;}
         
        
        return hiddenPhrase.toString();
    }
   @Override
   public AllGamesRecord playAll(){
    return new AllGamesRecord();
   }
   @Override
   public GameRecord play(){
    return new GameRecord(0,null,0,phrase.length());
   }
   @Override
   public boolean playnext(){
    return true;
   }

    public  abstract char getGuess(String previousGuess);
    public static void main(String[] args) {
        List<String> phraseList =  new LinkedList<String>() ; 
        try {
            phraseList = Files.readAllLines(Paths.get("/Users/ayman/IdeaProjects/Wheel of Fortune v2/src/Phrases.txt"));
        } catch (IOException e) {
            System.out.println(e);}
            System.out.println(phraseList.size());
        }
       
    }
