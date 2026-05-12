package se.yrgo.exceptions;

/**
 * Exception thrown when invalid player data is provided,
 * for example invalid ratings or jersey numbers.
 */
public class InvalidPlayerException extends RuntimeException {

    public InvalidPlayerException(String message) {
        super(message);
    }
}