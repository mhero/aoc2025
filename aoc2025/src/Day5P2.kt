import java.io.File

fun main() {
    val lines = File("input_5.txt").readLines()
    val blank = lines.indexOfFirst { it.isBlank() }

    val ranges = lines.take(blank)
        .map {
            val (a, b) = it.split("-").map(String::toLong)
            a to b
        }
        .sortedBy { it.first }

    // merge
    val merged = mutableListOf<Pair<Long, Long>>()
    for ((start, end) in ranges) {
        if (merged.isEmpty() || start > merged.last().second + 1) {
            merged += start to end
        } else {
            val last = merged.last()
            merged[merged.lastIndex] = last.first to maxOf(last.second, end)
        }
    }

    // sum total fresh IDs
    val total = merged.sumOf { (s, e) -> e - s + 1 }

    println(total)
}
