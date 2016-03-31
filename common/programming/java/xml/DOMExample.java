// -----------------------------------------------------------------------------
// DOMExample.java
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
import org.w3c.dom.*;
import org.w3c.dom.Node.*;

import oracle.xml.parser.v2.*;

/**
 * -----------------------------------------------------------------------------
 * Demonstrate how to use DOM.
 * 
 * @version 1.0
 * @author  Jeffrey M. Hunter  (jhunter@idevelopment.info)
 * @author  http://www.idevelopment.info
 * -----------------------------------------------------------------------------
 */
 
public class DOMExample {

    /*
     * +---------------------------------------------+
     * | METHOD: main                                |
     * +---------------------------------------------+
     */
    static public void main(String[] argv) {
        try {

            if (argv.length != 1) {
                // must pass in the name of the XML file
                System.err.println("Usage: java DOMExample filename");
                System.exit(1);
            }

            // Get an instance of the parser
            DOMParser parser = new DOMParser();

            // Generate a URL from the filename
            URL url = createURL(argv[0]);

            // Set various parser options; validation on,
            // warnings shown, error stream set to stderr.
            parser.setErrorStream(System.err);
            parser.setValidationMode(true);
            parser.showWarnings(true);
            // parse the document
            parser.parse(url);

            // Obtain the document
            XMLDocument doc = parser.getDocument();

            // print document elements
            System.out.print("The elements are: ");
            printElements(doc);

            // print document elements attributes
            System.out.println("The attributes of each element are: ");
            printElementAttributes(doc);

        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }


    /*
     * +---------------------------------------------+
     * | METHOD: printElements                       |
     * +---------------------------------------------+
     */
    static void printElements(Document doc) {

        NodeList nodelist = doc.getElementsByTagName("*");
        Node     node;

        for (int i=0; i<nodelist.getLength(); i++) {
            node = nodelist.item(i);
            System.out.print(node.getNodeName() + " ");
        }

        System.out.println();

    }


    /*
     * +---------------------------------------------+
     * | METHOD: printElementAttributes              |
     * +---------------------------------------------+
     */
    static void printElementAttributes(Document doc) {

        NodeList      nodelist = doc.getElementsByTagName("*");
        Node          node;
        Element       element;
        NamedNodeMap  nnm = null;

        String attrname;
        String attrval;
        int    i, len;

        len = nodelist.getLength();

        for (int j=0; j < len; j++) {
            element = (Element)nodelist.item(j);
            System.out.println(element.getTagName() + ":");
            nnm = element.getAttributes();
        }

        if (nnm != null) {
            for (i=0; i<nnm.getLength(); i++) {
                node = nnm.item(i);
                attrname = node.getNodeName();
                attrval  = node.getNodeValue();
                System.out.println(" " + attrname + " = " + attrval);
            }
        }

        System.out.println();

    }


    /*
     * +---------------------------------------------+
     * | METHOD: createURL                           |
     * +---------------------------------------------+
     */
    static URL createURL(String filename) {

        URL url = null;

        try {
            url = new URL(filename);
        } catch (MalformedURLException ex) {
            try {
                File f = new File(filename);
                url = f.toURL();
            } catch (MalformedURLException e) {
                System.out.println("Cannot create URL for: " + filename);
                System.exit(0);
            }
        }

        return url;

    }

}


