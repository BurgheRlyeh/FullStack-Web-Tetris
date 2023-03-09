package utils

import react.State
import kotlin.random.Random

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
    speed = 1000,
    gameOver = false
)

fun defaultGrid() = Array(18) { IntArray(10) { 0 } }

fun random(min: Int, max: Int) = Random.nextInt(min, max)

fun randomShape() = random(1, shapes.size - 1)

fun nextRotation(shape: Int, rotation: Int) = (rotation + 1) % shapes[shape].size

fun canMoveTo(shape: Int, grid: Grid, x: Int, y: Int, rotation: Int): Boolean {
    var currentShape = shapes[shape][rotation]
    // Get the width and height of the grid
    var gridWidth = grid[0].size - 1
    var gridHeight = grid.size - 1
    // Loop over the shape array
    for (row in currentShape.indices) {
        for (col in currentShape[row].indices) {
            // If the value isn't 0 it's part of the shape
            if (currentShape[row][col] != 0) {
                // Offset the square to map it to the larger grid
                var proposedX = col + x
                var proposedY = row + y

                // Off the left or right side or off the bottom return false
                if (proposedX < 0 || gridWidth < proposedX || gridHeight < proposedY) {
                    return false
                }

                if (proposedY < 0) {
                    continue
                }

                // Get the possible row. This might be undefined if we're above the top
                var possibleRow = grid[proposedY]

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

// Checks for completed rows and scores points
fun checkRows(grid: Grid): Int {
    // Points increase for each row completed
    // i.e. 40 points for completing one row, 100 points for two rows
    var points = intArrayOf(0, 40, 100, 300, 1200)
    var completedRows = 0

    for (row in grid.indices) {
        // No empty cells means it can't find a 0, so the row must be complete!
        if (grid[row].indexOf(0) == -1) {
            completedRows += 1
            // Remove the row and add a new empty one at the top
            js("grid.splice(row, 1)")
            js("grid.unshift(Array(10).fill(0))")
        }
    }

    return points[completedRows]
}

// Adds current shape to grid
fun addBlockToGrid(shape: Int, grid: Grid, x: Int, y: Int, rotation: Int): Pair<Grid, Boolean> {
    // At this point the game is not over
    var blockOffGrid = false
    var block = shapes[shape][rotation]
    val newGrid = defaultGrid()
    for (i in grid.indices) {
        for (j in grid[i].indices) {
            newGrid[i][j] = grid[i][j]
        }
    }

    for (row in block.indices) {
        for (col in block[row].indices) {
            if (block[row][col] != 0) {
                var yIndex = row + y
                // If the yIndex is less than 0 part of the block
                // is off the top of the screen and the game is over
                if (yIndex < 0) {
                    blockOffGrid = true
                } else {
                    newGrid[row + y][col + x] = shape
                }
            }
        }
    }

    return Pair(newGrid, blockOffGrid)
}

val shapes = arrayOf(
    // none
    arrayOf(
        arrayOf(
            arrayOf(0,0,0,0),
            arrayOf(0,0,0,0),
            arrayOf(0,0,0,0),
            arrayOf(0,0,0,0)
        )
    ),

    // I
    arrayOf(
        arrayOf(
            arrayOf(0,0,0,0),
            arrayOf(1,1,1,1),
            arrayOf(0,0,0,0),
            arrayOf(0,0,0,0)
        ),
        arrayOf(
            arrayOf(0,1,0,0),
            arrayOf(0,1,0,0),
            arrayOf(0,1,0,0),
            arrayOf(0,1,0,0)
        )
    ),

    // T
    arrayOf(
        arrayOf(
            arrayOf(0,0,0,0),
            arrayOf(1,1,1,0),
            arrayOf(0,1,0,0),
            arrayOf(0,0,0,0)
        ),
        arrayOf(
            arrayOf(0,1,0,0),
            arrayOf(1,1,0,0),
            arrayOf(0,1,0,0),
            arrayOf(0,0,0,0)
        ),
        arrayOf(
            arrayOf(0,1,0,0),
            arrayOf(1,1,1,0),
            arrayOf(0,0,0,0),
            arrayOf(0,0,0,0)
        ),
        arrayOf(
            arrayOf(0,1,0,0),
            arrayOf(0,1,1,0),
            arrayOf(0,1,0,0),
            arrayOf(0,0,0,0)
        )
    ),

    // L
    arrayOf(
        arrayOf(
            arrayOf(0,0,0,0),
            arrayOf(1,1,1,0),
            arrayOf(1,0,0,0),
            arrayOf(0,0,0,0)
        ),
        arrayOf(
            arrayOf(1,1,0,0),
            arrayOf(0,1,0,0),
            arrayOf(0,1,0,0),
            arrayOf(0,0,0,0)
        ),
        arrayOf(
            arrayOf(0,0,1,0),
            arrayOf(1,1,1,0),
            arrayOf(0,0,0,0),
            arrayOf(0,0,0,0)
        ),
        arrayOf(
            arrayOf(0,1,0,0),
            arrayOf(0,1,0,0),
            arrayOf(0,1,1,0),
            arrayOf(0,0,0,0)
        )
    ),

    // J
    arrayOf(
        arrayOf(
            arrayOf(1,0,0,0),
            arrayOf(1,1,1,0),
            arrayOf(0,0,0,0),
            arrayOf(0,0,0,0)
        ),
        arrayOf(
            arrayOf(0,1,1,0),
            arrayOf(0,1,0,0),
            arrayOf(0,1,0,0),
            arrayOf(0,0,0,0)
        ),
        arrayOf(
            arrayOf(0,0,0,0),
            arrayOf(1,1,1,0),
            arrayOf(0,0,1,0),
            arrayOf(0,0,0,0)
        ),
        arrayOf(
            arrayOf(0,1,0,0),
            arrayOf(0,1,0,0),
            arrayOf(1,1,0,0),
            arrayOf(0,0,0,0)
        )
    ),

    // Z
    arrayOf(
        arrayOf(
            arrayOf(0,0,0,0),
            arrayOf(1,1,0,0),
            arrayOf(0,1,1,0),
            arrayOf(0,0,0,0)
        ),
        arrayOf(
            arrayOf(0,0,1,0),
            arrayOf(0,1,1,0),
            arrayOf(0,1,0,0),
            arrayOf(0,0,0,0)
        )
    ),

    // S
    arrayOf(
        arrayOf(
            arrayOf(0,0,0,0),
            arrayOf(0,1,1,0),
            arrayOf(1,1,0,0),
            arrayOf(0,0,0,0)
        ),
        arrayOf(
            arrayOf(0,1,0,0),
            arrayOf(0,1,1,0),
            arrayOf(0,0,1,0),
            arrayOf(0,0,0,0)
        )
    ),

    arrayOf(
        arrayOf(
            arrayOf(0,1,1,0),
            arrayOf(0,1,1,0),
            arrayOf(0,0,0,0),
            arrayOf(0,0,0,0)
        )
    )
)