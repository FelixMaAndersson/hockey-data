package se.yrgo.dataaccess;

import java.util.List;

import se.yrgo.domain.Player;
import se.yrgo.domain.Position;
import se.yrgo.exceptions.PlayerNotFoundException;

/**
 * Interface defining data access operations for Player entities.
 * Implementations handle the communication with the database.
 */
public interface PlayerDao {
    void create(Player player);

    Player getById(int playerId) throws PlayerNotFoundException;

    List<Player> getAllPlayers();

    void update(Player player) throws PlayerNotFoundException;

    void delete(Player player) throws PlayerNotFoundException;
}
