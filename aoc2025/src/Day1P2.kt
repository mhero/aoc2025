import java.io.File

fun main() {
    var pos = 50
    var totalZeroHits = 0L

    File("input_1.txt").forEachLine { line ->
        if (line.isBlank()) return@forEachLine
        val dir = line[0]
        val dist = line.substring(1).toLong()

        totalZeroHits += countZeroHits(pos, dist, dir)

        pos = when (dir) {
            'L' -> (pos - dist).mod100()
            'R' -> (pos + dist).mod100()
            else -> error("bad input: $line")
        }
    }

    println(totalZeroHits)
}

fun Long.mod100(): Int {
    val r = (this % 100).toInt()
    return if (r < 0) r + 100 else r
}

fun countZeroHits(start: Int, dist: Long, dir: Char): Long {
    val raw = if (dir == 'R') (100 - start) % 100 else start % 100
    val first = if (raw == 0) 100L else raw.toLong()
    if (first > dist) return 0L
    return 1L + (dist - first) / 100L
}
