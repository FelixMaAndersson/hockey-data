package se.yrgo.exceptions;

public class TeamNotFoundException extends Exception {

    public TeamNotFoundException(long id) {
        super("Team not found with id: " + id);
    }

    public TeamNotFoundException(String name) {
        super("Team not found with name: " + name);
    }
}