package se.yrgo.dataaccess;

import java.util.List;

import se.yrgo.domain.Team;

public interface TeamDao {
	public void create(Team newTeam);
	// public List<Team> getIncompleteTeams(String userId);
	public void update(Team teamToUpdate) throws RecordNotFoundException;
	public void delete(Team oldTeam) throws RecordNotFoundException;
}
