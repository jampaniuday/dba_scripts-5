// -----------------------------------------------------------------------------
// LinkedListApp.java
// -----------------------------------------------------------------------------

/**
 * -----------------------------------------------------------------------------
 * Used to provide an example of using our linked list implementation.
 * -----------------------------------------------------------------------------
 * @author Mark Allen Weiss
 * @see LinkedList
 * -----------------------------------------------------------------------------
 */

public class LinkedListApp {

    private static void prtln() {
        System.out.println();
    }

    private static void prt(String s) {
        System.out.print(s);
    }

    private static void printList(LinkedList theList) {

        if (theList.isEmpty()) {

            prt("[Empty list]");

        }  else {

            LinkedListItr itr = theList.first();
            for( ; !itr.isPastEnd(); itr.advance() ) {
                prt(itr.retrieve() + " ");
            }
        }

        prtln();

    }

    public static void main(String[] args) {

        LinkedList theList = new LinkedList();
        LinkedListItr theItr;

        theItr = theList.zeroth();
        printList(theList);

        for (int i=0; i < 10; i++) {
            theList.insert( new Integer(i), theItr);
            printList(theList);
            theItr.advance();
        }

        for (int i=0; i<10; i += 2) {
            theList.remove( new Integer(i));
        }

        for (int i=0; i<10; i++) {
            if( (i % 2 == 0 ) != ( theList.find( new Integer(i)).isPastEnd() ) ) {
                prt("[Find fails]");
            }
        }

        prt("[Finished all delete operations]\n");
        printList(theList);

    }

}
