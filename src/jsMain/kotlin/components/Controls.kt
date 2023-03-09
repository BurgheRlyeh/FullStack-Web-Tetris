package components

import actions.moveDown
import actions.moveLeft
import actions.moveRight
import actions.rotate
import csstype.ClassName
import dispatch
import react.FC
import react.Props
import react.dom.html.ReactHTML
import react.dom.html.ReactHTML.div
import react.redux.useSelector
import utils.GameState

val Controls = FC<Props> {
    var isRunning = useSelector { state: GameState -> state.isRunning }
    var gameOver = useSelector { state: GameState -> state.gameOver }

    div {
        className = ClassName("controls")

        /** Left **/
        ReactHTML.button {
            className = ClassName("control-button")
            disabled = !isRunning || gameOver
            +"Left"
            onClick = { e ->
                if (isRunning && !gameOver) {
                    dispatch(moveLeft())
                }
            }
        }

        /** Right **/
        ReactHTML.button {
            className = ClassName("control-button")
            disabled = !isRunning || gameOver
            +"Right"
            onClick = { e ->
                if (isRunning && !gameOver) {
                    dispatch(moveRight())
                }
            }
        }

        /** Rotate **/
        ReactHTML.button {
            className = ClassName("control-button")
            disabled = !isRunning || gameOver
            +"Rotate"
            onClick = { e ->
                if (isRunning && !gameOver) {
                    dispatch(rotate())
                }
            }
        }

        /** Down **/
        ReactHTML.button {
            className = ClassName("control-button")
            disabled = !isRunning || gameOver
            +"Down"
            onClick = { e ->
                if (isRunning && !gameOver) {
                    dispatch(moveDown())
                }
            }
        }
    }
}