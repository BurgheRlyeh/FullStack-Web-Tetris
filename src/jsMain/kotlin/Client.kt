import kotlinext.js.require
import web.dom.document
import react.create
import react.dom.client.createRoot

fun main() {
    require("./styles/index.css")

    val container = document.createElement("div")
    document.body.appendChild(container)

    val welcome = App.create()
    createRoot(container).render(welcome)
}