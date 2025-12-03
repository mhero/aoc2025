import java.io.File

fun main() {
    val lines = File("input_3.txt").readLines()

    var total = 0
    for (line in lines) {
        var best = 0
        val digits = line.map { it.digitToInt() }

        for (i in digits.indices) {
            for (j in i+1 until digits.size) {
                val v = digits[i] * 10 + digits[j]
                if (v > best) best = v
            }
        }

        total += best
    }

    println(total)
}
