+------------------------------------------------------------------------------+
|                               README.txt                                     |
|                        JDBC Programming Examples                             |
| ---------------------------------------------------------------------------- |
|                            Jeffrey M. Hunter                                 |
|                        jhunter@idevelopment.info                             |
|                          www.idevelopment.info                               |
| ---------------------------------------------------------------------------- |
|     Copyright (c) 1998-2004 Jeffrey M. Hunter. All rights reserved.          |
|                                                                              |
| All code and material located at the Internet address of                     |
| http://www.idevelopment.info is the copyright of Jeffrey M. Hunter, 2004 and |
| is protected under copyright laws of the United States. This code may        |
| not be hosted on any other site without my express, prior, written           |
| permission. Application to host any of the material elsewhere can be made by |
| contacting me at jhunter@idevelopment.info.                                  |
|                                                                              |
| I have made every effort and taken great care in making sure that the        |
| code included on my web site is technically accurate, but I disclaim any and |
| all responsibility for any loss, damage or destruction of data or any other  |
| property which may arise from relying on it. I will in no case be liable for |
| any monetary damages arising from such loss, damage or destruction.          |
| ---------------------------------------------------------------------------- |
| This README.txt file provides notes on how to configure your operating       |
| system environment to compile, build, and run the JDBC programming examples  |
| provided from the www.idevelopment.info website.                             |
+------------------------------------------------------------------------------+

======================================================
  INSTALLING ANT
======================================================

    All code within the JDBC Programming Examples module can be built using the 
    popular Ant tool.

        1.) Download the latest version of Ant:
    
            The Ant program can be downloaded from http://ant.apache.org/.
            Select the "Download" link for "Binary Distribuions". At the time
            of this writing, I was able to download the file: 
            apache-ant-1.6.2-bin.zip.
    
        2.) Installing the Ant software:
    
            After downloading the archived file, installation is nothing more
            than unzipping the file into any directory. For this example, I
            will be installing Ant 1.6.2 into the root directory of the C:\.
        
                copy apache-ant-1.6.2-bin.zip c:\
                cd \
                unzip apache-ant-1.6.2-bin.zip
        
            This will create the directory C:\apache-ant-1.6.2
        
        3.) Finally, we need to setup a few environment variables:
    
            First, remove any CLASSPATH setting. You will want Ant to take care
            of what it is best at, handling the CLASSPATH settings. Next set the
            following environment variables to the appropriate directories:
        
                ANT_HOME  =  C:\apache-ant-1.6.2
                PATH      =  %ANT_HOME%\bin;%PATH%
        
        4.) Test your Ant installation:
    
            Now that Ant is installed, let's perform a simple test by requesting
            Ant's version. Open a new command prompt and type the following:
        
                C:\> ant -version
                Apache Ant version 1.6.2 compiled on July 16 2004
        
            That's it. Ant is now successfully installed!
        

======================================================
  BUILDING THE CODE
======================================================

    As mentioned already, the JDBC Programming Examples can be built using Ant.
    At this point, Ant should be installed and configured for your operating
    environment. This section describes how to build all examples contained
    within this module.

    Change to the directory that contains this README.txt file (and more
    importantly, the build.xml file).
    
    NOTE: Before attempting to build any of the JDBC Programming Examples, you
          will need to first edit the file "ojvm_loadjava.properties". This file
          defines the connection information that will be used for the 
          loadjava task. You are asked to provide an Oracle database username
          and password (with DBA privileges) along with an Oracle TNS
          service name. The "loadjava" task will fail if the the database
          credentials are not correct.

    Now type in the "ant" command with no arguments. This will run the "usage" 
    task that displays all tasks and what they are responsible for:
    
        C:\> ant

    If you want to build all JDBC Programming examples, run Ant with the 
    'dist' task as follows:
    
        C:\oracle\common\programming\jdbc> ant dist

    If you want to clean out all temporary directories, use the 'clean' task:
    
        C:\oracle\common\programming\jdbc> ant clean


======================================================
  RUNNING THE EXAMPLES  -  (Using Ant Tasks)
======================================================

    The 'dist' task will create a JAR file that includes all JDBC examples for
    this project. The JAR file will be deployed to the <project>/dist directory.
    The build file includes tasks tht can be used to run several of the 
    example JDBC code. Before running any of the JDBC Programming Examples 
    through Any tasks, you will need to first edit the "run.properties" file.
    Open this file and make all Oracle database property changes for your
    environment.
    
    NOTE: In order for the run tasks to work, you need to first edit and
          configure the "run.properties" included with the build.xml file!
    
    Let's walk through some of those tasks.
    
    Run the JDBC Thin Client Example Code:
    
        C:\oracle\common\programming\jdbc> ant -emacs run_thin
    
    Run the JDBC OCI8 Client Example Code: (This require an Oracle Client Install)
    
        C:\oracle\common\programming\jdbc> ant -emacs run_oci
    


======================================================
  RUNNING THE EXAMPLES  -  (Using Command-line)
======================================================

    The 'dist' task will create a JAR file that includes all JDBC examples for
    this project. The JAR file will be deployed to the <project>/dist directory.
    Although the build file for this project has tasks to run each of the
    examples, you can also run them from the command-line as follows:
 
    % cd dist

    +--------------+-----------------------------------------------------------+
    |  Example #1  |         JDBC Thin Client                                  |
    +--------------+-----------------------------------------------------------+
     
    The JDBC Thin Client example application is defined within the Main-Class
    of the manifest file and can be run simply by running the deployed JAR
    file:
     
        % java -jar JdbcExamples-2.0.jar -sid JEFFDB -host melody.idevelopment.info -user scott -password tiger


    +--------------+-----------------------------------------------------------+
    |  Example #2  |         JDBC OCI8 Client                                  |
    +--------------+-----------------------------------------------------------+
     
    In order to run the JDBC OCI8 Client example class, you will need to first
    ensure that you have an Oracle Client Install.
     
        % java -cp JdbcExamples-2.0.jar JdbcExamples.JdbcExampleOCI -tns JEFFDB_MELODY.IDEVELOPMENT.INFO -user scott -password tiger


    +--------------+-----------------------------------------------------------+
    |  Example #3  |         Oracle JVM Test  -  (within the Oracle Database)  |
    +--------------+-----------------------------------------------------------+

        % java -cp JdbcExamples-2.0.jar OracleJVMExamples.TestConnection


    +--------------+-----------------------------------------------------------+
    |  Example #4  |         Oracle JVM Test  -  (outside the Oracle Database) |
    +--------------+-----------------------------------------------------------+

        % sqlplus /nolog
    
        SQL> connect scott/tiger
        
        SQL> set serveroutput on
        SQL> call dbms_java.set_output(5000);
        
        SQL> call oracle_jvm_mgr.show_user();
     

======================================================
  ANT TIPS
======================================================

    1.) When running the "run tasks" (i.e. run_thin) use the Ant option
        '-emacs' to allow Ant to produce output logging information without 
        adornments. (Those annoying [java] prefixes for each line in the 
        output!)
 
            % ant -emacs run_thin
 
    2.) When running the "run tasks" (i.e. run_thin), be careful when using
        the '-q' Ant option. This option tells Ant to be extra quiet, but
        will suppress stdout. It will not, however, suppress stderr.
        
            % ant -emacs -q run_thin

    3.) If you want to really see what Ant is doing when performing tasks,
        you can use the '-v' option. This option provides detailed verbose
        output for things like 'Detecting O/S', 'Detecting JVM to use', 
        'URI for the buildfile', 'Which files are being included, omitted,
        or skipped for a particular task'.

            % ant -v
