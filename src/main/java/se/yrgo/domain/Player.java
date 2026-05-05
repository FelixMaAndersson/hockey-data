package se.yrgo.domain;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private String playerId;
    private String fullName;
    private String teamName;
    private String position;
    private String jerseyNr; // should this be an int?
    private String captain; // in teams?
    private int refereeHeckling;
    private int beerChugging;
    private int diving;
    private int game;
    private int snusing;
    private int swag;
    private int salary;

	// needed for JPA - ignore until then
	public Player() {}
}
