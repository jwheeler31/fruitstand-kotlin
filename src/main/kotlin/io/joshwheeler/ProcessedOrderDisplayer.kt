package io.joshwheeler

/**
 * An interested party that displays processed orders.
 *
 * @author jwheeler
 */
class ProcessedOrderDisplayer : InterestedParty {
    override fun update(order: Order) {
        println("ProcessedOrderDisplayer: Order has been processed: $order")
    }
}