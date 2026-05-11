package se.yrgo.services.teams;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.yrgo.dataaccess.PlayerDao;
import se.yrgo.dataaccess.TeamDao;
import se.yrgo.domain.Player;
import se.yrgo.domain.Position;
import se.yrgo.domain.Team;
import se.yrgo.exceptions.TeamNotFoundException;
import se.yrgo.exceptions.PlayerNotFoundException;

import java.util.List;

@Service
@Transactional
public class TeamManagementService {

    private static final int MAX_PLAYERS = 6;

    private final TeamDao teamDao;
    private final PlayerDao playerDao;

    public TeamManagementService(TeamDao teamDao, PlayerDao playerDao) {
        this.teamDao = teamDao;
        this.playerDao = playerDao;
    }

    public List<Team> getAllTeams() {
        return teamDao.getAllTeams();
    }

    // CREATE
    public Team createTeam(String name) {
        Team team = new Team(name);
        teamDao.create(team);

        return team;
    }

    // UPDATE
    public void updateTeam(Team team) throws TeamNotFoundException {
        teamDao.update(team);
    }

    @Transactional
    public void updateTeamName(String oldName, String newName) throws TeamNotFoundException {
        Team team = teamDao.getByName(oldName);
        team.setName(newName);
        teamDao.update(team);
    }

    // DELETE
    public void deleteTeam(Team team) throws TeamNotFoundException {
        teamDao.delete(team);
    }

    // ADD PLAYER (NU MED TEAM NAME IN I STÄLLET)
    public void addPlayerToTeam(String teamName, int playerId)
            throws TeamNotFoundException, PlayerNotFoundException {

        Team team = teamDao.getByName(teamName);
        Player player = playerDao.getById(playerId);

        List<Player> players = team.getPlayers();

        if (players.size() >= MAX_PLAYERS) {
            throw new RuntimeException("Team already has maximum 6 players");
        }

        if (!team.canAfford(player)) {
            throw new RuntimeException("Salary cap exceeded. Remaining budget: "
                    + team.getRemainingBudget()
                    + ", player salary: "
                    + player.getSalary());
        }

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

        long totalForwards =
                centers + leftWings + rightWings;

        if ((pos == Position.CENTER
                || pos == Position.LEFT_WING
                || pos == Position.RIGHT_WING)
                && totalForwards >= 3) {
            throw new RuntimeException("Max 3 forwards allowed");
        }

        if (pos == Position.DEFENDER && defenders >= 2) {
            throw new RuntimeException("Max 2 defenders allowed");
        }

        if (pos == Position.GOALIE && goalies >= 1) {
            throw new RuntimeException("Max 1 goalie allowed");
        }
    }

    public Team getTeamByName(String teamName) throws TeamNotFoundException {
        return teamDao.getByName(teamName);
    }

    public Team getTeamById(int id) throws TeamNotFoundException {
        return teamDao.getById(id);
    }
}