package io.joshwheeler

import assertk.Assert
import org.junit.jupiter.api.Assertions.*

import assertk.assertThat
import assertk.assertions.*
import org.junit.jupiter.api.Test
import io.joshwheeler.getInvalidFruitArguments
import io.joshwheeler.toFruit
import io.joshwheeler.calculateTotal
import kotlin.IllegalArgumentException
import kotlin.test.assertFailsWith

/**
 * Tests functions defined in main class.
 *
 * @author jwheeler
 */
internal class MainKtTest {
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
     * Tests conversion of string to Fruit.
     */
    @Test
    fun testToFruit() {
        val validApples: List<String> = listOf("apple", "Apple", "APPLE", "aPpLe")
        val apples: List<Fruit> = toFruit(validApples)
        assertThat(apples).containsOnly(Fruit.APPLE)

        val validOranges: List<String> = listOf("orange", "Orange", "ORANGE", "OrAnGe")
        val oranges: List<Fruit> = toFruit(validOranges)
        assertThat(oranges).containsOnly(Fruit.ORANGE)

        val invalidOranges: List<String> = listOf("oringe")
        assertFailsWith<IllegalArgumentException> { toFruit(invalidOranges) }

        val invalidApples: List<String> = listOf("apol")
        assertFailsWith<IllegalArgumentException> { toFruit(invalidApples) }
    }

    /**
     * Tests calculation of total of fruits.
     */
    @Test
    fun testCalculateTotal() {
        assertThat(calculateTotal(listOf(Fruit.APPLE))).isEqualTo(0.6)
        assertThat(calculateTotal(listOf(Fruit.ORANGE))).isEqualTo(0.25)

        assertThat(calculateTotal(
            listOf(Fruit.APPLE, Fruit.APPLE, Fruit.ORANGE, Fruit.APPLE)
        )).isEqualTo(2.05)
    }
}