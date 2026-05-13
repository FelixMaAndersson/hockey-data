package se.yrgo.services.players;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.yrgo.dataaccess.PlayerDao;
import se.yrgo.domain.Player;
import se.yrgo.domain.Position;
import se.yrgo.exceptions.InvalidPlayerException;
import se.yrgo.exceptions.PlayerNotFoundException;

import java.util.List;
import java.util.Objects;

/**
 * Service class for player-related business logic.
 *
 * This class handles creating, updating, retrieving and deleting players.
 * It also validates player data before it is saved or updated.
 *
 * Database operations are delegated to PlayerDao.
 */
@Service
public class PlayerManagementService {

    private final PlayerDao dao;

    @Autowired
    public PlayerManagementService(PlayerDao dao) {
        this.dao = dao;
    }

    @Transactional
    public Player createPlayer(String fullName,
                               Position position, int jerseyNr,
                               int refereeHeckling, int beerChugging,
                               int diving, int swag, int snusing) {

        Player player = new Player(
                fullName,
                position,
                jerseyNr,
                refereeHeckling,
                beerChugging,
                diving,
                swag,
                snusing
        );

        validatePlayer(player);
        dao.create(player);

        return player;
    }

    @Transactional(readOnly = true)
    public Player getPlayerById(int playerId) throws PlayerNotFoundException {
        return dao.getById(playerId);
    }

    @Transactional(readOnly = true)
    public List<Player> getAllPlayers() {
        return dao.getAllPlayers();
    }

    @Transactional
    public void updatePlayer(int playerId, String fullName, Position position,
                             int jerseyNr, int refereeHeckling, int beerChugging,
                             int diving, int swag, int snusing) throws PlayerNotFoundException {

        Player player = dao.getById(playerId);
        player.setFullName(fullName);
        player.setPosition(position);
        player.setJerseyNr(jerseyNr);
        player.setRefereeHeckling(refereeHeckling);
        player.setBeerChugging(beerChugging);
        player.setDiving(diving);
        player.setSwag(swag);
        player.setSnusing(snusing);

        validatePlayer(player);
        player.updateSalary();
        dao.update(player);
    }

    @Transactional
    public void deletePlayer(int playerId) throws PlayerNotFoundException {
        Player player = dao.getById(playerId);
        dao.delete(player);
    }


    /**
     * validates that name, jersey number and stats follow SQL rules
     * @param player
     */
    private void validatePlayer(Player player) {
        StringBuilder errors = new StringBuilder();

        if (player.getFullName() == null || player.getFullName().isBlank()) {
            errors.append("The player must have a name.\n");
        }

        if (player.getJerseyNr() == 99 && !Objects.equals(player.getFullName(), "Wayne Gretzky")) {
            errors.append("Who do you think you are?! You're not Wayne Gretzky.\n");
        } else if (player.getJerseyNr() < 1 || player.getJerseyNr() > 99) {
            errors.append("Jersey number must be between 1 and 99.\n");
        }

        if (player.getRefereeHeckling() < 1 || player.getRefereeHeckling() > 100) {
            errors.append("Referee heckling must be between 1 and 100.\n");
        }

        if (player.getBeerChugging() < 1 || player.getBeerChugging() > 100) {
            errors.append("Beer chugging must be between 1 and 100.\n");
        }

        if (player.getDiving() < 1 || player.getDiving() > 100) {
            errors.append("Diving must be between 1 and 100.\n");
        }

        if (player.getSwag() < 1 || player.getSwag() > 100) {
            errors.append("Swag must be between 1 and 100.\n");
        }

        if (player.getSnusing() < 1 || player.getSnusing() > 100) {
            errors.append("Snusing must be between 1 and 100.\n");
        }

        if (!errors.isEmpty()) {
            throw new InvalidPlayerException(errors + "No player was created");
        }
    }
}