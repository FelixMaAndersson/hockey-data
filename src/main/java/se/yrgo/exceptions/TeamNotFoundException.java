package se.yrgo.exceptions;

/**
 * Custom exception thrown when a team is not found in the database.
 * Provides constructors for both team ID and team name.
 */
public class TeamNotFoundException extends Exception {

    public TeamNotFoundException(long id) {
        super("Team not found with id: " + id);
    }

    public TeamNotFoundException(String name) {
        super("Team not found with name: " + name);
    }
}