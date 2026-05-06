package se.yrgo.exceptions;

public class LeagueNotFoundException extends Exception {

    public LeagueNotFoundException(String name) {
        super("League: " + name + " not found.");
    }

    public LeagueNotFoundException(int id) {
        super("League with id: " + id + " not found.");
    }
}
