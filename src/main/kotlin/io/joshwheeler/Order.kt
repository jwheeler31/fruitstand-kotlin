package io.joshwheeler

/**
 * Order data.
 *
 * @param id The order ID
 * @param fruitMap The order contents.
 * @param total The order total.
 * @author jwheeler
 */
data class Order(val id: Int, val fruitMap: Map<Fruit, Int>, val total: Double)
