package components

import actions.moveDown
import csstype.ClassName
import dispatch
import kotlinx.browser.window
import react.*
import react.dom.html.ReactHTML.div
import react.redux.useDispatch
import react.redux.useSelector
import utils.GameState
import utils.shapes
import web.timers.FrameRequestId
import web.timers.cancelAnimationFrame
import web.timers.requestAnimationFrame

val GridBoard = FC<Props> {
    val requestRef = useRef<Double>(null)
    var lastUpdateTimeRef = useRef(0.0)
    var progressTimeRef = useRef(0.0)

    val game = useSelector { state: GameState -> state }
    var grid = game.grid
    var shape = game.shape
    var rotation = game.rotation
    var x = game.x
    var y = game.y
    var isRunning = game.isRunning
    var speed = game.speed

    var block = shapes[shape][rotation]
    var blockColor = shape

    fun update(time: Double) {
        requestRef.asDynamic().current = requestAnimationFrame(::update)
        if (!isRunning) {
            return
        }
        if (lastUpdateTimeRef.current == 0.0) {
            lastUpdateTimeRef.current = time
        }
        val deltaTime = time - lastUpdateTimeRef.current!!
        progressTimeRef.current = progressTimeRef.current?.plus(deltaTime)
        if (progressTimeRef.current!! > speed) {
            dispatch(moveDown())
            progressTimeRef.current = 0.0
        }
        lastUpdateTimeRef.current = time
    }

    useEffect(listOf(isRunning)) {
        if (isRunning) {
            requestRef.current = window.requestAnimationFrame { e -> update(e) }.toDouble()
        }
        else {
            window.cancelAnimationFrame(requestRef.current!!.toInt())
        }
    }

//    useEffect(dependencies = listOf(isRunning).toTypedArray(), effect = {
//        requestRef.current = window.requestAnimationFrame { e -> update(e) }.toDouble()
//        window.cancelAnimationFrame(requestRef.current!!.toInt())
//    })


//    rawUseEffect({
//        raqUseEffect@{
//            requestRef.current = window.requestAnimationFrame { e -> update(e) }.toDouble()
//            return@raqUseEffect window.cancelAnimationFrame(requestRef.current!!.toInt())
//        }
//    }, listOf(isRunning).toTypedArray())

    div {
        className = ClassName("grid-board")

        grid.mapIndexed { row, rowArray ->
            rowArray.mapIndexed { col, square ->
                // Find the block x and y on the shape grid
                // By subtracting the x and y from the col and the row we get the position of the upper left corner of the block array as if it was superimposed over the main grid
//                console.log("block.size = ${block.size}")
                var blockX = col - game.x
//                console.log("blockX = $blockX")
                var blockY = row - game.y
//                console.log("blockY = $blockY")
                var cl = square
                // Map current falling block to grid.
                // For any squares that fall on the grid we need to look at the block array and see if there is a 1 in this case we use the block color.
                if (blockX >= 0 && blockX < block.size && blockY >= 0 && blockY < block.size) {
                    console.log("i'm in if")
                    cl = if (block[blockY][blockX] == 0) cl else blockColor
                }
                // Generate a unique key for every block
                var k = row * grid[0].size + col;

                GridSquare {
                    key = k.toString()
                    this.color = cl
                }
            }
        }
    }
}
