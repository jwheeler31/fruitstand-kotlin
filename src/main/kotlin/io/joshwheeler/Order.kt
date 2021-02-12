package io.joshwheeler

/**
 * Order data.
 *
 * @param id The order ID
 * @param fruitMap The order contents.
 * @param total The order total.
 * @param state The order state.
 * @author jwheeler
 */
data class Order(
    val id: Int,
    val fruitMap: Map<Fruit, Int>,
    val total: Double,
    val state: OrderState = OrderState.PENDING)

enum class OrderState {
    /**
     * Order pending.
     */
    PENDING,

    /**
     * Order completed.
     */
    COMPLETED,

    /**
     * Order failed.
     */
    FAILED
}
