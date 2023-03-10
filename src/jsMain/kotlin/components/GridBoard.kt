package components

import actions.MoveDown
import csstype.ClassName
import dispatch
import kotlinx.browser.window
import react.*
import react.dom.html.ReactHTML.div
import react.redux.useSelector
import utils.GameState
import shapes.shapes
import web.timers.requestAnimationFrame

val GridBoard = FC<Props> {
    val requestRef = useRef<Double>(null)
    val lastUpdateTimeRef = useRef(0.0)
    val progressTimeRef = useRef(0.0)

    val state = useSelector { state: GameState -> state }
    val (grid, shape, rotation, x, y, _, isRunning, _, speed, _) = state

    val block = shapes[shape][rotation]

    fun update(time: Double) {
        requestRef.asDynamic().current = requestAnimationFrame(::update)
        if (!isRunning) {
            return
        }
        if (lastUpdateTimeRef.current == 0.0) {
            lastUpdateTimeRef.current = time
        }
        progressTimeRef.current = progressTimeRef.current?.plus(time - lastUpdateTimeRef.current!!)
        if (progressTimeRef.current!! > speed) {
            dispatch(MoveDown())
            progressTimeRef.current = 0.0
        }
        lastUpdateTimeRef.current = time
    }

    useEffect(listOf(isRunning)) {
        if (isRunning) {
            requestRef.current = window.requestAnimationFrame { update(it) }.toDouble()
        } else {
            window.cancelAnimationFrame(requestRef.current!!.toInt())
        }
    }

    div {
        className = ClassName("grid-board")

        grid.mapIndexed { r, row ->
            row.mapIndexed { c, square ->
                GridSquare {
                    key = (r * grid[0].size + c).toString()
                    this.color =
                        if (x <= c && c - x < block.size &&
                            y <= r && r - y < block.size &&
                            block[r - y][c - x] != 0
                        ) shape
                        else square
                }
            }
        }
    }
}
