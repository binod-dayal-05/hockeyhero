package model;

import java.util.ArrayList;

//represents a market that a user can purchase players from to play for their team
public class Market {
    
    private ArrayList<Player> playersForHire;

    //EFFECTS: creates new market with no players available to hire
    public Market() {
        playersForHire = new ArrayList<Player>();
    }

    //EFFECTS: returns list of players available for hire
    public ArrayList<Player> getAvailablePlayers() {
        return playersForHire;
    }

    //MODIFIES: this
    //EFFECTS: adds the player back to available players
    public void addPlayer(Player p) {
        playersForHire.add(p);
    }

    //MODIFIES: this
    //EFFECTS: removes the player from available players
    public void removePlayer(Player p) {
        playersForHire.remove(p);
    }

}
