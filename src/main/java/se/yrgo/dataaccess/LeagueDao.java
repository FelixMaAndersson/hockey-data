package se.yrgo.dataaccess;

import se.yrgo.domain.League;
import se.yrgo.domain.Player;

import java.util.List;

public interface LeagueDao {

    public void create(League league);

    public League getById(String leagueId) throws RecordNotFoundException;

    public List<League> getByName(String name);

    public void update(League league) throws RecordNotFoundException;

    public void delete(League oldleague) throws RecordNotFoundException;

    public List<League> getAllLeagues();

    public League getFullLeagueDetail(String playerId) throws RecordNotFoundException;
}
