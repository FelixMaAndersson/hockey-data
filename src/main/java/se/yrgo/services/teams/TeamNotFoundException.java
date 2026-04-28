package se.yrgo.services.teams;

public class TeamNotFoundException extends RuntimeException {
    public TeamNotFoundException(String message) {
        super(message);
    }
}
