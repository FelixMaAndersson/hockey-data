package se.yrgo.dataaccess;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import se.yrgo.domain.Player;
import se.yrgo.domain.Position;
import se.yrgo.domain.Team;
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
    public Player getById(int playerId) throws PlayerNotFoundException {

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
        Player managedPlayer = em.find(Player.class, player.getPlayerId());

        if (managedPlayer == null) {
            throw new PlayerNotFoundException(player.getPlayerId());
        }

        List<Team> teams = em.createQuery(
                        "SELECT t FROM Team t JOIN t.players p WHERE p.playerId = :playerId",
                        Team.class)
                .setParameter("playerId", managedPlayer.getPlayerId())
                .getResultList();

        for (Team team : teams) {
            team.removePlayer(managedPlayer);
        }

        em.remove(managedPlayer);
    }
}