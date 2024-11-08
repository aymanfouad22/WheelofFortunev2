public abstract class Game {
    public  AllGamesRecord playAll(){
        while(playnext()){
           play();
        }
        return new AllGamesRecord();
    };
    public abstract GameRecord play();
    public abstract boolean playnext();
}
