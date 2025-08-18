package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

/**
 * Unit tests for the Team class
 */
public class TeamTest {

    private Team testTeam;
    private ArrayList<Player> testRoster;
    private Player testPlayer1;
    private Player testPlayer2;
    private Player testPlayer3;

    @BeforeEach
    void runBefore() {
        testTeam = new Team("Canucks", 11);
        testTeam.setWins(3);
        testTeam.setTies(2);
        testTeam.setLosses(1);
        this.testRoster = new ArrayList<Player>();
        setPlayerValues();
    }

    void setPlayerValues() {
        this.testPlayer1 = new Player();
        testPlayer1.setName("Connor McDavid");
        testPlayer1.setNumber(97);
        testPlayer1.setOffense(99);
        testPlayer1.setDefense(90);
        testPlayer1.setWage(1000);
        this.testPlayer2 = new Player();
        testPlayer2.setName("Auston Matthews");
        testPlayer2.setNumber(34);
        testPlayer2.setOffense(95);
        testPlayer2.setDefense(98);
        testPlayer2.setWage(900);
        this.testPlayer3 = new Player();
        testPlayer3.setName("Nathan MacKinnon");
        testPlayer3.setNumber(29);
        testPlayer3.setOffense(97);
        testPlayer3.setDefense(92);
        testPlayer3.setWage(800);
        testRoster.add(testPlayer1);
        testRoster.add(testPlayer2);
        testRoster.add(testPlayer3);
    }

    @Test
    void testAddPlayer() {
        testTeam.addPlayer(testPlayer1);
        assertEquals(testPlayer1, testRoster.get(0));
        testTeam.setId(5);
        testTeam.addPlayer(testPlayer1);
        assertEquals(testPlayer1, testRoster.get(0));
    }

    @Test
    void testRemovePlayer() {
        testTeam.addPlayer(testPlayer1);
        testTeam.addPlayer(testPlayer2);
        testTeam.addPlayer(testPlayer3);
        testTeam.removePlayer(testPlayer2);
        assertEquals(testPlayer3, testTeam.getRoster().get(1));
        testTeam.setId(5);
        testTeam.addPlayer(testPlayer1);
        testTeam.addPlayer(testPlayer2);
        testTeam.addPlayer(testPlayer3);
        testTeam.removePlayer(testPlayer2);
        assertEquals(testPlayer3, testTeam.getRoster().get(1));
    }

    @Test
    void testAddOpponent() {
        Team testTeam2 = new Team("2", 2);
        testTeam.addOpponent(testTeam2);
        assertEquals(2, testTeam.getNextOpponent());
    }

    @Test
    void testAddOpponentFromId() {
        testTeam.addOpponentFromId(6);
        assertEquals(6, testTeam.getNextOpponent());
    }

    @Test
    void testRemoveOpponent() {
        Team testTeam2 = new Team("2", 2);
        Team testTeam3 = new Team("3", 3);
        testTeam.addOpponent(testTeam2);
        testTeam.addOpponent(testTeam3);
        testTeam.removeOpponent(0);
        assertEquals(3, testTeam.getNextOpponent());
    }

    @Test
    void testGetOverall() {
        testTeam.addPlayer(testPlayer1);
        testTeam.addPlayer(testPlayer2);
        testTeam.addPlayer(testPlayer3);
        assertEquals(95, testTeam.getOverall());
    }

    @Test
    void testGetOffense() {
        testTeam.addPlayer(testPlayer1);
        testTeam.addPlayer(testPlayer2);
        testTeam.addPlayer(testPlayer3);
        assertEquals(97, testTeam.getOffense());
    }

    @Test
    void testGetDefense() {
        testTeam.addPlayer(testPlayer1);
        testTeam.addPlayer(testPlayer2);
        testTeam.addPlayer(testPlayer3);
        assertEquals(93, testTeam.getDefense());
    }

    @Test
    void testGetPoints() {
        assertEquals(8, testTeam.getPoints());
    }

    @Test
    void testGetOpponent() {
        Team testTeam2 = new Team("2", 2);
        Team testTeam3 = new Team("3", 3);
        testTeam.addOpponent(testTeam2);
        testTeam.addOpponent(testTeam3);
        assertEquals(3, testTeam.getOpponent(1));
    }

    @Test
    void testGetNextOpponent() {
        Team testTeam2 = new Team("2", 2);
        Team testTeam3 = new Team("3", 3);
        testTeam.addOpponent(testTeam2);
        testTeam.addOpponent(testTeam3);
        assertEquals(2, testTeam.getNextOpponent());
    }

    @Test
    void testGetName() {
        assertEquals("Canucks", testTeam.getName());
    }

    @Test
    void testGetWins() {
        assertEquals(3, testTeam.getWins());
    }

    @Test
    void testSetWins() {
        testTeam.setWins(5);
        assertEquals(5, testTeam.getWins());
    }

    @Test
    void testGetTies() {
        assertEquals(2, testTeam.getTies());
    }

    @Test
    void testSetTies() {
        testTeam.setTies(7);
        assertEquals(7, testTeam.getTies());
    }

    @Test
    void testGetLosses() {
        assertEquals(1, testTeam.getLosses());
    }

    @Test
    void testSetLosses() {
        testTeam.setLosses(9);
        assertEquals(9, testTeam.getLosses());
    }

    @Test
    void testGetRoster() {
        testTeam.addPlayer(testPlayer1);
        testTeam.addPlayer(testPlayer2);
        testTeam.addPlayer(testPlayer3);
        assertEquals(testRoster, testTeam.getRoster());
    }

    @Test
    void testGetOpponents() {
        Team testTeam2 = new Team("2", 2);
        Team testTeam3 = new Team("3", 3);
        testTeam.addOpponent(testTeam2);
        testTeam.addOpponent(testTeam3);
        ArrayList<Integer> testList = new ArrayList<Integer>();
        testList.add(2);
        testList.add(3);
        assertEquals(testList, testTeam.getOpponents());
    }

    @Test
    void testGetGamesPlayed() {
        assertEquals(6, testTeam.getGamesPlayed());
    }

    @Test
    void testGetId() {
        assertEquals(11, testTeam.getId());
    }

    @Test
    void testSetId() {
        testTeam.setId(1);
        assertEquals(1, testTeam.getId());
    }

    @Test
    void testAddWin() {
        testTeam.addWin();
        assertEquals(4, testTeam.getWins());
        testTeam.setId(5);
        testTeam.addWin();
        assertEquals(5, testTeam.getWins());
    }

    @Test
    void testAddTie() {
        testTeam.addTie();
        assertEquals(3, testTeam.getTies());
        testTeam.setId(5);
        testTeam.addTie();
        assertEquals(4, testTeam.getTies());
    }

    @Test
    void testAddLoss() {
        testTeam.addLoss();
        assertEquals(2, testTeam.getLosses());
        testTeam.setId(5);
        testTeam.addLoss();
        assertEquals(3, testTeam.getLosses());
    }
    
}
