package se.yrgo.dataaccess;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import se.yrgo.domain.Player;
import se.yrgo.domain.Position;
import se.yrgo.exceptions.PlayerNotFoundException;

import java.util.List;

@Repository
public class PlayerDaoImpl implements PlayerDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public void create(Player player) {

        em.persist(player);

    }

    @Override
    public Player getById(String playerId) throws PlayerNotFoundException {

        Player player = em.find(Player.class, playerId);

        if (player == null) {
            throw new PlayerNotFoundException(playerId);
        }

        return player;
    }

    @Override
    public List<Player> getAllPlayers() {
        return em.createQuery(
                        "SELECT p FROM Player p", Player.class)
                .getResultList();
    }

    @Override
    public void update(Player player) throws PlayerNotFoundException {

        if (em.find(Player.class, player.getPlayerId()) == null) {
            throw new PlayerNotFoundException(player.getPlayerId());
        }

        em.merge(player);
    }

    @Override
    public void delete(Player player) throws PlayerNotFoundException {

        Player managed = em.find(Player.class, player.getPlayerId());

        if (managed == null) {
            throw new PlayerNotFoundException(player.getPlayerId());
        }

        em.remove(managed);
    }

    @Override
    public List<Player> getPlayersByPosition(Position position) {

        return em.createQuery(
                        "SELECT p FROM Player p WHERE p.position = :position",
                        Player.class)
                .setParameter("position", position)
                .getResultList();
    }

    @Override
    public List<Player> getPlayersBySalaryRange(int minSalary, int maxSalary) {
        return em.createQuery(
                        "SELECT p FROM Player p WHERE p.salary BETWEEN :min AND :max",
                        Player.class)
                .setParameter("min", minSalary)
                .setParameter("max", maxSalary)
                .getResultList();
    }
}