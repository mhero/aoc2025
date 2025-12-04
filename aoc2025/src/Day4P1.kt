import java.io.File

fun main() {
    val lines = File("input_4.txt").readLines()
    var accessible = 0
    val rows = lines.size

    for (r in 0 until rows) {
        val row = lines[r]
        for (c in row.indices) {
            if (row[c] != '@') continue

            var adj = 0
            for (dr in -1..1) for (dc in -1..1) {
                if (dr == 0 && dc == 0) continue
                val nr = r + dr
                val nc = c + dc

                if (nr in 0 until rows &&
                    nc in 0 until lines[nr].length &&
                    lines[nr][nc] == '@'
                ) adj++
            }

            if (adj < 4) accessible++
        }
    }

    println(accessible)
}
