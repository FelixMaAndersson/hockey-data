package se.yrgo.services.leagues;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import se.yrgo.dataaccess.LeagueDao;
import se.yrgo.dataaccess.LeagueDaoImpl;
import se.yrgo.domain.League;
import se.yrgo.domain.Team;
import se.yrgo.exceptions.LeagueNotFoundException;

import java.util.List;

@Service
@Transactional
public class LeagueManagementService {

    private final LeagueDao dao;

    @Autowired
    public LeagueManagementService(LeagueDao dao) {
        this.dao = dao;
    }

    public void createLeague(String name) {
        League league = new League(name);
        dao.create(league);
    }

    public void updateLeague(String oldName, String newName) throws LeagueNotFoundException {
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
            throws LeagueNotFoundException {

        League league = dao.getByName(leagueName);

        if (league.getTeams().size() >= 10) {
            throw new IllegalStateException(
                    "A league cannot contain more than 10 teams");
        }

        Team team = new Team(teamName);

        league.addTeam(team);

        dao.update(league);
    }


}
