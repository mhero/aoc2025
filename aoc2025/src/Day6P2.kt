import java.io.File
import java.math.BigInteger

fun main() {
    val lines = File("input_6.txt").readLines()
    if (lines.isEmpty()) {
        println(0)
        return
    }

    val maxLen = lines.maxOf { it.length }
    val padded = lines.map { it.padEnd(maxLen, ' ') }

    // Separator columns = all-space
    val isSep = BooleanArray(maxLen) { col ->
        padded.all { it[col] == ' ' }
    }

    // Detect problem spans
    val spans = mutableListOf<IntRange>()
    var c = 0
    while (c < maxLen) {
        if (!isSep[c]) {
            val start = c
            while (c < maxLen && !isSep[c]) c++
            spans.add(start until c)
        } else c++
    }

    var total = BigInteger.ZERO

    for (span in spans) {
        // Bottom row operator
        val opCell = padded.last()
            .substring(span.start, span.endInclusive + 1)
            .trim()

        if (opCell.isEmpty()) continue
        val op = opCell.first()

        // Build numbers column-wise (right→left)
        val numbers = mutableListOf<BigInteger>()

        // For each column in the span, right→left
        for (col in span.endInclusive downTo span.start) {
            // Gather this column's characters across all non-operator rows
            val digitStr = buildString {
                for (row in 0 until padded.size - 1) {
                    val ch = padded[row][col]
                    if (ch != ' ') append(ch)
                }
            }
            if (digitStr.isNotEmpty()) {
                numbers.add(BigInteger(digitStr))
            }
        }

        if (numbers.isEmpty()) continue

        val result = when (op) {
            '+' -> numbers.fold(BigInteger.ZERO, BigInteger::add)
            '*' -> numbers.fold(BigInteger.ONE, BigInteger::multiply)
            else -> continue
        }

        total += result
    }

    println(total)
}
