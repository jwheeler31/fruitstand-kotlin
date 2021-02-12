package io.joshwheeler

import org.junit.jupiter.api.Test
import assertk.assertThat
import assertk.assertions.*
import org.junit.jupiter.api.BeforeAll

/**
 * Tests for notifications.
 *
 * @author jwheeler
 */
class NotificationTest {
    companion object {
        @BeforeAll
        @JvmStatic
        internal fun beforeAll() {
            val inventory: Inventory = Inventory.instance
            inventory.appleInventory = 5
            inventory.orangeInventory = 3
        }
    }

    /**
     * Test notifications.
     */
    @Test
    fun testNotifications() {
        val inventory: Inventory = Inventory.instance
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
        assertThat(inventory.appleInventory).isEqualTo(3)
        assertThat(inventory.orangeInventory).isEqualTo(2)

        orderProcessor.removeInterestedParty(helper1)
        val fruitMap2 = toFruitMap(listOf("Apple", "Apple"))
        val total2 = calculateTotal(fruitMap2)
        val order2 = Order(2, fruitMap2, total2)
        orderProcessor.interestingUpdate(order2)
        assertThat(inventory.appleInventory).isEqualTo(1)
        assertThat(inventory.orangeInventory).isEqualTo(2)

        orderProcessor.removeInterestedParty(helper2)
        val fruitMap3 = toFruitMap(listOf("Orange", "Orange"))
        val total3 = calculateTotal(fruitMap3)
        val order3 = Order(3, fruitMap3, total3)
        orderProcessor.interestingUpdate(order3)
        assertThat(inventory.appleInventory).isEqualTo(1)
        assertThat(inventory.orangeInventory).isEqualTo(0)

        assertThat(helper1.receivedOrders[0].state).isEqualTo(OrderState.COMPLETED)
        assertThat(helper2.receivedOrders[0].state).isEqualTo(OrderState.COMPLETED)
        assertThat(helper2.receivedOrders[1].state).isEqualTo(OrderState.COMPLETED)

        // Test apple fail
        helper1.receivedOrders.clear()
        orderProcessor.addInterestedParty(helper1)
        val fruitMap4 = toFruitMap(listOf("Apple", "Apple"))
        val total4 = calculateTotal(fruitMap4)
        val order4 = Order(4, fruitMap4, total4)
        orderProcessor.interestingUpdate(order4)
        assertThat(helper1.receivedOrders[0].state).isEqualTo(OrderState.FAILED)
        assertThat(inventory.appleInventory).isEqualTo(1)
        assertThat(inventory.orangeInventory).isEqualTo(0)

        // Test orange fail and inventory rollback
        inventory.addOranges(1)
        val fruitMap5 = toFruitMap(listOf("Apple", "Orange", "Orange"))
        val total5 = calculateTotal(fruitMap5)
        val order5 = Order(4, fruitMap5, total5)
        orderProcessor.interestingUpdate(order5)
        assertThat(helper1.receivedOrders[0].state).isEqualTo(OrderState.FAILED)
        assertThat(inventory.appleInventory).isEqualTo(1)
        assertThat(inventory.orangeInventory).isEqualTo(1)
    }
}