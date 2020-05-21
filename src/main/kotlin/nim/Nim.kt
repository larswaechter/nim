package nim

class Nim(
        override val board: IntArray,
        override val history: List<IntArray> = listOf(board),
        override val currentPlayer: Int = 1) : NimGame, Minimax<NimGame, Move> {

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

        return Nim(board, this.history.plus(board), -this.currentPlayer)
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
        return Nim(this.history[this.history.size - 1 - number], this.history.subList(0, this.history.size - number), nextPlayer)
    }

    override fun bestMove(): Move {
        val move: Pair<Move?, Float> = this.minimax(game = this, maximize = this.currentPlayer == 1)
        // return move.first?:this.getRandomMove()
        return if (this.currentPlayer * move.second <= 0) this.getRandomMove() else move.first!!
    }

    override fun getPossibleMoves(): List<Move> {
        val possibleMoves: MutableList<Move> = mutableListOf()

        for (rowIdx in this.board.indices) {
            for (amount in 1..this.board[rowIdx]) {
                possibleMoves.add(Move(rowIdx, amount))
            }
        }

        return possibleMoves.toList()
    }

    override fun evaluate(): Int = if (this.isGameOver()) -this.currentPlayer * 1 else 0

    override fun isGameOver(): Boolean = this.board.none { n -> n > 0 }

    override fun toString(): String {
        var res: String = ""
        this.board.forEach { res += "\n" + "I ".repeat(it) }
        return res;
    }
}