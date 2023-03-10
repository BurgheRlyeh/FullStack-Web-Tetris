package components

import actions.*
import csstype.ClassName
import dispatch
import react.FC
import react.Props
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.div
import react.redux.useSelector
import utils.GameState

val ScoreBoard = FC<Props> {
    val state = useSelector { state: GameState -> state }
    val (_, _, _, _, _, _, isRunning, score, _, gameOver) = state

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
            onClick = {
                if (!gameOver) {
                    dispatch(if (isRunning) Pause() else Resume())
                }
            }
        }
        button {
            className = ClassName("score-board-button")
            +"Restart"
            onClick = { dispatch(Restart())}
        }
    }
}