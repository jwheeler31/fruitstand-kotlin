package io.joshwheeler

/**
 * Placeholder for a push notification on order prcess.
 *
 * @author jwheeler
 */
class ProcessedOrderPushNotification : InterestedParty {
    override fun update(order: Order) {
        println("ProcessedOrderPushNotification: Push notification sent for order $order")
    }
}