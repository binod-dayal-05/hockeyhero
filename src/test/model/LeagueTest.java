package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the League class
 */
public class LeagueTest {

    private League testLeague;
    private Team testTeam;

    @BeforeEach
    void runBefore() {
        this.testLeague = new League();
        this.testTeam = new Team("Canucks", 11);
    }

    @Test
    void testMakeTeamsAndGetTeams() {
        testLeague.makeTeams(testTeam);
        assertEquals(testTeam, testLeague.getTeams().get(11));
    }

    @Test
    void testMakeSchedule() {
        testLeague.makeTeams(testTeam);
        testLeague.makeSchedule();
        assertEquals(11, testTeam.getOpponents().size());
    }
    
    @Test
    void testSortOrder() {
        testTeam.addWin();
        testLeague.makeTeams(testTeam);
        testLeague.sortOrder();
        assertEquals(testTeam, testLeague.getTeams().get(0));
    }

    @Test
    void testFindTeam() {
        testLeague.makeTeams(testTeam);
        assertEquals(testTeam, testLeague.findTeam(11));
        assertEquals(null, testLeague.findTeam(12));
    }

    @Test
    void testGetMatchday() {
        assertEquals(1, testLeague.getMatchday());
    }

    @Test
    void testSetMatchday() {
        testLeague.setMatchday(5);
        assertEquals(5, testLeague.getMatchday());
    }
    
}
