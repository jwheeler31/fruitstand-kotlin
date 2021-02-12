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

    println(calculateTotal(toFruitMap(argsList)))
}