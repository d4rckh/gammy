# Gammy

Universal server for your game

### Current features:

authentication and authorization:
- authenticate players
- authenticate admin
- create player profiles
- validate permissions against objects

game stats:
- create game stats example:
```json
{
    "id": 5,
    "apiName": "xp",
    "displayName": "XP",
    "description": "",
    "type": "INTEGER",
    "defaultValue": 0,
    "maxValue": 300,
    "minValue": 220,
    "maxChange": 20,
    "onlyIncrement": false
}
```
- update player stat with different update methods
- validate player stat using min/max values, max change value and only increment
- keep track of score changes

achievements:
- create game achievements
- unlock and lock achievements
- dynamic unlock with conditions
  - example: automatically unlock achievement when `kills` stat is >= 10: `stat("kills") >= 10`

leaderboards:
- dynamic leaderboards based on already created game stats
- can be ascending or descending

user reporting:
- report players in-game
- view reports for player or by player

interaction tracking:
- track interactions
- track daily streaks for interactions