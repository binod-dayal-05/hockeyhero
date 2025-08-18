package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.json.JSONArray;
import org.json.JSONObject;

import persistence.Writable;

//represents a league object that handles game scheduling, league standings and more
public class League implements Writable {

    private RandomGenerator randomGenerator;
    private ArrayList<Team> teams;
    private int leagueSize;
    private int teamSize;
    private int matchday;

    //EFFECTS: creates new league with empty list of teams, new randomGenerator object, sets league size to 12,
    //         team size to 3, and starts the matchday at 1.
    public League() {
        teams = new ArrayList<Team>();
        randomGenerator = new RandomGenerator();
        leagueSize = 12;
        teamSize = 3;
        this.matchday = 1;
    }

    //MODIFIES: this, myTeam
    //EFFECTS: makes list of teams in the league by creating new teams with new players in them, adding user's team
    //         into the end of list. each player and team will have random names, except the user's [user's team and 
    //         player names have already been previously set in Manage class]
    public void makeTeams(Team myTeam) {
        for (int x = 0; x < (leagueSize - 1); x++) {
            Team t = new Team(randomGenerator.generateTeamName(), x);
            for (int y = 0; y < teamSize; y++) {
                Player p = new Player();
                t.addPlayer(p);
            }
            teams.add(t);
        }
        teams.add(myTeam);
    }

    //REQUIRES: there are at least 12 teams in the league
    //MODIFIES: this, t
    //EFFECTS: creates game schedule for each team
    public void makeSchedule() {
        for (int z = 0; z < 12; z++) { // go through all 12 teams
            Team t = teams.get(z);
            int pos = teams.indexOf(t);
            int y = 1;
            for (int x = 0; x < 11; x++) { // add the other 11 opponents
                if ((12 - y - pos >= 0) && (12 - y - pos != pos)) {
                    t.addOpponent(teams.get(12 - y - pos));
                } else if ((12 - y - pos < 0) && (12 - y - pos + 12 != pos)) {
                    t.addOpponent(teams.get(12 - y - pos + 12));
                } else {
                    x--;
                }
                y++;
            }
        }
    }

    //This method's code is based on concepts learned here: https://www.youtube.com/watch?v=wzWFQTLn8hI
    //MODIFIES: this
    //EFFECTS: reorders list of teams in leaugue in descending order based on points total.
    public void sortOrder() {
        Collections.sort(teams, new Comparator<Team>() {
            public int compare(Team t1, Team t2) {
                return Integer.valueOf(t2.getPoints()).compareTo(t1.getPoints());
            }
        });
    }

    //EFFECTS: returns team in teams that has corresponding id. if no such team is found, return null
    public Team findTeam(int id) {
        Team t = null;
        for (int x = 0; x < teams.size(); x++) {
            if (teams.get(x).getId() == id) {
                t = teams.get(x);
            }
        }
        return t;
    }

    //get and set methods

    //EFFECTS: returns all teams in the league
    public ArrayList<Team> getTeams() {
        return teams;
    }

    //EFFECTS: returns matchday count
    public int getMatchday() {
        return matchday;
    }

    //MODIFIES: this
    //EFFECTS: sets matchday count to given integer
    public void setMatchday(int matchday) {
        this.matchday = matchday;
    }

    //Code reconfigured from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo 
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("teams", teamsToJson(getTeams()));
        json.put("matchday", getMatchday());
        return json;
    }

    //Code reconfigured from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo 
    //EFFECTS: returns teams in this league as a JSON array
    private JSONArray teamsToJson(ArrayList<Team> teams) {
        JSONArray jsonArray = new JSONArray();
        for (Team t : teams) {
            jsonArray.put(t.toJson());
        }
        return jsonArray;
    }

}
