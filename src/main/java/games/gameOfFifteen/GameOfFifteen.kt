package games.gameOfFifteen

import board.Direction
import board.GameBoard
import board.createGameBoard
import games.game.Game
import games.game2048.Game2048

/*
 * Implement the Game of Fifteen (https://en.wikipedia.org/wiki/15_puzzle).
 * When you finish, you can play the game by executing 'PlayGameOfFifteen'.
 */
fun newGameOfFifteen(initializer: GameOfFifteenInitializer = RandomGameInitializer()): Game =
    GameOfFifteen(initializer)

class GameOfFifteen(private val initializer: GameOfFifteenInitializer) : Game {
    private val board = createGameBoard<Int?>(4)

    override fun initialize() {
        val values = initializer.initialPermutation
        board.getAllCells()
            .forEachIndexed { index, cell -> board[cell] = if (values.lastIndex >= index) values[index] else null }
    }

    override fun canMove(): Boolean = true

    override fun hasWon(): Boolean {
        val result = mutableListOf<Int>()
        repeat(board.width) { i ->
            repeat(board.width) { j ->
                result.add(board[board.getCell(i + 1, j + 1)] ?: 16)
            }
        }
        return result == (1..16).toList()
    }

    override fun processMove(direction: Direction) {
        board.run {
            val blank = find { it == null }
            blank?.let {cell->
                cell.getNeighbour(direction.reversed())?.let {
                    board[cell] = board[it]
                    board[it] = null
                }
            }
        }
    }


    override fun get(i: Int, j: Int): Int? {
        return board[board.getCell(i, j)]
    }

}