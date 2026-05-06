package se.yrgo.dataaccess;

import se.yrgo.domain.Team;
import se.yrgo.domain.Player;
import se.yrgo.exceptions.TeamNotFoundException;

import java.util.List;

public interface TeamDao {

    void create(Team team);

    Team getById(long teamId) throws TeamNotFoundException;

    Team getByName(String name) throws TeamNotFoundException;

    void update(Team team) throws TeamNotFoundException;

    void delete(Team team) throws TeamNotFoundException;

    List<Team> getAllTeams();

    List<Player> getAllPlayers(long teamId) throws TeamNotFoundException;
}