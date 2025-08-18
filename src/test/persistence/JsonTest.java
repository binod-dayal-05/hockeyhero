package persistence;

import model.Player;
import model.Team;

import static org.junit.jupiter.api.Assertions.assertEquals;

//Code reconfigured from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo 

/**
 * Unit tests for JsonReaderTest and JsonWriterTest classes to inherit
 */
public class JsonTest {

    protected void checkTeam(String name, int wins, int ties, int losses, int gamesPlayed, int id, Team t) {
        assertEquals(name, t.getName());
        assertEquals(wins, t.getWins());
        assertEquals(ties, t.getTies());
        assertEquals(losses, t.getLosses());
        assertEquals(gamesPlayed, t.getGamesPlayed());
        assertEquals(id, t.getId());
    }

    protected void checkPlayer(String name, int number, int offense, int defense, int wage, Player p) {
        assertEquals(name, p.getName());
        assertEquals(number, p.getNumber());
        assertEquals(offense, p.getOffense());
        assertEquals(defense, p.getDefense());
        assertEquals(wage, p.getWage());
    }
}
