package persistence;

import model.League;
import model.Team;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

//Code reconfigured from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo 

/**
 * Unit tests for the JsonReader class
 */
public class JsonReaderTest extends JsonTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader jsonReader = new JsonReader("./data/noSuchFile.json");
        try {
            League league = jsonReader.read();
            fail("IOException expected but not found.");
        } catch (IOException e) {
            // yipee
        }
    }

    @Test
    void testReaderEmptyLeague() {
        JsonReader jsonReader = new JsonReader("./data/testReaderEmptyLeague.json");
        try {
            League league = jsonReader.read();
            assertEquals(0, league.getTeams().size());
            assertEquals(1, league.getMatchday());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralLeague() {
        JsonReader jsonReader = new JsonReader("./data/testReaderGeneralLeague.json");
        try {
            League league = jsonReader.read();
            Team myTeam = league.getTeams().get(11);
            assertEquals(12, league.getTeams().size());
            assertEquals(1, league.getMatchday());
            assertEquals(11, myTeam.getOpponents().size());
            checkTeam("Canucks", 3, 2, 1, 6, 11, myTeam);
            checkPlayer("Elias Petterson", 40, 95, 93, 1000, myTeam.getRoster().get(0));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

}
