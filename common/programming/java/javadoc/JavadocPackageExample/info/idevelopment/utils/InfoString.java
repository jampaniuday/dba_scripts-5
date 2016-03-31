// -----------------------------------------------------------
// InfoString.java
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
 * Primarily used to return the length or substring of a given 
 * String object.
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

public class InfoString {

  private String vString;

  /**
    * Sole constructor. 
    * Accepts and stores a String for use by this class.
    * @param s The String to be stored and worked on by this class.
   */
  public InfoString (String s) {
    this.vString = s;
  }

  /**
   * Gets the original String used when object was created.
   * @return Original String passed in when object was created.
   */
  public String getOriginalString() {
    return vString;
  }

  /**
   * Gets the current length of the object string.
   * @return The current length of the object string.
   */
  public int getLength() {
    return vString.length();
  } 
 
  /**
   * Used to return a portion of the stored string object
   * using an offset and particular length.  Length should
   * be greater than the offset value.
   * @param offset Position in the original stored string to start from.
   * @param length Length of the original stored string object to return.
   * @return Portion of the string using offset and length.
   */
  public String getSubstring(int offset, int length) {
    return (vString.substring(offset, length));
  }

  /**
   * Used to return a portion of the stored string object
   * starting at the offset and continue to the end.  The end of
   * the string will be determined by the length of the string.
   * @param offset Position in the original stored string to start from.
   * @return Portion of the string starting at the offset.
   */
  public String getSubstring(int offset) {
    return (vString.substring(offset, vString.length()));
  }

}
