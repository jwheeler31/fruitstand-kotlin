package io.joshwheeler

/**
 * Enumeration of fruits.
 *
 * @author jwheeler
 */
enum class Fruit(val price: Double) {
    /**
     * An apple.
     */
    APPLE(0.60),

    /**
     * An orange.
     */
    ORANGE(0.25);

    companion object {
        /**
         * Returns pretty values of the enumeration.
         */
        fun prettyValues(): List<String> {
            return values().map(Fruit::toString).map(String::toLowerCase).map(String::capitalize)
        }
    }
}