// -----------------------------------------------------------
// StringCase.java
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
 
package info.idevelopment.utils;

/**
 * Used to change the case of a given String object.
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
 * @version 1.0,  &nbsp; 07-SEP-2002
 * @since SDK1.4
 */

public class StringCase {

  private String vString;

  /**
   * Sole constructor used to create this object.
   * @param s String object for class to work on.
   */
  public StringCase(String s) {
    this.vString = s;
  }

  /**
   * Used to convert the original string object to upper case.
   * @return Upper case version of the original string.
   */
  public String toUpper() {
    return vString.toUpperCase();
  }

  /**
   * Used to convert the original string object to lower case.
   * @return Lower case version of the original string.
   */
  public String toLower() {
    return vString.toLowerCase();
  }

}
