package se.yrgo.exceptions;

public class PlayerNotFoundException extends Exception {

    public PlayerNotFoundException(String playerId) {
        super("Player with id " + playerId + " was not found");
    }

}
