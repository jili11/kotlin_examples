package games.gameOfFifteen

/*
 * This function should return the parity of the permutation.
 * true - the permutation is even
 * false - the permutation is odd
 * https://en.wikipedia.org/wiki/Parity_of_a_permutation

 * If the game of fifteen is started with the wrong parity, you can't get the correct result
 *   (numbers sorted in the right order, empty cell at last).
 * Thus the initial permutation should be correct.
 */
fun isEven(permutation: List<Int>): Boolean {
//    val array = permutation.toMutableList() //3,1,4,6,5
//
//    var pos = 0
//    with(array) {
//        for (j in 1 until size) {   //j=1
//            var i = j - 1          //i=0
//            val value = this[j]   //value=1
//            while (i >= 0 && this[i] > value) { //true && true, true && true
//                this[i + 1] = this[i]    //3,3,4,6,5;
//                pos++                   // 1
//                i--                     //0
//            }
//            this[i + 1] = value
//        }
//    }
//
//    return pos % 2 == 0

    var count = 0

    var i = 0
    var j: Int
    while (i < permutation.size) {
        j = i+1
        while(j < permutation.size) {
            if (permutation[i] > permutation[j]) count++
            j++
        }
        i++
    }

    return count % 2 == 0
}