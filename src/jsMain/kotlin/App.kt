import components.*
import csstype.ClassName
import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h1
import react.dom.html.ReactHTML.header
import react.redux.Provider
import reducers.gameReducer
import redux.RAction
import redux.createStore
import redux.rEnhancer
import utils.defaultGameState

var gameStore = createStore(::gameReducer, defaultGameState(), rEnhancer())

fun dispatch(rAction: RAction) = gameStore.dispatch(rAction)

val App = FC<Props> {
    Provider {
        this.store = gameStore

        div {
            className = ClassName("App")

            header {
                className = ClassName("App-header")

                h1 {
                    className = ClassName("App-title")
                    +"Tetris"
                }
            }
            GridBoard()
            NextBlock()
            ScoreBoard()
            Controls()
            MessagePopup()
        }
    }
}