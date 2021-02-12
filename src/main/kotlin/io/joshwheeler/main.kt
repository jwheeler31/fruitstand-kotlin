package io.joshwheeler

import kotlin.system.exitProcess

/**
 * Returns a list of invalid fruit arguments; otherwise an empty list.
 */
fun getInvalidFruitArguments(fruitList: List<String>): List<String> {
    return fruitList.filter { candidate ->
        !Fruit.prettyValues()
            .map(String::toLowerCase)
            .contains(candidate.toLowerCase())
    }
}

/**
 * Converts a list of fruit to a map of fruit and its occurrences in the list.
 */
fun toFruitMap(fruitList: List<String>): Map<Fruit, Int> {
    val fruitMap = mutableMapOf<Fruit, Int>()
    fruitList.map(String::toUpperCase).map(Fruit::valueOf)
        .forEach { fruit: Fruit ->
        fruitMap.computeIfPresent(fruit) { _, v -> v + 1}
        fruitMap.putIfAbsent(fruit, 1)
    }
    return fruitMap
}

/**
 * Function to calculate count for "buy one, get one free".
 */
fun buyOneGetOneFree(count: Int): Int = (count / 2) + count % 2

/**
 * Function to calculate count for "buy three for the price of two".
 */
fun buyThreeForTwo(count: Int): Int = ((count / 3) * 2) + count % 3

/**
 * Calculates the total price of fruit, with discounts applied.
 */
fun calculateTotal(fruitMap: Map<Fruit, Int>): Double {
    var total: Double = 0.0;
    fruitMap.keys.forEach {
        // TODO: this would be simplified by the command pattern
        total += when (it) {
            Fruit.APPLE -> buyOneGetOneFree(fruitMap[it]!!) * it.price
            Fruit.ORANGE -> buyThreeForTwo(fruitMap[it]!!) * it.price
            else -> throw IllegalArgumentException("Unexpected fruit $it")
        }
    }
    return total
}

/**
 * Main application.
 */
fun main(args: Array<String>) {
    if (args.isEmpty()) {
        println("A space delimited list of fruits is required (options: ${Fruit.prettyValues()})")
        exitProcess(1)
    }

    val argsList: List<String> = args.asList()
    if (getInvalidFruitArguments(argsList).isNotEmpty()) {
        println("Invalid fruits: ${getInvalidFruitArguments(argsList)}")
        exitProcess(1)
    }

    val orderDisplayer: InterestedParty = ProcessedOrderDisplayer()
    val pushNotification: InterestedParty = ProcessedOrderPushNotification()

    val orderProcessor: InterestingSubject = OrderProcessor()
    orderProcessor.addInterestedParty(orderDisplayer)
    orderProcessor.addInterestedParty(pushNotification)

    val fruitMap1 = toFruitMap(argsList)
    val total1 = calculateTotal(fruitMap1)
    val order1 = Order(1, fruitMap1, total1)
    orderProcessor.interestingUpdate(order1)

    orderProcessor.removeInterestedParty(pushNotification)
    val fruitMap2 = toFruitMap(listOf("Apple", "Apple"))
    val total2 = calculateTotal(fruitMap2)
    val order2 = Order(2, fruitMap2, total2)
    orderProcessor.interestingUpdate(order2)

    orderProcessor.removeInterestedParty(orderDisplayer)
    val fruitMap3 = toFruitMap(listOf("Orange", "Orange"))
    val total3 = calculateTotal(fruitMap3)
    val order3 = Order(3, fruitMap3, total3)
    orderProcessor.interestingUpdate(order3)
}