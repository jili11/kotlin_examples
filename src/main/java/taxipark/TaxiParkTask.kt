package taxipark

import java.util.*
import kotlin.math.max

/*
 * Task #1. Find all the drivers who performed no trips.
 */
fun TaxiPark.findFakeDrivers(): Set<Driver> =
    this.allDrivers.filter { d -> this.trips.none { it.driver.equals(d) } }.toSet()

/*
 * Task #2. Find all the clients who completed at least the given number of trips.
 */
fun TaxiPark.findFaithfulPassengers(minTrips: Int): Set<Passenger> =
    this.allPassengers.filter { p -> this.trips.count { it.passengers.contains(p) } >= minTrips }.toSet()

/*
 * Task #3. Find all the passengers, who were taken by a given driver more than once.
 */
fun TaxiPark.findFrequentPassengers(driver: Driver): Set<Passenger> =
    this.allPassengers.filter { p -> this.trips.count { it.passengers.contains(p) && it.driver == driver } > 1 }.toSet()

/*
 * Task #4. Find the passengers who had a discount for majority of their trips.
 */
fun TaxiPark.findSmartPassengers(): Set<Passenger> =
    this.allPassengers.filter { p ->
        this.trips.count { it.passengers.contains(p) && it.discount != null } >
                this.trips.count { it.passengers.contains(p) && it.discount == null }
    }
        .toSet()

/*
 * Task #5. Find the most frequent trip duration among minute periods 0..9, 10..19, 20..29, and so on.
 * Return any period if many are the most frequent, return `null` if there're no trips.
 */
fun TaxiPark.findTheMostFrequentTripDurationPeriod(): IntRange? {
    return if (this.trips.isEmpty()) {
        null
    } else {
        val maxValue = this.trips.map { it.duration }.max() ?: 0
        val map = HashMap<Int, IntRange>()
        for (i in 0..maxValue step 10) {
            val range = IntRange(i, i + 9)
            val numberOfTrips = this.trips.filter { it.duration in range }.count()
            map[numberOfTrips] = range
        }
        map[map.toSortedMap().lastKey()]
    }


}

/*
 * Task #6.
 * Check whether 20% of the drivers contribute 80% of the income.
 */
fun TaxiPark.checkParetoPrinciple(): Boolean {

    return if (this.trips.isEmpty()) {
        false
    } else {
        val costFor20PercentDrivers =
            this.trips.groupBy { it.driver }.mapValues { it.value.sumByDouble { t -> t.cost } }
                .entries.sortedByDescending { it.value }
                .slice(0..(this.allDrivers.count() * 0.2 - 1).toInt()).sumByDouble { it.value }
        val totalCost = this.trips.sumByDouble { it.cost }
        return costFor20PercentDrivers >= totalCost * 0.8
    }
}


data class Value(val s: String)

fun equals1(v1: Value?, v2: Value?): Boolean {
    return v1 == v2
}

fun equals2(v1: Value?, v2: Value?): Boolean = v1 === v2 || (v1?.equals(v2)) ?: false
