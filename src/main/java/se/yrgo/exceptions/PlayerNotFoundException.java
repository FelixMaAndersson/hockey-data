package se.yrgo.exceptions;

/**
 * Exception thrown when a player with the given id
 * could not be found in the database.
 */
public class PlayerNotFoundException extends Exception {

    public PlayerNotFoundException(int playerId) {
        super("Player with id " + playerId + " was not found");
    }

}
