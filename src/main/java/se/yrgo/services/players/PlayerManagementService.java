package se.yrgo.services.players;

import java.util.List;

import se.yrgo.domain.Player;


public interface PlayerManagementService {

    public void newPlayer(Player newPlayer);


    public void updatePlayer(Player changedPlayer);


    public void deletePlayer(Player oldPlayer);


    public Player findPlayerById(String playerId) throws PlayerNotFoundException;

    public List<Player> findPlayerByName(String name);

    public List<Player> getAllPlayers();


    public Player getFullPlayerDetail(String playerId) throws PlayerNotFoundException;
}