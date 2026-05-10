package se.yrgo.exceptions;

/**
 * Thrown when a requested league cannot be found in the database.
 * This is a checked exception that must be handled by the caller.
 */
public class LeagueNotFoundException extends Exception {

    /**
     * Creates a new exception with the league name that was not found.
     *
     * @param name the name of the league that could not be found
     */
    public LeagueNotFoundException(String name) {
        super("League: " + name + " not found.");
    }

    /**
     * Creates a new exception with the league ID that was not found.
     *
     * @param id the ID of the league that could not be found
     */
    public LeagueNotFoundException(int id) {
        super("League with id: " + id + " not found.");
    }
}
