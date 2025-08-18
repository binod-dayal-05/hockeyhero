package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

/**
 * Unit tests for the Market class
 */
public class MarketTest {

    private Market testMarket;
    private ArrayList<Player> testPlayersForHire;
    private Player testPlayer1;
    private Player testPlayer2;
    private Player testPlayer3;

    @BeforeEach
    void runBefore() {
        this.testMarket = new Market();
        this.testPlayersForHire = new ArrayList<Player>();
        this.testPlayer1 = new Player();
        this.testPlayer2 = new Player();
        this.testPlayer3 = new Player();
        testPlayersForHire.add(testPlayer1);
        testPlayersForHire.add(testPlayer2);
        testPlayersForHire.add(testPlayer3);
        testMarket.addPlayer(testPlayer1);
        testMarket.addPlayer(testPlayer2);
        testMarket.addPlayer(testPlayer3);
    }

    @Test
    void testGetAvailablePlayers() {
        assertEquals(testPlayersForHire, testMarket.getAvailablePlayers());
    }

    @Test
    void testAddPlayer() {
        testMarket.addPlayer(testPlayer1);
        assertEquals(testPlayer1, testMarket.getAvailablePlayers().get(3));
    }

    @Test
    void testRemovePlayer() {
        testMarket.removePlayer(testMarket.getAvailablePlayers().get(1));
        assertEquals(testPlayer3, testMarket.getAvailablePlayers().get(1));
    }
}