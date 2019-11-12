
package Hangman;

public class Player {
    private String name;
    private int gamesPlayed;
    private int wins;
    
    public Player(){
        name = "Player";
        gamesPlayed = 0;
        wins = 0;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public void addGamesPlayed() {
        this.gamesPlayed++;
    }

    public int getWins() {
        return wins;
    }

    public void addWins() {
        this.wins++;
    }
    
    public String toString(){
        String str = name+","+gamesPlayed+","+wins;
        return str;
    }

    public void setGamesPlayed(int gamesPlayed) {
        this.gamesPlayed = gamesPlayed;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }
    
    
}
