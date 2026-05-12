package se.yrgo.domain;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Entity class representing a hockey team. Contains fields for team name, league and players.
 * Provides methods for calculating total salary, remaining budget and checking if a player can be added to the team.
 */
@Entity
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    // Many-to-one relationship with League. A team belongs to one league, and a league can have many teams.
    @ManyToOne
    @JoinColumn(name = "league_id")
    private League league;
    // Many-to-many relationship with Player. A team can have many players, and a player can belong to many teams.
    @ManyToMany
    @JoinTable(
            name = "TEAM_PLAYER",
            joinColumns = @JoinColumn(name = "TEAM_ID"),
            inverseJoinColumns = @JoinColumn(name = "PLAYER_ID")
    )
    private Set<Player> players = new HashSet<>();

    public static final int MAX_TEAM_SALARY = 24_997_500;
    public static final int MAX_PLAYERS = 6;

   // Method to calculate the total salary of all players in the team.
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
   // Method to check if a player can be added to the team without exceeding the maximum salary limit.
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
    // Methods to calculate how many more players of each position can be added to the team based on the maximum allowed.
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