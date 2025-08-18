package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import model.Event;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import persistence.JsonReader;
import persistence.JsonWriter;
import model.Team;
import model.Player;
import model.Market;
import model.EventLog;
import model.League;

// All code about Swing and JFrame was learned from the following youtube series:
// https://youtube.com/playlist?list=PL3bGLnkkGnuV699lP_f9DvxyK5lMFpq6U&si=-J6c9oSfTSbTGbBz 

// Code about resetting frame adapted from: https://stackoverflow.com/a/6261090
//                                     and: https://stackoverflow.com/a/70221536

// Code for displaying image adapted from: https://stackoverflow.com/a/18027889
//                                    and: https://stackoverflow.com/a/18335435

// Code for removing target box on buttons adapted from: https://stackoverflow.com/a/16368435

// Reconfigured from SmartHome in https://github.students.cs.ubc.ca/CPSC210/LongFormProblemStarters.git

// Printing of event log reconfigured from https://github.students.cs.ubc.ca/CPSC210/AlarmSystem

// Code for calling printEventsLogged() activating on window close adapted from: 
// https://docs.oracle.com/javase/tutorial/uiswing/events/windowlistener.html
// and
// https://docs.oracle.com/javase/tutorial/uiswing/components/frame.html#windowevents

//represents graphical user interface where user will manage their team and play games
public class HockeyHeroGUI extends JFrame {
    private JButton newGameButton;
    private JButton loadButton;
    private JButton saveButton;
    private JButton purchaseButton;
    private JButton viewRosterButton;
    private JButton viewStandingsButton;
    private JButton playGamesButton;
    private JButton viewScheduleButton;
    private JButton nextGameButton;
    private JLabel titleLabel;
    private JLabel rosterCountLabel;
    private JPanel mainPanel;

    private static final String JSON_LEAGUE = "./data/League.json";
    private static final String IMAGE_PATH = "./data/hockeyimage.jpg";

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private int matchday;
    private Random rng;
    private League league;
    private Market market;
    private Team myTeam;

    //EFFECTS: constructs the HockeyHeroGUI frame by initializing frame, intializing the non-jframe fields, and
    //         initializing the panels and the buttons.
    public HockeyHeroGUI() {
        initializeFrame();
        initializeObjects();
        initializePanelsButtons();
    }

