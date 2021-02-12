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
 * Converts a list of strings to fruits.
 */
fun toFruit(fruitList: List<String>): List<Fruit> {
    return fruitList.map(String::toUpperCase).map(Fruit::valueOf)
}

/**
 * Calculates the total price of fruit.
 */
fun calculateTotal(fruitList: List<Fruit>): Double {
    return fruitList.sumByDouble { fruit: Fruit -> fruit.price }
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

    println(calculateTotal(toFruit(args.asList())))
}