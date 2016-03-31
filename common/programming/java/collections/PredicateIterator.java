// -----------------------------------------------------------------------------
// PredicateIterator.java
// -----------------------------------------------------------------------------

/*
 * =============================================================================
 * Copyright (c) 1998-2011 Jeffrey M. Hunter. All rights reserved.
 * 
 * All source code and material located at the Internet address of
 * http://www.idevelopment.info is the copyright of Jeffrey M. Hunter and
 * is protected under copyright laws of the United States. This source code may
 * not be hosted on any other site without my express, prior, written
 * permission. Application to host any of the material elsewhere can be made by
 * contacting me at jhunter@idevelopment.info.
 *
 * I have made every effort and taken great care in making sure that the source
 * code and other content included on my web site is technically accurate, but I
 * disclaim any and all responsibility for any loss, damage or destruction of
 * data or any other property which may arise from relying on it. I will in no
 * case be liable for any monetary damages arising from such loss, damage or
 * destruction.
 * 
 * As with any code, ensure to test this code in a development environment 
 * before attempting to run it in production.
 * =============================================================================
 */
 
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * -----------------------------------------------------------------------------
 * The following class implements the Iterator interface and provides access
 * to a predicate that will filter out certains elements using the Predicate
 * interface.
 * 
 * @version 1.0
 * @author  Jeffrey M. Hunter  (jhunter@idevelopment.info)
 * @author  http://www.idevelopment.info 
 * -----------------------------------------------------------------------------
 */

public class PredicateIterator implements Iterator {

    Iterator iter;
    Predicate pred;
    Object next;
    boolean doneNext = false;

    public PredicateIterator(Iterator iter, Predicate pred) {
        this.iter = iter;
        this.pred = pred;
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }

    public boolean hasNext() {
        doneNext = true;
        boolean hasNext;
        while (hasNext = iter.hasNext()) {
            next = iter.next();
            if (pred.predicate(next)) {
                break;
            }
        }
        return hasNext;
    }

    public Object next() {
        if (!doneNext) {
            boolean has = hasNext();
            if (!has) {
                throw new NoSuchElementException();
            }
        }
        doneNext = false;
        return next;
    }

}
