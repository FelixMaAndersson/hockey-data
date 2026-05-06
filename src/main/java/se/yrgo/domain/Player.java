package se.yrgo.domain;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private String playerId;
    private String fullName;
    private Team team;
    private Position position;
    private int jerseyNr;
    private boolean captain;

    // stats / ratings
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