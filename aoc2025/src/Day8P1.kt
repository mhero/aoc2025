import java.io.File
import java.math.BigInteger


class UnionFind(n: Int) {
    private val parent = IntArray(n) { it }
    private val size = IntArray(n) { 1 }

    fun find(x: Int): Int {
        var v = x
        while (parent[v] != v) {
            parent[v] = parent[parent[v]]
            v = parent[v]
        }
        return v
    }

    fun union(a: Int, b: Int) {
        var ra = find(a)
        var rb = find(b)
        if (ra == rb) return
        if (size[ra] < size[rb]) {
            val t = ra; ra = rb; rb = t
        }
        parent[rb] = ra
        size[ra] += size[rb]
    }

    fun componentSizes(): List<Int> {
        val rootCount = mutableMapOf<Int, Int>()
        for (i in parent.indices) {
            val r = find(i)
            rootCount[r] = rootCount.getOrDefault(r, 0) + 1
        }
        return rootCount.values.toList()
    }
}

fun main() {
    val lines = File("input_8.txt").readLines()
        .map { it.trim() }
        .filter { it.isNotEmpty() }

    val pts = lines.map {
        val parts = it.split(',').map { p -> p.trim().toLong() }
        Triple(parts[0], parts[1], parts[2])
    }

    val n = pts.size
    if (n < 1) {
        println("0")
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
            val d2 = dx * dx + dy * dy + dz * dz
            edges.add(Edge(i, j, d2))
        }
    }

    edges.sortWith(compareBy<Edge> { it.dist2 }.thenBy { it.a }.thenBy { it.b })

    val take = minOf(1000, edges.size)
    val uf = UnionFind(n)
    for (k in 0 until take) {
        val e = edges[k]
        uf.union(e.a, e.b)
    }

    val sizes = uf.componentSizes().sortedDescending()
    val top3 = sizes.take(3) + List(maxOf(0, 3 - sizes.size)) { 1 } // pad with 1s if <3 components
    var product = BigInteger.ONE
    for (s in top3) product = product.multiply(BigInteger.valueOf(s.toLong()))

    println(product)
}
