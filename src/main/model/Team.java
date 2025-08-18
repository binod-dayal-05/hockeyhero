package model;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import persistence.Writable;

// event logging reconfigured from https://github.students.cs.ubc.ca/CPSC210/AlarmSystem

// represents a hockey team with its own information, stats, players, opponents and more
public class Team implements Writable {

    private String name;
    private int wins;
    private int ties;
    private int losses;
    private ArrayList<Player> roster;
    private ArrayList<Integer> opponents;
    private int gamesPlayed;
    private int id;

    //EFFECTS: creates new instance of a team with a given name, 0 wins, ties, and losses, an empty roster, an empty
    //         list of opponent team id's, 0 games played, and a given team id.
    public Team(String name, int id) {
        this.name = name;
        this.wins = 0;
        this.ties = 0;
        this.losses = 0;
        this.roster = new ArrayList<Player>();
        this.opponents = new ArrayList<Integer>();
        this.gamesPlayed = 0;
        this.id = id;
        if (id == 11) {
            EventLog.getInstance().logEvent(new Event("User team created: " + name));
        }
    }

    //REQUIRES: player is not already in roster
    //MODIFIES: this
    //EFFECTS: adds the selected player to the roster
    public void addPlayer(Player p) {
        roster.add(p);
        if (id == 11) {
            EventLog.getInstance().logEvent(new Event("Player " + p.getName() + " added to user team's roster."));
        }
    }

    //REQUIRES: player is in roster
    //MODIFIES: this
    //EFFECTS: removes the selected player from the roster
    public void removePlayer(Player p) {
        roster.remove(p);
        if (id == 11) {
            EventLog.getInstance().logEvent(new Event("Player " + p.getName() + " removed from user team's roster."));
        }
    }

    //REQUIRES: opp is not this team
    //MODIFIES: this
    //EFFECTS: adds an opponent team to this team's list of opponent teams
    public void addOpponent(Team opp) {
        opponents.add(opp.getId());
    }

    //REQUIRES: there exists a team in the league with this id
    //MODIFIES: this
    //EFFECTS: adds the given team id number into opponents list
    public void addOpponentFromId(int id) {
        opponents.add(id);
    }

    //REQUIRES: there exists an opponent at the specified index
    //MODIFIES: this
    //EFFECTS: removes opponent at specified index
    public void removeOpponent(int pos) {
        opponents.remove(pos);
    }

    //EFFECTS: returns average between offense and defense stats across whole team
    public int getOverall() {
        int overall = (getOffense() + getDefense()) / 2;
        return overall;
    }

    //EFFECTS: returns average offense stat across whole team
    public int getOffense() {
        int offense = 0;
        for (Player p : roster) {
            offense += p.getOffense();
        }
        return offense / roster.size();
    }

    //EFFECTS: returns average defense stat across whole team
    public int getDefense() {
        int defense = 0;
        for (Player p : roster) {
            defense += p.getDefense();
        }
        return defense / roster.size();
    }

    //EFFECTS: returns points total by giving 2 points per win, 1 point per tie
    public int getPoints() {
        return wins * 2 + ties;
    }

    //EFFECTS: returns id of opponent at specified index in opponents
    public Integer getOpponent(int pos) {
        return opponents.get(pos);
    }

    //EFFECTS: returns first id of opponent in opponents
    public Integer getNextOpponent() {
        return opponents.get(0);
    }

    // get and set methods

    //EFFECTS: returns team name
    public String getName() {
        return name;
    }

    //EFFECTS: returns win count
    public int getWins() {
        return wins;
    }

    //MODIFIES: this
    //EFFECTS: sets win count to given integer
    public void setWins(int wins) {
        this.wins = wins;
    }

    //EFFECTS: returns tie count
    public int getTies() {
        return ties;
    }

    //MODIFIES: this
    //EFFECTS: sets tie count to given integer
    public void setTies(int ties) {
        this.ties = ties;
    }

    //EFFECTS: returns loss count
    public int getLosses() {
        return losses;
    }

    //MODIFIES: this
    //EFFECTS: sets loss count to given integer
    public void setLosses(int losses) {
        this.losses = losses;
    }

    //EFFECTS: returns roster
    public ArrayList<Player> getRoster() {
        return roster;
    }

    //EFFECTS: returns list of opponent team id's
    public ArrayList<Integer> getOpponents() {
        return opponents;
    }

    //MODIFIES: this
    //EFFECTS: returns games played
    public int getGamesPlayed() {
        gamesPlayed = wins + ties + losses;
        return gamesPlayed;
    }

    //EFFECTS: returns this team's id
    public int getId() {
        return id;
    }

    //MODIFIES: this
    //EFFECTS: sets this team's id to given integer
    public void setId(int id) {
        this.id = id;
    }

    //MODIFIES: this
    //EFFECTS: adds one to win count
    public void addWin() {
        this.wins += 1;
        if (id == 11) {
            EventLog.getInstance().logEvent(new Event("User team '" + getName() + "' won a game."));
        }
    }

    //MODIFIES: this
    //EFFECTS: adds one to tie count
    public void addTie() {
        this.ties += 1;
        if (id == 11) {
            EventLog.getInstance().logEvent(new Event("User team '" + getName() + "' tied a game."));
        }
    }

    //MODIFIES: this
    //EFFECTS: adds one to loss count
    public void addLoss() {
        this.losses += 1;
        if (id == 11) {
            EventLog.getInstance().logEvent(new Event("User team '" + getName() + "' lost a game."));
        }
    }

    //Code reconfigured from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo 
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", getName());
        json.put("wins", getWins());
        json.put("ties", getTies());
        json.put("losses", getLosses());
        json.put("id", getId());
        json.put("gamesPlayed", getGamesPlayed());
        json.put("roster", rosterToJson(getRoster()));
        json.put("opponents", opponentsToJson(getOpponents()));
        return json;
    }

    //Code reconfigured from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo 
    //EFFECTS: returns roster in this team as a JSON array
    private JSONArray rosterToJson(ArrayList<Player> roster) {
        JSONArray jsonArray = new JSONArray();
        for (Player player : roster) {
            jsonArray.put(player.toJson());
        }
        return jsonArray;
    }

    //Code reconfigured from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo 
    //EFFECTS: returns opponent id's for this team as a JSON array
    private JSONArray opponentsToJson(ArrayList<Integer> opponents) {
        JSONArray jsonArray = new JSONArray();
        for (int id : opponents) {
            JSONObject json = new JSONObject();
            json.put("id", id);
            jsonArray.put(json);
        }
        return jsonArray;
    }

}
