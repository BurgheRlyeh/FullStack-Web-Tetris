package utils

import react.State
import kotlin.random.Random
import shapes.shapes

typealias Grid = Array<IntArray>

data class GameState(
    val grid: Grid,
    val shape: Int,
    val rotation: Int,
    val x: Int,
    val y: Int,
    val nextShape: Int,
    val isRunning: Boolean,
    val score: Int,
    val level: Int,
    val speed: Int,
    val gameOver: Boolean
): State

fun defaultGameState() = GameState(
    grid = defaultGrid(),
    shape = randomShape(),
    rotation = 0,
    x = 5,
    y = -4,
    nextShape = randomShape(),
    isRunning = true,
    score = 0,
    level = 1,
    speed = 1000,
    gameOver = false
)

fun defaultGrid() = Array(18) { IntArray(10) { 0 } }

fun randomShape() = Random.nextInt(1, shapes.size - 1)

fun nextRotation(shape: Int, rotation: Int) = (rotation + 1) % shapes[shape].size

fun canMoveTo(shape: Int, grid: Grid, x: Int, y: Int, rotation: Int): Boolean {
    val currentShape = shapes[shape][rotation]
    // Get the width and height of the grid
    val gridWidth = grid[0].size - 1
    val gridHeight = grid.size - 1
    // Loop over the shape array
    for (row in currentShape.indices) {
        for (col in currentShape[row].indices) {
            // If the value isn't 0 it's part of the shape
            if (currentShape[row][col] != 0) {
                // Offset the square to map it to the larger grid
                val proposedX = col + x
                val proposedY = row + y

                // Off the left or right side or off the bottom return false
                if (proposedX < 0 || gridWidth < proposedX || gridHeight < proposedY) {
                    return false
                }

                if (proposedY < 0) {
                    continue
                }

                // Get the possible row. This might be undefined if we're above the top
                val possibleRow = grid[proposedY]

                // If the row is not undefined you're on the grid
                if (possibleRow[proposedX] != 0) {
                    // This square must be filled
                    return false
                }
            }
        }
    }
    return true
}

fun checkRows(grid: Grid): Int {
    val points = intArrayOf(0, 40, 100, 300, 1200)
    var completedRows = 0

    for (row in grid.indices) {
        if (grid[row].indexOf(0) == -1) {
            ++completedRows
            // Remove the row and add a new empty one at the top
            js("grid.splice(row, 1)")
            js("grid.unshift(Array(10).fill(0))")
        }
    }

    return points[completedRows]
}

fun addBlockToGrid(shape: Int, grid: Grid, x: Int, y: Int, rotation: Int): Pair<Grid, Boolean> {
    var blockOffGrid = false

    val newGrid = defaultGrid()
    for (i in grid.indices) {
        for (j in grid[i].indices) {
            newGrid[i][j] = grid[i][j]
        }
    }

    shapes[shape][rotation].forEachIndexed { row, array ->
        array.forEachIndexed { col, square ->
            if (square != 0) {
                if (row + y < 0) {
                    blockOffGrid = true
                } else {
                    newGrid[row + y][col + x] = shape
                }
            }
        }
    }

    return Pair(newGrid, blockOffGrid)
}
