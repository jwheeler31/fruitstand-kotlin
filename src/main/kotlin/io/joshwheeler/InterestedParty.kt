package io.joshwheeler

/**
 * Interface for interested parties to implement.
 *
 * @author jwheeler
 */
interface InterestedParty {
    /**
     * Action to perform when an order update comes through.
     *
     * @param The order.
     */
    fun update(order: Order)
}