package se.yrgo.domain;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "league_id")
    private League league;

    @ManyToMany
    private List<Player> players = new ArrayList<>();

    public Team() {}

    public Team(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public League getLeague() {
        return league;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setName(String name) {
        this.name = name;
    }

    // 🔥 Behåll kollegans logik
    public void setLeague(League league) {
        this.league = league;
        if (league != null && !league.getTeams().contains(this)) {
            league.getTeams().add(this);
        }
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    @Override
    public String toString() {
        return "Team{" +
                "name='" + name + '\'' +
                ", players=" + players +
                '}';
    }
}