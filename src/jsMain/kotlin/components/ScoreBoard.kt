package components

import actions.pause
import actions.restart
import actions.resume
import csstype.ClassName
import dispatch
import react.FC
import react.Props
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.div
import react.redux.useDispatch
import react.redux.useSelector
import reducers.gameReducer
import redux.combineReducers
import utils.GameState

val ScoreBoard = FC<Props> { props ->
    val game = useSelector { state: GameState -> state }
    var score = game.score
    var isRunning = game.isRunning
    var gameOver = game.gameOver

    div {
        className = ClassName("score-board")

        div {
            +"Score: $score"
        }
        div {
            +"Level: 1"
        }
        button {
            className = ClassName("score-board-button")
            +if (isRunning) "Pause" else "Play"
            onClick = { e ->
                if (!gameOver) {
                    dispatch(if (isRunning) pause() else resume())
                }
            }
        }
        button {
            className = ClassName("score-board-button")
            +"Restart"
            onClick = { e -> dispatch(restart())}
        }
    }
}