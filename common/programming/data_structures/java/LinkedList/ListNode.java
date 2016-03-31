// -----------------------------------------------------------------------------
// ListNode.java
// -----------------------------------------------------------------------------

/**
 * -----------------------------------------------------------------------------
 * Basic node stored in a linked list.
 * -----------------------------------------------------------------------------
 * @author Mark Allen Weiss
 * @see LinkedList
 * -----------------------------------------------------------------------------
 */

class ListNode {

    Object    element;
    ListNode  next;

    ListNode(Object theElement) {
        this(theElement, null);
    }

    ListNode(Object theElement, ListNode n) {
        element = theElement;
        next    = n;
    }

}
