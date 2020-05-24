package nim

import kotlin.random.Random

fun main() {
    // runTests()
    // return
    // val game: NimGame = Nim(intArrayOf(1,2,3,4,5))
    // println(game.bestMove())
    // val s = System.currentTimeMillis()
    // println(100 + Random.nextInt(1, 50))
    // println(System.currentTimeMillis() - s)

    startGame()
}

fun startGame() {
    /**
     * Choose opponent
     */
    var opponentInput = "";
    do {
        println("Choose your opponent Nim (1) or NimPerfect (2) or run tests (t):")
        opponentInput = readLine()!!
    } while (!opponentInput.matches(Regex("[12t]")))

    if (opponentInput == "t") {
        runTests()
        return
    }

    val opponent = opponentInput.toInt()

    /**
     * Generate board
     */
    var board: IntArray = intArrayOf(3, 4, 5);
    while (true) {
        println("Enter sticks per row (Default: 3-4-5):")
        val boardInput = readLine()!!
        if (boardInput.isNotEmpty()) {
            board = if (boardInput == "r") {
                createRandomBoard()
            } else {
                // TODO: Match boardInput.matches(Regex("^[0-9]+(-[0-9]+)?+"))
                val sticksPerRow: Array<String> = boardInput.split("-").toTypedArray()
                sticksPerRow.map() { it.toInt() }.toIntArray()
            }
            break;
        } else break;
    }

    var game: NimGame = if (opponent == 1) Nim(board) else NimPerfect(board)

    println(game)

    gameLoop@
    while (true) {
        println()

        // Game finished
        if (game.isGameOver()) {
            println("Game finished. ${if (game.getWinner() == 1) "You won!" else "You lost!"}")
            println("Leave empty and press <Enter> to restart or enter \"u\" to undo a number of moves:")

            val action = readLine()!!
            if (action.isEmpty()) {
                startGame()
            } else if (action == "u") {
                game = undoMoves(game)
                println(game)
            } else if (action == "e" || action == "q") break

            // Game running
        } else {
            // Human player
            if (game.currentPlayer == 1) {
                println("Who should play your move? You (1) or NPC (2) or undo (3):")
                when (readLine()!!) {
                    // Human
                    "1" -> {
                        println("Enter your move row.amount:")
                        val move = readLine()!!.split(".")
                        game = game.move(Move(move[0].toInt() - 1, move[1].toInt()))
                    }
                    // NPC
                    "2" -> {
                        game = game.move(game.bestMove())
                    }
                    // Undo
                    "3" -> game = undoMoves(game)
                    "e" -> break@gameLoop
                    "q" -> break@gameLoop
                    else -> continue@gameLoop
                }
                // NPC
            } else {
                println("Your opponent is moving...")
                game = game.move(game.bestMove())
            }
            println(game)
        }
    }
}

fun runTests() {
    var stats: Pair<Int, Int> = Pair(0, 0)

    for (i in 1..40) {
        // Pick random starting player and create random board
        val startingPlayer = if (Random.nextInt(100) % 2 == 0) 1 else -1
        val randomBoard = createRandomBoard()

        // Init game -> Check if we expect a win for starting player
        var game: NimGame = Nim(board = randomBoard, currentPlayer = startingPlayer)
        val expectWin = NimPerfect.isWinningPosition(game.board)

        // Set Nim or NimPerfect as starter
        var counter = if (startingPlayer == 1) 1 else 0;

        // Simulate game
        println("\nStarting test game #${i} - This may take a while...")
        println("Board: ${randomBoard.joinToString()}")
        println("Expected winner: Player ${if (expectWin) startingPlayer else -startingPlayer}")

        // We alternate between Nim and NimPerfect after each move
        while (!game.isGameOver()) {
            if (counter % 2 == 0) {
                game = Nim(board = game.board, currentPlayer = game.currentPlayer)
                game = game.move(game.bestMove())
            } else {
                game = NimPerfect(board = game.board, currentPlayer = game.currentPlayer)
                game = game.move(game.bestMove())
            }
            counter++
        }

        // Update stats
        stats = if (game.getWinner() == 1) Pair(stats.first + 1, stats.second) else Pair(stats.first, stats.second + 1)

        // Check here, if the right player has won
        if ((expectWin && startingPlayer != game.getWinner())) {
            println("Expected player $startingPlayer to win but player ${game.getWinner()} won! Stopping test.")
            break
        } else if (!expectWin && startingPlayer == game.getWinner()) {
            println("Expected player ${-startingPlayer} to win but player ${game.getWinner()} won! Stopping test.")
            break
        } else {
            println("Player ${game.getWinner()} won!")
        }
    }

    println("\nTest finished: Wins: Player 1 = ${stats.first} / Player -1 = ${stats.second}")
}

fun undoMoves(game: NimGame): NimGame {
    var input = ""
    while (true) {
        println("How many moves do you want do undo? Max. ${game.history.size - 1}:")
        input = readLine()!!
        if (input.matches(Regex("[0-9]+")) && input.toInt() < game.history.size) break
    }
    return game.undoMove(input.toInt())
}

fun createRandomBoard(): IntArray {
    var board = intArrayOf()
    for (amount in 1..Random.nextInt(2, 6)) board = board.plus(Random.nextInt(1, 8))
    return board
}