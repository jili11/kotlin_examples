fun evaluate(code: String, guess: String): Pair<Int, Int> {

    var rightPosition = 0
    var wrongPosition = 0
    var code2 = code
    var guess2 = guess

    for (x in 0..3) {
        if (code2[x] == guess[x]) {
            rightPosition++
            code2 = code2.replaceRange(x, x + 1, "-")
            guess2 = guess2.replaceRange(x, x + 1, "+")
        }
    }
    for (ch in guess2) {
        if (code2.indexOf(ch) != -1) {
            wrongPosition++
            code2 = code2.replaceRange(code2.indexOf(ch), code2.indexOf(ch) + 1, "-")
        }
    }

    println(rightPosition.toString() + ", " + wrongPosition.toString())
    return Pair(rightPosition, wrongPosition)

}

infix fun <T> T.eq(other: T) {
    if (this == other) println("OK")
    else println("Error: $this != $other")
}

fun main(args: Array<String>) {
    val s1: String? = null
    val s2: String? = ""
    s1.isEmptyOrNull() eq true
    s2.isEmptyOrNull() eq true

    val s3 = "   "
    s3.isEmptyOrNull() eq false
}

private fun String?.isEmptyOrNull(): Boolean {
    return (this ?: "").length > 0
}


data class Evaluation(val rightPosition: Int, val wrongPosition: Int)

fun evaluateGuess(secret: String, guess: String): Evaluation {

    val rightPositions = secret.zip(guess).count { p -> p.first == p.second }

    val commonLetters = "ABCDEF".sumBy { ch ->

        Math.min(secret.count { ch1 -> ch1 == ch }, guess.count { ch2 -> ch2 == ch })
    }
    return Evaluation(rightPositions, commonLetters - rightPositions)
}

