package se.yrgo.dataaccess;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import se.yrgo.domain.Team;
import se.yrgo.domain.Player;
import se.yrgo.exceptions.TeamNotFoundException;

import java.util.List;

/**
 * Implementation of the TeamDao interface using JPA. Provides methods for creating, retrieving, updating and deleting teams in the database.
 */

@Repository
public class TeamDaoImpl implements TeamDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Team team) {
        em.persist(team);
    }
    // Method to retrieve a team by its ID, including its players. Throws TeamNotFoundException if no team with the given ID exists.
    @Override
    public Team getById(long teamId) throws TeamNotFoundException {
        try {
            return em.createQuery(
                            "SELECT t FROM Team t LEFT JOIN FETCH t.players WHERE t.id = :id",
                            Team.class)
                    .setParameter("id", teamId)
                    .getSingleResult();
        } catch (NoResultException e) {
            throw new TeamNotFoundException(teamId);
        }
    }

    // Method to retrieve a team by its name, including its players. Throws TeamNotFoundException if no team with the given name exists.
    @Override
    public Team getByName(String name) throws TeamNotFoundException {
        try {
            return em.createQuery(
                            "SELECT t FROM Team t LEFT JOIN FETCH t.players WHERE t.name = :name",
                            Team.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException e) {
            throw new TeamNotFoundException(name);
        }
    }
    // Method to update an existing team. Throws TeamNotFoundException if no team with the given ID exists.
    @Override
    public void update(Team team) throws TeamNotFoundException {
        if (em.find(Team.class, team.getId()) == null) {
            throw new TeamNotFoundException(team.getId());
        }
        em.merge(team);
    }
    
    // Method to delete a team. Throws TeamNotFoundException if no team with the given ID exists.
    @Override
    public void delete(Team team) throws TeamNotFoundException {
        Team managed = em.find(Team.class, team.getId());
        if (managed == null) {
            throw new TeamNotFoundException(team.getId());
        }
        em.remove(managed);
    }
    // Method to retrieve all teams from the database, including their players.
    @Override
    public List<Team> getAllTeams() {
        return em.createQuery("SELECT t FROM Team t", Team.class)
                .getResultList();
    }
}