package se.yrgo.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.yrgo.domain.Player;
import se.yrgo.services.leagues.LeagueManagementService;
import se.yrgo.services.players.PlayerManagementService;
import se.yrgo.services.teams.TeamManagementService;

import java.util.Scanner;

@Component
public class Menu {

    private final Scanner input = new Scanner(System.in);
    private final LeagueManagementService leagueService;
    private final TeamManagementService teamService;
    private final PlayerManagementService playerService;


    @Autowired
    public Menu(LeagueManagementService leagueService,
                TeamManagementService teamService,
                PlayerManagementService playerService) {
        this.leagueService = leagueService;
        this.teamService = teamService;
        this.playerService = playerService;
    }


    public void header() {
        System.out.println("-------------------------------------------");
        System.out.println("   Strange Quality Hockey League - SQHL   ");
        System.out.println("-------------------------------------------");
        System.out.println("To exit, press [0] at anytime");
        System.out.println();
    }

    public void startMenu() {
        System.out.println("[1] CREATE (League, Team, Player)");
        System.out.println("[2] VIEW");
        System.out.println();
        System.out.println("[3] JOIN LEAGUE");
        System.out.print("Your choice: ");
    }

    public void start() {
        header();

        while (true) {
            startMenu();
            String choice = input.nextLine();

            switch (choice) {
                case "1" -> {
                    clearScreen();
                    createMenu();
                }
//                case "2" -> viewMenu();
//                case "3" -> joinLeague();
                case "0" -> System.exit(0);
                default -> {
                    System.out.println("You dumb puck, wrong choice, try again!");
                }
            }
            System.out.println();
        }
    }

    public void createMenu() {

        while (true) {
            header();
            System.out.println("[1] CREATE LEAGUE");
            System.out.println("[2] CREATE TEAM");
            System.out.println("[3] CREATE PLAYER");

            String choice = input.nextLine();

            switch (choice) {
                case "1" -> createLeague();
//                case "2" -> createTeam();
//                case "3" -> createPlayer();
                case "0" -> {
                    return;
                }
                default -> System.out.println("What the puck! Wrong choice, try again!");
            }
        }

    }

    public void createLeague() {
        header();
        System.out.print("Enter league name: ");
        String leagueName = input.nextLine().toLowerCase();
        leagueService.createLeague(leagueName);
        System.out.println("League: ´" + leagueName + "´ created!");

    }

    public void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

}
