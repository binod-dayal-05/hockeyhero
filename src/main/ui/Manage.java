package ui;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import model.Team;
import persistence.JsonReader;
import persistence.JsonWriter;
import model.Player;
import model.Market;
import model.Event;
import model.EventLog;
import model.League;

// Printing of event log reconfigured from https://github.students.cs.ubc.ca/CPSC210/AlarmSystem

//represents object that user interacts with to handle team management, viewing standings, saving/loading data and more
public class Manage {

    private Scanner scanner;
    private boolean endGame = false;
    private Market market;
    private Team myTeam;
    private League league;
    private Game game;

    private static final String JSON_LEAGUE = "./data/League.json";
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    // EFFECTS: constructs a new manage object with new scanner, new json writer, new json reader, new market, new
    //          league, and welcomes the user to the program.
    public Manage() {
        this.scanner = new Scanner(System.in);
        jsonWriter = new JsonWriter(JSON_LEAGUE);
        jsonReader = new JsonReader(JSON_LEAGUE);
        this.market = new Market();
        this.league = new League();
        welcome();
    }

    //EFFECTS: welcomes user by asking to start a new game or load a previous save, and executes their command
    public void welcome() {
        System.out.println("Welcome to Hockey Hero.");
        System.out.println("\nType 'load' to load from file.");
        System.out.println("Type 'new' to start a new team.");
        String input = this.scanner.nextLine();
        if (input.equals("load")) {
            loadLeague();
            myTeam = league.findTeam(11);
            game = new Game(myTeam, league, league.getMatchday(), league.getTeams());
            run();
        } else if (input.equals("new")) {
            create();
        } else {
            System.out.println("\nBad input...");
            welcome();
        }
    }

    //MODIFIES: this
    //EFFECTS: creates user team and begins the game
    public void create() {
        makeTeam();
        createPlayers(5);
        purchase();
        league.makeTeams(myTeam);
        league.makeSchedule();
        game = new Game(myTeam, league, 1, league.getTeams());
        run();
    }

    //MODIFIES: this
    //EFFECTS: gives user team the name of their choosing
    public void makeTeam() {
        System.out.println("\nPlease enter your team name: ");
        String name = this.scanner.nextLine();
        myTeam = new Team(name, 11);
        System.out.println("\nWelcome to the league, " + myTeam.getName() + "!");
    }

    //MODIFIES: this
    //EFFECTS: creates players to purchase and adds them to market
    public void createPlayers(int amount) {
        for (int x = 0; x < amount; x++) {
            Player p = new Player();
            market.addPlayer(p);
        }
    }

    //MODIFIES: this
    //EFFECTS: makes the user select players until they have at minimum 3 players in their roster. they may purchase
    //         more if desired
    public void purchase() {
        boolean exit = false;
        while (!exit || myTeam.getRoster().size() < 3) {
            if ((5 - market.getAvailablePlayers().size()) > 0) {
                createPlayers(5 - market.getAvailablePlayers().size());
            }
            if (myTeam.getRoster().size() < 3) {
                System.out.println("\nYou need at least 3 players on your roster.");
                System.out.println("Current roster size: " + myTeam.getRoster().size() + " players.");
            }
            System.out.println("\nPlayers available to purchase: ");
            for (int x = 0; x < market.getAvailablePlayers().size(); x++) {
                Player p = market.getAvailablePlayers().get(x);
                listStats(p, x);
            }
            exit = select(exit);
        }
    }

    //EFFECTS: prints out the information and stats of each player available to purchase
    public void listStats(Player p, int index) {
        System.out.println("\n(" + (index + 1) + ") Name: " + p.getName());
        System.out.println("Jersey number: " +  p.getNumber());
        System.out.println("Offense ability (out of 100): " + p.getOffense());
        System.out.println("Defense ability (out of 100): " + p.getDefense());
        System.out.println("Cost to buy: $" + p.getWage());
    }

    //EFFECTS: allows user to choose the specific player they want to purchase. returns whether they want to exit the
    //         player purchase menu or not
    public boolean select(boolean exit) {
        int choice = 0;
        while (choice < 1 || choice > 5) {
            System.out.println("\nPlease type the number next to the player you would like to select: ");
            choice = this.scanner.nextInt();
            this.scanner.nextLine();
            if (choice < 1 || choice > 5) {
                System.out.println("\nBad input...");
            }
        }
        addChoice(choice);
        return doneAdding(exit);
    }

    //MODIFIES: this
    //EFFECTS: adds selected player of choice to user team's roster, and removes them from available players in market
    public void addChoice(int choice) {
        myTeam.addPlayer(market.getAvailablePlayers().get(choice - 1));
        System.out.println(market.getAvailablePlayers().get(choice - 1).getName() + " added to roster!");
        market.removePlayer(market.getAvailablePlayers().get(choice - 1));
    }

