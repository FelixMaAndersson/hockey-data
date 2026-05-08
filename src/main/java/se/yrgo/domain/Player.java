package se.yrgo.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int playerId;

    @Column(nullable = false, length = 50)
    private String fullName;

    @ManyToMany(mappedBy = "players")
    private List<Team> teams = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Position position;

    @Column(nullable = false)
    @Min(1)
    @Max(98)
    private int jerseyNr;

    // stats / ratings
    @Column(nullable = false)
    @Min(1)
    @Max(100)
    private int refereeHeckling;

    @Column(nullable = false)
    @Min(1)
    @Max(100)
    private int beerChugging;

    @Column(nullable = false)
    @Min(1)
    @Max(100)
    private int diving;

    @Column(nullable = false)
    @Min(1)
    @Max(100)
    private int swag;

    @Column(nullable = false)
    @Min(1)
    @Max(100)
    private int snusing;

    @Column(nullable = false)
    private int salary;

    public Player() {
    }

    public Player(String fullName, Position position, int jerseyNr,
                  int refereeHeckling, int beerChugging, int diving,
                  int swag, int snusing) {

        this.fullName = fullName;
        this.position = position;
        this.jerseyNr = jerseyNr;
        this.refereeHeckling = refereeHeckling;
        this.beerChugging = beerChugging;
        this.diving = diving;
        this.swag = swag;
        this.snusing = snusing;
        this.salary = (refereeHeckling
        + beerChugging
        + diving
        + swag
        +snusing) * 16500;
    }

    public int getPlayerId() {
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

    public int getSwag() {
        return swag;
    }

    public int getSnusing() {
        return snusing;
    }

    public int getSalary() {
        return salary;
    }

    @Override
    public String toString() {
        return "Player: " + this.getFullName();
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