# Gammy

Universal server for your game

### Current features:
- authenticate players
- authenticate admin

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
- update player stat
- validate player stat
- get all player stats

achievements:
- create game achievement
- unlock and lock achievements
- dynamic unlock with conditions
  - example: automatically unlock achievement when `kills` stat is >= 10: `stat("kills") >= 10`
