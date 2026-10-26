Build the game logic for Snake and Ladder on a 100-cell board.

1. The board is configured with snakes and ladders, each a start and end cell.
2. Any number of players take turns in a fixed order. The dice value is supplied by the caller so the game is testable.
3. Landing on a snake’s head sends the player to its tail. Landing on a ladder’s base lifts them to its top.
4. A roll that would take a player past cell 100 is ignored.
5. The first player to land exactly on 100 wins, and no further moves are accepted.