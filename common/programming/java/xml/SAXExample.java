// -----------------------------------------------------------------------------
// SAXExample.java
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
 
import java.io.*;
import java.net.*;
import org.xml.sax.*;

import oracle.xml.parser.v2.*;

/**
 * -----------------------------------------------------------------------------
 * Demonstrate how to use SAX.
 * 
 * @version 1.0
 * @author  Jeffrey M. Hunter  (jhunter@idevelopment.info)
 * @author  http://www.idevelopment.info
 * -----------------------------------------------------------------------------
 */
 
public class SAXExample extends HandlerBase{

    // Store the locator
    Locator locator;

    /*
    ** +---------------------------------------------+
    ** | METHOD: main                                |
    ** +---------------------------------------------+
    */
    static public void main(String[] argv) {
    
        try {

            if (argv.length != 1) {
                // must pass in the name of the XML file
                System.err.println("Usage: java SAXExample filename");
                System.exit(1);
            }

            // Create a new handler for the parser
            SAXExample sample = new SAXExample();

            // Get an instance of the parser
            Parser parser = new SAXParser();

            // Set Handlers in the parser
            parser.setDocumentHandler(sample);
            parser.setEntityResolver(sample);
            parser.setDTDHandler(sample);
            parser.setErrorHandler(sample);

            // Convert file to URL and parse
            try {
                parser.parse(fileToURL(new File(argv[0])).toString());
            } catch (SAXParseException e) {
                System.out.println(e.getMessage());
            } catch (SAXException e) {
                System.out.println(e.getMessage());
            }

        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }


    /*
    ** +---------------------------------------------+
    ** | METHOD: fileToURL                           |
    ** +---------------------------------------------+
    */
    static URL fileToURL(File file) {

        String path = file.getAbsolutePath();
        String fSep = System.getProperty("file.separator");
        if (fSep != null && fSep.length() == 1) {
            path = path.replace(fSep.charAt(0), '/');
        }
        if (path.length() > 0 && path.charAt(0) != '/') {
            path = '/' + path;
        }

        try {
            return new URL("file", null, path);
        } catch (java.net.MalformedURLException e) {
            throw new Error("unexpected MalformedException");
        }

    }

    /*
    ** +-------------------------------------------------------------+
    ** | ----------------------------------------------------------- |
    ** |     Sample Implementation of DocumentHandler Interface      |
    ** | ----------------------------------------------------------- |
    ** +-------------------------------------------------------------+
    */

    /*
    ** +---------------------------------------------+
    ** | METHOD: setDocumentLocator                  |
    ** +---------------------------------------------+
    */
    public void setDocumentLocator(Locator locator) {
        System.out.println("SetDocumentLocator:");
        this.locator = locator;
    }


    /*
    ** +---------------------------------------------+
    ** | METHOD: startDocument                       |
    ** +---------------------------------------------+
    */
    public void startDocument() {
        System.out.println("StartDocument");
    }


    /*
    ** +---------------------------------------------+
    ** | METHOD: endDocument                         |
    ** +---------------------------------------------+
    */
    public void endDocument() throws SAXException {
        System.out.println("EndDocument");
    }


    /*
    ** +---------------------------------------------+
    ** | METHOD: startElement                        |
    ** +---------------------------------------------+
    */
    public void startElement(String name, AttributeList atts) throws SAXException {

        System.out.println ("StartElement: " + name);

        for (int i=0; i<atts.getLength(); i++) {
            String aname = atts.getName(i);
            String type  = atts.getType(i);
            String value = atts.getValue(i);

            System.out.println("    " + aname + "("+type+") = " + value);
        }

    }


    /*
    ** +---------------------------------------------+
    ** | METHOD: endElement                          |
    ** +---------------------------------------------+
    */
    public void endElement(String name) throws SAXException {
        System.out.println("EndElement:" + name);
    }


    /*
    ** +---------------------------------------------+
    ** | METHOD: chracters                           |
    ** +---------------------------------------------+
    */
    public void chracters(char[] cbuf, int start, int len) {
        System.out.print("Characters: ");
        System.out.println(new String(cbuf, start, len));
    }


    /*
    ** +---------------------------------------------+
    ** | METHOD: ignorableWhitespace                 |
    ** +---------------------------------------------+
    */
    public void ignorableWhitespace(char[] cbuf, int start, int len) {
        System.out.println("ignorableWhitespace:");
    }

    /*
    ** +---------------------------------------------+
    ** | METHOD: processingInstruction               |
    ** +---------------------------------------------+
    */
    public void processingInstruction(String target, String data) throws SAXException {
        System.out.println("processingInstruction: " + target + " " + data);
    }


    /*
    ** +-------------------------------------------------------------+
    ** | ----------------------------------------------------------- |
    ** |     Sample Implementation of EntityResolver Interface       |
    ** | ----------------------------------------------------------- |
    ** +-------------------------------------------------------------+
    */


    /*
    ** +---------------------------------------------+
    ** | METHOD: resolveEntity                       |
    ** +---------------------------------------------+
    */
    public InputSource resolveEntity(String publicID, String systemID) throws SAXException{

        System.out.println("resolveEntity:" + publicID + " " + systemID);
        System.out.println("Locator:" + locator.getPublicId() + " " + locator.getSystemId() +
                           " " + locator.getLineNumber() + " " + locator.getColumnNumber());
        return null;

    }


    /*
    ** +-------------------------------------------------------------+
    ** | ----------------------------------------------------------- |
    ** |     Sample Implementation of DTDHandler Interface           |
    ** | ----------------------------------------------------------- |
    ** +-------------------------------------------------------------+
    */


    /*
    ** +---------------------------------------------+
    ** | METHOD: notationDec1                        |
    ** +---------------------------------------------+
    */
    public void notationDec1(String name, String publicID, String systemID) {
        System.out.println("NotationDec1: " + name + " " + publicID + " " + systemID);
    }


    /*
    ** +---------------------------------------------+
    ** | METHOD: unparsedEntityDec1                  |
    ** +---------------------------------------------+
    */
    public void unparsedEntityDec1(String name, String publicID, String systemID, String notationName) {
        System.out.println("unparsedEntityDec1: " + name + " " + publicID + " " + systemID + " " + notationName);
    }


    /*
    ** +-------------------------------------------------------------+
    ** | ----------------------------------------------------------- |
    ** |     Sample Implementation of ErrorHandler Interface         |
    ** | ----------------------------------------------------------- |
    ** +-------------------------------------------------------------+
    */


    /*
    ** +---------------------------------------------+
    ** | METHOD: warning                             |
    ** +---------------------------------------------+
    */
    public void warning(SAXParseException e) throws SAXException {
        System.out.println("Warning: " + e.getMessage());
    }


    /*
    ** +---------------------------------------------+
    ** | METHOD: error                               |
    ** +---------------------------------------------+
    */
    public void error(SAXParseException e) throws SAXException {
        throw new SAXException(e.getMessage());
    }


    /*
    ** +---------------------------------------------+
    ** | METHOD: fatalError                          |
    ** +---------------------------------------------+
    */
    public void fatalError(SAXParseException e) throws SAXException {
        System.out.println("Fatal Error");
        throw new SAXException(e.getMessage());
    }


}


