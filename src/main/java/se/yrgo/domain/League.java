package se.yrgo.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a hockey league.
 * One league contains multiple teams (one-to-many relationship)
 */
@Entity
public class League {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;

    /**
     * A league can have many teams.
     * CascadeType.ALL means all operations (persist, merge, remove) are cascaded to teams.
     */
    @OneToMany(mappedBy = "league", cascade = CascadeType.ALL)
    private List<Team> teams = new ArrayList<>();

    public League() {
    }

    /**
     * Creates a new league with the given name.
     *
     * @param name the name of the league
     */
    public League(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public List<Team> getTeams() {
        return this.teams;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addTeam(Team team) {
        teams.add(team);
        team.setLeague(this);
    }

    @Override
    public String toString() {
        return "League: " + this.getName();
    }
}
