# SQHL - Strange Quality Hockey League

A school project developed as part of the course *Backendprogrammering* at YRGO.

---

## About the Project

SQHL is a fantasy hockey management system built with Java, Spring Framework, and JPA/Hibernate.
The system allows users to manage leagues, teams, and players through a text-based console menu.

---

## Features

- **Create** a league, team, or player
- **View** all registered leagues, teams, and players
- **Edit** or **remove** existing leagues, teams, and players
- **Join a league** by adding a team to an existing or newly created league

Players are assigned a salary based on their personal qualities.
The qualities are rated on a scale from 1 to 100, where a higher value increases the salary
and a lower value decreases it.

### Player Qualities

1. Referee Heckling | How skilled the player is at arguing with the referee 
2. Beer Chugging | The player's ability to chug a cold one after or during the game 
3. Diving | Acting skills on the ice 
4. Swag | Overall coolness factor 
5. Snusing | How much Swedish snus fits under the lip 

---

## Database

The project uses HSQLDB with file-based storage.
Data is persisted between sessions in local database files.
To reset the database, delete the generated `database.dat*` files in the project root.

---

## Relations

The project implements the following JPA entity relationships:

- **League → Team**: One-to-Many (one league can have many teams)
- **Team → League**: Many-to-One (many teams belong to one league)
- **Team ↔ Player**: Many-to-Many (a team can have many players, a player can belong to many teams)

---

## How to Run

### Required:
- Java 21 or higher
- Maven 3.6 or higher
- Git

Open a Unix-based terminal and run the following commands:

```bash
# Clone the repository
git clone https://github.com/FelixMaAndersson/hockey-data.git

# Navigate into the project folder
cd hockey-data

# Make the run script executable
chmod u+x run.sh

# Run the application
./run.sh
```

### Windows

```bat
run.bat
```

---

> **Note:** Make sure Java and Maven are installed and available in your PATH before running

By: Gustav, Felix & Eric 
 