package components

import actions.*
import csstype.ClassName
import dispatch
import react.FC
import react.Props
import react.dom.html.ReactHTML
import react.dom.html.ReactHTML.div
import react.redux.useSelector
import utils.GameState

val Controls = FC<Props> {
    val isRunning = useSelector { state: GameState -> state.isRunning }
    val gameOver = useSelector { state: GameState -> state.gameOver }
    val isDisabled = !isRunning || gameOver

    div {
        className = ClassName("controls")

        ReactHTML.button {
            className = ClassName("control-button")
            disabled = isDisabled
            +"Left"
            onClick = { if (!isDisabled) dispatch(MoveLeft()) }
        }

        ReactHTML.button {
            className = ClassName("control-button")
            disabled = isDisabled
            +"Right"
            onClick = { if (!isDisabled) dispatch(MoveRight()) }
        }

        ReactHTML.button {
            className = ClassName("control-button")
            disabled = isDisabled
            +"Rotate"
            onClick = { if (!isDisabled) dispatch(Rotate()) }
        }

        ReactHTML.button {
            className = ClassName("control-button")
            disabled = isDisabled
            +"Down"
            onClick = { if (!isDisabled) dispatch(MoveDown()) }
        }
    }
}