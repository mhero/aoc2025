import java.io.File
import java.math.BigInteger

fun main() {
    val input = File("input_2.txt").readText().trim()

    if (input.isEmpty()) {
        println("0")
        return
    }

    var total = BigInteger.ZERO

    val ranges = input.split(",").map { it.trim() }.filter { it.isNotEmpty() }
    for (r in ranges) {
        val parts = r.split("-")
        if (parts.size != 2) continue
        val L = BigInteger(parts[0])
        val R = BigInteger(parts[1])
        if (L > R) continue

        val maxFullDigits = R.toString().length
        val maxK = maxFullDigits / 2

        for (k in 1..maxK) {
            val pow10k = BigInteger.TEN.pow(k)
            val multiplier = pow10k.add(BigInteger.ONE) // (10^k + 1)
            val aLower = BigInteger.TEN.pow(k - 1) // smallest A with k digits
            val aUpper = BigInteger.TEN.pow(k).subtract(BigInteger.ONE) // largest A with k digits

            // A_min = max(aLower, ceil(L / multiplier))
            val ceilDiv = (L.add(multiplier).subtract(BigInteger.ONE)).divide(multiplier)
            val aMin = if (ceilDiv > aLower) ceilDiv else aLower

            // A_max = min(aUpper, floor(R / multiplier))
            val floorDiv = R.divide(multiplier)
            val aMax = if (floorDiv < aUpper) floorDiv else aUpper

            if (aMin <= aMax) {
                val count = aMax.subtract(aMin).add(BigInteger.ONE) // number of A's
                // sumA = (aMin + aMax) * count / 2
                val sumA = (aMin.add(aMax)).multiply(count).divide(BigInteger.valueOf(2))
                // contribution = multiplier * sumA
                val contrib = multiplier.multiply(sumA)
                total = total.add(contrib)
            }
        }
    }

    println(total.toString())
}
