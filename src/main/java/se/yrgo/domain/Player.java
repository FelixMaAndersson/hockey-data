package se.yrgo.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Player {

    @Id
    private String playerId;

    private String fullName;

    @ManyToMany(mappedBy = "players")
    private List<Team> teams = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Position position;

    private int jerseyNr;

    // stats / ratings
    private int refereeHeckling;
    private int beerChugging;
    private int diving;
    private int game;
    private int snusing;
    private int swag;

    private int salary;

    public Player() {
    }

    public Player(String playerId, String fullName, Position position, int jerseyNr,
                  int refereeHeckling, int beerChugging, int diving,
                  int game, int snusing, int swag, int salary) {

        this.playerId = playerId;
        this.fullName = fullName;
        this.position = position;
        this.jerseyNr = jerseyNr;
        this.refereeHeckling = refereeHeckling;
        this.beerChugging = beerChugging;
        this.diving = diving;
        this.game = game;
        this.snusing = snusing;
        this.swag = swag;
        this.salary = salary;
    }

    public String getPlayerId() {
        return playerId;
    }

    public String getFullName() {
        return fullName;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public Position getPosition() {
        return position;
    }

    public int getJerseyNr() {
        return jerseyNr;
    }

    public int getRefereeHeckling() {
        return refereeHeckling;
    }

    public int getBeerChugging() {
        return beerChugging;
    }

    public int getDiving() {
        return diving;
    }

    public int getGame() {
        return game;
    }

    public int getSnusing() {
        return snusing;
    }

    public int getSwag() {
        return swag;
    }

    public int getSalary() {
        return salary;
    }

    @Override
    public String toString() {
        return "Player{" +
                "playerId='" + playerId + '\'' +
                ", fullName='" + fullName + '\'' +
                ", position=" + position +
                ", jerseyNr=" + jerseyNr +
                ", salary=" + salary +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Player player)) {
            return false;
        }
        return Objects.equals(playerId, player.playerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerId);
    }
}