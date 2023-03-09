package components

import csstype.ClassName
import react.FC
import react.Props
import react.dom.html.ReactHTML
import react.dom.html.ReactHTML.div

external interface GridSquareProps : Props {
    var color: Int
}

val GridSquare = FC<GridSquareProps> { props ->
    div {
        className = ClassName("grid-square color-${props.color}")
    }

}