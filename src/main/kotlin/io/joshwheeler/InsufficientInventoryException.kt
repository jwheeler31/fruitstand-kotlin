package io.joshwheeler

import java.lang.Exception

/**
 * Custom exception for insufficient inventory.
 *
 * @author jwheeler
 */
class InsufficientInventoryException(message: String) : Exception(message) {
}