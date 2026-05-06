package se.yrgo.dataaccess;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import se.yrgo.domain.League;
import se.yrgo.domain.Team;
import se.yrgo.exceptions.LeagueNotFoundException;

import java.util.List;

/**
 * Data access object for League entities.
 * Handles database operations such as create, read, update and delete.
 */
@Repository
public class LeagueDaoImpl implements LeagueDao {

    @PersistenceContext
    private EntityManager em;

    /**
     * Saves a new league to the database.
     *
     * @param league the league to save
     */
    @Override
    public void create(League league) {
        em.persist(league);
    }

    /**
     * Finds a league by its ID.
     *
     * @param leagueId the ID of the league
     * @return the matching league
     * @throws LeagueNotFoundException if no league is found
     */
    @Override
    public League getById(int leagueId) throws LeagueNotFoundException {
        League league = em.find(League.class, leagueId);
        if (league == null) {
            throw new LeagueNotFoundException(leagueId);
        }
        return league;
    }

    /**
     * Finds a league by its name.
     *
     * @param name the name of the league
     * @return the matching league
     * @throws LeagueNotFoundException if no league is found
     */
    @Override
    public League getByName(String name) throws LeagueNotFoundException {
        try {
            return em.createQuery(
                            "SELECT l FROM League l WHERE l.name = :name", League.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException e) {
            throw new LeagueNotFoundException(name);
        }
    }

    /**
     * Updates an existing league.
     *
     * @param league the league to update
     * @throws LeagueNotFoundException if the league does not exist
     */
    @Override
    public void update(League league) throws LeagueNotFoundException {
        if (em.find(League.class, league.getId()) == null) {
            throw new LeagueNotFoundException(league.getId());
        }
        em.merge(league);
    }

    /**
     * Deletes a league from the database.
     *
     * @param league the league to delete
     * @throws LeagueNotFoundException if the league does not exist
     */
    @Override
    public void delete(League league) throws LeagueNotFoundException {
        League managed = em.find(League.class, league.getId());
        if (managed == null) {
            throw new LeagueNotFoundException(league.getId());
        }
        em.remove(managed);
    }

    /**
     * Returns all leagues stored in the database.
     *
     * @return list of all leagues
     */
    @Override
    public List<League> getAllLeagues() {
        return em.createQuery("SELECT l FROM League l", League.class)
                .getResultList();
    }

    /**
     * Returns all teams that belong to a specific league.
     *
     * @param leagueId the ID of the league
     * @return list of teams in the league
     * @throws LeagueNotFoundException if the league does not exist
     */
    @Override
    public List<Team> getAllTeams(int leagueId) throws LeagueNotFoundException {

        return em.createQuery(
                        "SELECT t FROM Team t WHERE t.league.id = :id", Team.class)
                .setParameter("id", leagueId)
                .getResultList();
    }
}



