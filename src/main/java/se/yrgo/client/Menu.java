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

/**
 * Text-based console menu for the Strange Quality Hockey League (SQHL) application.
 * Handles all user interaction and delegates operations to the appropriate service classes.
 *
 */
@Component
public class Menu {

    private final Scanner input = new Scanner(System.in);
    private final LeagueManagementService leagueService;
    private final TeamManagementService teamService;
    private final PlayerManagementService playerService;

    /**
     * Constructs a Menu with the required service classes.
     *
     * @param leagueService service for league operations
     * @param teamService   service for team operations
     * @param playerService service for player operations
     */
    @Autowired
    public Menu(LeagueManagementService leagueService,
                TeamManagementService teamService,
                PlayerManagementService playerService) {
        this.leagueService = leagueService;
        this.teamService = teamService;
        this.playerService = playerService;
    }


    /**
     * Prints the application header to the console.
     */
    public void header() {
        System.out.println("-------------------------------------------");
        System.out.println("   Strange Quality Hockey League - SQHL   ");
        System.out.println("-------------------------------------------");
        System.out.println();
    }

    /**
     * Prints the main menu options to the console.
     */
    public void startMenu() {
        System.out.println("[1] CREATE (League, Team, Player)");
        System.out.println("[2] VIEW (League, Team, Player)");
        System.out.println("[3] EDIT (League, Team, Player)");
        System.out.println("[4] JOIN LEAGUE");
        System.out.println("[0] EXIT");
        System.out.print("Your choice: ");
    }

    /**
     * Starts the main application loop.
     * Listens for user input and navigates to the appropriate menu.
     *
     * @throws LeagueNotFoundException if a league operation fails
     * @throws TeamNotFoundException   if a team operation fails
     * @throws PlayerNotFoundException if a player operation fails
     */
    public void start() throws LeagueNotFoundException, TeamNotFoundException, PlayerNotFoundException {


        while (true) {
            clearScreen();
            header();
            startMenu();
            String choice = input.nextLine();

            switch (choice) {
                case "1" -> createMenu();
                case "2" -> viewMenu();
                case "3" -> editMenu();
                case "4" -> joinLeague();
                case "0" -> System.exit(0);
                default -> System.out.println("You dumb puck, wrong choice, try again!");
            }
            System.out.println();
        }
    }

    /**
     * Displays the create menu and handles creation of leagues, teams, and players.
     *
     * @throws TeamNotFoundException   if a team operation fails
     * @throws PlayerNotFoundException if a player operation fails
     */
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

    /**
     * Displays the view menu and handles viewing of leagues, teams, and players.
     */
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

    /**
     * Handles the process of joining a league.
     * Allows the user to choose or create a league and a team.
     *
     * @throws LeagueNotFoundException if the league is not found
     * @throws TeamNotFoundException   if the team is not found
     * @throws PlayerNotFoundException if a player operation fails
     */
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

