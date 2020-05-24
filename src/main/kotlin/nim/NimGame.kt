package nim

interface NimGame {
    // Game board
    val board: IntArray

    // History of boards
    val history: List<IntArray>

    // Current player: 1 or -1
    val currentPlayer: Int

    fun move(move: Move): NimGame
    fun undoMove(number: Int): NimGame
    fun bestMove(): Move

    fun isGameOver(): Boolean = this.board.none { n -> n > 0 }

    /**
     * If the game is over, the player whose turn not is, has won
     * since this player did the last / finishing move.
     *
     * @return Player of the game
     */
    fun getWinner(): Int {
        assert(this.isGameOver())
        return -this.currentPlayer
    }

    override fun toString(): String
}