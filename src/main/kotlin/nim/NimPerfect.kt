package nim

class NimPerfect(
        override val board: IntArray,
        override val history: List<IntArray> = listOf(board),
        override val currentPlayer: Int = 1) : NimGame {

    companion object {
        fun isWinningPosition(map: IntArray): Boolean = map.fold(0) { i, j -> i.xor(j) } != 0
    }

    /**
     * Do move and return new game
     *
     * @param move [Move] Move to do
     * @return NimGame
     */
    override fun move(move: Move): NimGame {
        assert(!this.isGameOver())
        assert(move.row < this.board.size && move.amount <= this.board[move.row])

        val board = this.board.clone()
        board[move.row] -= move.amount

        return NimPerfect(board, this.history.plus(board), -this.currentPlayer)
    }

    /**
     * Undo a number of moves
     *
     * @param number [Int] Number of moves to undo
     * @return NimGame
     */
    override fun undoMove(number: Int): NimGame {
        assert(number < this.history.size)
        val nextPlayer: Int = if (number % 2 == 0) this.currentPlayer else -this.currentPlayer
        return NimPerfect(this.history[this.history.size - 1 - number], this.history.subList(0, this.history.size - number), nextPlayer)
    }

    override fun bestMove(): Move {
        val possibleMoves: List<Move> = this.getPossibleMoves()
        var bestMove: Move

        // Search for best move if current position is winning position until next position is not winning position
        do {
            bestMove = possibleMoves.random()
        } while (NimPerfect.isWinningPosition(this.board) && NimPerfect.isWinningPosition(this.move(bestMove).board))

        return bestMove
    }

    fun getPossibleMoves(): List<Move> {
        val possibleMoves: MutableList<Move> = mutableListOf()

        for (rowIdx in this.board.indices) {
            for (amount in 1..this.board[rowIdx]) {
                possibleMoves.add(Move(rowIdx, amount))
            }
        }

        return possibleMoves.toList()
    }

    override fun toString(): String {
        var res: String = ""
        this.board.forEach { res += "\n" + "I ".repeat(it) }
        return res;
    }
}