package model;

import java.util.Random;

//Code reconfigured from https://www.youtube.com/watch?v=FbS_sCCbHvI

//represents an object that is used to generate random player names and team names from a pool of names
public class RandomGenerator {
    int namesLength;
    int teamsLength;
    String[] firstNames = {"Connor", "Leon", "Auston", "William", "Nathan", "Sidney", "Alexander", "Elias",
        "Brock", "Quinn", "Cole", "Nick", "Steven", "Nikita", "Wayne", "Bobby", "Mario", "Pavel"};
    String[] lastNames = {"McDavid", "Draisaitl", "Matthews", "Nylander", "MacKinnon", "Crosby", "Ovechkin",
        "Petterson", "Boeser", "Hughes", "Caufield", "Suzuki", "Stamkos", "Kucherov", "Gretzky", "Orr", "Lemieux",
            "Bure"};
    String[] cities = {"Vancouver", "Edmonton", "Calgary", "Winnipeg", "Toronto", "Ottawa", "Montreal", "Detroit",
        "Boston", "Chicago", "New York"};
    String[] nicknames = {"Canucks", "Oilers", "Flames", "Jets", "Maple Leafs", "Senators", "Canadiens", "Red Wings",
        "Bruins", "Blackhawks", "Rangers"};

    //EFFECTS: creates new random generator object with 18 first and last names to choose from, and 11 city and 
    //         nicknames to choose from.
    public RandomGenerator() {
        namesLength = 18;
        teamsLength = 11;
    }

    //EFFECTS: randomizes a first and last name from pool of options, and then attaches them together.
    public String generatePlayerName() {
        Random random = new Random();
        String firstName = firstNames[random.nextInt(namesLength)];
        String lastName = lastNames[random.nextInt(namesLength)];
        return firstName + " " + lastName;
    }

    //EFFECTS: randomizes a city name and team nickname from pool of options, and then attaches them together.
    public String generateTeamName() {
        Random random = new Random();
        String city = cities[random.nextInt(teamsLength)];
        String nickname = nicknames[random.nextInt(teamsLength)];
        return city + " " + nickname;
    }
}
