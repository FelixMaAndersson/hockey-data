package se.yrgo.dataaccess;

import se.yrgo.domain.Player;
import se.yrgo.exceptions.PlayerNotFoundException;

import java.util.List;

public class PlayerDaoImpl implements PlayerDao {
    @Override
    public void create(Player player) {

    }

    @Override
    public Player getById(String playerId) throws PlayerNotFoundException {
        return null;
    }

    @Override
    public List<Player> getAllPlayers() {
        return List.of();
    }

    @Override
    public void update(Player player) throws PlayerNotFoundException {

    }

    @Override
    public void delete(Player player) throws PlayerNotFoundException {

    }
}
