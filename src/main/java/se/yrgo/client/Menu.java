package se.yrgo.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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
import se.yrgo.exceptions.InvalidPlayerException;

import java.util.List;
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
        System.out.println();
    }

    public void startMenu() {
        System.out.println("[1] CREATE (League, Team, Player)");
        System.out.println("[2] VIEW (League, Team, Player)");
        System.out.println("[3] JOIN LEAGUE");
        System.out.println("[4] EDIT (League, Team, Player)");
        System.out.println("[0] EXIT");
        System.out.print("Your choice: ");
    }

    public void start() throws LeagueNotFoundException, TeamNotFoundException, PlayerNotFoundException {
        header();
        System.out.println("Press [0] to EXIT\n");

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
                case "3" -> {
                    clearScreen();
                    joinLeague();
                }
                case "4" -> {
                    clearScreen();
                    editMenu();
                }
                case "0" -> System.exit(0);
                default -> {
                    System.out.println("You dumb puck, wrong choice, try again!");
                }
            }
            System.out.println();
        }
    }

    public void createMenu() throws TeamNotFoundException, PlayerNotFoundException {

        while (true) {
            header();
            System.out.println("[1] CREATE LEAGUE");
            System.out.println("[2] CREATE TEAM");
            System.out.println("[3] CREATE PLAYER");
            System.out.println("[0] BACK");
            System.out.print("Your choice: ");

            String choice = input.nextLine();

            switch (choice) {
                case "1" -> {
                    clearScreen();
                    createLeague();
                }
                case "2" -> {
                    clearScreen();
                    createTeam();
                }
                case "3" -> {
                    clearScreen();
                    createPlayer();
                }
                case "0" -> {
                    clearScreen();
                    return;
                }
                default -> System.out.println("What the puck! Wrong choice, try again!");
            }
        }
    }

    public void viewMenu() {
        while (true) {
            header();
            System.out.println("[1] VIEW LEAGUES");
            System.out.println("[2] VIEW TEAMS");
            System.out.println("[3] VIEW PLAYERS");
            System.out.println("[0] BACK");
            System.out.print("Your choice: ");

            String choice = input.nextLine();

            switch (choice) {
                case "1" -> {
                    clearScreen();
                    viewLeagues();
                }
                case "2" -> {
                    clearScreen();
                    viewTeams();
                }
                case "3" -> {
                    clearScreen();
                    viewPlayers();
                }
                case "0" -> {
                    clearScreen();
                    return;
                }
                default -> System.out.println("Quit pucking around, try again!");
            }
        }
    }

    public void joinLeague() throws LeagueNotFoundException, TeamNotFoundException, PlayerNotFoundException {
        League league = chooseOrCreateLeague();

        if (league == null) {
            return;
        }

        Team team = chooseOrCreateTeam();

        if (team == null) {
            return;
        }

        leagueService.addTeamToLeague(league.getName(), team.getName());

        System.out.println("Team " + team.getName()
                + " joined league " + league.getName());
    }

    public League chooseOrCreateLeague() {
        while (true) {
            header();
            System.out.println("[1] JOIN EXISTING LEAGUE");
            System.out.println("[2] CREATE LEAGUE TO JOIN");
            System.out.println("[0] BACK");
            System.out.print("Your choice: ");

            String choice = input.nextLine();

            switch (choice) {
                case "1" -> {
                    clearScreen();
                    return chooseExistingLeague();
                }
                case "2" -> {
                    clearScreen();
                    return createLeague();
                }
                case "0" -> {
                    clearScreen();
                    return null;
                }
                default -> System.out.println("What the puck, try again!");
            }
        }
    }

    public League createLeague() {
        header();
        System.out.print("Enter league name: ");
        String leagueName = input.nextLine().toLowerCase();
        League league = leagueService.createLeague(leagueName);
        System.out.println("League: ´" + leagueName + "´ created!");

        return league;
    }

    public Team createTeam() throws TeamNotFoundException, PlayerNotFoundException {
        header();
        System.out.print("Enter team name: ");
        String teamName = input.nextLine().toLowerCase();

        Team team = teamService.createTeam(teamName);

        System.out.println("Team: ´" + teamName + "´ created!");

        addPlayersToTeamMenu(team);

        System.out.println("Team " + team.getName() + " is complete!");

        return team;
    }

    public Player createPlayer() {
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

            System.out.println("\nSay hi to: " + player.getFullName() + " with a salary of: " + player.getSalary());

            return player;

        } catch (NumberFormatException e) {
            System.out.println("You must enter a pucking number.");
        } catch (InvalidPlayerException e) {
            System.out.println(e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid position. Use GOALIE, DEFENDER, CENTER, LEFT_WING or RIGHT_WING.");
        }

        return null;
    }

    public void viewPlayers() {
        header();

        List<Player> players = playerService.getAllPlayers();

        if (players.isEmpty()) {
            System.out.println("OH puck, no player has been registered...");
            return;
        }

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

    private void addPlayersToTeamMenu(Team team) throws TeamNotFoundException, PlayerNotFoundException {
        while (true) {
            header();
            System.out.println("Add players to " + team.getName());
            System.out.println("[1] ADD EXISTING PLAYER");
            System.out.println("[2] CREATE NEW PLAYER AND ADD");
            System.out.println("[0] DONE");
            System.out.print("Your choice: ");

            String choice = input.nextLine();

            switch (choice) {
                case "1" -> {
                    clearScreen();
                    addExistingPlayerToTeam(team);
                }
                case "2" -> {
                    clearScreen();
                    Player player = createPlayer();

                    if (player != null) {
                        teamService.addPlayerToTeam(team.getName(), player.getPlayerId());
                        System.out.println();
                        System.out.println(player.getFullName() + " added to " + team.getName());
                    }
                }
                case "0" -> {
                    clearScreen();
                    return;
                }
                default -> System.out.println("Wrong choice, try again!");
            }
        }
    }

    private void addExistingPlayerToTeam(Team team) {
        header();

        viewPlayers();

        System.out.print("Enter player id: ");
        int playerId = Integer.parseInt(input.nextLine());

        try {
            teamService.addPlayerToTeam(team.getName(), playerId);
            System.out.println("Player added to " + team.getName());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void viewTeams() {
        header();

        List<Team> teams = teamService.getAllTeams();

        if (teams.isEmpty()) {
            System.out.println("OH puck, no team has been registered...");
            return;
        }
        for (Team team : teams) {
            System.out.println(team.getName());
        }
    }

    public void viewLeagues() {
        header();

        List<League> leagues = leagueService.getAllLeagues();

        if (leagues.isEmpty()) {
            System.out.println("OH puck, no league has been registered...");
            return;
        }
        for (League league : leagues) {
            System.out.println(league.getName());
        }
    }

    private League chooseExistingLeague() {
        header();

        viewLeagues();

        System.out.print("Enter league name: ");
        String leagueName = input.nextLine().toLowerCase();

        try {
            return leagueService.getLeagueByName(leagueName);
        } catch (Exception e) {
            System.out.println("League not found.");
            return null;
        }
    }

    private Team chooseOrCreateTeam() throws TeamNotFoundException, PlayerNotFoundException {
        while (true) {
            header();
            System.out.println("[1] USE EXISTING TEAM");
            System.out.println("[2] CREATE NEW TEAM");
            System.out.println("[0] BACK");
            System.out.print("Your choice: ");

            String choice = input.nextLine();

            switch (choice) {
                case "1" -> {
                    clearScreen();
                    return chooseExistingTeam();
                }
                case "2" -> {
                    clearScreen();
                    return createTeam();
                }
                case "0" -> {
                    clearScreen();
                    return null;
                }
                default -> System.out.println("Wrong choice, try again!");
            }
        }
    }

    private Team chooseExistingTeam() {
        header();

        viewTeams();

        System.out.print("Enter team name: ");
        String teamName = input.nextLine().toLowerCase();

        try {
            return teamService.getTeamByName(teamName);
        } catch (Exception e) {
            System.out.println("Team not found.");
            return null;
        }
    }

    public void editMenu() {
        while (true) {
            header();
            System.out.println("[1] EDIT LEAGUE");
            System.out.println("[2] EDIT TEAM");
            System.out.println("[3] EDIT PLAYER");
            System.out.println("[0] BACK");
            System.out.print("Your choice: ");

            String choice = input.nextLine();

            switch (choice) {
                case "1" -> {
                    clearScreen();
                    editLeague();
                }
                case "2" -> {
                    clearScreen();
                    editTeam();
                }
                case "3" -> {
                    clearScreen();
                    editPlayer();
                }
                case "0" -> {
                    clearScreen();
                    return;
                }
                default -> System.out.println("Wrong choice, try again!");
            }
        }
    }

    public void editLeague() {
        header();

        if (leagueService.getAllLeagues().isEmpty()) {
            System.out.println("OH puck, no league has been registered...");
            return;
        }
        viewLeagues();

        System.out.print("Enter league name to edit: ");
        String oldName = input.nextLine().toLowerCase();

        System.out.print("Enter new name: ");
        String newName = input.nextLine().toLowerCase();

        try {
            leagueService.updateLeagueName(oldName, newName);
            System.out.println("League updated to: " + newName);
        } catch (Exception e) {
            System.out.println("League not found: " + oldName);
        }

        pressEnterToContinue();
    }

    public void editTeam() {
        header();

        if (teamService.getAllTeams().isEmpty()) {
            System.out.println("OH puck, no team has been registered...");
            return;
        }

        viewTeams();

        System.out.print("Enter team name to edit: ");
        String oldName = input.nextLine().toLowerCase();

        System.out.print("Enter new name: ");
        String newName = input.nextLine().toLowerCase();

        try {
            teamService.updateTeamName(oldName, newName);
            System.out.println("Team updated to: " + newName);
        } catch (Exception e) {
            System.out.println("Team not found: " + oldName);
        }

        pressEnterToContinue();
    }

    public void editPlayer() {
        header();

        if (playerService.getAllPlayers().isEmpty()) {
            System.out.println("OH puck, no player has been registered...");
            return;
        }

        viewPlayers();

        try {
            System.out.print("Enter player id to edit: ");
            int playerId = Integer.parseInt(input.nextLine());

            header();
            System.out.print("New full name: ");
            String fullName = input.nextLine();

            System.out.print("New position (GOALIE, DEFENDER, CENTER, LEFT_WING, RIGHT_WING): ");
            Position position = Position.valueOf(input.nextLine().toUpperCase());

            System.out.print("New jersey number: ");
            int jerseyNr = Integer.parseInt(input.nextLine());

            System.out.print("Referee heckling (1-100): ");
            int refereeHeckling = Integer.parseInt(input.nextLine());

            System.out.print("Beer chugging (1-100): ");
            int beerChugging = Integer.parseInt(input.nextLine());

            System.out.print("Diving (1-100): ");
            int diving = Integer.parseInt(input.nextLine());

            System.out.print("Swag (1-100): ");
            int swag = Integer.parseInt(input.nextLine());

            System.out.print("Snusing (1-100): ");
            int snusing = Integer.parseInt(input.nextLine());

            playerService.updatePlayer(playerId, fullName, position, jerseyNr,
                    refereeHeckling, beerChugging, diving, swag, snusing);

            System.out.println("Player updated!");

        } catch (NumberFormatException e) {
            System.out.println("You must enter a pucking number.");
        } catch (InvalidPlayerException e) {
            System.out.println(e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid position. Use GOALIE, DEFENDER, CENTER, LEFT_WING or RIGHT_WING.");
        } catch (PlayerNotFoundException e) {
            System.out.println("Player not found!");
        }

        pressEnterToContinue();
    }

    public void pressEnterToContinue() {
        System.out.println("\nPress ENTER to continue...");
        input.nextLine();
    }

    public void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

}
