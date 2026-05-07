package se.yrgo.dataaccess;

import java.util.List;

import se.yrgo.domain.Player;
import se.yrgo.domain.Position;
import se.yrgo.exceptions.PlayerNotFoundException;

public interface PlayerDao {
    void create(Player player);

    Player getById(String playerId) throws PlayerNotFoundException;

    List<Player> getAllPlayers();

    void update(Player player) throws PlayerNotFoundException;

    void delete(Player player) throws PlayerNotFoundException;

    List<Player> getPlayersByPosition(Position position);

    List<Player> getPlayersBySalaryRange(int minSalary, int maxSalary);

    List<Player> getBySalary(int salary) throws PlayerNotFoundException;
}
