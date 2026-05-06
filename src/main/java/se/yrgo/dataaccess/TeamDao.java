package se.yrgo.dataaccess;

import se.yrgo.domain.Team;
import se.yrgo.exceptions.PlayerNotFoundException;

public interface TeamDao {
	public void create(Team newTeam);
	// public List<Team> getIncompleteTeams(String userId);
	public void update(Team teamToUpdate) throws PlayerNotFoundException;
	public void delete(Team oldTeam) throws PlayerNotFoundException;
}
