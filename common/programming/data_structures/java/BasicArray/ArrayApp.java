// -----------------------------------------------------------------------------
// ArrayApp.java
// -----------------------------------------------------------------------------

/**
 * -----------------------------------------------------------------------------
 * Used to provide an example of using Java arrays as a simple data structure.

 * @author Jeffrey Hunter
 * -----------------------------------------------------------------------------
 */

public class ArrayApp {

    long[] arrayElems;    // Reference to array.
    int    numElements;   // Number of items to store in array object.

    public ArrayApp() {
        this(10);
    }

    public ArrayApp(int nElems) {

        // Instantiate new array object.
        arrayElems   = new long[nElems];
        numElements  = 0;

    }


    public void loadArray() {

        // Insert several (10) items into array object
        arrayElems[0] = 77;
        arrayElems[1] = 99;
        arrayElems[2] = 44;
        arrayElems[3] = 55;
        arrayElems[4] = 22;
        arrayElems[5] = 88;
        arrayElems[6] = 11;
        arrayElems[7] = 00;
        arrayElems[8] = 66;
        arrayElems[9] = 33;

        // Indicate 10 items in array
        numElements = 10;

    }


    public void displayElements() {

        System.out.println();
        System.out.println("DISPLAY ELEMENTS");
        System.out.println("-----------------------------------");
        System.out.println("  - Number of elements = " + numElements);
        System.out.print  ("  - ");
        for (int j=0; j<numElements; j++) {
            System.out.print(arrayElems[j] + " ");
        }
        System.out.println("");

    }

    public void searchElement(long sKey) {

      System.out.println();
      System.out.println("SEARCH ELEMENT");
      System.out.println("-----------------------------------");
      System.out.println("  - Searching for element = " + sKey);
      System.out.print  ("  - ");

      int j;

      for (j=0; j<numElements; j++) {
          if (arrayElems[j] == sKey) {
            break;
          }
      }

      if (j == numElements) {
          System.out.println("Can't find element " + sKey);
      } else {
          System.out.println("Found element " + sKey);
      }

    }


    public void deleteElement(long dKey) {

      System.out.println();
      System.out.println("DELETE ELEMENT");
      System.out.println("-----------------------------------");
      System.out.println("  - Request to delete element = " + dKey);
      System.out.print  ("  - ");

      int j;

      for(j=0; j<numElements; j++) {
          if(arrayElems[j] == dKey) {
               break;
          }
      }

      if (j == numElements) {

         System.out.println("Didn't find element " + dKey);

      } else {

         System.out.println("Found and deleting element " + dKey);

         // Move higher ones down
         for (int k=j; k<numElements; k++) {
             arrayElems[k] = arrayElems[k+1];
         }

         numElements--;  

      }


   

    }


    public static void main(String[] args) {

        ArrayApp arrayApp = new ArrayApp(20);
        arrayApp.loadArray();
        arrayApp.displayElements();
        arrayApp.searchElement(66);
        arrayApp.searchElement(34);
        arrayApp.deleteElement(55);
        arrayApp.deleteElement(34);
        arrayApp.displayElements();

    }

}
