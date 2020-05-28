package nim

/**
 * Interface for Nim
 */
interface NimGame {
    /**
     * Game board
     */
    val board: IntArray

    /**
     * History of boards
     */
    val history: List<IntArray>

    /**
     * Current player: 1 or -1
     */
    val currentPlayer: Int

    /**
     * Do move and return new game
     *
     * @param [move] move to perform
     * @return new game with applied move
     */
    fun move(move: Move): NimGame

    /**
     * Undo a number of moves
     *
     * @param [number] number of moves to undo
     * @return new game with undone moves
     */
    fun undoMove(number: Int): NimGame

    /**
     * Get best possible move
     * If player can not win we return a random move
     *
     * @return best possible or random move
     */
    fun bestMove(): Move

    /**
     * Check if no more moves are possible
     *
     * @return is game over
     */
    fun isGameOver(): Boolean = this.board.none { n -> n > 0 }

    /**
     * If the game is over, the player whose turn not is has won
     * since this player did the last / finishing move.
     *
     * @return winner of the game
     */
    fun getWinner(): Int {
        assert(this.isGameOver())
        return -this.currentPlayer
    }

    override fun toString(): String
}