package se.yrgo.domain;

import jakarta.persistence.*;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
    @JoinTable(
            name = "TEAM_PLAYER",
            joinColumns = @JoinColumn(name = "TEAM_ID"),
            inverseJoinColumns = @JoinColumn(name = "PLAYER_ID")
    )
    private List<Player> players = new ArrayList<>();

    public static final int MAX_TEAM_SALARY = 24_997_500;


    public int getTotalSalary() {
        return players.stream()
                .mapToInt(Player::getSalary)
                .sum();
    }

    public String getFormattedTotalSalary() {
        return String.format("%,d", getTotalSalary());
    }

    public int getRemainingBudget() {
        return MAX_TEAM_SALARY - getTotalSalary();
    }

    public String getFormattedRemainingBudget() {
        return String.format("%,d", getRemainingBudget());
    }

    public boolean canAfford(Player player) {
        return getTotalSalary() + player.getSalary() <= MAX_TEAM_SALARY;
    }

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

    public List<Player> getPlayers() {
        return players;
    }

    public void setName(String name) {
        this.name = name;
    }

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
        return "Team: " + this.getName();
    }
}