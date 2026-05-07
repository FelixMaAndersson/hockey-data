package se.yrgo.services.teams;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.yrgo.dataaccess.TeamDao;
import se.yrgo.domain.Player;
import se.yrgo.domain.Position;
import se.yrgo.domain.Team;
import se.yrgo.exceptions.TeamNotFoundException;

import java.util.List;

@Service
@Transactional
public class TeamManagementService {

    private TeamDao teamDao;

    public TeamManagementService(TeamDao teamDao) {
        this.teamDao = teamDao;
    }

    public void addPlayerToTeam(Team team, Player player) throws TeamNotFoundException {

        List<Player> players = team.getPlayers();

        long centers = count(players, Position.CENTER);
        long leftWings = count(players, Position.LEFT_WING);
        long rightWings = count(players, Position.RIGHT_WING);
        long defenders = count(players, Position.DEFENDER);
        long goalies = count(players, Position.GOALIE);

        validate(player, centers, leftWings, rightWings, defenders, goalies);

        team.addPlayer(player);
        teamDao.update(team);
    }

    private long count(List<Player> players, Position position) {
        return players.stream()
                .filter(p -> p.getPosition() == position)
                .count();
    }

    private void validate(Player player,
                          long centers,
                          long leftWings,
                          long rightWings,
                          long defenders,
                          long goalies) {

        Position pos = player.getPosition();

        long totalForwards = centers + leftWings + rightWings;

        if ((pos == Position.CENTER || pos == Position.LEFT_WING || pos == Position.RIGHT_WING)
                && totalForwards >= 3) {
            throw new RuntimeException("Max 3 forwards allowed (CENTER + WINGs)");
        }

        if (pos == Position.DEFENDER && defenders >= 2) {
            throw new RuntimeException("Max 2 defenders allowed");
        }

        if (pos == Position.GOALIE && goalies >= 1) {
            throw new RuntimeException("Max 1 goalie allowed");
        }
    }
}