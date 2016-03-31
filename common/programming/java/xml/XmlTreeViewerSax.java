// -----------------------------------------------------------------------------
// XmlTreeViewerSax.java
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
 
// Core Java APIs
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

// SAX
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

// Swing
import java.awt.*;
import javax.swing.*;
import javax.swing.tree.*;

/**
 * -----------------------------------------------------------------------------
 * Application that allows users to view the contents of an XML document as
 * a graphical tree. Also provides an example that demonstrates SAX events
 * and associated callback methods that can be used to perform action within
 * the parsing of an XML document.
 *
 * COMPILE:
 * --------------------------
 * javac -classpath $JAVALIB/xercesImpl.jar XmlTreeViewerSax.java
 *
 * javac -classpath %JAVALIB%\xercesImpl.jar XmlTreeViewerSax.java
 *
 * 
 * RUN PROGRAM:
 * --------------------------
 * java -classpath $JAVALIB/xercesImpl.jar:. XmlTreeViewerSax Tablespaces.xml
 *
 * java -classpath %JAVALIB%\xercesImpl.jar;. XmlTreeViewerSax Tablespaces.xml
 *
 * -----------------------------------------------------------------------------
 * @version 1.0
 * @author  Jeffrey M. Hunter  (jhunter@idevelopment.info)
 * @author  http://www.idevelopment.info
 * -----------------------------------------------------------------------------
 */
 
public class XmlTreeViewerSax extends JFrame {


    // Default parser to use
    private String vendorParserClass =
        "org.apache.xerces.parsers.SAXParser";

    // The base tree to render
    private JTree jTree;

    // Tree Model to use
    DefaultTreeModel defaultTreeModel;

    public XmlTreeViewerSax() {
        // Handle Swing setup
        super("SAX Tree Viewer");
        setSize(600, 450);
    }


    public void init(String xmlURI)
        throws IOException, SAXException {

        DefaultMutableTreeNode base = new DefaultMutableTreeNode(
                                            "XML Document: " + xmlURI);

        // Build Tree Model
        defaultTreeModel = new DefaultTreeModel(base);
        jTree = new JTree(defaultTreeModel);

        // Construct the tree hierarchy
        buildTree(defaultTreeModel, base, xmlURI);

        // Display the results
        getContentPane().add(new JScrollPane(jTree), BorderLayout.CENTER);

    }


    public void buildTree(    DefaultTreeModel treeModel
                            , DefaultMutableTreeNode base
                            , String xmlURI) 
                            throws IOException, SAXException {

        // Create instances needed for parsing
        XMLReader reader = XMLReaderFactory.createXMLReader(vendorParserClass);

        // Register content handler

        // Register error handler

        // Parse
        InputSource inputSource = new InputSource(xmlURI);
        reader.parse(inputSource);

    }

    
    /**
     * Sole entry point to the class and application.
     * @param args Array of String arguments.
     */
    public static void main(String[] args) {

        if (args.length != 1) {
            System.err.println(
                "Usage: java XmlTreeViewerSax " +
                "[XML Document URI]"
            );
            System.exit(1);
        }

        String documentURI = args[0];

        try {
            XmlTreeViewerSax viewer = new XmlTreeViewerSax();
            viewer.init(documentURI);
            viewer.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
