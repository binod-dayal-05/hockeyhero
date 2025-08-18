package ui;

import model.Team;
import model.League;
import java.util.ArrayList;
import java.util.Random;

//represents object that user interacts with to simulate games
public class Game {
    private Team myTeam;
    private Random rng;
    private int matchday;
    private League league;

    //EFFECTS: constructs new game object with user team, new random generator object, current league, and
    //         current matchday count
    public Game(Team myTeam, League league, int matchday, ArrayList<Team> teams) {
        this.myTeam = myTeam;
        this.rng = new Random();
        this.league = league;
        this.matchday = matchday;
    }

    //EFFECTS: displays user's upcoming opponents and plays their next game
    public void displaySchedule() {
        if (matchday < 12) {
            System.out.println("\nCURRENT SCHEDULE: ");
            System.out.println("Upcoming games: ");
            for (int x = 0; x < myTeam.getOpponents().size(); x++) {
                System.out.println(league.findTeam(myTeam.getOpponent(x)).getName());
            }
            playNextGame();
        }
    }

    //MODIFIES: this
    //EFFECTS: simulates user's next game, the next game for each team in the league, and increases matchday count for
    //         both this game and the league
    public void playNextGame() {
        System.out.println("\nToday's game is complete. Results: ");
        simulate(myTeam, league.findTeam(myTeam.getOpponent(0)));
        myTeam.removeOpponent(0);
        playGames();
        matchday++;
        league.setMatchday(matchday);
    }

    //MODIFIES: this
    //EFFECTS: for each team in the league, if they haven't played this week's game yet, it simulates their game
    //         as long as user team is not involved, as user simulates their games in previous method. once a game has
    //         been played, both teams are removed from each other's opponents lists.
    public void playGames() {
        for (Team t : league.getTeams()) {
            if (t.getGamesPlayed() < matchday) {
                if (league.findTeam(t.getNextOpponent()) != myTeam && league.findTeam(t.getNextOpponent()) != t) {
                    simulate(t, league.findTeam(t.getNextOpponent()));
                    Team nextOpp = league.findTeam(t.getNextOpponent());
                    nextOpp.removeOpponent(nextOpp.getOpponents().indexOf(t.getId()));
                    t.removeOpponent(0);
                } else {
                    t.removeOpponent(0);
                }
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: simulates games of 2 given teams. results are determined by offense and defense stats with a random
    //         factor in it as well. results get reflected in both teams by adding their result into their counts for
    //         win/tie/loss. if user's team played, show the results of the game.
    public void simulate(Team t1, Team t2) {
        int t1Offense = t1.getOffense();
        int t1Defense = t1.getDefense();
        int t2Offense = t2.getOffense();
        int t2Defense = t2.getDefense();
        int t1Goals = 0;
        int t2Goals = 0;
        t1Goals = t1Offense / t2Defense + (rng.nextInt(4));
        t2Goals = t2Offense / t1Defense + (rng.nextInt(4));
        if (t1Goals > t2Goals) {
            t1.addWin();
            t2.addLoss();
        } else if (t1Goals < t2Goals) {
            t1.addLoss();
            t2.addWin();
        } else {
            t1.addTie();
            t2.addTie();
        }
        if (t1 == myTeam) {
            System.out.println(t1.getName() + " " + t1Goals + "-" + t2Goals + " " + t2.getName());
        } else if (t2 == myTeam) {
            System.out.println(t2.getName() + ": " + t2Goals + "-" + t1Goals + ": " + t1.getName());
        }
    }

    // get and set methods

    //EFFECTS: return current matchday count
    public int getMatchday() {
        return matchday;
    }

    //MODIFIES: this
    //EFFECTS: sets current matchday count to given integer
    public void setMatchday(int matchday) {
        this.matchday = matchday;
    }
}
