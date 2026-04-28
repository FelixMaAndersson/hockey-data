package se.yrgo.dataaccess;

import java.util.List;

import se.yrgo.domain.Player;

public interface PlayerDao {

	public void create(Player player);

	public Player getById(String playerId) throws RecordNotFoundException;

	public List<Player> getByName(String name);

	public void update(Player playerToUpdate) throws RecordNotFoundException;

	public void delete(Player oldPlayer) throws RecordNotFoundException;

	public List<Player> getAllPlayers();


	public Player getFullPlayerDetail(String playerId) throws RecordNotFoundException;

}
