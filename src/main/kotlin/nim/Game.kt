package nim

import kotlin.random.Random

/**
 * Class for user interaction via command line
 */
class Game {
    init {
        this.start()
    }

    /**
     * Start game
     */
    private fun start() {
        val action = this.askAction()
        if (action == "t") this.runTests()
        else if (action == "p") {
            val opponent = this.askOpponent()
            val board = this.askBoard()
            val starter = this.askStarter()
            val game: NimGame = if (opponent == "1") Nim(board = board, currentPlayer = starter) else NimPerfect(board = board, currentPlayer = starter)
            this.play(game)
        }
    }

    /**
     * Ask user for an action
     * Play or test
     *
     * @return action
     */
    private fun askAction(): String {
        while (true) {
            print("\nWelcome, what do you want to do?\nPlay (p) or run tests (t): ")
            val userInput = readLine()!!
            if (userInput.matches(Regex("[pt]"))) return userInput
        }
    }

    /**
     * Ask user for an opponent
     * Nim or NimPerfect
     *
     * @return opponent
     */
    private fun askOpponent(): String {
        while (true) {
            print("\nChoose your opponent\nNim (1) or NimPerfect (2): ")
            val userInput = readLine()!!
            if (userInput.matches(Regex("[12]"))) return userInput
        }
    }

    /**
     * Ask user for a board
     *
     * @return board
     */
    private fun askBoard(): IntArray {
        while (true) {
            print("\nEnter sticks per row (e.g.: 2-2-4) or create a random board (r)\nLeave empty to use the default board (3-4-5): ")
            val userInput = readLine()!!
            if (userInput.isNotEmpty()) {
                if (userInput == "r") {
                    println("\nCreating random board...")
                    return this.generateRandomBoard()
                }
                // Allow 2-5 rows with 1-7 sticks
                else if (userInput.matches(Regex("^[1-7]+(-[1-7]+){1,4}\$"))) {
                    println("\nCreating board $userInput...")
                    val sticksPerRow = userInput.split("-")
                    return sticksPerRow.map() { it.toInt() }.toIntArray()
                } else {
                    println("Invalid board!")
                }
                // Default board
            } else {
                println("\nCreating default board...")
                return intArrayOf(3, 4, 5)
            }
        }
    }

    /**
     * Ask user for a starting player
     * User or computer
     *
     * @return starting player
     */
    private fun askStarter(): Int {
        while (true) {
            print("\nWho should start? You (1) or the computer (2): ")
            val userInput = readLine()!!
            if (userInput.matches(Regex("[12]"))) return if (userInput == "1") 1 else -1
        }
    }

    /**
     * Ask user what he want's to do in his turn
     *
     * @param [game]
     * @return game with applied user's turn
     */
    private fun askTurn(game: NimGame): NimGame {
        while (true) {
            print("It's your turn, who should play your move?\nYou (1) or the computer (2) or undo a move (3): ")
            val userInput = readLine()!!
            if (userInput.matches(Regex("[123]"))) {
                when (userInput) {
                    // Human
                    "1" -> return game.move(this.askMove(game))
                    // Computer
                    "2" -> {
                        val move = game.bestMove()
                        println("\nOkay, the computer is playing for you: $move")
                        return game.move(move)
                    }
                    // Undo
                    "3" -> return askUndoMoves(game)
                }
            }
        }
    }

    /**
     * Ask user to enter a move
     *
     * @param [game] game that handles the move
     * @return move to play
     */
    private fun askMove(game: NimGame): Move {
        while (true) {
            print("\nEnter your move in a format like row.amount: ")
            val move = readLine()!!
            // Check for right format
            if (move.matches(Regex("[1-5].[1-7]"))) {
                val position = move.split(".").map { it.toInt() }
                // Check if move is allowed
                if (position[0] - 1 >= 0 && position[0] - 1 < game.board.size && position[1] > 0 && position[1] <= game.board[position[0] - 1])
                    return Move(position[0] - 1, position[1])
            }
            println("Invalid move. Please try another one!")
        }
    }

    /**
     * Ask user to undo moves
     *
     * @param [game] game that undoes the moves
     * @return game with undone moves
     */
    private fun askUndoMoves(game: NimGame): NimGame {
        if (game.history.size == 1) {
            println("\nYou can not undo a move. No move was played yet.")
            return game
        }

        while (true) {
            print("\nHow many moves do you want do undo? (0-${game.history.size - 1}): ")
            val input = readLine()!!
            if (input.matches(Regex("[0-9]+")) && input.toInt() < game.history.size) {
                println("\nOkay, I undo the last $input moves.")
                return game.undoMove(input.toInt())
            }
        }
    }

    /**
     * Play a created game
     *
     * @param [initialGame] created game to play
     */
    private fun play(initialGame: NimGame) {
        var game = initialGame

        println("\nLet's start! ${if (game.currentPlayer == 1) "You begin." else "The computer begins."}")
        println(game)

        while (true) {
            println()

            // Game finished
            if (game.isGameOver()) {
                println("Game finished. ${if (game.getWinner() == 1) "You won :)" else "You lost :("}")
                print("Leave empty and press <ENTER> to restart, undo a number of moves (u) or quit (q): ")

                val action = readLine()!!
                if (action.isEmpty()) {
                    this.start()
                    break
                } else if (action == "u") {
                    game = askUndoMoves(game)
                    println(game)
                } else if (action == "q" || action == "e") break

                // Game running
            } else {
                // 1 = Human Player; -1 = Computer
                if (game.currentPlayer == 1) {
                    game = this.askTurn(game)
                } else {
                    val move = game.bestMove()
                    game = game.move(move)
                    println("Your opponent is playing: $move")
                }
                println(game)
            }
        }
    }

    /**
     * Generate a random board of 2-5 rows and 1-7 sticks
     *
     * @return random board
     */
    private fun generateRandomBoard(): IntArray {
        var board = intArrayOf()
        for (amount in 1..Random.nextInt(2, 6)) board = board.plus(Random.nextInt(1, 8))
        return board
    }

    /**
     * Run 40 test games
     */
    private fun runTests() {
        var stats: Pair<Int, Int> = Pair(0, 0)

        for (i in 1..40) {
            // Pick random starting player and create random board
            val startingPlayer = if (Random.nextInt(100) % 2 == 0) 1 else -1
            val randomBoard = generateRandomBoard()

            // Init game -> Check if we expect a win for starting player
            var game: NimGame = Nim(board = randomBoard, currentPlayer = startingPlayer)
            val expectWin = NimPerfect.isWinningPosition(game.board)

            // Set Nim or NimPerfect as starter
            var counter = if (Random.nextInt(100) % 2 == 0) 1 else 0;

            // Simulate game
            println("\nSimulating game #${i}")
            println("Board: ${randomBoard.joinToString()}")
            println("Starting player: Player $startingPlayer")
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
}