package se.yrgo.client;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import se.yrgo.domain.League;
import se.yrgo.domain.Player;
import se.yrgo.domain.Position;
import se.yrgo.domain.Team;
import se.yrgo.exceptions.LeagueNotFoundException;
import se.yrgo.exceptions.PlayerNotFoundException;
import se.yrgo.exceptions.TeamNotFoundException;
import se.yrgo.services.leagues.LeagueManagementService;
import se.yrgo.services.players.PlayerManagementService;
import se.yrgo.services.teams.TeamManagementService;

import java.util.List;

public class SimpleClient {

    public static void main(String[] args) throws LeagueNotFoundException, PlayerNotFoundException, TeamNotFoundException {

        ApplicationContext ctx = new ClassPathXmlApplicationContext("application.xml");

//        Menu menu = ctx.getBean(Menu.class);
//        menu.start();

        LeagueManagementService leagueService = ctx.getBean(LeagueManagementService.class);
        PlayerManagementService playerService = ctx.getBean(PlayerManagementService.class);
        TeamManagementService teamService = ctx.getBean(TeamManagementService.class);

        leagueService.createLeague("NHL");
        playerService.createPlayer("Felix Andersson", Position.DEFENDER, 98, 95, 76, 56, 83, 22 );

        teamService.createTeam("Frölunda");

        System.out.println(leagueService.getLeagueById(1));
        System.out.println(playerService.getPlayerById(1));
        System.out.println(teamService.getTeamById(1));


    }
}