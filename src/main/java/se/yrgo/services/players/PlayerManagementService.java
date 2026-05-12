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
     * Validates jersey numbers.
     * Number 99 is only allowed for Wayne Gretzky.
     */
    private void validateJerseyNumber(int jerseyNr, String fullName) {

        if (jerseyNr == 99 && !Objects.equals(fullName, "Wayne Gretzky")) {
            throw new InvalidPlayerException(
                    "Who do you think you are?! You're not Wayne Gretzky");
        } else if (jerseyNr < 1 || jerseyNr > 99) {
            throw new InvalidPlayerException(
                    "Jersey number must be between 1 and 99");
        }
    }

    private void validateRating(int rating, String statName) {

        if (rating < 1 || rating > 100) {
            throw new InvalidPlayerException(
                    statName + " must be between 1 and 100");
        }
    }

    /**
     * Validates that jersey number and player ratings follow the SQHL rules.
     */
    private void validatePlayer(Player player) {
        validateJerseyNumber(player.getJerseyNr(), player.getFullName());

        validateRating(player.getRefereeHeckling(), "Referee heckling");
        validateRating(player.getBeerChugging(), "Beer chugging");
        validateRating(player.getDiving(), "Diving");
        validateRating(player.getSwag(), "Swag");
        validateRating(player.getSnusing(), "Snusing");
    }

}