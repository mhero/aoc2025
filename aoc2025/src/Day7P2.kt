
import java.io.File
import java.math.BigInteger
import java.util.ArrayDeque

fun main() {
    val lines = File("input_7.txt").readLines()
    if (lines.isEmpty()) {
        println(0)
        return
    }

    val rows = lines.size
    val cols = lines.maxOf { it.length }
    val grid = Array(rows) { r -> lines[r].padEnd(cols, '.').toCharArray() }

    // find S
    var sr = -1
    var sc = -1
    for (r in 0 until rows) {
        val c = grid[r].indexOf('S')
        if (c >= 0) { sr = r; sc = c; break }
    }
    if (sr == -1) {
        println(0)
        return
    }

    // counts[r][c] = number of timelines currently queued at cell (r,c)
    val counts = Array(rows) { Array(cols) { BigInteger.ZERO } }
    val q = ArrayDeque<Pair<Int,Int>>()

    // start the single particle immediately below S
    val startR = sr + 1
    if (startR !in 0 until rows) {
        // started already out -> 1 timeline that left immediately
        println(1)
        return
    }
    counts[startR][sc] = BigInteger.ONE
    q.add(Pair(startR, sc))

    var exited = BigInteger.ZERO

    while (q.isNotEmpty()) {
        val (r, c) = q.removeFirst()
        var amt = counts[r][c]
        if (amt == BigInteger.ZERO) continue
        // consume
        counts[r][c] = BigInteger.ZERO

        // ensure still in bounds (defensive)
        if (r !in 0 until rows || c !in 0 until cols) {
            exited = exited.add(amt)
            continue
        }

        when (grid[r][c]) {
            '.', 'S' -> {
                val nr = r + 1
                val nc = c
                if (nr !in 0 until rows) {
                    exited = exited.add(amt)
                } else {
                    val before = counts[nr][nc]
                    counts[nr][nc] = before.add(amt)
                    if (before == BigInteger.ZERO) q.add(Pair(nr, nc))
                }
            }
            '^' -> {
                // split: send amt to left and right at same row
                val leftC = c - 1
                val rightC = c + 1
                if (leftC < 0) {
                    exited = exited.add(amt)
                } else {
                    val before = counts[r][leftC]
                    counts[r][leftC] = before.add(amt)
                    if (before == BigInteger.ZERO) q.add(Pair(r, leftC))
                }
                if (rightC >= cols) {
                    exited = exited.add(amt)
                } else {
                    val before = counts[r][rightC]
                    counts[r][rightC] = before.add(amt)
                    if (before == BigInteger.ZERO) q.add(Pair(r, rightC))
                }
            }
            else -> {
                // treat unknown chars as empty / pass-through
                val nr = r + 1
                val nc = c
                if (nr !in 0 until rows) {
                    exited = exited.add(amt)
                } else {
                    val before = counts[nr][nc]
                    counts[nr][nc] = before.add(amt)
                    if (before == BigInteger.ZERO) q.add(Pair(nr, nc))
                }
            }
        }
    }

    println(exited)
}
