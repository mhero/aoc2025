import java.io.File

fun pickMaxSubsequence(s: String, k: Int): String {
    val n = s.length
    val result = StringBuilder()
    var start = 0

    repeat(k) {
        val need = k - result.length - 1
        val end = n - need - 1

        var bestDigit = '0'
        var bestIdx = start

        for (i in start..end) {
            val d = s[i]
            if (d > bestDigit) {
                bestDigit = d
                bestIdx = i
            }
        }

        result.append(bestDigit)
        start = bestIdx + 1
    }

    return result.toString()
}

fun main() {
    val lines = File("input_3.txt").readLines()

    var total = 0L
    for (line in lines) {
        val best = pickMaxSubsequence(line, 12)
        total += best.toLong()
    }

    println(total)
}