    // adapted from https://docs.oracle.com/javase/tutorial/uiswing/events/windowlistener.html 
    //          and https://docs.oracle.com/javase/tutorial/uiswing/components/frame.html#windowevents
    //MODIFIES: this
    //EFFECTS: creates JFrame with title "Hockey Hero", 900x600 frame size, X button closes
    //         window, with BorderLayout.
    public void initializeFrame() {
        setTitle("Hockey Hero");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        setResizable(false);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                printEventsLogged();
                dispose();
            }
        });
    }

    // reconfigured from https://github.students.cs.ubc.ca/CPSC210/AlarmSystem
    //EFFECTS: prints out all events that have were logged while the program ran
    private void printEventsLogged() {
        EventLog eventsLogged = EventLog.getInstance();
        for (Event e : eventsLogged) {
            System.out.println(e);
        }
    }

    //MODIFIES: this
    //EFFECTS: initializes league and market, sets matchday to 1, and generates first 5 purchasable players.
    public void initializeObjects() {
        league = new League();
        market = new Market();
        matchday = 1;
        rng = new Random();
        addPlayersToMarket(5);
        jsonWriter = new JsonWriter(JSON_LEAGUE);
        jsonReader = new JsonReader(JSON_LEAGUE);
    }

    //MODIFIES: this
    //EFFECTS: sets top text to a welcome message, creates buttons and panels, and adds them to frame.
    public void initializePanelsButtons() {
        titleLabel = new JLabel("Welcome to Hockey Hero!", SwingConstants.CENTER);
        add(titleLabel, BorderLayout.NORTH);
        createButtons();
        JPanel buttonPanel = new JPanel();
        addButtons(buttonPanel);
        add(buttonPanel, BorderLayout.SOUTH);
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        add(mainPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    //MODIFIES: this
    //EFFECTS: creates buttons and adds their behaviour when clicked
    public void createButtons() {
        initializeButtons();
        firstThreeButtons();
        nextThreeButtons();
        lastButton();
    }

    //MODIFIES: this
    //EFFECTS: adds buttons to button panel
    public void addButtons(JPanel buttonPanel) {
        buttonPanel.add(newGameButton);
        buttonPanel.add(loadButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(purchaseButton);
        buttonPanel.add(viewRosterButton);
        buttonPanel.add(viewStandingsButton);
        buttonPanel.add(playGamesButton);
    }

    //MODIFIES: this
    //EFFECTS: creates buttons and disables all but first two buttons. disables focus paint
    public void initializeButtons() {
        newGameButton = new JButton("New Team");
        loadButton = new JButton("Load");
        saveButton = new JButton("Save");
        purchaseButton = new JButton("Purchase");
        viewRosterButton = new JButton("View Roster");
        viewStandingsButton = new JButton("View Standings");
        playGamesButton = new JButton("Play Games");
        saveButton.setEnabled(false);
        purchaseButton.setEnabled(false);
        viewRosterButton.setEnabled(false);
        viewStandingsButton.setEnabled(false);
        playGamesButton.setEnabled(false);
        newGameButton.setFocusPainted(false);
        loadButton.setFocusPainted(false);
        saveButton.setFocusPainted(false);
        purchaseButton.setFocusPainted(false);
        viewRosterButton.setFocusPainted(false);
        viewStandingsButton.setFocusPainted(false);
        playGamesButton.setFocusPainted(false);
    }

    //MODIFIES: this
    //EFFECTS: adds expected behaviour of what happens when button is clicked for first 3 buttons
    public void firstThreeButtons() {
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createTeam();
            }
        });
        loadButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadLeague();
            }
        });
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveLeague();
            }
        });
    }

    //MODIFIES: this
    //EFFECTS: adds expected behaviour of what happens when button is clicked for next 3 buttons
    //         also updates top text to show the purpose of the new page to the user
    public void nextThreeButtons() {
        purchaseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                purchasePlayers();
                titleLabel.setText("Purchase Players");
            }
        });
        viewRosterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewRoster();
                titleLabel.setText("My Roster");
            }
        });
        viewStandingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewStandings();
                titleLabel.setText("League Standings");
            }
        });
    }

    //MODIFIES: this
    //EFFECTS: adds expected behaviour of what happens when button is clicked for last button
    //         also updates top text to show the purpose of the new page to the user
    public void lastButton() {
        playGamesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.removeAll();
                titleLabel.setText("Games");
                nextGamesAndSchedule();
                revalideteRepaintMainPanel();
            }
        });
    }

    //MODIFIES: this
    //EFFECTS: creates user team with given name and starts league
    public void createTeam() {
        String teamName = JOptionPane.showInputDialog(this, "Enter your team name:");
        if (teamName != null && teamName.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "You must enter a name", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (teamName != null) {
            myTeam = new Team(teamName, 11);
            updateButtonStates();
            startLeague();
        }
    }

    //MODIFIES: this
    //EFFECTS: creates teams and schedules for league
    public void startLeague() {
        league.makeTeams(myTeam);
        league.makeSchedule();
    }

    //MODIFIES: this
    //EFFECTS: sorts league and displays standings for entire league
    public void viewStandings() {
        league.sortOrder();
        mainPanel.removeAll();
        for (int x = 0; x < league.getTeams().size(); x++) {
            Team team = league.getTeams().get(x);
            JPanel teamPanel = new JPanel();
            teamPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            teamPanel.add(new JLabel((x + 1) + ". " + team.getName() + " - Points: " + team.getPoints() 
                    + ", Record (W-D-L): " + team.getWins() + "-" + team.getTies() + "-" + team.getLosses() 
                    + ", Games Played: " + team.getGamesPlayed()));
            mainPanel.add(teamPanel);
        }
        revalideteRepaintMainPanel();
    }

    //MODIFIES: this
    //EFFECTS: allows user to purchase players. most buttons will be unavailable while the user 
    //         does not have at least 3 players on team.
    public void purchasePlayers() {
        mainPanel.removeAll();
        ArrayList<Player> available = market.getAvailablePlayers();
        for (Player p : available) {
            JPanel playerPanel = new JPanel();
            playerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            playerPanel.add(new JLabel(p.getName() + " - Offense: " 
                    + p.getOffense() + ", Defense: " + p.getDefense()));
            // not same as this.purchaseButton: this one is next to each player, the field is for purchase page
            JButton purchaseButton = new JButton("Purchase");
            purchaseButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    myTeam.addPlayer(p);
                    market.removePlayer(p);
                    addPlayersToMarket(1);
                    updateButtonStates();
                    purchasePlayers();
                }
            });
            purchaseButton.setFocusPainted(false);
            playerPanel.add(purchaseButton);
            mainPanel.add(playerPanel);
        }
        showCount();
    }

    //MODIFIES: this
    //EFFECTS: shows how many players user has on their team and refreshes roster panel
    public void showCount() {
        int playerCount = myTeam.getRoster().size();
        rosterCountLabel = new JLabel("Players on Roster: " + playerCount, SwingConstants.CENTER);
        rosterCountLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(rosterCountLabel);
        revalideteRepaintMainPanel();
    }


    //MODIFIES: this
    //EFFECTS: displays all players on user team. creates buttons for showing player, removing player from roster, and
    //         best player on roster. adds a vertical scroll bar in case the roster size is large.
    public void viewRoster() {
        mainPanel.removeAll();
        JPanel rosterPanel = new JPanel();
        rosterPanel.setLayout(new BoxLayout(rosterPanel, BoxLayout.Y_AXIS));
        for (Player p : myTeam.getRoster()) {
            JPanel playerPanel = new JPanel();
            playerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
            JLabel playerInfo = new JLabel(p.getName());
            playerPanel.add(playerInfo);
            infoButton(playerPanel, p);
            removeButton(playerPanel, p);
            rosterPanel.add(playerPanel);
        }
        JScrollPane scrollPane = new JScrollPane(rosterPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        mainPanel.add(scrollPane, BorderLayout.WEST);
        if (myTeam.getRoster().size() > 0) {
            JButton bestPlayerButton = new JButton("Show Best Player On Team");
            JPanel bestPanel = new JPanel();
            bestPlayerButton(bestPlayerButton, bestPanel);
            rosterPanel.add(bestPanel, FlowLayout.CENTER);
        }
        revalideteRepaintMainPanel();
    }

    //MODIFIES: this
    //EFFECTS: creates a button that when clicked, shows the info of the best player on the team. "best" means
    //         the player with the highest average stats.
    public void bestPlayerButton(JButton bestPlayerButton,JPanel bestPanel) {
        bestPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        bestPlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Player best = findBest();
                JOptionPane.showMessageDialog(HockeyHeroGUI.this,
                        "Name: " + best.getName()
                        + "\nOffense: " + best.getOffense()
                        + "\nDefense: " + best.getDefense()
                        + "\nJersey Number: " + best.getNumber()
                        + "\nWage: $" + best.getWage(),
                        "Your best player",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });
        bestPlayerButton.setFocusPainted(false);
        bestPanel.add(bestPlayerButton);
    }

    //EFFECTS: returns best player on team
    public Player findBest() {
        Player best = null;
        for (int x = 0; x < myTeam.getRoster().size(); x++) {
            if (x == 0) {
                best = myTeam.getRoster().get(x);
            }
            if (myTeam.getRoster().get(x).getOverall() > best.getOverall()) {
                best = myTeam.getRoster().get(x);
            }
        }
        return best;
    }

    //EFFECTS: creates the information button for a player, and displays all their stats and info in
    //         a new small dialog window with an info icon
    public void infoButton(JPanel playerPanel, Player p) {
        JButton infoButton = new JButton("Info");
        infoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(HockeyHeroGUI.this,
                        "Name: " + p.getName()
                        + "\nOffense: " + p.getOffense()
                        + "\nDefense: " + p.getDefense()
                        + "\nJersey Number: " + p.getNumber()
                        + "\nWage: $" + p.getWage(),
                        "Player Info",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });
        infoButton.setFocusPainted(false);
        playerPanel.add(infoButton);
    }

    //EFFECTS: creates the remove player button for a player, allowing the user to remove chosen player
    //         from roster.
    public void removeButton(JPanel playerPanel, Player p) {
        JButton removeButton = new JButton("Remove");
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                myTeam.removePlayer(p);
                viewRoster();
                updateButtonStates();
            }
        });
        removeButton.setFocusPainted(false);
        playerPanel.add(removeButton);
    }

    //MODIFIES: this
    //EFFECTS: loads current league from file
    public void loadLeague() {
        try {
            league = jsonReader.read();
            myTeam = league.getTeams().get(11);
            matchday = league.getMatchday();
            updateButtonStates();
            JOptionPane.showMessageDialog(this, "League loaded from " + JSON_LEAGUE);
        } catch (IOException e) {
            String s = "Unable to read from file: " + JSON_LEAGUE;
            JOptionPane.showMessageDialog(this, s + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //MODIFIES: this
    //EFFECTS: saves current league to file
    public void saveLeague() {
        try {
            jsonWriter.open();
            jsonWriter.write(league);
            jsonWriter.close();
            JOptionPane.showMessageDialog(this, "League saved to " + JSON_LEAGUE);
        } catch (FileNotFoundException e) {
            String s = "Unable to save to file: " + JSON_LEAGUE;
            JOptionPane.showMessageDialog(this, s + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //MODIFIES: this
    //EFFECTS: updates which buttons are enabled to all but first 2. last button will be disabled at any moment
    //         user's roster size falls below 3 players
    public void updateButtonStates() {
        if (myTeam != null) {
            newGameButton.setEnabled(false);
            loadButton.setEnabled(false);
            saveButton.setEnabled(true);
            purchaseButton.setEnabled(true);
            viewRosterButton.setEnabled(true);
            viewStandingsButton.setEnabled(true);
            playGamesButton.setEnabled(myTeam.getRoster().size() >= 3);
        }
    }

    //MODIFIES: this
    //EFFECTS: creates specified amount of players to create and add them to market
    public void addPlayersToMarket(int amount) {
        for (int x = 0; x < amount; x++) {
            market.addPlayer(new Player());
        }
    }

    //MODIFIES: this
    //EFFECTS: displays user's upcoming opponents
    public void displaySchedule() {
        mainPanel.removeAll();
        nextGamesAndSchedule();
        ArrayList<Integer> opponents = myTeam.getOpponents();
        JLabel title = new JLabel("Upcoming Opponents:");
        mainPanel.add(title);
        for (int opponent : opponents) {
            JLabel opponentLabel = new JLabel(league.findTeam(opponent).getName());
            mainPanel.add(opponentLabel);
        }
        revalideteRepaintMainPanel();
    }

    //MODIFIES: this
    //EFFECTS: creates view schedule and next game buttons and adds their expected functionality.
    public void nextGamesAndSchedule() {
        viewScheduleButton = new JButton("View Schedule");
        nextGameButton = new JButton("Next Game");
        viewScheduleButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displaySchedule();
            }
        });
        nextGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainPanel.removeAll();
                addButtonsBack();
                playNextGame();
                displayImage();
                revalideteRepaintMainPanel();
                matchday = league.getMatchday();
            }
        });
        addButtonsBack();
    }

    //MODIFIES: this
    //EFFECTS: adds the two buttons to frame. disables them if no more games remain
    public void addButtonsBack() {
        viewScheduleButton.setFocusPainted(false);
        nextGameButton.setFocusPainted(false);
        mainPanel.add(viewScheduleButton);
        mainPanel.add(nextGameButton);
    }

    //adapted from: https://stackoverflow.com/a/18027889 and https://stackoverflow.com/a/18335435
    //MODIFIES: this
    //EFFECTS: displays hockey image beneath the score
    public void displayImage() {
        ImageIcon imageIcon = new ImageIcon(IMAGE_PATH);
        Image image = imageIcon.getImage();
        Image scaledImage = image.getScaledInstance(479, 270,Image.SCALE_SMOOTH);
        imageIcon = new ImageIcon(scaledImage);
        mainPanel.add(new JLabel(imageIcon));
    }

    //MODIFIES: this
    //EFFECTS: simulates user's next game, the next game for each team in the league, and increases matchday count for
    //         both this game and the league
    public void playNextGame() {
        JLabel gameCompleteLabel = new JLabel("Today's game is complete. Results: ");
        mainPanel.add(gameCompleteLabel);
        simulate(myTeam, league.findTeam(myTeam.getOpponent(0)));
        myTeam.removeOpponent(0);
        playGames();
        matchday++;
        league.setMatchday(matchday);
        checkIfDone();
    }

    //MODIFIES: this
    //EFFECTS: for each team in the league, if they haven't played this week's game yet, it simulates their game
    //         as long as user team is not involved, as user simulates their games in previous method. once a game has
    //         been played, both teams are removed from each other's opponents lists.
    public void playGames() {
        for (Team t : league.getTeams()) {
            if (t.getGamesPlayed() < matchday) {
                if (league.findTeam(t.getNextOpponent()) != myTeam && league.findTeam(t.getNextOpponent()) != t) {
                    simulate(t, league.findTeam(t.getNextOpponent()));
                    Team nextOpp = league.findTeam(t.getNextOpponent());
                    nextOpp.removeOpponent(nextOpp.getOpponents().indexOf(t.getId()));
                    t.removeOpponent(0);
                } else {
                    t.removeOpponent(0);
                }
            }
        }
    }

    //MODIFIES: this
    //EFFECTS: simulates games of 2 given teams. results are determined by offense and defense stats with a random
    //         factor in it as well. results get reflected in both teams by adding their result into their counts for
    //         win/tie/loss. if user's team played, show the results of the game.
    public void simulate(Team t1, Team t2) {
        int t1Offense = t1.getOffense();
        int t1Defense = t1.getDefense();
        int t2Offense = t2.getOffense();
        int t2Defense = t2.getDefense();
        int t1Goals = 0;
        int t2Goals = 0;
        t1Goals = t1Offense / t2Defense + (rng.nextInt(4));
        t2Goals = t2Offense / t1Defense + (rng.nextInt(4));
        if (t1Goals > t2Goals) {
            t1.addWin();
            t2.addLoss();
        } else if (t1Goals < t2Goals) {
            t1.addLoss();
            t2.addWin();
        } else {
            t1.addTie();
            t2.addTie();
        }
        if (t1 == myTeam) {
            showScore(t1, t1Goals, t2, t2Goals);
        } else if (t2 == myTeam) {
            showScore(t2, t2Goals, t1, t1Goals);
        }
    }

    //MODIFIES: this
    //EFFECTS: displays the score of user team's game against opponent
    public void showScore(Team myTeam, int myGoals, Team opp, int oppGoals) {
        JLabel score = new JLabel(myTeam.getName() + " " + myGoals + " - "
                + oppGoals + " " + opp.getName());
        mainPanel.add(score);
    }

    //EFFECTS: if matchday is bigger than 11 initiate the sequence to finish the game
    public void checkIfDone() {
        if (matchday > 11) {
            finish();
        }
    }

    //MODIFIES: this
    //EFFECTS: lets user know game is done and only allows them to view standings or close application
    public void finish() {
        titleLabel.setText("Thanks for playing. View final standings or close application.");
        viewScheduleButton.setEnabled(false);
        nextGameButton.setEnabled(false);
        finalButtons();
    }

    //MODIFIES: this
    //EFFECTS:
    public void finalButtons() {
        saveButton.setEnabled(false);
        purchaseButton.setEnabled(false);
        viewRosterButton.setEnabled(false);
        playGamesButton.setEnabled(false);
    }

    //adapted from: https://stackoverflow.com/a/6261090 and https://stackoverflow.com/a/70221536
    //MODIFIES: this
    //EFFECTS: revalidates and resets main panel
    public void revalideteRepaintMainPanel() {
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    // //for testing purposes only so I can run the GUI quicker. will be deleted upon hand-in
    // public static void main(String[] args) {
    //     new HockeyHeroGUI();
    // }
}