package reducers

import actions.*
import redux.RAction
import utils.*

fun gameReducer(state: GameState = defaultGameState(), action: RAction): GameState {
    val (grid, shape, rotation, x, y, nextShape, isRunning, score, level, speed, _) = state

    when (action) {
        is Rotate -> {
            val newRotation = nextRotation(shape, rotation)
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
            val maybeY = y + 1

            // Check if the current block can move here
            if (canMoveTo(shape, grid, x, maybeY, rotation)) {
                // If so move down don't place the block
                return state.copy(y = maybeY)
            }

            // If not place the block
            // (this returns an object with a grid and game over bool)
            val (newGrid, gameOver) = addBlockToGrid(shape, grid, x, y, rotation)

            // Game Over
            if (gameOver) {
                return state.copy(shape = 0, grid = newGrid, gameOver = true)
            }

            var newSpeed = speed
            var newLevel = level
            if (2 * score >= level * speed) {
                newSpeed = newSpeed * 8 / 10
                ++newLevel
            }

            return defaultGameState().copy(
                grid = newGrid,
                shape = nextShape,
                score = score + checkRows(newGrid),
                speed = newSpeed,
                level = newLevel,
                isRunning = isRunning
            )
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