package nim

/**
 * Interface for implementing Minimax algorithm in two-player zero-sum games
 */
interface Minimax<Game, Move> {
    /**
     * Evaluate game state for current player.
     *  For player +1, a higher value is better. (maximizer)
     *  For player -1, a lower value is better. (minimizer)
     *
     * @return Positive or negative integer
     */
    fun evaluate(): Int

    /**
     * Get list of all possible moves
     *
     * @return List of possible moves
     */
    fun getPossibleMoves(): List<Move>

    /**
     * Check if a player has won or no further moves are possible
     * @return If game has ended
     */
    fun isGameOver(): Boolean

    /**
     * Do move and switch current player
     */
    fun move(move: Move): Game

    /**
     * Undo a number of moves
     */
    fun undoMove(number: Int): Game

    /**
     * Pick random move from possible moves list
     *
     * @return Random move
     */
    fun getRandomMove(): Move = this.getPossibleMoves().random()

    /**
     * Minimax algorithm that finds best move
     *
     * @param game [Game] Game
     * @param depth [Int] Tree depth
     * @param maximize [Boolean] Maximize or Minimize
     * @param alpha [Float]
     * @param beta [Float]
     * @return Pair of (Move, Score)
     */
    fun minimax(
            game: Game,
            depth: Int = this.getPossibleMoves().size,
            maximize: Boolean = true,
            alpha: Float = Float.NEGATIVE_INFINITY,
            beta: Float = Float.POSITIVE_INFINITY): Pair<Move?, Float>
    {
        if (depth == 0 || (game as Minimax<Game, Move>).isGameOver())
            return Pair<Move?, Float>(null, (game as Minimax<Game, Move>).evaluate().toFloat())

        var minOrMax: Pair<Move?, Float> = Pair(null, if (maximize) alpha else beta)

        // Iterate all possible moves
        for (move in (game as Minimax<Game, Move>).getPossibleMoves()) {
            var newGame: Game = (game as Minimax<Game, Move>).move(move)
            val newAlphaBeta: Pair<Float, Float> = if(maximize) Pair(minOrMax.second, beta) else Pair(alpha, minOrMax.second)

            // Call child nodes
            val child = this.minimax(newGame, depth - 1, !maximize, newAlphaBeta.first, newAlphaBeta.second)
            val score: Pair<Move?, Float> = Pair(move, child.second)

            // Check for maximum or minimum
            if ((maximize && score.second > minOrMax.second) || (!maximize && score.second < minOrMax.second)) {
                minOrMax = score
                if (maximize && minOrMax.second >= beta) break
                else if (!maximize && minOrMax.second <= alpha) break
            }
        }
        return minOrMax
    }
}