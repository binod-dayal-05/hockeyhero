package model;

import java.util.Random;

import org.json.JSONObject;

import persistence.Writable;

//represents a hockey player with all his information and stats
public class Player implements Writable {
    private String name;
    private int number;
    private int offense;
    private int defense;
    private int wage;

    private RandomGenerator nameGenerator;
    private Random rng;

    // EFFECTS: creates player with random values for name, jersey number [1-99], offense and defense ratings [80-99],
    //          and determines wage based on average rating; [80-90] rating can have wages from [$250-500], [90-99]
    //          rating can have wages from [$500-1000].
    public Player() {
        this.nameGenerator = new RandomGenerator();
        this.rng = new Random();

        this.name = nameGenerator.generatePlayerName();
        this.number = rng.nextInt(99) + 1;
        this.offense = rng.nextInt(20) + 80;
        this.defense = rng.nextInt(20) + 80;

        if (((offense + defense) / 2) < 90) {
            this.wage = rng.nextInt(251) + 250;
        } else {
            this.wage = rng.nextInt(501) + 500;
        }
        
    }

    // get and set methods

    //EFFECTS: returns name of player
    public String getName() {
        return name;
    }

    //MODIFIES: this
    //EFFECTS: sets name of player
    public void setName(String name) {
        this.name = name;
    }

    //EFFECTS: returns jersey number
    public int getNumber() {
        return number;
    }

    //MODIFIES: this
    //EFFECTS: sets jersey number
    public void setNumber(int number) {
        this.number = number;
    }

    //EFFECTS: returns offense stat
    public int getOffense() {
        return offense;
    }

    //MODIFIES: this
    //EFFECTS: sets offense stat
    public void setOffense(int offense) {
        this.offense = offense;
    }

    //EFFECTS: returns defense stat
    public int getDefense() {
        return defense;
    }

    //MODIFIES: this
    //EFFECTS: sets defense stat
    public void setDefense(int defense) {
        this.defense = defense;
    }

    //EFFECTS: returns player's wages in $
    public int getWage() {
        return wage;
    }

    //MODIFIES: this
    //EFFECTS: sets wage in $
    public void setWage(int wage) {
        this.wage = wage;
    }

    //EFFECTS: returns average between offense and defense stat
    public int getOverall() {
        return (offense + defense) / 2;
    }

    //Code reconfigured from https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo 
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("number", number);
        json.put("offense", offense);
        json.put("defense", defense);
        json.put("wage", wage);
        return json;
    }

}
