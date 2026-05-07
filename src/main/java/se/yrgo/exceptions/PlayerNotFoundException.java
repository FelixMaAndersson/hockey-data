package se.yrgo.exceptions;

public class PlayerNotFoundException extends Exception {

    public PlayerNotFoundException(int playerId) {
        super("Player with id " + playerId + " was not found");
    }

}
