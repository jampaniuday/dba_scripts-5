# +----------------------------------------------------------------------------+
# | FILE      : README.txt                                                     |
# | AUTHOR    : Jeffrey Hunter                                                 |
# | WEB       : http://www.iDevelopment.info                                   |
# | CATEGORY  : Java Programming                                               |
# | SUBJECT   : XML                                                            |
# +----------------------------------------------------------------------------+


I. INTRODUCTION

    The following note provides specific information about Java/XML programming.


II. JAVA XML PARSERS

    Today, developers have a wide array of choices when it comes to Java XML 
    parsers. While several of them may require you to purchase them, many more
    are available free over the Internet.

    In the Java Programming XML Section, I will provide example code for each 
    of the Java XML Parsers listed below.


    ==========================
        Xerces2
    ==========================
    
        URL             : http://xml.apache.org
        Latest Version  : 2.2.0
        File(s)         : xercesImpl.jar
                            (Contains the Apache XML Parser)
                          
                          xmlParserAPIs.jar
                            (Contains the DOM and SAX APIs)
        Package         : org.apache.xerces.parser.*
        Download        : http://xml.apache.org/dist/xerces-j/

                          
    ==========================
        Xerces1
    ==========================

        URL             : http://xml.apache.org/xerces-j/index.html
        Latest Version  : 1.4.4
        File(s)         : xerces.jar
                            (Contains the Apache XML Parser and DOM/SAX APIs)
        Package         : org.apache.xerces.parser.*
        Download        : http://xml.apache.org/dist/xerces-j/


    ==========================
        Oracle XML Parser
    ==========================

        URL             : http://otn.oracle.com/tech/xml/content.html
        Latest Version  : 9.2.0.4.0
        File(s)         : xmlparserv2.jar
                            (Contains the Oracle XML Parser and DOM/SAX APIs)
        Package         : oracle.xml.parser.v2.*
        Download        : http://otn.oracle.com/software/tech/xml/xdk_java/content.html


    ==========================
        Crimson
    ==========================

        URL             : http://xml.apache.org/crimson/index.html
        Latest Version  : 1.1.3
        File(s)         : crimson.jar
                            (Contains the Crimson XML Parser and DOM/SAX APIs)
        Package         : org.apache.crimson.parser.*
                          org.apache.crimson.tree.*
        Download        : http://xml.apache.org/dist/crimson/
        Notes           : The Crimson codebase is based on the Sun Project X 
                          parser. It is also the parser currently shipping in 
                          Sun products (including Sun's SDK 1.4 or higher); 
                          however, the future plan is to move to a different 
                          codebase called Xerces Java 2. Xerces 2 is currently
                          under development.


III. ORAXML Utility


    Overview
    ----------------------------------
    
    The Oracle XML Developers Kit (XDK) provides a convenient command-line
    utility to quickly point out errors in an XML file called oraxml. The oraxml
    file is typically installed in $ORACLE_HOME/bin when the XDK is installed. 
    Invoking oraxml is as easy as:

        % oraxml filename.xml

    The results of the command will tell you if your document is "well-formed" 
    or is will print the offending errors to the console.

    If the oraxml command does not work or is simply not found, you can call the
    class directly from the Oracle XML Parser java archive file xmlparserv2.jar.

        % java -classpath xmlparserv2.jar:. oracle.xml.parser.v2.oraxml


    oraxml command-line arguments
    ----------------------------------

    The oraxml class provides a command-line interface to validate XML files 
    java oracle.xml.parser.v2.oraxml options* 

        -help               Prints the help message
        -version            Prints the release version
        -dtd                Validates the input file with DTD validation
        -schema             Validates the input file with Schema validation
        -log <logfile>      Writes the errors/logs to the output file
        -comp               Compresses the input xml file
        -decomp             Decompresses the input compressed file
        -enc                Prints the encoding of the input file
        -warning            Show warnings

    Examples
    ---------------------------------- 
    
    The following example runs the oraxml class directly from  from the 
    command-line using Oracle's XML Parser class. As you can see, the first run
    of the command produced errors with the document. After fixing those errors,
    I ran oraxml one final time to ensure that all errors where corrected.

    % java oracle.xml.parser.v2.oraxml truesource_md_tablespaces.xml

    file:/config/DatabaseInventory.xml<Line 99, Column 36>: XML-0190: (Fatal Error) Whitespace required.
    file:/config/DatabaseInventory.xml<Line 99, Column 36>: XML-0201: (Fatal Error) Expected name instead of /.
    file:/config/DatabaseInventory.xml<Line 99, Column 36>: XML-0137: (Error) Attribute '' used but not declared.
    file:/config/DatabaseInventory.xml<Line 99, Column 36>: XML-0122: (Fatal Error) '=' missing in attribute.
    file:/config/DatabaseInventory.xml<Line 99, Column 37>: XML-0125: (Fatal Error) Attribute value should start with quote.
    Error while parsing input file: truesource_md_tablespaces.xml(Whitespace required.)

    % java oracle.xml.parser.v2.oraxml truesource_md_tablespaces.xml

    The input XML file is parsed without errors using partial validation mode


