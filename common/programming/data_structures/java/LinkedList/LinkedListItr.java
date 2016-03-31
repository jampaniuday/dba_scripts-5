// -----------------------------------------------------------------------------
// LinkedListItr.java
// -----------------------------------------------------------------------------

/**
 * -----------------------------------------------------------------------------
 * 
 * ++++++++++++++++++++++++++++    PUBLIC OPERATIONS     +++++++++++++++++++++++
 * void      advance( )     --> Advance
 * boolean   isPastEnd( )   --> True if at "null" position in list
 * Object    retrieve       --> Return item in current position
 * 
 * +++++++++++++++++++++++++++++++++    ERRORS     +++++++++++++++++++++++++++++
 * No special errors
 *
 * -----------------------------------------------------------------------------
 * Linked list implementation of the list iterator using a header node.
 * Package friendly only, with a ListNode.
 * Maintains "current position" in the linked list.
 * -----------------------------------------------------------------------------
 * @author Mark Allen Weiss
 * @see LinkedList
 * -----------------------------------------------------------------------------
 */

public class LinkedListItr {

    /* Current position within the LinkedList */
    ListNode current;

    /**
     * Construct the list iterator
     * @param theNode any node in the linked list.
     */
    LinkedListItr(ListNode theNode) {
        current = theNode;
    }

    /**
     * Test if the current position is past the end of the list.
     * @return true if the current position is null.
     */
    public boolean isPastEnd() {
        return current == null;
    }

    /**
     * Return the item stored in the current position.
     * @return the stored item or null if the current position
     * is not in the list.
     */
    public Object retrieve() {
        return isPastEnd() ? null : current.element;
    }

    /**
     * Advance the current position to the next node in the list.
     * If the current position is null, then do nothing.
     */
    public void advance() {

        if (!isPastEnd()) {
            current = current.next;
        }

    }

}
