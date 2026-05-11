package se.yrgo.services.leagues;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.yrgo.dataaccess.LeagueDao;
import se.yrgo.dataaccess.TeamDao;
import se.yrgo.domain.League;
import se.yrgo.domain.Team;
import se.yrgo.exceptions.LeagueNotFoundException;
import se.yrgo.exceptions.TeamNotFoundException;

import java.util.List;

/**
 * Service class for managing hockey leagues.
 * Handles business logic for creating, updating, deleting and retrieving leagues.
 * All methods are transactional by default.
 */
@Service
@Transactional
public class LeagueManagementService {

    private final LeagueDao dao;
    private final TeamDao teamDao;

    /**
     * Constructs a LeagueManagementService with the required DAOs.
     *
     * @param dao     the data access object for leagues
     * @param teamDao the data access object for teams
     */
    @Autowired
    public LeagueManagementService(LeagueDao dao, TeamDao teamDao) {
        this.dao = dao;
        this.teamDao = teamDao;
    }

    /**
     * Creates a new league with the given name and saves it to the database.
     *
     * @param name the name of the league
     * @return the created league
     */
    public League createLeague(String name) {
        League league = new League(name);
        dao.create(league);
        return league;
    }

    /**
     * Updates the name of an existing league.
     *
     * @param oldName the current name of the league
     * @param newName the new name to set
     * @throws LeagueNotFoundException if no league with the given name exists
     */
    public void updateLeagueName(String oldName, String newName) throws LeagueNotFoundException {
        League league = dao.getByName(oldName);
        league.setName(newName);
        dao.update(league);
    }

    /**
     * Deletes a league by name.
     *
     * @param name the name of the league to delete
     * @throws LeagueNotFoundException if no league with the given name exists
     */
    public void deleteLeague(String name) throws LeagueNotFoundException {
        League league = dao.getByName(name);
        dao.delete(league);
    }

    /**
     * Retrieves a league by its name.
     *
     * @param name the name of the league
     * @return the matching league
     * @throws LeagueNotFoundException if no league with the given name exists
     */
    public League getLeagueByName(String name) throws LeagueNotFoundException {
        return dao.getByName(name);
    }

    /**
     * Returns all leagues stored in the database.
     *
     * @return list of all leagues, empty list if none found
     */
    public List<League> getAllLeagues() {
        return dao.getAllLeagues();
    }

    /**
     * Adds a team to a league.
     * A league can contain a maximum of 10 teams.
     *
     * @param leagueName the name of the league to add the team to
     * @param teamName   the name of the team to add
     * @throws LeagueNotFoundException if no league with the given name exists
     * @throws TeamNotFoundException   if no team with the given name exists
     */
    public void addTeamToLeague(String leagueName, String teamName)
            throws LeagueNotFoundException, TeamNotFoundException {

        League league = dao.getByName(leagueName);

        if (league.getTeams().size() >= 10) {
            System.out.println("A league cannot contain more than 10 teams");
            return;
        }

        Team team = teamDao.getByName(teamName);

        league.addTeam(team);

        dao.update(league);
    }

}
