package io.joshwheeler

/**
 * Interface for interesting subjects to implement.
 *
 * @author jwheeler
 */
interface InterestingSubject {
    /**
     * Add an interested party to notify.
     *
     * @param party The party.
     */
    fun addInterestedParty(party: InterestedParty)

    /**
     * Remove an interested party.
     *
     * @param party The party.
     */
    fun removeInterestedParty(party: InterestedParty)

    /**
     * Notify interested parties.
     */
    fun notifyInterestedParties()

    /**
     * An interesting update.
     *
     * @param interesting: The interesting thing.
     */
    fun interestingUpdate(interesting: Any)
}