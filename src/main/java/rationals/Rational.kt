package rationals

import java.math.BigInteger

infix fun BigInteger.divBy(r2: BigInteger): Rational = Rational(this, r2)

infix fun Long.divBy(l2: Long): Rational = Rational(this.toBigInteger(), l2.toBigInteger())

infix fun Int.divBy(i2: Int): Rational = Rational(this.toBigInteger(), i2.toBigInteger())

fun String.toRational(): Rational{
    val numbers = this.split("/")

    when {
        numbers.size == 1 -> return Rational(numbers[0].toBigInteger(), BigInteger.ONE)
        else -> return Rational(numbers[0].toBigInteger(), numbers[1].toBigInteger())
    }
}


class Rational(val n: BigInteger, val d: BigInteger) : Comparable<Rational> {

    override fun compareTo(other: Rational): Int =
        this.n.times(other.d).compareTo(other.n.times(this.d))

    override fun equals(other: Any?): Boolean =
        if (other == null) false
        else
        this.compareTo(other as Rational) == 0

    operator fun minus(other: Rational): Rational =
        simplify((this.n.times(other.d).minus(other.n.times(this.d))).divBy(d.times(other.d)))

    operator fun plus(other: Rational): Rational =
        simplify((this.n.times(other.d).plus(other.n.times(this.d))).divBy(d.times(other.d)))

    operator fun times(other: Rational): Rational =
        simplify((this.n.times(other.n)).divBy(d.times(other.d)))

    operator fun div(other: Rational): Rational =
        simplify((this.n.times(other.d)).divBy(d.times(other.n)))


    operator fun unaryMinus(): Rational = Rational(n.negate(), d)


    override fun toString(): String {
        return when {
            d == 1.toBigInteger() || n.rem(d) == 0.toBigInteger() -> n.div(d).toString()
            else -> {
                val r = simplify(this)

                if(r.d < 0.toBigInteger() || (r.n < 0.toBigInteger() && r.d < 0.toBigInteger())){
                    formatRational(Rational(r.n.negate(), r.d.negate()))
                } else {
                    formatRational(Rational(r.n, r.d))
                }
            }
        }
    }

    fun formatRational(r: Rational) : String = r.n.toString() + "/" + r.d.toString()

    fun maxCommonDenominator(n1: BigInteger, n2: BigInteger): BigInteger =
        if (n2 != 0.toBigInteger()) maxCommonDenominator(n2, n1 % n2) else n1

    fun simplify(r1: Rational): Rational {
        val maxCommonDenominator = maxCommonDenominator(r1.n, r1.d).abs()
        return Rational(r1.n.div(maxCommonDenominator), r1.d.div(maxCommonDenominator))
    }

    override fun hashCode(): Int {
        var result = n.hashCode()
        result = 31 * result + d.hashCode()
        return result
    }


}

fun main() {
    val half = 1 divBy 2
    val third = 1 divBy 3

    val sum: Rational = half + third
    println(5 divBy 6 == sum)

    val difference: Rational = half - third
    println(1 divBy 6 == difference)

    val product: Rational = half * third
    println(1 divBy 6 == product)

    val quotient: Rational = half / third
    println(3 divBy 2 == quotient)

    val negation: Rational = -half
    println(-1 divBy 2 == negation)

    println((2 divBy 1).toString() == "2")
    println((-2 divBy 4).toString() == "-1/2")
    println("117/1098".toRational().toString() == "13/122")

    val twoThirds = 2 divBy 3
    println(half < twoThirds)

    println(half in third..twoThirds)

    println(2000000000L divBy 4000000000L == 1 divBy 2)

    println("912016490186296920119201192141970416029".toBigInteger() divBy
            "1824032980372593840238402384283940832058".toBigInteger() == 1 divBy 2)
}





