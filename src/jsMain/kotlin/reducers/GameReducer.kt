package reducers

import actions.*
import react.State
import redux.RAction
import utils.*

fun gameReducer(state: GameState = defaultGameState(), action: RAction): GameState {
    var shape = state.shape
    var grid = state.grid
    var x = state.x
    var y = state.y
    var rotation = state.rotation
    var nextShape = state.nextShape
    var score = state.score
    var isRunning = state.isRunning

    when (action) {
        is Rotate -> {
            var newRotation = nextRotation(shape, rotation)
            if (canMoveTo(shape, grid, x, y, newRotation)) {
                return state.copy(rotation = newRotation)
            }
            return state
        }
        is MoveRight -> {
            if (canMoveTo(shape, grid, x + 1, y, rotation)) {
                return state.copy(x = x + 1)
            }
            return state
        }
        is MoveLeft -> {
            // subtract 1 from the x and check if this new position is possible by calling `canMoveTo()
            if (canMoveTo(shape, grid, x - 1, y, rotation)) {
                return state.copy(x = x - 1)
            }
            return state
        }
        is MoveDown -> {
            // Get the next potential Y position
            var maybeY = y + 1

            // Check if the current block can move here
            if (canMoveTo(shape, grid, x, maybeY, rotation)) {
                console.log("can move down")
                // If so move down don't place the block
                return state.copy(y = maybeY)
            }
            console.log("cannot move down")

            // If not place the block
            // (this returns an object with a grid and gameover bool)
            var (newGrid, gameOver) = addBlockToGrid(shape, grid, x, y, rotation)

            if (gameOver) {
                // Game Over
                return state.copy(shape = 0, grid = newGrid, gameOver = true)
            }

            // reset somethings to start a new shape/block
            return defaultGameState().copy(
                grid = newGrid,
                shape = nextShape,
                score = score + checkRows(newGrid),
                isRunning = isRunning
            )

            // TODO: Check and Set level
        }
        is Resume -> {
            return state.copy(isRunning = true)
        }
        is Pause -> {
            return state.copy(isRunning = false)
        }
        is GameOver -> {
            return state
        }
        is Restart -> {
            return defaultGameState()
        }
        else -> {
            return state
        }
    }
}