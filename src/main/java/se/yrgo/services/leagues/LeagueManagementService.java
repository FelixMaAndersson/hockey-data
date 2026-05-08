package se.yrgo.services.leagues;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.yrgo.dataaccess.LeagueDao;
import se.yrgo.dataaccess.TeamDao;
import se.yrgo.domain.League;
import se.yrgo.domain.Team;
import se.yrgo.exceptions.LeagueNotFoundException;
import se.yrgo.exceptions.TeamNotFoundException;

import java.util.List;

@Service
@Transactional
public class LeagueManagementService {

    private final LeagueDao dao;
    private final TeamDao teamDao;

    @Autowired
    public LeagueManagementService(LeagueDao dao, TeamDao teamDao) {
        this.dao = dao;
        this.teamDao = teamDao;
    }

    public League createLeague(String name) {
        League league = new League(name);
        dao.create(league);
        return league;
    }

    public void updateLeagueName(String oldName, String newName) throws LeagueNotFoundException {
        League league = dao.getByName(oldName);
        league.setName(newName);
        dao.update(league);
    }

    public void deleteLeague(String name) throws LeagueNotFoundException {
        League league = dao.getByName(name);
        dao.delete(league);
    }

    public League getLeagueById(int id) throws LeagueNotFoundException {
        return dao.getById(id);
    }

    public League getLeagueByName(String name) throws LeagueNotFoundException {
        return dao.getByName(name);
    }

    public List<League> getAllLeagues() {
        return dao.getAllLeagues();
    }

    public List<Team> getTeamsInLeague(String name) throws LeagueNotFoundException {
        League league = dao.getByName(name);
        return dao.getAllTeams(league.getId());
    }

        @Transactional
        public void addTeamToLeague(String leagueName, String teamName)
                throws LeagueNotFoundException, TeamNotFoundException {

            League league = dao.getByName(leagueName);

            if (league.getTeams().size() >= 10) {
                System.out.println("A league cannot contain more than 10 teams");
                return;
            }

            Team team = teamDao.getByName(teamName);

            league.addTeam(team);

            dao.update(league);
        }

}
