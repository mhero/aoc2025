import java.io.File

fun main() {
    var pos = 50
    var countZero = 0

    File("input_1.txt").forEachLine { line ->
        if (line.isBlank()) return@forEachLine
        val dir = line[0]
        val dist = line.substring(1).toInt()

        pos = when (dir) {
            'L' -> (pos - dist).floorMod(100)
            'R' -> (pos + dist).floorMod(100)
            else -> error("bad input: $line")
        }

        if (pos == 0) countZero++
    }

    println(countZero)
}

fun Int.floorMod(mod: Int): Int {
    val r = this % mod
    return if (r < 0) r + mod else r
}
