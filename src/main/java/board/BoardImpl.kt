package board


public fun createSquareBoard(width: Int): SquareBoard {
    val sb = SquareBoardImpl(width)
    sb.cells = createEmptyBoard(width)
    return sb;
}

public fun <T> createGameBoard(width: Int): GameBoard<T> {
    val gb = GameBoardImpl<T>(width)
    gb.cells = createEmptyBoard(width)
    gb.cells.forEach { it.forEach { cell: Cell -> gb.cellValues += cell to null } }
    return gb
}


class GameBoardImpl<T>(override val width: Int) : SquareBoardImpl(width), GameBoard<T> {

    val cellValues = mutableMapOf<Cell, T?>()

    override fun get(cell: Cell): T? = cellValues[cell]

    override fun set(cell: Cell, value: T?) {
        cellValues += cell to value
    }

    override fun filter(predicate: (T?) -> Boolean): Collection<Cell> =
        cellValues.filterValues { predicate.invoke(it) }.keys


    override fun find(predicate: (T?) -> Boolean): Cell? =
        cellValues.filter { predicate.invoke(it.value) }.keys.first()


    override fun any(predicate: (T?) -> Boolean): Boolean =
        cellValues.values.any(predicate)


    override fun all(predicate: (T?) -> Boolean): Boolean =
        cellValues.values.all(predicate)


}

open class SquareBoardImpl(override val width: Int) : SquareBoard {

    lateinit var cells: Array<Array<Cell>>;

    override fun getCellOrNull(i: Int, j: Int): Cell? {
        if (i < 1 || i > width || j < 1 || j > width) return null
        else return getCell(i, j)
    }

    override fun getCell(i: Int, j: Int): Cell = cells[i - 1][j - 1]


    override fun getAllCells(): Collection<Cell> =
        IntRange(1, width).flatMap { i: Int ->
            IntRange(1, width)
                .map { j: Int -> getCell(i, j) }
        }.toList()


    override fun getRow(i: Int, jRange: IntProgression): List<Cell> =
        when {
            jRange.last > width -> IntRange(jRange.first, width).map { j:Int -> getCell(i, j) }.toList()
            else -> jRange.map { j: Int -> getCell(i, j) }.toList()
        }


    override fun getColumn(iRange: IntProgression, j: Int): List<Cell> =
        when {
            iRange.last > width -> IntRange(iRange.first, width).map { i:Int -> getCell(i, j) }.toList()
            else -> iRange.map { i: Int -> getCell(i, j) }.toList()
        }


    override fun Cell.getNeighbour(direction: Direction): Cell? =
        when (direction) {
            Direction.UP -> getCellOrNull(this.i - 1, this.j)
            Direction.DOWN -> getCellOrNull(this.i + 1, this.j)
            Direction.RIGHT -> getCellOrNull(this.i, this.j + 1)
            Direction.LEFT -> getCellOrNull(this.i, this.j - 1)
        }


}

fun createEmptyBoard(width: Int): Array<Array<Cell>> {
    var board = arrayOf<Array<Cell>>()

    for (i in 1..width) {
        var array = arrayOf<Cell>()
        for (j in 1..width) {
            array += Cell(i, j)
        }
        board += array
    }
    return board
}


data class Cell(val i: Int, val j: Int) {
    override fun toString() = "($i, $j)"
}

enum class Direction {
    UP, DOWN, RIGHT, LEFT;

    fun reversed() = when (this) {
        UP -> DOWN
        DOWN -> UP
        RIGHT -> LEFT
        LEFT -> RIGHT
    }
}

interface SquareBoard {
    val width: Int

    fun getCellOrNull(i: Int, j: Int): Cell?
    fun getCell(i: Int, j: Int): Cell

    fun getAllCells(): Collection<Cell>

    fun getRow(i: Int, jRange: IntProgression): List<Cell>
    fun getColumn(iRange: IntProgression, j: Int): List<Cell>

    fun Cell.getNeighbour(direction: Direction): Cell?
}

interface GameBoard<T> : SquareBoard {

    operator fun get(cell: Cell): T?
    operator fun set(cell: Cell, value: T?)

    fun filter(predicate: (T?) -> Boolean): Collection<Cell>
    fun find(predicate: (T?) -> Boolean): Cell?
    fun any(predicate: (T?) -> Boolean): Boolean
    fun all(predicate: (T?) -> Boolean): Boolean
}

