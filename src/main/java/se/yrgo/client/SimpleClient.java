package se.yrgo.client;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import se.yrgo.domain.Position;
import se.yrgo.services.players.PlayerManagementService;
import se.yrgo.services.teams.TeamManagementService;

public class SimpleClient {

    public static void main(String[] args) {

        try (ClassPathXmlApplicationContext container =
                     new ClassPathXmlApplicationContext("application.xml")) {

            PlayerManagementService playerService =
                    container.getBean(PlayerManagementService.class);

            TeamManagementService teamService =
                    container.getBean(TeamManagementService.class);

            // 1. Create players
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

            // 2. Create team
            teamService.createTeam("Skövde Snusmasters");

            // 3. Add players to team
            teamService.addPlayerToTeam("Skövde Snusmasters", "P001");
            teamService.addPlayerToTeam("Skövde Snusmasters", "P002");

            // 4. Show result
            System.out.println("Team created and players added!");
        }
    }
}