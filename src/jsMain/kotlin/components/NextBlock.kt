package components

import csstype.ClassName
import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import react.redux.useSelector
import utils.GameState
import utils.shapes

val NextBlock = FC<Props> { props ->
    var nextShape = useSelector { state: GameState -> state.nextShape }
    var box = shapes[nextShape][0]

    div {
        className = ClassName("next-block")
        box.mapIndexed { row, rowArray ->
            rowArray.mapIndexed { col, square ->
                GridSquare {
                    key = "$row$col"
                    color = if (square == 0) 0 else nextShape
                }
            }
        }
    }
}