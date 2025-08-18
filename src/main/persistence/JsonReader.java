package persistence;

import model.League;
import model.Player;
import model.Team;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

//Code reconfigured from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo 

//Represents a reader that reads league from JSON data stored in file
public class JsonReader {
    private String source;

    //EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    //EFFECTS: reads league from file and returns it;
    //         throws IOException if an error occurs reading data from file
    public League read() throws IOException {
        String jsonData = new String(Files.readAllBytes(Paths.get(source)));
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseLeague(jsonObject);
    }

    //EFFECTS: parses league from JSON object and returns it
    private League parseLeague(JSONObject jsonObject) {
        League league = new League();
        league.setMatchday(jsonObject.getInt("matchday"));
        JSONArray jsonArray = jsonObject.getJSONArray("teams");
        for (Object json : jsonArray) {
            JSONObject nextTeamObject = (JSONObject) json;
            Team team = parseTeam(nextTeamObject);
            league.getTeams().add(team);
        }
        return league;
    }

    //EFFECTS: parses team from JSON object and returns it
    private Team parseTeam(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int wins = jsonObject.getInt("wins");
        int ties = jsonObject.getInt("ties");
        int losses = jsonObject.getInt("losses");
        int id = jsonObject.getInt("id");
        Team team = new Team(name, id);
        team.setWins(wins);
        team.setTies(ties);
        team.setLosses(losses);
        JSONArray rosterArray = jsonObject.getJSONArray("roster"); // Add players to the team
        for (Object json : rosterArray) {
            JSONObject playerObject = (JSONObject) json;
            Player player = parsePlayer(playerObject);
            team.addPlayer(player);
        }
        JSONArray opponentsArray = jsonObject.getJSONArray("opponents"); // Add opponents to the team
        for (Object json : opponentsArray) {
            JSONObject opponentObject = (JSONObject) json;
            int opponentId = opponentObject.getInt("id");
            team.addOpponentFromId(opponentId);
        }
        return team;
    }

    //EFFECTS: parses player from JSON object and returns it
    private Player parsePlayer(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        int number = jsonObject.getInt("number");
        int offense = jsonObject.getInt("offense");
        int defense = jsonObject.getInt("defense");
        int wage = jsonObject.getInt("wage");

        Player player = new Player();
        player.setName(name);
        player.setNumber(number);
        player.setOffense(offense);
        player.setDefense(defense);
        player.setWage(wage);
        return player;
    }
}
