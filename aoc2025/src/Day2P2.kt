import java.io.File
import java.math.BigInteger

fun main() {
    val input = File("input_2.txt").readText().trim()
    if (input.isEmpty()) {
        println("0")
        return
    }

    val ranges = input.split(",").map { it.trim() }.filter { it.isNotEmpty() }

    val invalids = HashSet<BigInteger>()

    for (r in ranges) {
        val parts = r.split("-")
        if (parts.size != 2) continue
        val L = BigInteger(parts[0])
        val R = BigInteger(parts[1])
        if (L > R) continue

        val maxDigits = R.toString().length
        // k = digits in the repeated block
        for (k in 1..maxDigits) {
            // t = repetition count, at least 2
            var t = 2
            while (k * t <= maxDigits) {
                val kt = k * t
                // M = (10^(k*t) - 1) / (10^k - 1)
                val ten = BigInteger.TEN
                val powK = ten.pow(k)
                val powKt = ten.pow(kt)
                val numerator = powKt.subtract(BigInteger.ONE)
                val denominator = powK.subtract(BigInteger.ONE)
                val mult = numerator.divide(denominator)

                // A digit bounds
                val aMinDigits = if (k == 1) BigInteger.ONE else ten.pow(k - 1)
                val aMaxDigits = ten.pow(k).subtract(BigInteger.ONE)

                // A range from L <= A * mult <= R  =>  ceil(L/mult) .. floor(R/mult)
                val ceilA = (L.add(mult).subtract(BigInteger.ONE)).divide(mult)
                val floorA = R.divide(mult)

                val aMin = if (ceilA > aMinDigits) ceilA else aMinDigits
                val aMax = if (floorA < aMaxDigits) floorA else aMaxDigits

                if (aMin <= aMax) {
                    var A = aMin
                    while (A <= aMax) {
                        val aStr = A.toString()
                        // ensure A has exactly k digits (leading zeros not allowed)
                        if (aStr.length == k) {
                            // check A is primitive (its decimal string is NOT a repetition of a smaller block)
                            if (isPrimitive(aStr)) {
                                val value = A.multiply(mult)
                                invalids.add(value)
                            }
                        }
                        A = A.add(BigInteger.ONE)
                    }
                }

                t++
            }
        }
    }

    // sum all unique invalid IDs
    var total = BigInteger.ZERO
    for (v in invalids) total = total.add(v)
    println(total.toString())
}

// returns true if s is not composed of repeating smaller substring
fun isPrimitive(s: String): Boolean {
    val n = s.length
    for (d in 1..n / 2) {
        if (n % d != 0) continue
        val part = s.substring(0, d)
        val builder = StringBuilder()
        repeat(n / d) { builder.append(part) }
        if (builder.toString() == s) return false
    }
    return true
}
