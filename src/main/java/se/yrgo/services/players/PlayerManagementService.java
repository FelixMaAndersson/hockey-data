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
public class PlayerManagementService {

    private final PlayerDao dao;

    @Autowired
    public PlayerManagementService(PlayerDao dao) {
        this.dao = dao;
    }

    @Transactional
    public void createPlayer(String playerId, String fullName,
                             Position position, int jerseyNr,
                             int refereeHeckling, int beerChugging,
                             int diving, int game, int snusing) {

        Player player = new Player(
                playerId,
                fullName,
                position,
                jerseyNr,
                refereeHeckling,
                beerChugging,
                diving,
                game,
                snusing
        );

        dao.create(player);
    }

    @Transactional(readOnly = true)
    public Player getPlayerById(String playerId) throws PlayerNotFoundException {
        return dao.getById(playerId);
    }

    @Transactional(readOnly = true)
    public List<Player> getAllPlayers() {
        return dao.getAllPlayers();
    }

    @Transactional
    public void updatePlayer(Player player) throws PlayerNotFoundException {
        dao.update(player);
    }

    @Transactional
    public void deletePlayer(String playerId) throws PlayerNotFoundException {
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

//    @Transactional(readOnly = true)
//    public getPlayersBySalaryRange() {
//        return dao.getPlayersBySalaryRange(minSalary, maxSalary);
//    }

}