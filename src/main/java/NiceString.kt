import org.junit.Assert

fun String.isNice(): Boolean {
    val doesNotContainsBuBaBe = !this.contains("bu") && !this.contains("ba") && !this.contains("be")
    val containsAtLeastThreeVowels = this.count { "aeiou".contains(it) } > 2
    var containsADoubleLetterFollowingEachOther = false
    this.forEachIndexed { index, element ->
        if (index != 0) {
            if (this.get(index - 1) == element) {
                containsADoubleLetterFollowingEachOther = true
            }
        }
    }
    return listOf(doesNotContainsBuBaBe, containsAtLeastThreeVowels, containsADoubleLetterFollowingEachOther)
        .filter { it }
        .count() >= 2

}

fun main() {

    testNiceString("ijao", true)
    testNiceString("aza", false)
    testNiceString("abaca", false)
    testNiceString("baaa", true)
    testNiceString("aaab", true)
    testNiceString("geaa", true)
    testNiceString("ynzz", true)
    testNiceString("ijao", true)
    testNiceString("nn", true)
    testNiceString("zuu", true)
    testNiceString("uaa", true)
    testNiceString("upui", true)
    testNiceString("oouh", true)
    testNiceString("wddf", true)
    testNiceString("baii", true)
    testNiceString("obee", true)
    testNiceString("beiuu", true)
    testNiceString("uyyxqptkvbtz", true)
    testNiceString("limseelx", true)
    testNiceString("zwhueqe", true)
    testNiceString("iwuvevd", true)
    testNiceString("qcdpogyeti", true)
    testNiceString("ygmuuyuj", true)
    testNiceString("cuimjyyakh", true)
    testNiceString("eufalmmwwbnid", true)
    testNiceString("kbzstzwhjeestb", true)
    testNiceString("rdfieknqrwxx", true)
    testNiceString("mzhevzkmmz", true)
    testNiceString("mzhevzkmmz", true)
    testNiceString("jootdvhbesdns", true)
    testNiceString("crncuotgburrcv", true)
    testNiceString("burppqqeivsrw", true)
    testNiceString("", false)
    testNiceString("hfrcnykh", false)
    testNiceString("qc", false)
    testNiceString("ymsetecw", false)
    testNiceString("bei", false)
    testNiceString("mbalqw", false)
    testNiceString("bekqe", false)
    testNiceString("luosbaqzdh", false)
    testNiceString("zcgsdbuxeo", false)
    testNiceString("bukipcmju", false)
    testNiceString("sisxxjwlkbu", false)
    testNiceString("bawbxffum", false)
    testNiceString("bbau", false)
    testNiceString("ax", false)
    testNiceString("baa", false)
    testNiceString("aebe", false)
    testNiceString("bbau", false)
    testNiceString("uibe", false)
    testNiceString("srxn", false)
    testNiceString("wvad", false)

}

private fun testNiceString(string: String, expected: Boolean) {
    Assert.assertEquals("Wrong result for \"$string\".isNice()", expected, string.isNice())

}

