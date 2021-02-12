package io.joshwheeler

import assertk.assertThat
import assertk.assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import kotlin.IllegalArgumentException
import kotlin.test.assertFailsWith

/**
 * Tests functions defined in main class.
 *
 * @author jwheeler
 */
internal class MainKtTest {
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
     * Tests fruit arguments are valid and case insensitive.
     */
    @Test
    fun testInvalidFruitArguments() {
        val validApples: List<String> = listOf("apple", "Apple", "APPLE", "aPpLe")
        assertThat(getInvalidFruitArguments(validApples)).hasSize(0)

        val validOranges: List<String> = listOf("orange", "Orange", "ORANGE", "OrAnGe")
        assertThat(getInvalidFruitArguments(validOranges)).hasSize(0)

        val invalidApples: List<String> = listOf("a pple", "apole", "apile")
        assertThat(getInvalidFruitArguments(invalidApples)).hasSize(3)

        val invalidOranges: List<String> = listOf("o range", "oringe")
        assertThat(getInvalidFruitArguments(invalidOranges)).hasSize(2)
    }

    /**
     * Tests a conversion of a list of fruit strings to a map.
     */
    @Test
    fun testToFruitMap() {
        val validApples: List<String> = listOf("apple", "Apple", "APPLE", "aPpLe")
        val applesMap: Map<Fruit, Int> = toFruitMap(validApples)
        assertThat(applesMap.keys).containsOnly(Fruit.APPLE)
        assertThat(applesMap[Fruit.APPLE]).isEqualTo(4)

        val validOranges: List<String> = listOf("orange", "Orange", "ORANGE", "OrAnGe")
        val orangesMap: Map<Fruit, Int> = toFruitMap(validOranges)
        assertThat(orangesMap.keys).containsOnly(Fruit.ORANGE)
        assertThat(orangesMap[Fruit.ORANGE]).isEqualTo(4)

        val invalidOranges: List<String> = listOf("oringe")
        assertFailsWith<IllegalArgumentException> { toFruitMap(invalidOranges) }

        val invalidApples: List<String> = listOf("apol")
        assertFailsWith<IllegalArgumentException> { toFruitMap(invalidApples) }

        val emptyMap: Map<Fruit, Int> = toFruitMap(listOf())
        assertThat(emptyMap).hasSize(0)
        assertThat(emptyMap[Fruit.APPLE]).isNull();
        assertThat(emptyMap[Fruit.ORANGE]).isNull();

        val fruitMap: Map<Fruit, Int> = toFruitMap(validApples + validOranges)
        assertThat(fruitMap[Fruit.APPLE]).isEqualTo(4)
        assertThat(fruitMap[Fruit.ORANGE]).isEqualTo(4)
    }

    /**
     * Tests calculation of totalling fruits given a list.
     */
    @Test
    fun testCalculateTotalFromMap() {
        // Single pieces of fruit
        assertThat(calculateTotal(mapOf(Pair(Fruit.APPLE, 1)))).isEqualTo(Fruit.APPLE.price)
        assertThat(calculateTotal(mapOf(Pair(Fruit.ORANGE, 1)))).isEqualTo(Fruit.ORANGE.price)

        // Multiple pieces of fruit on the exactly matching deals
        assertThat(calculateTotal(
            mapOf(Pair(Fruit.APPLE, 2), Pair(Fruit.ORANGE, 3))
        )).isEqualTo(Fruit.APPLE.price + Fruit.ORANGE.price * 2)

        // Pieces of fruit with n-1 of deals
        assertThat(calculateTotal(
            mapOf(Pair(Fruit.APPLE, 1), Pair(Fruit.ORANGE, 2))
        )).isEqualTo(Fruit.APPLE.price + Fruit.ORANGE.price * 2)

        // Pieces of fruit with n+1 of deals
        assertThat(calculateTotal(
            mapOf(Pair(Fruit.APPLE, 3), Pair(Fruit.ORANGE, 5))
        )).isEqualTo(Fruit.APPLE.price * 2 + Fruit.ORANGE.price * 4)
    }

    /**
     * Tests the "buy one, get one free" function.
     */
    @Test
    fun testBuyOneGetOneFree() {
        assertThat(buyOneGetOneFree(1)).isEqualTo(1)
        assertThat(buyOneGetOneFree(2)).isEqualTo(1)
        assertThat(buyOneGetOneFree(3)).isEqualTo(2)
    }

    /**
     * Tests the "buy three for the price of two" function.
     */
    @Test
    fun testBuyThreeForTwo() {
        assertThat(buyThreeForTwo(1)).isEqualTo(1)
        assertThat(buyThreeForTwo(2)).isEqualTo(2)
        assertThat(buyThreeForTwo(3)).isEqualTo(2)
        assertThat(buyThreeForTwo(4)).isEqualTo(3)
        assertThat(buyThreeForTwo(5)).isEqualTo(4)
    }
}