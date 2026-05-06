package se.yrgo.dataaccess;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import se.yrgo.domain.Team;
import se.yrgo.domain.Player;
import se.yrgo.exceptions.TeamNotFoundException;

import java.util.List;

@Repository
public class TeamDaoImpl implements TeamDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Team team) {
        em.persist(team);
    }

    @Override
    public Team getById(long teamId) throws TeamNotFoundException {
        Team team = em.find(Team.class, teamId);
        if (team == null) {
            throw new TeamNotFoundException(teamId);
        }
        return team;
    }

    @Override
    public Team getByName(String name) throws TeamNotFoundException {
        try {
            return em.createQuery(
                    "SELECT t FROM Team t WHERE t.name = :name", Team.class)
                    .setParameter("name", name)
                    .getSingleResult();
        } catch (NoResultException e) {
            throw new TeamNotFoundException(name);
        }
    }

    @Override
    public void update(Team team) throws TeamNotFoundException {
        if (em.find(Team.class, team.getId()) == null) {
            throw new TeamNotFoundException(team.getId());
        }
        em.merge(team);
    }

    @Override
    public void delete(Team team) throws TeamNotFoundException {
        Team managed = em.find(Team.class, team.getId());
        if (managed == null) {
            throw new TeamNotFoundException(team.getId());
        }
        em.remove(managed);
    }

    @Override
    public List<Team> getAllTeams() {
        return em.createQuery("SELECT t FROM Team t", Team.class)
                .getResultList();
    }

    @Override
    public List<Player> getAllPlayers(long teamId) throws TeamNotFoundException {
        Team team = em.find(Team.class, teamId);
        if (team == null) {
            throw new TeamNotFoundException(teamId);
        }

        return team.getPlayers();
    }
}