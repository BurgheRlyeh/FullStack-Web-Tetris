package actions

import redux.RAction

class Pause     : RAction // Pause the game
class Resume    : RAction // Resume a paused game
class MoveLeft  : RAction // Move piece left
class MoveRight : RAction // Move piece right
class Rotate    : RAction // Rotate piece
class MoveDown  : RAction // Move piece down
class GameOver  : RAction // The game is over
class Restart   : RAction // Restart Game

fun moveRight  () = MoveRight   ()
fun moveLeft   () = MoveLeft    ()
fun rotate     () = Rotate      ()
fun moveDown   () = MoveDown    ()
fun pause      () = Pause       ()
fun resume     () = Resume      ()
fun restart    () = Restart     ()
