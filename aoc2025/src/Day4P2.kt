import java.io.File

fun main() {
    val grid = File("input_4.txt").readLines().map { it.toCharArray() }
    val rows = grid.size
    grid.maxOf { it.size }

    fun adjCount(r: Int, c: Int): Int {
        var n = 0
        for (dr in -1..1) for (dc in -1..1) {
            if (dr == 0 && dc == 0) continue
            val nr = r + dr
            val nc = c + dc
            if (nr in 0 until rows &&
                nc in 0 until grid[nr].size &&
                grid[nr][nc] == '@'
            ) n++
        }
        return n
    }

    var removedTotal = 0

    while (true) {
        val toRemove = mutableListOf<Pair<Int, Int>>()

        for (r in 0 until rows) {
            for (c in grid[r].indices) {
                if (grid[r][c] != '@') continue
                if (adjCount(r, c) < 4) toRemove += r to c
            }
        }

        if (toRemove.isEmpty()) break

        for ((r, c) in toRemove) grid[r][c] = '.'
        removedTotal += toRemove.size
    }

    println(removedTotal)
}
