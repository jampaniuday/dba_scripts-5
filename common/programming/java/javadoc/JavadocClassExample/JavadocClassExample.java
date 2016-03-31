// -----------------------------------------------------------
// JavadocClassExample.java
// -----------------------------------------------------------

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

import java.util.*;

/**
 * Used to test the functionality of <code>javadoc</code> on a 
 * single class file.
 * 
 * This class also implements many of the conventions used in
 * the "Java Coding and Documenting Conventions" guide within
 * the DBA Documentation Library.
 * <p>
 * For more information on the different features of Javadoc, visit
 * <a target="_blank" href="http://java.sun.com/j2se/javadoc">Javadoc Tool Home Page</a>
 * @author Jeffrey Hunter
 * @author <a href="mailto:jhunter@iDevelopment.info">jhunter@iDevelopment.info</a>
 * @author <a target="_blank" href="http://www.iDevelopment.info">www.iDevelopment.info</a>
 * @version 2.0,  &nbsp; 04-SEP-2002
 * @since SDK1.4
 */

public class JavadocClassExample {

    /**
     * A Date object used to store the date and time
     * this object is created. It can be retrieved
     * by sending this object a message to the 
     * {@link #getDate} method.
     * <p>
     * Notice that since this 
     * member is <code>private</code>, <code>javadoc</code>
     * will not process it by default.
     * <p>
     * In order for <code>javadoc</code> to process private
     * members would require the argument <b>-private</b>
     * be passed on the command line to <code>javadoc</code>.
     */
    private Date objectDate;

    /**
     * Represents the number of instances of this class.
     * This integer gets incremented by one within the constructor.
     */
    public static int numberOfInstances = 0;

    /**
     * Constructor used to create this object.  Responsible for setting
     * this object's creation date, as well as incrementing the number instances
     * of this object.
     * @param d A Date object that is used to set the Date/Time this object was created.
     * @see #numberOfInstances
     */
    public JavadocClassExample(Date d) {
      this.objectDate = d;
      this.numberOfInstances++;
    }

    /**
     * Noarg constructor will simply pass a new Date
     * object to the <i>Date</i> constructor.
     * @see #JavadocClassExample(Date)
     */
    public JavadocClassExample() {
      this(new Date());
    }

    /**
     * Returns the Date/Time this object was created. The Date is set
     * within the constructor.
     * @return <code>Date</code> object that indicates when the instance was created.
     * @see #JavadocClassExample
     */
    public Date getDate() {
      return this.objectDate;
    }

    /**
     * Sole entry point to the class and application.
     * @param args Array of String arguments.
     * @exception java.lang.InterruptedException
     *            Thrown from the Thread class.
     */
    public static void main(String[] args)
      throws InterruptedException {

      JavadocClassExample myObject1 = new JavadocClassExample();
      System.out.println("Started Object 1 at     : " + myObject1.getDate());
      System.out.println("  - Number of instances : " + JavadocClassExample.numberOfInstances + "\n");

      Thread.sleep(3000);

      JavadocClassExample myObject2 = new JavadocClassExample(new Date());
      System.out.println("Started Object 2 at     : " + myObject2.getDate());
      System.out.println("  - Number of instances : " + JavadocClassExample.numberOfInstances + "\n");

    }

}
