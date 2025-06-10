# **Gammy: Universal Game Backend Server**

Gammy is a **powerful and flexible universal backend server** designed to accelerate game development. Built with **Micronaut** and **Java 21**, it provides essential features like authentication, game statistics, achievements, leaderboards, user reporting, and interaction tracking, allowing you to focus on crafting engaging gameplay experiences.


## **Features**

Gammy offers a comprehensive set of features to power your game's backend:


### **Authentication and Authorization**

- **Player and Admin Authentication**: Securely authenticate both players and administrative users.

- **Player Profile Creation**: Easily create and manage player profiles.

- **Permission Validation**: Validate user permissions against specific in-game objects or actions.


### **Game Statistics**

- **Customizable Game Stats**: Define and manage various game statistics with detailed properties.

- **Flexible Stat Updates**: Update player statistics using different methods.

- **Stat Validation**: Enforce data integrity with validation rules based on **min/max values**, **maximum change** per update, and **increment-only** restrictions.

- **Score Change Tracking**: Keep a historical record of all score changes for auditing and analysis.


### **Achievements**

- **Game Achievement Management**: Create and manage a variety of in-game achievements.

- **Unlock/Lock Functionality**: Manually unlock or lock achievements for players.

- **Dynamic Unlocking with Conditions**: Define conditions for automatic achievement unlocking.

* **Example**: Automatically unlock an achievement when the kills stat is greater than or equal to 10: stat("kills") >= 10.


### **Leaderboards**

- **Dynamic Leaderboard Creation**: Generate leaderboards dynamically based on already created game statistics.

- **Ascending or Descending Order**: Configure leaderboards to display scores in ascending or descending order.


### **User Reporting**

- **In-Game Player Reporting**: Allow players to report other users directly within the game.

- **Report Viewing**: View reports for a specific player or reports submitted by a particular player.


### **Interaction Tracking**

- **Interaction Logging**: Track various player interactions within the game.

- **Daily Streak Tracking**: Monitor and manage daily streaks for specific interactions.


## **Data Models (Entities & DTOs)**

Gammy uses several data models to represent game-related entities and data transfer objects for API communication.


### **GameAchievementEntity**

Represents a definition for a game achievement.

- id: integer (nullable) - Unique identifier for the achievement.

- name: string - Display name of the achievement.

- apiName: string - Unique API-friendly name for the achievement.

- description: string - Detailed description of the achievement.

- unlockExpression: string (nullable) - A conditional expression for dynamic unlocking (e.g., stat("kills") >= 10).


### **GameInteractionEntity**

Defines a type of interaction that can be tracked in the game.

- id: integer (nullable) - Unique identifier for the interaction type.

- apiName: string - Unique API-friendly name for the interaction type.

- displayName: string - Display name of the interaction type.

- streak: GameInteractionStreak (enum: NONE, DAILY) - Specifies if this interaction contributes to a daily streak.


### **GameStatEntity**

Defines a customizable game statistic.

- id: integer (nullable) - Unique identifier for the game stat.

- apiName: string - Unique API-friendly name for the game stat.

- displayName: string - Display name of the game stat.

- description: string - Detailed description of the game stat.

- type: GameStatType (enum: INTEGER, FLOAT) - Data type of the stat.

- defaultValue: number - The initial value for the stat.

- maxValue: number (nullable) - Maximum allowed value for the stat.

- minValue: number (nullable) - Minimum allowed value for the stat.

- maxChange: number (nullable) - Maximum allowed change per update.

- onlyIncrement: boolean - If true, the stat can only increase.


### **LeaderboardCreateRequest**

A DTO for creating a new leaderboard.

- displayName: string - Display name of the leaderboard.

- apiName: string - Unique API-friendly name for the leaderboard.

- description: string - Description of the leaderboard.

- sortMethod: LeaderboardSortMethod (enum: ASCENDING, DESCENDING) - How the leaderboard entries are sorted.

- gameStatApiName: string - The apiName of the game stat this leaderboard is based on.


### **LeaderboardEntity**

Represents a defined leaderboard.

- id: integer (nullable) - Unique identifier for the leaderboard.

- displayName: string - Display name of the leaderboard.

- apiName: string - Unique API-friendly name for the leaderboard.

- description: string - Description of the leaderboard.

- sortMethod: LeaderboardSortMethod (enum: ASCENDING, DESCENDING) - How the leaderboard entries are sorted.

- gameStat: GameStatEntity - The game stat associated with this leaderboard.


### **LeaderboardEntries**

Represents a leaderboard with its entries.

- leaderboard: LeaderboardEntity - The leaderboard definition.

- entries: array of LeaderboardEntry - A list of ranked entries.


### **LeaderboardEntry**

Represents a single entry in a leaderboard.

- player: PlayerEntity - The player associated with this entry.

- value: number - The player's stat value for this leaderboard.

- rank: integer - The player's rank on the leaderboard.


### **PlayerAchievementEntity**

Represents an achievement that a specific player has unlocked.

- id: integer (nullable) - Unique identifier for the player achievement record.

- gameAchievement: GameAchievementEntity - The achievement definition.

- player: PlayerEntity - The player who unlocked the achievement.

- createdAt: string (date-time format) - Timestamp when the achievement was unlocked.


### **PlayerEntity**

