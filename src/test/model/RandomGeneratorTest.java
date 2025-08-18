package model;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the RandomGenerator class
 */
public class RandomGeneratorTest {

    private RandomGenerator testRand;

    @BeforeEach
    void runBefore() {
        this.testRand = new RandomGenerator();
    }

    @Test
    void testGeneratePlayerName() {
        assertNotEquals(null, testRand.generatePlayerName());
    }

    @Test
    void testGenerateTeamName() {
        assertNotEquals(null, testRand.generateTeamName());
    }
}
