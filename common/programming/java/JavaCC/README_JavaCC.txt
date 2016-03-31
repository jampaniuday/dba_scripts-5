# +--------------------------------------------+
# | FILE      : README_JavaCC.txt              |
# | AUTHOR    : Jeffrey Hunter                 |
# | WEB       : http://www.iDevelopment.info   |
# | CATEGORY  : Java Programming               |
# | SUBJECT   : JAVA COMPILERS / PARSERS       |
# +--------------------------------------------+


I. INTRODUCTION
===============

NOTE: JavaCC (a software program developed by Sun Microsystems) is no longer 
available for downloading from its original website. If you wish to obtain a 
current copy of JavaCC access the following Sun Microsystems’ website:

    http://www.experimentalstuff.com/Technologies/JavaCC/

The Java Parser Generator, is a product of Sun Microsystems'

JavaCC 2.1 was jointly released by Metamata Inc. (now a wholly owned subsidiary 
of WebGain, Inc. and Sun Microsystems on August 24, 2001)

Old URL:
    http://www.webgain.com


Oldest URL:
    http://www.metamata.com



II. DESCRIPTION
=============== 

Java Compiler Compiler (JavaCC) is the most popular parser generator for use 
with Java applications. A parser generator is a tool that reads a grammar 
specification and converts it to a Java program that can recognize matches to 
the grammar.

In addition to the parser generator itself, JavaCC provides other standard 
capabilities related to parser generation such as tree building (via a tool 
called JJTree included with JavaCC), actions, debugging, etc. 

The latest release of JavaCC is Version 3.0. 

We’ve had tens of thousands of downloads and estimate serious users in the 
thousands. Our newsgroup comp.compilers.tools.javacc and our mailing list 
together have a few thousand participants.

JavaCC works with any Java VM version 1.2 or greater. It has been certified to 
be 100% Pure Java. JavaCC has been tested on different platforms without any 
special porting requirements. Given that we have seen JavaCC run on 5 or 6 
platforms, we think this is a great testimonial to the “Write Once Run 
Anywhere” aspect of Java. We say this as engineers who have personally 
experienced the benefits of writing applications in Java.



III. DOWNLOADING
================

  URL
  ---
  http://www.experimentalstuff.com/Technologies/JavaCC/

  File Size: 523 KB


IV. INSTALLING 3.0
==================

When you download the JavaCC software, you get a tar.gz file named 
javacc-3.0.tar.gz. Simply download this file to the target directory, gunzip it 
and then untar the contents:

    cp javacc-3.0.tar.gz /usr/local
    cd /usr/local
    gunzip javacc-3.0.tar.gz
    tar xvf javacc-3.0.tar
    ln –s javacc-3.0 javacc

Now, setup your environment to reflect the JavaCC installation:

    JAVA_HOME=/usr/local/java
    export JAVA_HOME

    JAVACC_HOME=/var/www/html/domains/idevelopment/javacc
    export JAVACC_HOME

    PATH=$PATH:$JAVA_HOME/bin:$JAVACC_HOME/bin
    export PATH


V. Installing JavaCC 2.1
========================

When you download the JavaCC software, you get a ZIP file that contains 
JavaCC2_1.class. We offer the ZIP option because some browsers do not work 
well when downloading Java class files. 

JavaCC is packaged for release with the Metamata installer - a fancy pure Java 
installer that can automatically install JavaCC on a variety of platforms automatically and make it ready for use right away.

Once you have JavaCC2_1.class, you simply run it as you would any other Java 
program.

The installer is invoked by running the downloaded class file as a Java program 
as follows:

    java JavaCC2_1

In case you prefer to perform a command line installation, type:

    java JavaCC2_1 -c

NOTE: You should not type the .class at the end! Also make sure your CLASSPATH 
is set correctly. This will lead you through a sequence of screens which 
results in the installation of JavaCC on your machine.


VI. TESTING THE INSTALLATION
============================

Release 2.1 and 3.0

    cd $JAVACC_HOME/examples/SimpleExamples
    javacc Simple1.jj
    javac Simple1.java
    java Simple1

Try typing various different inputs to Simple1.  Remember <control-d> may be 
used to indicate the end of file (this is on the UNIX platform). Here are some 
sample runs:

    % java Simple1
    {{}}<return>
    <control-d>
    %


    % java Simple1
    {x<return>

    Lexical error at line 1, column 2.  Encountered: "x"
    TokenMgrError: Lexical error at line 1, column 2.  Encountered: "x" (120), after : ""
        at Simple1TokenManager.getNextToken(Simple1TokenManager.java:146)
        at Simple1.getToken(Simple1.java:140)
        at Simple1.MatchedBraces(Simple1.java:51)
        at Simple1.Input(Simple1.java:10)
        at Simple1.main(Simple1.java:6)
    %


    % java Simple1
    {}}<return>

    ParseException: Encountered "}" at line 1, column 3.
    Was expecting one of:
      <EOF>
      "\n" ...
      "\r" ...

        at Simple1.generateParseException(Simple1.java:184)
        at Simple1.jj_consume_token(Simple1.java:126)
        at Simple1.Input(Simple1.java:32)
        at Simple1.main(Simple1.java:6)
    %


VII. GETTING STARTED
====================

  To get started, take a look at the "examples" directory. Look at the README 
  files present here for detailed information on how to get started with the 
  examples. 

  The vast majority of our users are able to get productive with JavaCC after 
  going through these examples. The remainder get productive after a couple of 
  visits to the mailing list.


VIII. javacc Command Line Syntax
===============================

  First, you can obtain a synopsis of the command line syntax by simply typing 
  "javacc". This is what you get: 


    % javacc
    Java Compiler Compiler Version 3.0 (Parser Generator)

    Usage:
        javacc option-settings inputfile

    "option-settings" is a sequence of settings separated by spaces.
    Each option setting must be of one of the following forms:

        -optionname=value (e.g., -STATIC=false)
        -optionname:value (e.g., -STATIC:false)
        -optionname       (equivalent to -optionname=true.  e.g., -STATIC)
        -NOoptionname     (equivalent to -optionname=false. e.g., -NOSTATIC)

    Option settings are not case-sensitive, so one can say "-nOsTaTiC" instead
    of "-NOSTATIC".  Option values must be appropriate for the corresponding
    option, and must be either an integer, a boolean, or a string value.

    The integer valued options are:

        LOOKAHEAD              (default 1)
        CHOICE_AMBIGUITY_CHECK (default 2)
        OTHER_AMBIGUITY_CHECK  (default 1)

    The boolean valued options are:

        STATIC                 (default true)
        DEBUG_PARSER           (default false)
        DEBUG_LOOKAHEAD        (default false)
        DEBUG_TOKEN_MANAGER    (default false)
        OPTIMIZE_TOKEN_MANAGER (default true)
        ERROR_REPORTING        (default true)
        JAVA_UNICODE_ESCAPE    (default false)
        UNICODE_INPUT          (default false)
        IGNORE_CASE            (default false)
        COMMON_TOKEN_ACTION    (default false)
        USER_TOKEN_MANAGER     (default false)
        USER_CHAR_STREAM       (default false)
        BUILD_PARSER           (default true)
        BUILD_TOKEN_MANAGER    (default true)
        SANITY_CHECK           (default true)
        FORCE_LA_CHECK         (default false)
        CACHE_TOKENS           (default false)
        KEEP_LINE_COLUMN       (default true)

    The string valued options are:

        OUTPUT_DIRECTORY       (default Current Directory)

    EXAMPLE:
        javacc -STATIC=false -LOOKAHEAD:2 -debug_parser mygrammar.jj
    