package io.joshwheeler

import assertk.assertThat
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import kotlin.test.assertFailsWith

/**
 * Test inventory functionality.
 *
 * @author jwheeler
 */
class InventoryTest {
    companion object {
        /**
         * Test initializer
         */
        @BeforeAll
        @JvmStatic
        internal fun beforeAll() {
            val inventory: Inventory = Inventory.instance
            inventory.appleInventory = 5
            inventory.orangeInventory = 3
        }
    }

    /**
     * Tests successful removal of inventory.
     */
    @Test
    @Order(1)
    fun testInventoryRemoval() {
        val inventory: Inventory = Inventory.instance

        assertThat(inventory.removeApples(4)).equals(4)
        assertThat(inventory.appleInventory).equals(1)
        assertFailsWith<InsufficientInventoryException> { inventory.removeApples(2) }
        assertThat(inventory.appleInventory).equals(1)
        assertThat(inventory.removeApples(1)).equals(1)
        assertThat(inventory.appleInventory).equals(0)

        assertFailsWith<InsufficientInventoryException> { inventory.removeOranges(4) }
        assertThat(inventory.orangeInventory).equals(3)
        assertThat(inventory.removeOranges(2)).equals(2)
        assertThat(inventory.orangeInventory).equals(1)
    }

    @Test
    @Order(2)
    fun testSingleton() {
        val inventory: Inventory = Inventory.instance
        assertThat(inventory.appleInventory).isEqualTo(0)
        assertThat(inventory.orangeInventory).isEqualTo(1)
    }
}