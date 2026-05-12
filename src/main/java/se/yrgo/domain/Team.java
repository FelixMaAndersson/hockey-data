package se.yrgo.domain;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private Set<Player> players = new HashSet<>();

    public static final int MAX_TEAM_SALARY = 24_997_500;
    public static final int MAX_PLAYERS = 6;


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

    public Set<Player> getPlayers() {
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

    public void removePlayer(Player player) {
        players.remove(player);
    }

    public long countPlayersByPosition(Position position) {
        long count = 0;

        for (Player player : players) {
            if (player.getPosition() == position) {
                count++;
            }
        }

        return count;
    }

    public long getRemainingForwards() {
        long forwards =
                countPlayersByPosition(Position.CENTER)
                        + countPlayersByPosition(Position.LEFT_WING)
                        + countPlayersByPosition(Position.RIGHT_WING);

        return 3 - forwards;
    }

    public long getRemainingDefenders() {
        return 2 - countPlayersByPosition(Position.DEFENDER);
    }

    public long getRemainingGoalies() {
        return 1 - countPlayersByPosition(Position.GOALIE);
    }

    public boolean hasRoomFor(Player player) {
        Position pos = player.getPosition();

        if (pos == Position.DEFENDER) {
            return getRemainingDefenders() > 0;
        }

        if (pos == Position.GOALIE) {
            return getRemainingGoalies() > 0;
        }

        return getRemainingForwards() > 0;
    }

    public boolean hasPlayer(Player player) {
        return players.contains(player);
    }

    @Override
    public String toString() {
        return "Team: " + this.getName();
    }
}