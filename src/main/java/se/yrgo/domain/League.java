package se.yrgo.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class League {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;

    @OneToMany(mappedBy = "league", cascade = CascadeType.ALL)
    private List<Team> teams = new ArrayList<>();

    public League() {
    }

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
        return "League{" +
                "name='" + name + '\'' +
                ", teams=" + teams +
                '}';
    }
}
