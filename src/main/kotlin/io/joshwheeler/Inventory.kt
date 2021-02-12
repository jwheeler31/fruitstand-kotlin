package io.joshwheeler

/**
 * The inventory - a stand in for a database.
 *
 * @author jwheeler
 */
class Inventory private constructor() {
    /**
     * Apples in inventory.
     *
     * Note: would normally be private, but exposing for testing purposes.
     */
    var appleInventory: Int = 5

    /**
     * Oranges in inventory.
     *
     * Note: would normally be private, but exposing for testing purposes.
     */
    var orangeInventory: Int = 3

    private object HOLDER {
        val INSTANCE = Inventory()
    }

    companion object {
        val instance: Inventory by lazy { HOLDER.INSTANCE }
    }

    /**
     * Removes apples from inventory.
     *
     * @param count Apples to remove.
     * @return Apples removed.
     */
    fun removeApples(count: Int): Int {
        if (count > appleInventory) {
            throw InsufficientInventoryException("Insufficient inventory to remove $count apples (only " +
                    "$appleInventory available).")
        } else {
            appleInventory -= count
        }

        return count
    }

    /**
     * Add apples to inventory.
     *
     * @param Count to add.
     */
    fun addApples(count: Int) {
        appleInventory += count
    }

    /**
     * Removes oranges from inventory.
     *
     * @param count Oranges to remove.
     * @return Oranges removed.
     */
    fun removeOranges(count: Int): Int {
        if (count > orangeInventory) {
            throw InsufficientInventoryException("Insufficient inventory to remove $count oranges (only " +
                    "$orangeInventory available).")
        } else {
            orangeInventory -= count
        }

        return count
    }

    /**
     * Add oranges to inventory.
     *
     * @param count Count to add.
     */
    fun addOranges(count: Int) {
        orangeInventory += count
    }
}