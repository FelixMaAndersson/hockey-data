package se.yrgo.services.players;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.yrgo.dataaccess.PlayerDao;
import se.yrgo.domain.Player;
import se.yrgo.domain.Position;
import se.yrgo.exceptions.PlayerNotFoundException;

import java.util.List;

@Service
@Transactional
public class PlayerManagementService {

    private final PlayerDao dao;

    @Autowired
    public PlayerManagementService(PlayerDao dao) {
        this.dao = dao;
    }

    public void createPlayer(String playerId, String fullName,
                             Position position, int jerseyNr,
                             int refereeHeckling, int beerChugging,
                             int diving, int game, int snusing,
                             int swag, int salary) {

        Player player = new Player(
                playerId,
                fullName,
                position,
                jerseyNr,
                refereeHeckling,
                beerChugging,
                diving,
                game,
                snusing,
                swag,
                salary
        );

        dao.create(player);
    }

    public Player getPlayerById(String playerId) throws PlayerNotFoundException {
        return dao.getById(playerId);
    }

    public List<Player> getAllPlayers() {
        return dao.getAllPlayers();
    }

    public void updatePlayer(Player player) throws PlayerNotFoundException {
        dao.update(player);
    }

    public void deletePlayer(String playerId) throws PlayerNotFoundException {
        Player player = dao.getById(playerId);
        dao.delete(player);
    }
}