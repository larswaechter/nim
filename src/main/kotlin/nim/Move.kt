package nim

/**
 * A move for Nim
 *
 * @property [row] the row to remove a number of sticks from
 * @property [amount] the amount / number of sticks to remove
 */
class Move(val row: Int, val amount: Int) {
    override fun toString(): String {
        return "$row.$amount"
    }
}