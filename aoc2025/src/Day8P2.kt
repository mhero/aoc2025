import java.io.File
import java.math.BigInteger

data class Edge(val a: Int, val b: Int, val dist2: Long)

class UnionFindToo(n: Int) {
    private val parent = IntArray(n) { it }
    private val size = IntArray(n) { 1 }
    private var components = n

    fun find(x: Int): Int {
        var v = x
        while (parent[v] != v) {
            parent[v] = parent[parent[v]]
            v = parent[v]
        }
        return v
    }

    // returns true if merged distinct components
    fun union(a: Int, b: Int): Boolean {
        var ra = find(a)
        var rb = find(b)
        if (ra == rb) return false
        if (size[ra] < size[rb]) {
            val t = ra; ra = rb; rb = t
        }
        parent[rb] = ra
        size[ra] += size[rb]
        components--
        return true
    }

    fun done(): Boolean = components == 1
    fun remaining(): Int = components
}

fun main() {
    val lines = File("input_8.txt").readLines().map { it.trim() }.filter { it.isNotEmpty() }
    val pts = lines.map {
        val p = it.split(",").map { x -> x.trim().toLong() }
        if (p.size != 3) throw IllegalArgumentException("Bad line: '$it'")
        Triple(p[0], p[1], p[2])
    }

    val n = pts.size
    if (n < 2) {
        println("Need at least 2 points")
        return
    }

    val edges = ArrayList<Edge>((n * (n - 1)) / 2)
    for (i in 0 until n) {
        val (xi, yi, zi) = pts[i]
        for (j in i + 1 until n) {
            val (xj, yj, zj) = pts[j]
            val dx = xi - xj
            val dy = yi - yj
            val dz = zi - zj
            // compute squared distance in Long; check overflow possibility (rare for AoC inputs)
            val d2 = dx * dx + dy * dy + dz * dz
            edges.add(Edge(i, j, d2))
        }
    }

    edges.sortWith(compareBy<Edge> { it.dist2 }.thenBy { it.a }.thenBy { it.b })

    val uf = UnionFindToo(n)
    var lastA = -1
    var lastB = -1

    for (e in edges) {
        if (uf.union(e.a, e.b)) {
            lastA = e.a
            lastB = e.b
            if (uf.done()) break
        }
    }

    if (lastA == -1 || lastB == -1) {
        println("No merging edge found. Components remaining: ${uf.remaining()}")
        return
    }

    val x1 = pts[lastA].first
    val x2 = pts[lastB].first
    val product = BigInteger.valueOf(x1).multiply(BigInteger.valueOf(x2))
    println("Last merge between indices $lastA and $lastB => x's: $x1, $x2")
    println(product)
}
