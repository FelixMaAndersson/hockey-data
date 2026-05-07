package se.yrgo.client;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import se.yrgo.domain.Player;
import se.yrgo.domain.Position;
import se.yrgo.domain.Team;
import se.yrgo.services.leagues.LeagueManagementService;
import se.yrgo.services.players.PlayerManagementService;
import se.yrgo.services.teams.TeamManagementService;

public class SimpleClient {

    public static void main(String[] args) {

        try (ClassPathXmlApplicationContext container =
                     new ClassPathXmlApplicationContext("application.xml")) {

            LeagueManagementService leagueService =
                    container.getBean(LeagueManagementService.class);

            TeamManagementService teamService =
                    container.getBean(TeamManagementService.class);

            PlayerManagementService playerService =
                    container.getBean(PlayerManagementService.class);

            // 1. Create league
            leagueService.createLeague("Snus Hockey League");

            // 2. Create team and add it to league
//            leagueService.addTeamToLeague("Snus Hockey League", "Skövde Snusmasters");

            // 3. Create players
            playerService.createPlayer(
                    "P001",
                    "Bosse Slagskott",
                    Position.DEFENDER,
                    44,
                    90,
                    75,
                    40,
                    88,
                    99
            );

            playerService.createPlayer(
                    "P002",
                    "Glenn Snusberg",
                    Position.CENTER,
                    13,
                    65,
                    95,
                    30,
                    91,
                    100
            );

            // 4. Add players to team

//            Player player = playerService.getPlayerById("P001");
//
//            teamService.addPlayerToTeam(team, player);


            System.out.println("League, team and players created!");
        }
    }
}