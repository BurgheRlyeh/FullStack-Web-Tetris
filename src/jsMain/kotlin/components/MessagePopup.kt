package components

import csstype.ClassName
import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h1
import react.redux.useSelector
import utils.GameState

val MessagePopup = FC<Props> {
    val state = useSelector { state: GameState -> state }
    val (_, _, _, _, _, _, isRunning, _, _, _, gameOver) = state

    var message = ""
    var isHidden = "hidden"

    if (gameOver) {
        message = "Game Over"
        isHidden = ""
    }
    else if (!isRunning) {
        message = "Paused"
        isHidden = ""
    }

    div {
       className = ClassName("message-popup $isHidden")
       h1 {
           +message
       }
    }
}