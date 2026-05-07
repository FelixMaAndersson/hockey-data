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

@Service
public class PlayerManagementService {

    private final PlayerDao dao;

    @Autowired
    public PlayerManagementService(PlayerDao dao) {
        this.dao = dao;
    }

    @Transactional
    public void createPlayer(String fullName,
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
    public void updatePlayer(Player player) throws PlayerNotFoundException {
        validatePlayer(player);
        dao.update(player);
    }

    @Transactional
    public void deletePlayer(int playerId) throws PlayerNotFoundException {
        Player player = dao.getById(playerId);
        dao.delete(player);
    }

    @Transactional(readOnly = true)
    public List<Player> getPlayersByPosition(Position position) {
        return dao.getPlayersByPosition(position);
    }

    @Transactional(readOnly = true)
    public List<Player> getPlayersBySalaryRange(int minSalary, int maxSalary) {
        return dao.getPlayersBySalaryRange(minSalary, maxSalary);
    }

    @Transactional(readOnly = true)
    public List<Player> getPlayerByName(String name) {
        return dao.getPlayerByName(name);
    }


    private void validateJerseyNumber(int jerseyNr, String fullName) {

        if (jerseyNr == 99 && !Objects.equals(fullName, "Wayne Gretzky")) {
            throw new InvalidPlayerException(
                    "Who do you think you are?! You're not Wayne Gretzky");
        } else if (jerseyNr < 1 || jerseyNr > 99) {
            throw new InvalidPlayerException(
                    "Jersey number must be between 1 and 98");
        }
    }

    private void validateRating(int rating, String statName) {

        if (rating < 1 || rating > 100) {
            throw new InvalidPlayerException(
                    statName + " must be between 1 and 100");
        }
    }

    private void validatePlayer(Player player) {
        validateJerseyNumber(player.getJerseyNr(), player.getFullName());

        validateRating(player.getRefereeHeckling(), "Referee heckling");
        validateRating(player.getBeerChugging(), "Beer chugging");
        validateRating(player.getDiving(), "Diving");
        validateRating(player.getSwag(), "Swag");
        validateRating(player.getSnusing(), "Snusing");
    }

}