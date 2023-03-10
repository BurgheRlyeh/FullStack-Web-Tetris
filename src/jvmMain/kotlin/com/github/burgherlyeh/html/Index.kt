package com.github.burgherlyeh.html

import kotlinx.html.*

fun HTML.index() {
    head {
        title("FullStack Web Tetris")
    }
    body {
        div {
            id = "root"
        }
        script(src = "/static/Full-Stack-Web-Tetris.js") {}
    }
}