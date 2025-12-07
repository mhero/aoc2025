import java.io.File
import java.util.ArrayDeque

fun main() {
    val lines = File("input_7.txt").readLines()
    val rows = lines.size
    val cols = lines.maxOf { it.length }
    val grid = Array(rows) { r -> lines[r].padEnd(cols, '.').toCharArray() }

    var sr = -1
    var sc = -1

    for (r in 0 until rows) {
        val idx = grid[r].indexOf('S')
        if (idx >= 0) {
            sr = r
            sc = idx
            break
        }
    }

    val q = ArrayDeque<Pair<Int,Int>>()
    q.add(Pair(sr + 1, sc))

    var splits = 0L
    val visited = HashSet<Pair<Int,Int>>()   // prevent infinite loops

    while (q.isNotEmpty()) {
        val (r, c) = q.removeFirst()

        if (r !in 0 until rows || c !in 0 until cols) continue
        val key = Pair(r, c)
        if (!visited.add(key)) continue   // already processed this beam path

        when (grid[r][c]) {
            '.', 'S' -> {
                q.add(Pair(r + 1, c))
            }
            '^' -> {
                splits++
                if (c - 1 >= 0) q.add(Pair(r, c - 1))
                if (c + 1 < cols) q.add(Pair(r, c + 1))
            }
            else -> {
                q.add(Pair(r + 1, c))
            }
        }
    }

    println(splits)
}