    //EFFECTS: checks if and returns whether more players need to be added or if the user chose to do add more
    public boolean doneAdding(boolean exit) {
        String userInput;
        if (myTeam.getRoster().size() >= 3) {
            System.out.println("\nType q to exit the player market. Type any other character to buy another player: ");
            userInput = this.scanner.nextLine();
            if (userInput.equals("q")) {
                exit = true;
            }
        } else {
            System.out.println("\nYou still need " + (3 - myTeam.getRoster().size()) + " more player(s).");
            System.out.println("Type any character to buy another player.");
            userInput = this.scanner.nextLine();
            exit = false;
        }
        return exit;
    }

    // EFFECTS: keeps the program running until the user chooses to end it
    public void run() {
        String input;
        while (!endGame) {
            if (game.getMatchday() > 11) {
                endOfSeason();
            } else {
                input = showCommands();
                processCommands(input);
            }
        }
    }

    //EFFECTS: displays final standings and asks whether to start a new game or end the program
    public void endOfSeason() {
        String input;
        System.out.println("\nYour league's games have finished. Final standings: ");
        viewStandings();
        System.out.println("\nPress 'n' to begin a new game. Press 'q' to quit.");
        input = this.scanner.nextLine();
        if (input.equals("n")) {
            new Manage();
        } else if (input.equals("q")) {
            endGame = true;
            System.out.println("\nThanks for playing Hockey Hero!");
            printEventsLogged();
        }
    }

    // reconfigured from https://github.students.cs.ubc.ca/CPSC210/AlarmSystem
    //EFFECTS: prints out all events that have were logged while the program ran
    private void printEventsLogged() {
        EventLog eventsLogged = EventLog.getInstance();
        for (Event e : eventsLogged) {
            System.out.println(e);
        }
    }

    //EFFECTS: displays all the commands the user can make. returns the user's choice
    public String showCommands() {
        System.out.println("\nType 'p' to purchase new players.");
        System.out.println("Type 'r' to view your roster");
        System.out.println("Type 's' to view the current standings.");
        System.out.println("Type 'g' to play a games.");
        System.out.println("Type 'load' to load to file.");
        System.out.println("Type 'save' to save to file.");
        String userInput = this.scanner.nextLine();
        return userInput;
    }

    //EFFECTS: carries out the user's command
    public void processCommands(String input) {
        if (input.equals("p")) {
            purchase();
        } else if (input.equals("r")) {
            viewRoster();
        } else if (input.equals("s")) {
            viewStandings();
        } else if (input.equals("g")) {
            game.displaySchedule();
        } else if (input.equals("q")) {
            endGame = true;
        } else if (input.equals("load")) {
            loadLeague();
        } else if (input.equals("save")) {
            saveLeague();
        } else {
            System.out.println("\nBad input.");
            input = showCommands();
        }
    }

    //MODIFIES: this
    //EFFECTS: loads league from file
    private void loadLeague() {
        try {
            league = jsonReader.read();
            System.out.println("\nLoaded League from " + JSON_LEAGUE);
        } catch (IOException e) {
            System.out.println("\nUnable to read from file: " + JSON_LEAGUE);
        }
    }

    //MODIFIES: this
    //EFFECTS: saves current league to file
    private void saveLeague() {
        try {
            jsonWriter.open();
            jsonWriter.write(league);
            jsonWriter.close();
            System.out.println("\nSaved League to " + JSON_LEAGUE);
        } catch (FileNotFoundException e) {
            System.out.println("\nUnable to write to file: " + JSON_LEAGUE);
        }
    }

    //EFFECTS: prints user's roster and asks if they wish to remove players from it
    public void viewRoster() {
        System.out.println("\nYour current players: ");
        for (int x = 0; x < myTeam.getRoster().size(); x++) {
            Player p = myTeam.getRoster().get(x);
            listStats(p, x);
        }
        System.out.println("Would you like to remove any players? (y/n): ");
        String input = this.scanner.nextLine();
        if (input.equals("y")) {
            editRoster();
        }
    }

    //MODIFIES: this
    //EFFECTS: allows the user to choose which player to remove and removes it, then displays updated roster
    public void editRoster() {
        System.out.println("\nPlease type the number next to the player you would like to remove: ");
        int choice = this.scanner.nextInt();
        this.scanner.nextLine();
        myTeam.removePlayer(myTeam.getRoster().get(choice - 1));
        System.out.println("Player removed from roster!");
        viewRoster();
    }

    //MODIFIES: this
    //EFFECTS: sorts the league standings by points in descending order, then displays updated standings
    public void viewStandings() {
        league.sortOrder();
        System.out.println("\nLEAGUE STANDINGS: ");
        for (int x = 0; x < league.getTeams().size(); x++) {
            Team t = league.getTeams().get(x);
            System.out.println((x + 1) + ") " + t.getName() + " POINTS: " + t.getPoints() + " RECORD (W-D-L): " 
                    + t.getWins() + "-" + t.getTies() + "-" + t.getLosses() + " GAMES PLAYED: " + t.getGamesPlayed());
        }
    }

}
