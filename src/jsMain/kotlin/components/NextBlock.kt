package components

import csstype.ClassName
import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import react.redux.useSelector
import utils.GameState
import shapes.shapes

val NextBlock = FC<Props> {
    val nextShape = useSelector { state: GameState -> state.nextShape }

    div {
        className = ClassName("next-block")
        shapes[nextShape][0].mapIndexed { row, rowArray ->
            rowArray.mapIndexed { col, square ->
                GridSquare {
                    key = "$row$col"
                    color = if (square == 0) 0 else nextShape
                }
            }
        }
    }
}