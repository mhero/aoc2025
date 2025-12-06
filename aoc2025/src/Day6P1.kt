
import java.math.BigInteger
import java.io.File

fun main() {
    val lines = File("input_6.txt").readLines()
    if (lines.isEmpty()) {
        println(0)
        return
    }

    val maxLen = lines.maxOf { it.length }
    val padded = lines.map { it.padEnd(maxLen, ' ') }

    val isSep = BooleanArray(maxLen) { col ->
        padded.all { it[col] == ' ' }
    }

    val spans = mutableListOf<IntRange>()
    var col = 0
    while (col < maxLen) {
        if (!isSep[col]) {
            val start = col
            while (col < maxLen && !isSep[col]) col++
            spans.add(start until col)
        } else col++
    }

    var grandTotal = BigInteger.ZERO

    for (span in spans) {
        val cells = padded.map { it.substring(span.start, span.endInclusive + 1).trim() }
        val op = cells.last().firstOrNull() ?: continue

        val numbers = cells.dropLast(1)
            .mapNotNull { s -> if (s.isBlank()) null else BigInteger(s) }

        if (numbers.isEmpty()) continue

        val result = when (op) {
            '+' -> numbers.fold(BigInteger.ZERO) { a, b -> a + b }
            '*' -> numbers.fold(BigInteger.ONE) { a, b -> a * b }
            else -> continue
        }

        grandTotal += result
    }

    println(grandTotal)
}
