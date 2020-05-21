package nim

class Move(val row: Int, val amount: Int) {
    override fun toString(): String {
        return "$row.$amount"
    }
}