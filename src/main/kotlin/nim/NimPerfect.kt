package nim

/**
 *  Implementation of Nim with XOR-Algorithm
 *
 * @property board Nim board
 * @property history history of boards
 * @property currentPlayer current player
 */
class NimPerfect(
        override val board: IntArray,
        override val history: List<IntArray> = listOf(board),
        override val currentPlayer: Int = 1) : NimGame {

    companion object {
        /**
         * Check if given board is a winning position for current player
         *
         * @param [board] board to evaluate
         * @return whether it's a winning position or not
         */
        fun isWinningPosition(board: IntArray): Boolean = board.fold(0) { i, j -> i.xor(j) } != 0
    }

    /**
     * Do move and return new game
     *
     * @param [move] move to perform
     * @return new game with applied move
     */
    override fun move(move: Move): NimGame {
        assert(!this.isGameOver())
        assert(move.row >= 0 && move.row < this.board.size && move.amount > 0 && move.amount <= this.board[move.row])

        val board = this.board.clone()
        board[move.row] -= move.amount

        return NimPerfect(board, this.history.plus(board), -this.currentPlayer)
    }

    /**
     * Undo a number of moves
     *
     * @param [number] number of moves to undo
     * @return new game with undone moves
     */
    override fun undoMove(number: Int): NimGame {
        assert(number < this.history.size)
        val nextPlayer: Int = if (number % 2 == 0) this.currentPlayer else -this.currentPlayer
        return NimPerfect(this.history[this.history.size - 1 - number], this.history.subList(0, this.history.size - number), nextPlayer)
    }

    /**
     * Get best possible move
     * If player can not win we return a random move
     *
     * @return [bestMove] best possible or random move
     */
    override fun bestMove(): Move {
        val possibleMoves = this.getPossibleMoves()
        if (!isWinningPosition(this.board)) return possibleMoves.random()

        while (true) {
            val move = possibleMoves.random()
            if (!isWinningPosition(this.move(move).board)) return move
        }
    }

    /**
     * Get a list of all possible moves
     *
     * @return possible moves
     */
    private fun getPossibleMoves(): List<Move> {
        val possibleMoves: MutableList<Move> = mutableListOf()

        for (rowIdx in this.board.indices) {
            for (amount in 1..this.board[rowIdx]) {
                possibleMoves.add(Move(rowIdx, amount))
            }
        }

        return possibleMoves.toList()
    }

    override fun toString(): String {
        var s = ""
        this.board.forEachIndexed { index, i -> s += "\n (${index + 1})\t" + "I ".repeat(i) }
        return s
    }
}