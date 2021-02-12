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
        this.interestingOrder = interesting as Order
        this.notifyInterestedParties()
    }
}