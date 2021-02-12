package io.joshwheeler

/**
 * Stand-in InterestedParty for testing.
 *
 * @author jwheeler
 */
class InterestedPartyTestHelper : InterestedParty {
    val receivedOrders = mutableListOf<Order>()

    override fun update(order: Order) {
        this.receivedOrders.add(order)
    }
}