Represents a player profile in the game.

- id: integer (nullable) - Unique identifier for the player.

- username: string - Player's username.

- hashedPassword: string - Player's hashed password (for internal use, not exposed).


### **PlayerGameStatUpdateRequest**

A DTO for updating a player's game stat.

- gameStatApiName: string - The apiName of the stat to update.

- newValue: number - The new value for the stat.

- updateMethod: PlayerGameStatUpdateMethod (enum: LOWEST, LARGEST, FORCE) - Method to apply the update.


### **PlayerInteractionEntity**

Represents a tracked interaction by a player.

- id: integer (nullable) - Unique identifier for the interaction record.

- gameInteraction: GameInteractionEntity - The type of interaction.

- player: PlayerEntity - The player who performed the interaction.

- timestamp: string (date-time format) - When the interaction occurred.

- streak: integer - Current streak count for this interaction (if applicable).


### **PlayerRegistrationRequest**

A DTO for registering a new player.

- username: string - Desired username for the new player.

- password: string - Password for the new player.


### **PlayerReportEntity**

Represents a report made by one player against another.

- id: integer (nullable) - Unique identifier for the report.

- reporterPlayer: PlayerEntity (nullable) - The player who made the report.

- reportedPlayer: PlayerEntity - The player being reported.

- text: string - The content of the report.


### **PlayerStatEntity**

Represents a player's current value for a specific game statistic.

- id: integer (nullable) - Unique identifier for the player stat record.

- gameStat: GameStatEntity - The definition of the game stat.

- player: PlayerEntity - The player owning this stat.

- value: number - The current value of the stat for the player.

- lastUpdate: string (date-time format) - Timestamp of the last update to this stat.


### **ReportPlayerRequest**

A DTO for submitting a player report.

- reporterPlayerId: integer - ID of the player submitting the report.

- reportedPlayerId: integer - ID of the player being reported.

- text: string - The message of the report.


### **StatUpdateHistoryEntity**

Records a historical change to a player's game stat.

- id: integer (nullable) - Unique identifier for the history record.

- player: PlayerEntity - The player whose stat was updated.

- gameStat: GameStatEntity - The game stat that was updated.

- oldValue: number - The value of the stat before the update.

- newValue: number - The value of the stat after the update.

- timestamp: string (date-time format) - When the update occurred.

- suspicious: boolean - Indicates if the update was flagged as suspicious.


## **Endpoints**

Gammy exposes a set of **RESTful endpoints**, typically accessible at localhost:8080 (or your configured host and port).


### **Authentication**

- GET /user: Retrieves information about the currently authenticated user.

- POST /user/login/admin: Authenticates an admin user.

- POST /user/login/player: Authenticates a player.

- POST /user/player: Creates a new player profile (requires PlayerRegistrationRequest in body).


### **Players**

- GET /players: Retrieves a list of all players.

- GET /players/{playerId}: Retrieves details for a specific player by their ID.

- POST /players: Creates a new player (requires PlayerRegistrationRequest in body).


### **Game Statistics**

- GET /stats/game: Retrieves all defined game statistics.

- POST /stats/game: Creates a new game statistic definition (requires GameStatEntity in body).

- PUT /stats/game: Updates an existing game statistic definition (requires GameStatEntity in body).

- GET /stats/player/{playerId}: Retrieves all statistics for a specific player.

- POST /stats/player/{playerId}: Updates a player's specific stat (requires PlayerGameStatUpdateRequest in body).

- GET /stats/player/{playerId}/history: Retrieves the history of changes for all stats for a specific player.

- GET /stats/player/{playerId}/{statApiName}/history: Retrieves the history of changes for a player's specific stat by its apiName.


### **Achievements**

- GET /achievements/game: Retrieves all defined game achievements.

- POST /achievements/game: Creates a new game achievement definition (requires GameAchievementEntity in body).

- PUT /achievements/game: Updates an existing game achievement definition (requires GameAchievementEntity in body).

- GET /achievements/player/{playerId}: Retrieves all achievements for a specific player.

- POST /achievements/player/{playerId}/{apiName}: Unlocks a specific achievement for a player.

- DELETE /achievements/player/{playerId}/{apiName}: Deletes (locks) a specific achievement for a player.


### **Leaderboards**

- POST /leaderboards: Creates or updates a leaderboard (requires LeaderboardCreateRequest or LeaderboardEntity in body).

- GET /leaderboards/{apiName}: Retrieves a leaderboard based on a specific game statistic's apiName.


### **User Reporting**

- POST /reports: Submits a new player report (requires ReportPlayerRequest in body).

- GET /reports/by/{playerId}: Retrieves reports submitted by a specific player.

- GET /reports/for/{playerId}: Retrieves reports made against a specific player.


### **Interaction Tracking**

- GET /interactions/type: Retrieves all defined interaction types.

- POST /interactions/type: Creates a new interaction type (requires GameInteractionEntity in body).

- PUT /interactions/type: Updates an existing interaction type (requires GameInteractionEntity in body).

- GET /interactions/type/{apiName}: Retrieves details for a specific interaction type.

- POST /interactions/track/{playerId}/{interactionApiName}: Records an interaction for a player.

- GET /interactions/player/{playerId}: Retrieves all interaction data for a specific player.
