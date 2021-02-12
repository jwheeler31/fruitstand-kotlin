package io.joshwheeler

import org.junit.jupiter.api.Test
import assertk.assertThat
import assertk.assertions.*

/**
 * Tests for notifications.
 *
 * @author jwheeler
 */
class NotificationTest {
    /**
     * Test notifications.
     */
    @Test
    fun testNotifications() {
        val helper1 = InterestedPartyTestHelper()
        val helper2 = InterestedPartyTestHelper()

        val orderProcessor: InterestingSubject = OrderProcessor()
        orderProcessor.addInterestedParty(helper1)
        orderProcessor.addInterestedParty(helper2)
        assertThat(((orderProcessor as OrderProcessor).interestedParties))
            .containsExactlyInAnyOrder(helper1, helper2)

        val fruitMap1 = toFruitMap(listOf("Apple", "Orange", "Apple"))
        val total1 = calculateTotal(fruitMap1)
        val order1 = Order(1, fruitMap1, total1)
        orderProcessor.interestingUpdate(order1)

        orderProcessor.removeInterestedParty(helper1)
        val fruitMap2 = toFruitMap(listOf("Apple", "Apple"))
        val total2 = calculateTotal(fruitMap2)
        val order2 = Order(2, fruitMap2, total2)
        orderProcessor.interestingUpdate(order2)

        orderProcessor.removeInterestedParty(helper2)
        val fruitMap3 = toFruitMap(listOf("Orange", "Orange"))
        val total3 = calculateTotal(fruitMap3)
        val order3 = Order(3, fruitMap3, total3)
        orderProcessor.interestingUpdate(order3)

        assertThat(helper1.receivedOrders).containsExactly(order1)
        assertThat(helper2.receivedOrders).containsExactly(order1, order2)
    }
}