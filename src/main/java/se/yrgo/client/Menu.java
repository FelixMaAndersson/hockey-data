package se.yrgo.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.yrgo.services.leagues.LeagueManagementService;

import java.util.Scanner;

@Component
public class Menu {

    private final Scanner input = new Scanner(System.in);
    private final LeagueManagementService leagueService;

    @Autowired
    public Menu(LeagueManagementService leagueService) {
        this.leagueService = leagueService;
    }

    public void header() {
        System.out.println("-------------------------------------------");
        System.out.println("   Strange Quality Hockey League - SQHL   ");
        System.out.println("-------------------------------------------");
        System.out.println();
        System.out.println("To exit, press [0] at anytime");
        System.out.println();
    }

    public void startMenu() {
        header();
        System.out.println("[1] CREATE (League, Team, Player)");
        System.out.println("[2] VIEW");
        System.out.println();
        System.out.println("[3] JOIN LEAGUE");
    }

    public void start() {
        header();

        while (true) {
            startMenu();
            String choice = input.nextLine();

            switch (choice) {
                case "1" -> createMenu();
                case "2" -> viewMenu();
                case "3" -> joinLeague();
                case "0" -> System.exit(0);
                default -> System.out.println("You dumb puck, wrong choice, try again!");
            }
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
                case "2" -> createTeam();
                case "3" -> createPlayer();
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


}
