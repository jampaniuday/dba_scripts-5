// -----------------------------------------------------------------------------
// OracleXmlDomExample.java
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
 
/**
 * -----------------------------------------------------------------------------
 * Used to provide an example of how to build a DOM Tree from an example XML 
 * file provided on the command line. This example uses the Oracle XML parser.
 *
 * COMPILE:
 * --------------------------
 * javac -classpath $JAVALIB/xmlparserv2.jar OracleXmlDomExample.java
 *
 * javac -classpath %JAVALIB%\xmlparserv2.jar OracleXmlDomExample.java
 *
 * 
 * RUN PROGRAM:
 * --------------------------
 * java -classpath $JAVALIB/xmlparserv2.jar:. OracleXmlDomExample DatabaseInventory.xml
 *
 * java -classpath %JAVALIB%\xmlparserv2.jar;. OracleXmlDomExample DatabaseInventory.xml
 * 
 * -----------------------------------------------------------------------------
 * @version 1.0
 * @author  Jeffrey M. Hunter  (jhunter@idevelopment.info)
 * @author  http://www.idevelopment.info
 * -----------------------------------------------------------------------------
 */


// Core Java APIs
import java.io.File;
import java.io.IOException;

// Oracle XML Parser
import oracle.xml.parser.v2.DOMParser;
import oracle.xml.parser.v2.XMLDocument;
import oracle.xml.parser.v2.XMLParseException;

// DOM
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.NamedNodeMap;

// SAX
import org.xml.sax.SAXException;


public class OracleXmlDomExample {

    private static void printNode(Node node, String indent)  {

        switch (node.getNodeType()) {

            case Node.DOCUMENT_NODE:
                System.out.println("<xml version=\"1.0\">\n");
                // recurse on each child
                NodeList nodes = node.getChildNodes();
                if (nodes != null) {
                    for (int i=0; i<nodes.getLength(); i++) {
                        printNode(nodes.item(i), "");
                    }
                }
                break;
                
            case Node.ELEMENT_NODE:
                String name = node.getNodeName();
                System.out.print(indent + "<" + name);
                NamedNodeMap attributes = node.getAttributes();
                for (int i=0; i<attributes.getLength(); i++) {
                    Node current = attributes.item(i);
                    System.out.print(
                        " " + current.getNodeName() +
                        "=\"" + current.getNodeValue() +
                        "\"");
                }
                System.out.print(">");
                
                // recurse on each child
                NodeList children = node.getChildNodes();
                if (children != null) {
                    for (int i=0; i<children.getLength(); i++) {
                        printNode(children.item(i), indent + "  ");
                    }
                }
                
                System.out.print("</" + name + ">");
                break;

            case Node.TEXT_NODE:
                System.out.print(node.getNodeValue());
                break;
        }

    }


    /**
     * Sole entry point to the class and application.
     * @param args Array of String arguments.
     */
    public static void main(String[] args) {

        if (args.length != 1) {
            System.err.println("\nUsage: java OracleXmlDomExample filename\n");
            System.exit(1);
        }

        // Create URL String from incoming file
        String url = "file:" + new File(args[0]).getAbsolutePath();

        System.out.println();
        System.out.println("Passed in File               : " + args[0]);
        System.out.println("Object to Parse (String URL) : " + url);
        System.out.println();

        try {
        
            // Create a DOM Parser
            DOMParser parser = new DOMParser();

            // Set parser options
            parser.setErrorStream(System.err);
            parser.setValidationMode(DOMParser.DTD_VALIDATION);
            parser.showWarnings(true);

            // Parser the incoming file (URL)
            parser.parse(url);

            // Obtain the document
            Document doc = parser.getDocument();

            // Print the document from the DOM tree and feed it an initial 
            // indentation of nothing
            printNode(doc, "");
            
            System.out.println("\n");

        } catch (IOException ioe) {

            ioe.printStackTrace();

        } catch (SAXException saxe) {

            saxe.printStackTrace();

        }

    }

}
