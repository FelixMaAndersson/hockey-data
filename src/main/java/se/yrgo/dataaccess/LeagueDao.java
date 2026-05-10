package se.yrgo.dataaccess;

import se.yrgo.domain.League;
import se.yrgo.domain.Team;
import se.yrgo.exceptions.LeagueNotFoundException;

import java.util.List;

/**
 * Interface defining data access operations for League entities.
 * Implementations handle the communication with the database.
 */
public interface LeagueDao {

    public void create(League league);

    public League getById(int leagueId) throws LeagueNotFoundException;

    public League getByName(String name) throws LeagueNotFoundException;

    public void update(League league) throws LeagueNotFoundException;

    public void delete(League league) throws LeagueNotFoundException;

    public List<League> getAllLeagues();

    public List<Team> getAllTeams(int leagueId) throws LeagueNotFoundException;
}
