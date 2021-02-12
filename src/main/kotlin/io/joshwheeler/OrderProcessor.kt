package io.joshwheeler

/**
 * A subject that handles notification of interesting order updates.
 *
 * @author jwheeler
 */
class OrderProcessor : InterestingSubject {
    /**
     * The parties interested in the orders.
     *
     * Note: this would not typically be accessible, and a mock used in tests,
     *  but I'm exposing for time's sake.
     */
    val interestedParties = mutableListOf<InterestedParty>()

    /**
     * The inventory.
     */
    val inventory: Inventory = Inventory.instance

    /**
     * The interesting order.
     *
     * Note: this would not typically be accessible, and a mock used in tests,
     *  but I'm exposing for time's sake.
     */
    private var interestingOrder = Order(0, emptyMap(), 0.0)

    override fun addInterestedParty(party: InterestedParty) {
        interestedParties.add(party)
    }

    override fun removeInterestedParty(party: InterestedParty) {
        interestedParties.remove(party)
    }

    override fun notifyInterestedParties() {
        interestedParties.forEach { it.update(interestingOrder) }
    }

    override fun interestingUpdate(interesting: Any) {
        val order = interesting as Order
        var apples = 0
        var oranges = 0
        try {
            if (order.fruitMap[Fruit.APPLE] != null) {
                apples = inventory.removeApples(order.fruitMap[Fruit.APPLE]!!)
            }
        } catch (e: InsufficientInventoryException) {
            this.interestingOrder = Order(order.id, order.fruitMap, order.total, OrderState.FAILED)
            this.notifyInterestedParties();
            return
        }

        try {
            if (order.fruitMap[Fruit.ORANGE] != null) {
                oranges = inventory.removeOranges(order.fruitMap[Fruit.ORANGE]!!)
            }
        } catch (e: InsufficientInventoryException) {
            inventory.addApples(apples)
            this.interestingOrder = Order(order.id, order.fruitMap, order.total, OrderState.FAILED)
            this.notifyInterestedParties()
            return
        }

        this.interestingOrder = Order(order.id, order.fruitMap, order.total, OrderState.COMPLETED)
        this.notifyInterestedParties()
    }
}