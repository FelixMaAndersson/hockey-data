package se.yrgo.dataaccess;

import org.springframework.jdbc.core.JdbcTemplate;
import se.yrgo.domain.League;

import java.util.List;

public class LeagueDaoImpl implements LeagueDao {

    private JdbcTemplate template;

    public LeagueDaoImpl(JdbcTemplate template) {
        this.template = template;
    }

    @Override
    public void create(League league) {
    }

    @Override
    public League getById(String leagueId) throws RecordNotFoundException {
        return null;
    }

    @Override
    public List<League> getByName(String name) {
        return List.of();
    }

    @Override
    public void update(League league) throws RecordNotFoundException {

    }

    @Override
    public void delete(League oldleague) throws RecordNotFoundException {

    }

    @Override
    public List<League> getAllLeagues() {
        return List.of();
    }

    @Override
    public League getFullLeagueDetail(String playerId) throws RecordNotFoundException {
        return null;
    }
}
