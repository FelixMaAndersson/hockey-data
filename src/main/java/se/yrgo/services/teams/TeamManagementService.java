package se.yrgo.services.teams;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.yrgo.dataaccess.PlayerDao;
import se.yrgo.dataaccess.TeamDao;
import se.yrgo.domain.Player;
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

        if (!team.hasRoomFor(player)) {
            throw new RuntimeException(
                    "No room for another " + player.getPosition());
        }

        if (players.size() >= Team.MAX_PLAYERS) {
            throw new RuntimeException("Team already has maximum 6 players");
        }

        if (!team.canAfford(player)) {
            throw new RuntimeException("Salary cap exceeded. Remaining budget: "
                    + team.getRemainingBudget()
                    + ", player salary: "
                    + player.getSalary());
        }



        team.addPlayer(player);
        teamDao.update(team);
    }

    public Team getTeamByName(String teamName) throws TeamNotFoundException {
        return teamDao.getByName(teamName);
    }
}