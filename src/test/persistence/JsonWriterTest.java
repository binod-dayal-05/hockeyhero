package persistence;

import model.League;
import model.Player;
import model.Team;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

//Code reconfigured from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo 

/**
 * Unit tests for the JsonWriter class
 */
public class JsonWriterTest extends JsonTest {
    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter jsonWriter = new JsonWriter("./data/my\0illegal:fileName.json");
            jsonWriter.open();
            fail("IOException expected but not caught.");
        } catch (IOException e) {
            // yipee
        }
    }

    @Test
    void testWriterEmptyLeague() {
        try {
            League league = new League();
            JsonWriter jsonWriter = new JsonWriter("./data/testWriterEmptyLeague.json");
            jsonWriter.open();
            jsonWriter.write(league);
            jsonWriter.close();

            JsonReader jsonReader = new JsonReader("./data/testWriterEmptyLeague.json");
            league = jsonReader.read();
            assertEquals(0, league.getTeams().size());
            assertEquals(1, league.getMatchday());
        } catch (IOException e) {
            fail("IOException not expected, but was caught.");
        }
    }

    @Test
    void testWriterGeneralLeague() {
        try {
            League league = new League();
            Team myTeam = new Team("Canucks", 11);
            add(myTeam);

            myTeam.setWins(3);
            myTeam.setTies(2);
            myTeam.setLosses(1);

            league.makeTeams(myTeam);
            league.makeSchedule();

            JsonWriter jsonWriter = new JsonWriter("./data/testWriterGeneralLeague.json");
            jsonWriter.open();
            jsonWriter.write(league);
            jsonWriter.close();
            JsonReader jsonReader = new JsonReader("./data/testWriterGeneralLeague.json");
            league = jsonReader.read();

            assertEquals(12, league.getTeams().size());
            assertEquals(1, league.getMatchday());
            assertEquals(11, myTeam.getOpponents().size());
            checkTeam("Canucks", 3, 2, 1, 6, 11, myTeam);
            checkPlayer("Elias Petterson", 40, 95, 93, 1000, myTeam.getRoster().get(0));
        } catch (IOException e) {
            fail("Exception not expected, but was caught.");
        }
    }

    void add(Team myTeam) {
        Player p = makePlayer();
        myTeam.addPlayer(p);
    }

    public Player makePlayer() {
        Player p = new Player();
        p.setName("Elias Petterson");
        p.setNumber(40);
        p.setOffense(95);
        p.setDefense(93);
        p.setWage(1000);
        return p;
    }

}
