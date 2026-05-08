package se.yrgo.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.yrgo.domain.League;
import se.yrgo.domain.Player;
import se.yrgo.domain.Position;
import se.yrgo.domain.Team;
import se.yrgo.services.leagues.LeagueManagementService;
import se.yrgo.services.players.PlayerManagementService;
import se.yrgo.services.teams.TeamManagementService;
import se.yrgo.exceptions.InvalidPlayerException;

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
        System.out.println("[2] VIEW (League, Team, Player)");
        System.out.println("[3] JOIN LEAGUE !!! FEATURE NOT YET AVAILABLE !!!");
        System.out.println("[0] EXIT");
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
                case "2" -> {
                    clearScreen();
                    viewMenu();
                }
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
            System.out.println("[0] BACK");

            String choice = input.nextLine();

            switch (choice) {
                case "1" -> createLeague();
                case "2" -> createTeam();
                case "3" -> createPlayer();
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

    public void createTeam() {
        header();
        System.out.print("Enter team name: ");
        String teamName = input.nextLine().toLowerCase();
        teamService.createTeam(teamName);
        System.out.println("Team: ´" + teamName + "´ created!");
    }

    public void createPlayer() {
        header();

        try {
            System.out.print("Full name: ");
            String fullName = input.nextLine();

            System.out.print("Position (GOALIE, DEFENDER, CENTER, LEFT_WING or RIGHT_WING) : ");
            Position position = Position.valueOf(input.nextLine().toUpperCase());

            System.out.print("Jersey number: ");
            int jerseyNr = Integer.parseInt(input.nextLine());

            System.out.print("How good your player is at heckling the referee (1-100): ");
            int refereeHeckling = Integer.parseInt(input.nextLine());

            System.out.print("How much of a beer chugging king your player is (1-100): ");
            int beerChugging = Integer.parseInt(input.nextLine());

            System.out.print("How good of an actor your player is (1-100): ");
            int diving = Integer.parseInt(input.nextLine());

            System.out.print("The swag factor (1-100): ");
            int swag = Integer.parseInt(input.nextLine());

            System.out.print("How much ettans lös our player can shove under the lip (1-100): ");
            int snusing = Integer.parseInt(input.nextLine());

            Player player = playerService.createPlayer(fullName, position, jerseyNr, refereeHeckling, beerChugging, diving, swag, snusing);

            System.out.println("Say hi to: " + player.getFullName() + " with a salary of: " + player.getSalary());

        } catch (NumberFormatException e) {
            System.out.println("You must enter a number.");
        } catch (InvalidPlayerException e) {
            System.out.println(e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid position. Use GOALIE, DEFENDER, CENTER, LEFT_WING or RIGHT_WING.");
        }

    }

    public void viewMenu() {
        while (true) {
            header();
            System.out.println("[1] VIEW LEAGUES");
            System.out.println("[2] VIEW TEAMS");
            System.out.println("[3] VIEW PLAYERS");
            System.out.println("[0] BACK");

            String choice = input.nextLine();

            switch (choice) {
                case "1" -> viewLeagues();
                case "2" -> viewTeams();
                case "3" -> viewPlayers();
                case "0" -> {
                    return;
                }
                default -> System.out.println("Quit pucking around, try again!");
            }
        }
    }

    public void viewPlayers() {
        header();

        for (Player player : playerService.getAllPlayers()) {
            System.out.println(player.getPlayerId()
                    + " - "
                    + player.getFullName()
                    + " - "
                    + player.getPosition()
                    + " - salary: "
                    + player.getSalary());
        }
    }

    public void viewTeams() {
        header();

        for (Team team : teamService.getAllTeams()) {
            System.out.println(team.getName());
        }
    }

    public void viewLeagues() {
        header();

        for (League league : leagueService.getAllLeagues()) {
            System.out.println(league.getName());
        }
    }

    public void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

}
