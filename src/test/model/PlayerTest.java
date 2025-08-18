package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the Player class
 */
public class PlayerTest {

    private Player testPlayer1;

    @BeforeEach
    void runBefore() {
        this.testPlayer1 = new Player();
        testPlayer1.setName("Connor McDavid");
        testPlayer1.setNumber(97);
        testPlayer1.setOffense(99);
        testPlayer1.setDefense(90);
        testPlayer1.setWage(1000);
    }

    @Test
    void testGetName() {
        assertEquals("Connor McDavid", testPlayer1.getName());
    }

    @Test
    void testSetName() {
        testPlayer1.setName("Auston Matthews");
        assertEquals("Auston Matthews", testPlayer1.getName());
    }

    @Test
    void testGetNumber() {
        assertEquals(97, testPlayer1.getNumber());
    }

    @Test
    void testSetNumber() {
        testPlayer1.setNumber(34);
        assertEquals(34, testPlayer1.getNumber());
    }

    @Test
    void testGetOffense() {
        assertEquals(99, testPlayer1.getOffense());
    }

    @Test
    void testSetOffense() {
        testPlayer1.setOffense(97);
        assertEquals(97, testPlayer1.getOffense());
    }

    @Test
    void testGetDefense() {
        assertEquals(90, testPlayer1.getDefense());
    }

    @Test
    void testSetDefense() {
        testPlayer1.setDefense(98);
        assertEquals(98, testPlayer1.getDefense());
    }

    @Test
    void testGetWage() {
        assertEquals(1000, testPlayer1.getWage());
    }

    @Test
    void testSetWage() {
        testPlayer1.setWage(900);
        assertEquals(900, testPlayer1.getWage());
    }

    @Test
    void testGetOverall() {
        assertEquals(94, testPlayer1.getOverall());
    }
}