    /**
     * Prompts the user to either join an existing league or create a new one.
     *
     * @return the selected or newly created league, or null if the user goes back
     */
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
                    return chooseExistingLeague();
                }
                case "2" -> {
                    return createLeague();
                }
                case "0" -> {
                    return null;
                }
                default -> System.out.println("What the puck, try again!");
            }
        }
    }

    /**
     * Prompts the user to create a new league and saves it to the database.
     *
     * @return the created league
     */
    public League createLeague() {
        header();
        System.out.print("Enter league name: ");
        String leagueName = input.nextLine().toLowerCase();
        League league = leagueService.createLeague(leagueName);
        System.out.println("League: ´" + leagueName + "´ created!");

        return league;
    }

    /**
     * Prompts the user to create a new team and optionally add players to it.
     *
     * @return the created team
     * @throws TeamNotFoundException   if the team is not found during player assignment
     * @throws PlayerNotFoundException if a player is not found during player assignment
     */
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

    /**
     * Prompts the user to create a new player by entering all required attributes.
     * Validates input and displays the created player's salary.
     *
     * @return the created player, or null if input was invalid
     */
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

            System.out.print("How much ettans lös your player can carry under the lip (1-100): ");
            int snusing = Integer.parseInt(input.nextLine());

            Player player = playerService.createPlayer(fullName, position, jerseyNr, refereeHeckling, beerChugging, diving, swag, snusing);

            System.out.println("\nSay hi to: " + player.getFullName() + " with a salary of: " + player.getFormattedSalary() + " kr");

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

    /**
     * Displays all registered players in the console.
     * Shows player ID, name, position, and salary.
     */
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
                    + player.getFormattedSalary());
        }
    }

    /**
     * Displays a menu for adding players to a team.
     * Allows the user to add existing players or create new ones.
     *
     * @param team the team to add players to
     * @throws TeamNotFoundException   if the team is not found
     * @throws PlayerNotFoundException if a player is not found
     */
    private void addPlayersToTeamMenu(Team team) throws TeamNotFoundException, PlayerNotFoundException {
        while (true) {
            team = teamService.getTeamByName(team.getName());

            header();
            System.out.println("Add players to " + team.getName());
            printTeamStatus(team);
            System.out.println();

            System.out.println("[1] ADD EXISTING PLAYER");
            System.out.println("[2] CREATE NEW PLAYER AND ADD");
            System.out.println("[0] DONE");
            System.out.print("Your choice: ");

            String choice = input.nextLine();

            switch (choice) {
                case "1" -> addExistingPlayerToTeam(team);
                case "2" -> {
                    Player player = createPlayer();

                    if (player != null) {
                        try {
                            teamService.addPlayerToTeam(team.getName(), player.getPlayerId());

                            Team updatedTeam = teamService.getTeamByName(team.getName());

                            System.out.println();
                            System.out.println(player.getFullName() + " added to " + updatedTeam.getName());
                            System.out.println("Remaining budget: " + updatedTeam.getFormattedRemainingBudget() + " kr");

                        } catch (RuntimeException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                }
                case "0" -> {
                    return;
                }
                default -> System.out.println("Wrong choice, try again!");
            }
        }
    }

    /**
     * Handles adding an existing player to a team.
     * Displays available players and the team's current budget before adding.
     *
     * @param team the team to add the player to
     */
    private void addExistingPlayerToTeam(Team team) {
        header();

        try {
            team = teamService.getTeamByName(team.getName());

            System.out.println("Adding player to " + team.getName());
            System.out.println("Current salary: " + team.getFormattedTotalSalary() + " kr");
            System.out.println("Remaining budget: " + team.getFormattedRemainingBudget() + " kr");
            System.out.println();

            viewPlayers();

            System.out.print("Enter player id: ");
            int playerId = Integer.parseInt(input.nextLine());

            Player player = playerService.getPlayerById(playerId);

            System.out.println();
            System.out.println("Player salary: " + player.getFormattedSalary() + " kr");

            teamService.addPlayerToTeam(team.getName(), playerId);

            Team updatedTeam = teamService.getTeamByName(team.getName());

            System.out.println(player.getFullName() + " added to " + team.getName());
            System.out.println("Remaining budget: " + updatedTeam.getFormattedRemainingBudget() + " kr");

        } catch (NumberFormatException e) {
            System.out.println("You must enter a pucking number.");
        } catch (PlayerNotFoundException e) {
            System.out.println("Player not found.");
        } catch (TeamNotFoundException e) {
            System.out.println("Team not found.");
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Displays all registered teams in the console.
     */
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

    /**
     * Displays all registered leagues in the console.
     */
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

    /**
     * Prompts the user to select an existing league by name.
     *
     * @return the selected league, or null if not found
     */
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

    /**
     * Prompts the user to either use an existing team or create a new one.
     *
     * @return the selected or newly created team, or null if the user goes back
     * @throws TeamNotFoundException   if the team is not found
     * @throws PlayerNotFoundException if a player operation fails
     */
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
                    return chooseExistingTeam();
                }
                case "2" -> {
                    return createTeam();
                }
                case "0" -> {
                    return null;
                }
                default -> System.out.println("Wrong choice, try again!");
            }
        }
    }

    /**
     * Prompts the user to select an existing team by name.
     *
     * @return the selected team, or null if not found
     */
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

    /**
     * Displays the edit menu and navigates to edit or remove options
     * for leagues, teams, and players.
     *
     * @throws LeagueNotFoundException if a league operation fails
     */
    public void editMenu() throws LeagueNotFoundException {
        while (true) {
            header();
            System.out.println("[1] EDIT LEAGUE");
            System.out.println("[2] EDIT TEAM");
            System.out.println("[3] EDIT PLAYER");
            System.out.println("[0] BACK");
            System.out.print("Your choice: ");

            String choice = input.nextLine();

            switch (choice) {
                case "1" -> editOrRemoveLeague();
                case "2" -> editOrRemoveTeam();
                case "3" -> editOrRemovePlayer();
                case "0" -> {
                    return;
                }
                default -> System.out.println("You stupid puck, try again!");
            }
        }
    }

    /**
     * Displays a sub-menu for editing or removing a league.
     *
     * @throws LeagueNotFoundException if the league is not found
     */
    public void editOrRemoveLeague() throws LeagueNotFoundException {
        while (true) {
            header();
            System.out.println("[1] EDIT LEAGUE");
            System.out.println("[2] REMOVE LEAGUE");
            System.out.println("[0] BACK");
            System.out.print("Your choice: ");

            String choice = input.nextLine();

            switch (choice) {
                case "1" -> editLeague();
                case "2" -> removeLeague();
                case "0" -> {
                    return;
                }
                default -> System.out.println("Wrong choice, try again!");
            }
        }
    }

    /**
     * Displays a sub-menu for editing or removing a team.
     */
    public void editOrRemoveTeam() {
        while (true) {
            header();
            System.out.println("[1] EDIT TEAM");
            System.out.println("[2] REMOVE TEAM");
            System.out.println("[0] BACK");
            System.out.print("Your choice: ");

            String choice = input.nextLine();

            switch (choice) {
                case "1" -> editTeam();
                case "2" -> removeTeam();
                case "0" -> {
                    return;
                }
                default -> System.out.println("Wrong choice, try again!");
            }
        }
    }

    /**
     * Displays a sub-menu for editing or removing a player.
     */
    public void editOrRemovePlayer() {
        while (true) {
            header();
            System.out.println("[1] EDIT PLAYER");
            System.out.println("[2] REMOVE PLAYER");
            System.out.println("[0] BACK");
            System.out.print("Your choice: ");

            String choice = input.nextLine();

            switch (choice) {
                case "1" -> editPlayer();
                case "2" -> removePlayer();
                case "0" -> {
                    return;
                }
                default -> System.out.println("Wrong choice, try again!");
            }
        }
    }

    /**
     * Prompts the user to remove a league by name.
     */
    public void removeLeague() {
        header();

        if (leagueService.getAllLeagues().isEmpty()) {
            System.out.println("OH puck, no league has been registered...");
            pressEnterToContinue();
            return;
        }

        viewLeagues();

        System.out.print("Enter league name to remove: ");
        String name = input.nextLine().toLowerCase();

        try {
            leagueService.deleteLeague(name);
            System.out.println("League '" + name + "' removed!");
        } catch (LeagueNotFoundException e) {
            System.out.println(name + " is not an existing league");
        }

        pressEnterToContinue();
    }

    /**
     * Prompts the user to remove a team by name.
     */
    public void removeTeam() {
        header();

        if (teamService.getAllTeams().isEmpty()) {
            System.out.println("OH puck, no team has been registered...");
            pressEnterToContinue();
            return;
        }

        viewTeams();

        System.out.print("Enter team name to remove (0 to go back): ");
        String name = input.nextLine().toLowerCase();

        try {
            Team team = teamService.getTeamByName(name);

            teamService.deleteTeam(team);
            System.out.println("Team '" + name + "' removed!");
        } catch (TeamNotFoundException e) {
            System.out.println(name + " is not an existing team");
        }

        pressEnterToContinue();
    }

    /**
     * Prompts the user to remove a player by ID.
     */
    public void removePlayer() {
        header();

        if (playerService.getAllPlayers().isEmpty()) {
            System.out.println("OH puck, no player has been registered...");
            pressEnterToContinue();
            return;
        }

        viewPlayers();

        System.out.print("Enter player id to remove: ");
        String idInput = input.nextLine();

        try {
            int playerId = Integer.parseInt(idInput);
            playerService.deletePlayer(playerId);
            System.out.println("Player removed!");
        } catch (NumberFormatException e) {
            System.out.println("You must enter a pucking number.");
        } catch (PlayerNotFoundException e) {
            System.out.println("Player not found!");
        }

        pressEnterToContinue();
    }

    /**
     * Prompts the user to edit the name of an existing league.
     *
     * @throws LeagueNotFoundException if the league is not found
     */
    public void editLeague() {
        header();

        if (leagueService.getAllLeagues().isEmpty()) {
            System.out.println("OH puck, no league has been registered...");
            return;
        }
        viewLeagues();

        String oldName = "";

        try {
            System.out.print("Enter league name to edit: ");
            oldName = input.nextLine().toLowerCase();

            System.out.print("Enter new name: ");
            String newName = input.nextLine().toLowerCase();

            leagueService.updateLeagueName(oldName, newName);
            System.out.println("League updated to: " + newName);
        } catch (LeagueNotFoundException e) {
            System.out.println(oldName + " is not an existing league!");
        }

        pressEnterToContinue();
    }

    /**
     * Prompts the user to edit the name of an existing team.
     */
    public void editTeam() {
        header();

        if (teamService.getAllTeams().isEmpty()) {
            System.out.println("OH puck, no team has been registered...");
            return;
        }

        viewTeams();

        String oldName = "";

        try {
            System.out.print("Enter team name to edit: ");
            oldName = input.nextLine().toLowerCase();

            System.out.print("Enter new name: ");
            String newName = input.nextLine().toLowerCase();

            teamService.updateTeamName(oldName, newName);
            System.out.println("Team updated to: " + newName);
        } catch (TeamNotFoundException e) {
            System.out.println(oldName + " is not an existing team!");
        }

        pressEnterToContinue();
    }

    /**
     * Prompts the user to edit an existing player's attributes.
     * Validates input and displays the updated player's salary.
     */
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

            Player player = playerService.getPlayerById(playerId);

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
            System.out.println("\nSay hi to: " + player.getFullName() + " with a salary of: " + player.getFormattedSalary() + " kr");


        } catch (NumberFormatException e) {
            System.out.println("You must enter a pucking number.");
        } catch (InvalidPlayerException e) {
            System.out.println(e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid position. Use GOALIE, DEFENDER, CENTER, LEFT_WING or RIGHT_WING.");
        } catch (PlayerNotFoundException e) {
            System.out.println("Not an existing player!");
        }

        pressEnterToContinue();
    }

    /**
     * Waits for the user to press ENTER before continuing.
     */
    public void pressEnterToContinue() {
        System.out.println("\nPress ENTER to continue...");
        input.nextLine();
    }

    /**
     * Prints the current status of a team including salary, remaining budget,
     * and available positions.
     *
     * @param team the team to print status for
     */
    private void printTeamStatus(Team team) {
        System.out.println("Current salary: " + team.getFormattedTotalSalary() + " kr");
        System.out.println("Remaining budget: " + team.getFormattedRemainingBudget() + " kr");
        System.out.println("Positions left:");
        System.out.println("- Forwards: " + team.getRemainingForwards());
        System.out.println("- Defenders: " + team.getRemainingDefenders());
        System.out.println("- Goalies: " + team.getRemainingGoalies());
    }

    private void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

}
