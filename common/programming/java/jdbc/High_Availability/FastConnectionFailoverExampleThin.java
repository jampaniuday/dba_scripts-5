// -----------------------------------------------------------------------------
// FastConnectionFailoverExampleThin.java
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

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import oracle.jdbc.pool.OracleDataSource;
import oracle.jdbc.pool.OracleConnectionCacheManager;

/**
 * -----------------------------------------------------------------------------
 * Describe the Fast Connection Failover (FCF) mechanism using Oracle Real
 * Application Clusters (RAC) 10g Release 2 and provide an example of how it 
 * works with the Oracle 10g Release 2 JDBC thin driver (ojdbc14.jar). Given
 * that Fast Connection Failover is driver-independent, no application logical
 * in this class would need to change other than the JDBC URL String
 * (connectionURL in this example) to make use of the OCI driver.
 * 
 * Starting from Oracle Database 11g Release 2 (11.2), the Fast Connection
 * Failover mechanism described in this example has been deprecated, and
 * replaced with Universal Connection Pool (UCP) for JDBC. Oracle recommends
 * that you take advantage of the new architecture, which is  more powerful and
 * offers better performance.
 * 
 * Fast Connection Failover offers a driver-independent way for any Java
 * Database Connectivity (JDBC) application to take advantage of the connection
 * failover facilities offered by Oracle Database 10g. When enabled, Fast
 * Connection Failover provides the following features:
 * 
 *      1. ) Rapid detection and cleanup of invalid cached connections, that is,
 *           DOWN event processing. When an Oracle component fails (i.e. node,
 *           instance, service, etc.), Oracle Clusterware will immediately
 *           publish FAN events via Oracle Notification Service (ONS). When the
 *           JDBC application receives a FAN "DOWN" event from RAC, Fast
 *           Connection Failover will search for and immediately remove any
 *           physical connections associated with that component that it finds
 *           in the implicit connection cache. With Fast Connection Failover
 *           enabled, the JDBC application need not worry about receiving
 *           SQLExceptions with the following Oracle error when retrieving a
 *           connection from the connection cache:
 *           
 *           ORA-17143: Invalid or Stale Connection found in the Connection Cache
 *           
 *           The JDBC application does, however, need to handle the 
 *           SQLException:
 *           
 *           ORA-17008: Closed Connection
 *           
 *           since it may be holding an invalid connection and attempt to do
 *           work through that invalid connection. The JDBC application will
 *           receive a FAN event from the RAC and the Fast Connection Failover
 *           mechanism will automatically start its "down event processing" by
 *           cleaning up the connection cache and removing all invalid cached
 *           connections.
 *           
 *      2.) Load balancing of available connections, that is, UP event
 *          processing. When a failed or new Oracle component is brought
 *          into the cluster (i.e. node, instance, service, etc.), Oracle
 *          Clusterware will immediately publish FAN events via Oracle
 *          Notification Service (ONS). When the JDBC application receives a
 *          FAN "UP" event from RAC, Fast Connection Failover will startup new
 *          connections and balance them accordingly to all available RAC
 *          services.
 *          
 *      3.) Run-time work request distribution to all active RAC instances.
 * 
 * The advantages of Fast Connection Failover include:
 * 
 *      1.) Driver independence
 *      
 *          Fast Connection Failover supports both the JDBC Thin and JDBC Oracle
 *          Call Interface (OCI) drivers.
 *          
 *      2.) Integration with implicit connection cache
 *      
 *          The two features work together synergistically to improve
 *          application performance and high availability.
 *          
 *      3.) Integration with Oracle Real Application Clusters (RAC)
 *      
 *          This provides superior Real Application Clusters/High Availability
 *          event notification mechanisms.
 *          
 *      4.) Easy integration with application code
 *      
 *          You only need to enable Fast Connection Failover and no further
 *          configuration is required.
 * 
 * An application enables Fast Connection Failover by calling
 * setFastConnectionFailoverEnabled(true) on a DataSource instance, before
 * retrieving any connections from that instance.
 * 
 * You cannot enable Fast Connection Failover when reinitializing a connection
 * cache. You must enable it before using the OracleDataSource instance.
 * 
 *      NOTE: After a cache is Fast Connection Failover-enabled, you cannot 
 *            disable Fast Connection Failover during the lifetime of that
 *            cache.
 * 
 * Starting with Oracle 11g, FAN can be used with SQL*Plus. With Oracle RAC 11g,
 * you can specify the -F (FAILOVER) option. This enabled SQL*Plus to interact
 * with the OCI failover mode in a Real Application Cluster (RAC) environment.
 * In this mode, a service or instance failure is transparently handled with
 * transaction status messages if applicable.
 * 
 * The remaining sections in this documentation include step-by-step 
 * instructions to test and configure the Oracle Notification Service (ONS) on
 * both the "server" (the Oracle RAC Database Cluster) and the "client" (the
 * application server running this class) which is required by FCF.
 *      
 * -----------------------------------------------------------------------------
 * SERVICES
 * -----------------------------------------------------------------------------
 * 
 * Introduction:
 * 
 *      Oracle Notification Service (ONS) is part of Oracle Clusterware and is
 *      used to propagate messages between RAC nodes and to application-tiers.
 *      ONS is the foundation for Fast Application Notification (FAN) upon
 *      which Fast Connection Failover (FCF) was built.
 *      
 *      RAC uses FAN to publish configuration changes (state changes) and 
 *      LBA events. Applications can react to those published events in two
 *      ways:
 *      
 *          1.) by using ONS api (you need to program it)
 *          2.) by using FCF (automatic by using JDBC implicit connection cache
 *              on the application server)
 *              
 *      You can also respond to FAN event by using server-side callout but
 *      this is on the server side (as their name suggests it).
 * 
 * Services Explained:
 * 
 *      ONS - Oracle Notification Service
 *      
 *            ONS allows users to send SMS messages, e-mails, voice 
 *            notifications, and fax messages in an effortless manner. Oracle
 *            Clusterware uses ONS to send notifications about the state of the
 *            database instances and other critical services to client (or 
 *            middle tier) applications that use this information for 
 *            load-balancing and for fast failure detection.
 *            
 *            ONS is a daemon process that communicates with other ONS daemons 
 *            (on local or remote nodes) which inform each other of the current
 *            state of the database components on a server. For example, if a
 *            listener, instance, node, or service is down, a "down" event is
 *            triggered by the EVMD process, which is then sent by the local
 *            ONS daemon to the ONS daemon process on other nodes, including
 *            all clients and application servers subscribed (registered) in the
 *            configuration.
 *            
 *            ONS gets installed and configured automatically with Oracle
 *            Clusterware as part of a RAC install. Any client node or middle
 *            tier node will need to have Oracle installed (typically the Oracle
 *            client).
 *            
 *      FAN - Fast Application Notification
 *      
 *            As stated before, ONS communicates all events generated by the
 *            EVMD process to all nodes registered with the ONS. FAN is a
 *            notification mechanism that RAC uses to notify other processes
 *            about configuration and service level information which includes
 *            service state changes, such as UP, DOWN, or RESTART events.
 *            FAN uses ONS for server-to-server and server-to-client
 *            notification of these events. ONS is required by FAN.
 *            
 *            RAC publishes the FAN events the minute any changes are made to
 *            the cluster. So, instead of waiting for the application to check
 *            on individual nodes to detect an anomaly, the applications are
 *            asynchronously notified by FAN events and are able to react
 *            immediately.
 *            
 *            FAN also publishes Load Balancing Advisory (LBA) events. 
 *            Applications are in a position to take full advantage of the
 *            LBA FAN events to facilitate a smooth transition of connections
 *            to healthier nodes in the cluster.
 *            
 *            In addition, FAN can also be implemented with Server-Side Callouts
 *            on the database (RAC) tier.
 *            
 *            SERVER-SIDE CALLOUTS:
 *            
 *                  Oracle Real Application Clusters High Availability
 *                  Extensions (RACX) utilizes events for notifications about
 *                  state changes in the system that may be of interest to other
 *                  components in the system or to the user. Some of the events
 *                  are informational that merely informs user something
 *                  happening in the system. Some of the events are action
 *                  driven that require administrator or other application 
 *                  programs to take appropriate actions to correct the changes
 *                  because the state changes in the system will likely affect
 *                  other applications. Some examples of such events include
 *                  database or instance up/down, service up/down, node down 
 *                  etc. Oracle RAC supports the infrastructure that allows 
 *                  users to define their own event callout programs. These 
 *                  programs will be invoked by Oracle RAC system when certain 
 *                  events occur. Oracle RAC passes necessary information as 
 *                  arguments to these callouts such as the related node name, 
 *                  service name, instance name, database name etc. The user 
 *                  callout programs will parse these information and pass them
 *                  to other user's application or system administrator. The 
 *                  user callout programs are expected to understand the 
 *                  communication protocol used by other user applications.
 *                  The user callout programs should be placed in the following 
 *                  directory: $ORA_CRS_HOME/racg/usrco. Oracle RAC will only 
 *                  invoke the callouts placed in this location.
 *                  
 *                  For more information on creating event user callout
 *                  programs, consult the file: $ORA_CRS_HOME/racg/README
 *            
 *      FCF - Fast Connection Failover
 *      
 *            Uses FAN "down" events to automatically and rapidly detect and 
 *            cleanup any invalid connections found in the connection cache.
 *            Uses FAN "up" events to load balance available connections. FCF
 *            also responds to runtime work requests to distribute connections
 *            to all available RAC nodes.
 * 
 *      How these services work together:
 *      
 *            [ONS]   <---requires---   [FAN]   <---requires---   [FCF]
 * 
 * -----------------------------------------------------------------------------
 * FAST CONNECTION FAILOVER PREREQUISITES
 * -----------------------------------------------------------------------------
 * 
 *      Fast connection failover is available under the following circumstances:
 *
 *      1.) The implicit connection cache is enabled. Fast Connection Failover
 *          works in conjunction with the JDBC connection caching mechanism.
 *          This helps applications manage connections to ensure high
 *          availability.
 * 
 *      2.) The application uses service names to connect to the database; the
 *          application cannot use service IDs (ORACLE_SID). Use a service name
 *          rather than an SID when setting the OracleDataSource url property.
 *          
 *      3.) The underlying database has Release 10 (10.1) or Release 10 (10.2)
 *          Real Application Clusters (RAC) capability. If failover events are
 *          not propagated, connection failover cannot occur.
 *          
 *      4.) Oracle Notification Service (ONS) is configured and available on the
 *          node where JDBC is running and on the Oracle RAC. JDBC depends on
 *          ONS to propagate database events from RAC and notify JDBC of them.
 *          If ONS is not correctly set up, then implicit connection cache
 *          creation fails and an ONSException is thrown at the first
 *          getConnection request.
 *          
 *      5.) The JVM in which your JDBC instance is running must have
 *          oracle.ons.oraclehome set to point to your ORACLE_HOME. For example:
 *          
 *          -Doracle.ons.oraclehome=C:\oracle\product\10.2.0\db_1
 *          
 *      6.) Set the FastConnectionFailoverEnabled property before making the
 *          first getConnection request to an OracleDataSource. When Fast
 *          Connection Failover is enabled, the failover applies to all 
 *          connections in the connection cache. If your application explicitly
 *          creates a connection cache using the Connection Cache Manager, then
 *          you must first set FastConnectionFailoverEnabled before retrieving
 *          any connections.
 *          
 *      7.) The ons.jar must be part of the CLASSPATH for the application.
 *          The ons.jar can be found in the Oracle Client installation.
 *  
 * -----------------------------------------------------------------------------
 * IMPLICIT CONNECTION CACHING
 * -----------------------------------------------------------------------------
 *      
 *      The Fast Connection Failover mechanism depends on the "Implicit 
 *      Connection Cache". As a result, for Fast Connection Failover to be 
 *      available, implicit connection caching must be enabled.
 *      
 *      Connection caching, generally implemented in the middle tier, is a means
 *      of keeping and using caches of physical database connections.
 *      
 *          NOTE: The connection caching architecture in Oracle 10g has been
 *                redesigned so that caching is transparently integrated into 
 *                the datasource architecture. Oracle's previous cache
 *                architecture, based on OracleConnectionCache and
 *                OracleConnectionCacheImpl, is deprecated. Oracle recommends
 *                that you take advantage of the new architecture, which is more
 *                powerful and offers better performance.
 *      
 *      The Implicit Connection Cache is an improved JDBC 3.0-compliant 
 *      connection cache implementation for DataSource. Java and J2EE 
 *      applications benefit from transparent access to the cache, support for 
 *      multiple users, and the ability to request connections based on 
 *      user-defined profiles.
 *      
 *      An application turns the implicit connection cache on by invoking 
 *      setConnectionCachingEnabled(true) on an OracleDataSource:
 *      
 *          OracleDataSource.setConnectionCachingEnabled(true)
 *      
 *      After implicit caching is turned on, the first connection request to the 
 *      OracleDataSource transparently creates a connection cache. There is no 
 *      need for application developers to write their own cache 
 *      implementations.
 *      
 *      The connection cache uses the concept of physical connections and 
 *      logical connections. Physical connections are the actual connections 
 *      returned by the database; logical connections are wrappers used by the
 *      cache to manipulate physical connections. You can think of logical
 *      connections as handles. The caches always return logical connections,
 *      which implement all the same interfaces as physical connections.
 *      
 *      The implicit connection cache offers:
 *      
 *          (*) Driver independence.
 *          
 *              Both the Thin and OCI drivers support the Implicit Connection
 *              Cache.
 *              
 *          (*) Transparent access to the JDBC connection cache.
 *          
 *              After an application turns implicit caching on, it uses the 
 *              standard OracleDataSource APIs to get connections. With caching
 *              enabled, all connection requests are serviced from the 
 *              connection cache.
 *              
 *              When an application invokes OracleConnection.close() to close
 *              the logical connection, the physical connection is returned to
 *              the cache.
 *              
 *          (*) Single cache per OracleDataSource instance.
 *          
 *              When connection caching is turned on, each OracleDataSource
 *              has exactly one cache associated with it. All connections
 *              obtained through that datasource, no matter what username and
 *              password are used, are returned to the cache. When an
 *              application requests a connection from the datasource, the cache
 *              either returns an existing connection or creates a new 
 *              connection with matching authentication information.
 *              
 *              NOTE: Caches cannot be shared between DataSource instances;
 *                    there is a one-to-one mapping between a DataSource
 *                    instance and a cache.
 *                    
 *          (*) Heterogeneous usernames and passwords per cache.
 *          
 *              Unlike in the previous cache implementation, all connections
 *              obtained through the same datasource are stored in a common
 *              cache, no matter what username and password the connection
 *              requests.
 *              
 *          (*) Support for JDBC 3.0 connection caching, including support for
 *              multiple users and the required cache properties.
 *              
 *          (*) Property-based configuration.
 *          
 *              Cache properties define the behavior of the cache. The supported
 *              properties set timeouts, the number of connections to be held in
 *              the cache, and so on. Using these properties, applications can
 *              reclaim and reuse abandoned connections. The implicit connection
 *              cache supports all the JDBC 3.0 connection cache properties.
 *              
 *          (*) OracleConnectionCacheManager.
 *          
 *              The new class OracleConnectionCacheManager provides a rich set
 *              of administrative APIs applications can use to manage the 
 *              connection cache. Using these APIs, applications can refresh
 *              stale connections. Each Virtual Machine has one distinguished
 *              instance of OracleConnectionCacheManager. Applications manage a
 *              cache through the single OracleConnectionCacheManager instance.
 *              
 *              NOTE: The cache name is not a cache property and cannot be
 *                    changed once the cache is created.
 *                    
 *          (*) User-defined connection attributes.
 *          
 *              The implicit connection cache supports user-defined connection
 *              attributes that can be used to determine which connections are
 *              retrieved from the cache. Connection attributes can be thought
 *              of as labels whose semantics are defined by the application, not
 *              by the caching mechanism.
 *              
 *          (*) Callback mechanism.
 *          
 *              The implicit connection cache provides a mechanism for users to
 *              define cache behavior when a connection is returned to the 
 *              cache, when handling abandoned connections, and when a 
 *              connection is requested but none is available in the cache.
 * 
 * -----------------------------------------------------------------------------
 * CONFIGURE ONS ON ORACLE RAC NODES
 * -----------------------------------------------------------------------------
 * 
 *      1.) Verify ONS Running on RAC Nodes
 *      
 *          Oracle RAC (more specifically, Oracle Clusterware) publishes Fast
 *          Application Notification (FAN) events via ONS. Verify ONS is running
 *          on all nodes in the database cluster using the "onsctl ping"
 *          command.
 *          
 *          NOTE: Make certain to run the "onsctl ping" command using the
 *                Oracle Clusterware home (i.e. $ORA_CRS_HOME/bin/onsctl):
 *     
 *              [oracle@thing1 ~]$ $ORA_CRS_HOME/bin/onsctl ping
 *              Number of onsconfiguration retrieved, numcfg = 2
 *              onscfg[0]
 *                  {node = thing1.idevelopment.info, port = 6200}
 *              Adding remote host thing1.idevelopment.info:6200
 *              onscfg[1]
 *                 {node = thing1.idevelopment.info, port = 6200}
 *              Adding remote host thing1.idevelopment.info:6200
 *              ons is running ...
 *              
 *              [oracle@thing2 ~]$ $ORA_CRS_HOME/bin/onsctl ping
 *              Number of onsconfiguration retrieved, numcfg = 2
 *              onscfg[0]
 *                  {node = thing1.idevelopment.info, port = 6200}
 *              Adding remote host thing1.idevelopment.info:6200
 *              onscfg[1]
 *                  {node = thing1.idevelopment.info, port = 6200}
 *              Adding remote host thing1.idevelopment.info:6200
 *              ons is running ...
 *
 *      2.) Verify Services Created / Running on RAC Nodes
 *      
 *          If the JDBC application is using the OCI driver, verify a service
 *          has been created with DBCA or SRVCTL and is enabled on at least one
 *          (preferably multiple nodes) in the RAC cluster. This service is not
 *          required when using the JDBC thin driver. Use the "crs_stat" command
 *          to check that Oracle Clusterware is managing the service:
 *          
 *              [oracle@thing1 ~]$ $ORA_CRS_HOME/bin/crs_stat | grep '\.srv'
 *              NAME=ora.thingdb.thingdb_srvc.thingdb1.srv
 *              NAME=ora.thingdb.thingdb_srvc.thingdb2.srv
 * 
 *      3.) Server-Side Connection Load Balancing
 *      
 *          If the JDBC application is using the OCI driver, verify that
 *          Server-Side Load Balancing is configured for the above mentioned
 *          service. First, make certain the VIP (thing1-vip and thing2-vip in
 *          this example) is the hostname used for each node in the listener.ora
 *          file. DBCA should have already performed this task:
 *          
 *              # ------------------------------
 *              # NODE: thing1.idevelopment.info
 *              # ------------------------------
 *              
 *              LISTENER_THING1 =
 *                (DESCRIPTION_LIST =
 *                  (DESCRIPTION =
 *                    (ADDRESS = (PROTOCOL = TCP)(HOST = thing1-vip.idevelopment.info)(PORT = 1521)(IP = FIRST))
 *                    (ADDRESS = (PROTOCOL = TCP)(HOST = 192.168.1.211)(PORT = 1521)(IP = FIRST))
 *                  )
 *                )
 *              
 *              SID_LIST_LISTENER_THING1 =
 *                (SID_LIST =
 *                  (SID_DESC =
 *                    (SID_NAME = PLSExtProc)
 *                    (ORACLE_HOME = /u01/app/oracle/product/10.2.0/db_1)
 *                    (PROGRAM = extproc)
 *                  )
 *                )
 *              
 *              # ------------------------------
 *              # NODE: thing2.idevelopment.info
 *              # ------------------------------
 * 
 *              LISTENER_THING2 =
 *                (DESCRIPTION_LIST =
 *                  (DESCRIPTION =
 *                    (ADDRESS = (PROTOCOL = TCP)(HOST = thing2-vip.idevelopment.info)(PORT = 1521)(IP = FIRST))
 *                    (ADDRESS = (PROTOCOL = TCP)(HOST = 192.168.1.212)(PORT = 1521)(IP = FIRST))
 *                  )
 *                )
 *              
 *              SID_LIST_LISTENER_THING2 =
 *                (SID_LIST =
 *                  (SID_DESC =
 *                    (SID_NAME = PLSExtProc)
 *                    (ORACLE_HOME = /u01/app/oracle/product/10.2.0/db_1)
 *                    (PROGRAM = extproc)
 *                  )
 *                )
 * 
 *          After verifying the contents of the listener.ora file, ensure that
 *          all VIPs are resolvable from all nodes in the RAC cluster AND all
 *          client or middle tiers being used. Oracle recommends that the VIPs
 *          for all RAC nodes are defined in DNS however if this is not possible,
 *          then they must be defined in the /etc/hosts file for any 
 *          client/mid-tier being used.
 *          
 *          Finally, ensure the LOCAL_LISTENER and REMOTE_LISTENER parameters
 *          are set in the initialization parameters. Your LOCAL_LISTENER
 *          parameter may be blank if you are using the default port 1521.
 *          
 * -----------------------------------------------------------------------------
 * ONS CONFIGURATION (Overview)
 * -----------------------------------------------------------------------------
 * 
 *      ONS configuration is controlled by the ONS configuration file, 
 *      ORACLE_HOME/opmn/conf/ons.config. This file tells the ONS daemon details
 *      about how it should behave and who it should talk to. Configuration 
 *      information within ons.config is defined in simple name/value pairs. 
 *      There are three values that should always be configured within 
 *      ons.config. The first is localport, the port that ONS binds to on the 
 *      localhost interface (127.0.0.1) to talk to local clients. An example of
 *      the localport configuration is:
 *
 *          localport=6101
 *
 *      The second value is remoteport, the port that ONS binds to on all 
 *      interfaces for talking to other ONS daemons. An example of the 
 *      remoteport configuration is:
 *
 *          remoteport=6200
 *
 *      The third value specifies nodes; a list of other ONS daemons to talk to.
 *      Node values are given as a comma-delimited list of either hostnames or 
 *      IP addresses plus ports. Note that the port value that is given is the 
 *      remote port that each ONS instance is listening on. In order to maintain
 *      an identical file on all nodes, the host:port of the current ONS node 
 *      can also be listed in the nodes list. It will be ignored when reading 
 *      the list.
 *      
 *      NOTE: The nodes listed in the "nodes" line are public node addresses and
 *            not VIP addresses.
 *
 *      The nodes listed in the nodes line correspond to the individual nodes in
 *      the RAC instance. Listing the nodes ensures that the middle-tier node 
 *      can communicate with the RAC nodes. At least one mid-tier node and one 
 *      node in the RAC instance must be configured to see one another. As long
 *      as one node on each side is aware of the other, all nodes are visible.
 *      You need not list every single cluster and middle-tier node in the ONS
 *      config file of each RAC node. In particular, if one ONS config file 
 *      cluster node is aware of the middle tier, then all nodes in the cluster
 *      are aware of it.
 *
 *      An example of the nodes configuration is:
 *
 *          nodes=myhost.example.com:6200,123.123.123.123:6200
 *
 *      There are also several optional values that can be provided in 
 *      ons.config. The first optional value is a loglevel. This specifies the 
 *      level of messages that should be logged by ONS. This value is an integer
 *      that ranges from 1, which indicates least messages logged, to 9, which 
 *      indicates most messages logged. The default value is 3. An example is:
 *
 *          loglevel=3
 *
 *      The second optional value is a logfile name. This specifies a log file
 *      that ONS should use for logging messages. The default value for logfile
 *      is $ORACLE_HOME/opmn/logs/ons.log. An example is:
 *
 *          logfile=/private/oraclehome/opmn/logs/myons.log
 *
 *      The third optional value is a walletfile name. A wallet file is used by
 *      the Oracle Secure Sockets Layer (SSL) to store SSL certificates. If a 
 *      wallet file is specified to ONS, it will use SSL when communicating with
 *      other ONS instances and require SSL certificate authentication from all
 *      ONS instances that try to connect to it. This means that if you want to
 *      turn on SSL for one ONS instance, then you must turn it on for all 
 *      instances that are connected. This value should point to the directory
 *      where your ewallet.p12 file is located. An example is:
 *
 *          walletfile=/private/oraclehome/opmn/conf/ssl.wlt/default
 *
 *      One optional value is reserved for use on the server side (the RAC 
 *      side). useocr=on is used to tell ONS to store all RAC nodes and port
 *      numbers in Oracle Cluster Registry (OCR) instead of in the ONS
 *      configuration file. This option is not available on the client side!
 *
 *      The ons.config file allows blank lines and comments on lines that begin
 *      with #.
 * 
 * -----------------------------------------------------------------------------
 * CLIENT-SIDE ONS CONFIGURATION
 * -----------------------------------------------------------------------------
 * 
 *      You can access the client-side ONS through ORACLE_HOME/opmn. On the 
 *      client side, there are two ways to set up ONS:
 *      
 *      (1) Remote ONS configuration
 *      
 *          The advantages of remote ONS subscription are:
 *          
 *              - Support for an All Java mid-tier stack.
 *              - No ONS daemon needed on the client computer and, therefore, no
 *                need to manage this process.
 *              - Simple configuration using the DataSource property.
 *          
 *          When using remote ONS subscription for Fast Connection Failover, the
 *          application invokes the following method on an OracleDataSource
 *          instance:
 *          
 *              setONSConfiguration(String remoteONSConfig)
 *              
 *          The remoteONSConfig parameter is a list of name/value pairs of the
 *          form name=value that are separated by a new line character (\n).
 *          name can be one of nodes, walletfile, or walletpassword. This
 *          parameter should specify at least the "nodes" ONS configuration
 *          attribute, which is a list of host:port pairs separated by comma
 *          (,). The hosts and ports denote the remote ONS daemons available on
 *          the RAC nodes.
 *          
 *          SSL could be used in communicating with the ONS daemons when the
 *          walletfile attribute is specified as an Oracle wallet file. In such
 *          cases, if the walletpassword attribute is not specified, single
 *          sign-on (SSO) would be assumed.
 * 
 *          Following are a few examples, assuming ods is an OracleDataSource
 *          instance:
 *          
 *              ods.setONSConfiguration("nodes=thing1.idevelopment.info:6200,thing2.idevelopment.info:6200");
 *              
 *              ods.setONSConfiguration("nodes=thing1.idevelopment.info:6200,thing2.idevelopment.info:6200\nwalletfile=/mydir/Wallet\nwalletpassword=mypasswd");
 *              
 *              ods.setONSConfiguration("nodes=thing1.idevelopment.info:6200,thing2.idevelopment.info:6200\nwalletfile=/mydir/conf/Wallet");
 *          
 *          Note:   The ons.jar must be in the CLASSPATH on the client. In the
 *                  case of Oracle Application Server, ONS is embedded in OPMN,
 *                  as before, and JDBC Fast Connection Failover continues to
 *                  work as before.
 *      
 *      (2) ONS daemon on the client side
 * 
 *          Example ons.config file for a client:
 *      
 *          # ------------------------------------------------
 *          # FILE: ORACLE_HOME/opmn/conf/ons.config
 *          # This is an example ons.config file for a client
 *          # ------------------------------------------------
 *          localport=6101 
 *          remoteport=6200 
 *          loglevel=3
 *          nodes=thing1.idevelopment.info:6200,thing2.idevelopment.info:6200
 *      
 *      After configuring ONS, you start the ONS daemon with the onsctl command.
 *      It is the user's responsibility to make sure that an ONS daemon is 
 *      running at all times. To start ONS on the client, run "onsctl start".
 *      For example, to start the ONS daemon on the host packmule.idevelopment.info,
 *      run the following:
 *      
 *          [oracle@packmule ~]$ onsctl start
 *          onsctl: ons started
 * 
 *      OTHER onsctl COMMANDS:
 *      
 *      Command    Effect                               Output 
 *      --------- ------------------------------------- -----------------------------------
 *      start     Starts the ONS daemon                 onsctl: ons started
 *      stop      Stops the ONS daemon                  onsctl: shutting down ons daemon...
 *      ping      Verifies whether the ONS daemon is    ons is running ...
 *                running
 *      reconfig  Triggers a reload of the ONS
 *                configuration without shutting down
 *                the ONS daemon
 *      help      Prints a help summary message for
 *                onsctl
 *      detailed Prints a detailed help message for
 *               onsctl
 * 
 * NOTE: This example uses the "ONS daemon on the client side" configuration
 *       which means each client running this class would require ONS to be
 *       installed and configured.
 * 
 * -----------------------------------------------------------------------------
 * SERVER-SIDE ONS CONFIGURATION USING racgons
 * -----------------------------------------------------------------------------
 * 
 *      You can access the server-side ONS (the RAC servers) through
 *      ORA_CRS_HOME/opmn. You configure the server side by using racgons to add
 *      the middle-tier node information (the application server machines) to
 *      OCR. This command is found in ORA_CRS_HOME/bin/racgons for Oracle 
 *      Clusterware. Before using racgons, you must edit ons.config to set
 *      useocr=on.
 *      
 *          # ------------------------------------------------
 *          # FILE: ORA_CRS_HOME/opmn/conf/ons.config
 *          # This is an example ons.config file for a server
 *          # ------------------------------------------------
 *          localport=6113
 *          remoteport=6200
 *          loglevel=3
 *          useocr=on
 *      
 *      The middle-tier nodes (the application server machines) should be
 *      configured in OCR, so that all nodes share the configuration, and no 
 *      matter which RAC nodes are up, they can communicate to the mid-tier.
 *      When running on a cluster, always configure the ONS hosts and ports not
 *      by using the ONS configuration files but using racgons. The racgons
 *      command stores the ONS hosts and ports in OCR where every node can see
 *      it. That way, you don't need to edit a file on every node to change the 
 *      configuration, just run a single command (racgons) on one of the cluster
 *      nodes.
 * 
 *      NOTE: In Oracle 10g Release 1 (10.1) it is not recommended to use the
 *            parameter useocr=on to store configuration information in the
 *            Oracle Cluster Registry (OCR). Also, the useocr is only valid on a
 *            RAC server. All client and middle-tier nodes should be
 *            configured using an ons.config file as demonstrated in the 
 *            previous section. This example uses Oracle 10g Release 2 (10.2)
 *            and will therefore use useocr=on to store the middle-tier nodes in
 *            the OCR.
 *            
 *      The racogns command enables you to specify hosts and ports for the
 *      middle-tier nodes from one node and then propagate your changes among
 *      all nodes in a cluster. The command takes two forms:
 * 
 *          racgons add_config hostname:port [hostname:port] [hostname:port] ...
 *          racgons remove_config hostname[:port] [hostname:port] [hostname:port] ...
 *          
 *      The add_config version adds the listed hostname(s), the remove_config
 *      version removes them. Both commands propagate the changes among all
 *      instances in a cluster.
 *      
 *      If multiple port numbers are configured for a host, the specified port
 *      number is removed from hostname. If only hostname is specified, all
 *      port numbers for that host are removed.
 *      
 *      Add the client / mid-tier node(s) to the OCR. This only needs to be
 *      performed one time from any node in the cluster:
 *      
 *          NOTE: Make certain to run the racgons command using the Oracle
 *                Clusterware home (i.e. $ORA_CRS_HOME/bin/racgons):
 *      
 *          [oracle@thing1 ~]$ $ORA_CRS_HOME/bin/racgons add_config packmule.idevelopment.info:6200
 *          
 *          [oracle@thing1 ~]$ $ORA_CRS_HOME/bin/onsctl ping
 *          Number of onsconfiguration retrieved, numcfg = 3
 *          onscfg[0]
 *             {node = thing1.idevelopment.info, port = 6200}
 *          Adding remote host thing1.idevelopment.info:6200
 *          onscfg[1]
 *             {node = thing2.idevelopment.info, port = 6200}
 *          Adding remote host thing2.idevelopment.info:6200
 *          onscfg[2]
 *             {node = packmule.idevelopment.info, port = 6200}
 *          Adding remote host packmule.idevelopment.info:6200
 *          ons is running ...
 *          
 *          [oracle@thing2 ~]$ $ORA_CRS_HOME/bin/onsctl ping
 *          Number of onsconfiguration retrieved, numcfg = 3
 *          onscfg[0]
 *             {node = thing1.idevelopment.info, port = 6200}
 *          Adding remote host thing1.idevelopment.info:6200
 *          onscfg[1]
 *             {node = thing2.idevelopment.info, port = 6200}
 *          Adding remote host thing2.idevelopment.info:6200
 *          onscfg[2]
 *             {node = packmule.idevelopment.info, port = 6200}
 *          Adding remote host packmule.idevelopment.info:6200
 *          ons is running ...
 * 
 * -----------------------------------------------------------------------------
 * UNDERSTANDING FAST CONNECTION FAILOVER
 * -----------------------------------------------------------------------------
 * 
 *      After Fast Connection Failover is enabled, the mechanism is automatic;
 *      no application intervention is needed. This section discusses how a
 *      connection failover is presented to an application and what steps the
 *      application takes to recover.
 *      
 *      --------------------------
 *      What The Application Sees
 *      --------------------------
 *      
 *      When a RAC service failure is propagated to the JDBC application, the
 *      database has already rolled back the local transaction. The cache
 *      manager then cleans up all invalid connections. When an application
 *      holding an invalid connection tries to do work through that connection,
 *      it receives SQLException, ORA-17008, Closed Connection.
 *      
 *      When an application receives a Closed Connection error message
 *      (ORA-17008), it should:
 *      
 *      1. Retry the connection request. This is essential, because the old
 *         connection is no longer open.
 *         
 *      2. Replay the transaction. All work done before the connection was
 *         closed has been lost.
 *      
 *         NOTE: The application should not try to roll back the transaction.
 *               The transaction was already rolled back in the database by the
 *               time the application received the exception.
 *               
 *         NOTE: The following are my observations and may or may not be
 *               accurate!!!!
 *               
 *               When using simple Connection Failover (before using Fast
 *               Connection Failover), it would be necessary to trap and handle
 *               the error:
 *                      
 *                      ORA-17143: Invalid or Stale Connection found in the Connection Cache
 *                      
 *               I would then call:
 *               
 *                      OracleConnectionCacheManager.getConnectionCacheManagerInstance().refreshCache(cacheName, OracleConnectionCacheManager.REFRESH_ALL_CONNECTIONS);
 *                      
 *               FCF is doing the work of cleaning up any stale / invalid
 *               connections in the cache - there is no need to call refresh the
 *               cache.
 *               
 *      --------------------------
 *      How FCF Works
 *      --------------------------
 *      
 *      Under Fast Connection Failover, each connection in the cache maintains
 *      a mapping to a service, instance, database, and hostname.
 *      
 *      When a database generates a RAC event, that event is forwarded to the
 *      JVM in which JDBC is running. A daemon thread inside the JVM receives
 *      the RAC event and passes it on to the Connection Cache Manager. The 
 *      Connection Cache Manager then throws SQL exceptions to the applications
 *      affected by the RAC event.
 *      
 *      A typical failover scenario may work like this:
 *      
 *      1. A database instance fails, leaving several stale connections in the
 *         cache.
 *          
 *      2. The RAC mechanism in the database generates a RAC event which is sent
 *         to the JVM containing JDBC.
 *      
 *      3. The daemon thread inside the JVM finds all the connections affected
 *         by the RAC event, notifies them of the closed connection through SQL
 *         exceptions (Oracle error: ORA-17008), and rolls back any open
 *         transactions.
 *         
 *      4. Each individual connection receives a SQL exception (ORA-17008) and
 *         must retry.
 *         
 * -----------------------------------------------------------------------------
 * COMPARISON OF FAST CONNECTION FAILOVER AND TAF
 * -----------------------------------------------------------------------------
 * 
 *      Fast Connection Failover differs from Transparent Application Failover
 *      (TAF) in the following ways:
 *      
 *      1. Application-level connection retries
 *      
 *         Fast Connection Failover supports application-level connection
 *         retries. This gives the application control of responding to
 *         connection failovers. The application can choose whether to retry
 *         the connection or to rethrow the exception. TAF supports connection
 *         retries only at the OCI/Net layer.
 *         
 *      2. Integration with the implicit connection cache
 *      
 *         Fast Connection Failover is well-integrated with the implicit
 *         connection cache, which allows the connection cache manager to manage
 *         the cache for high availability. For example, failed connections are
 *         automatically invalidated in the cache. TAF works at the network
 *         level on a per-connection basis, which means that the connection
 *         cache cannot be notified of failures.
 *         
 *      3. Event-based
 *      
 *         Fast Connection Failover is based on the RAC event mechanism. This
 *         means that Fast Connection Failover is efficient and detects failures
 *         quickly for both active and inactive connections.
 *         
 *      4. Load-balancing support
 *      
 *         Fast Connection Failover supports UP event load balancing of
 *         connections and run-time work request distribution across active RAC
 *         instances.
 *         
 *      NOTE: Oracle recommends not to use TAF and Fast Connection Failover in
 *            the same application.
 *            
 * -----------------------------------------------------------------------------
 * RUNNING THIS EXAMPLE PROGRAM
 * -----------------------------------------------------------------------------
 * 
 *      +--------------------------------+
 *      | SET THE CLASSPATH              |
 *      +--------------------------------+
 *      [oracle@packmule src]$ export CLASSPATH=.:/u01/app/oracle/product/10.2.0/db_1/jdbc/lib/ojdbc14.jar:/u01/app/oracle/product/10.2.0/db_1/opmn/lib/ons.jar
 *      
 *      +--------------------------------+
 *      | COMPILE                        |
 *      +--------------------------------+
 *      [oracle@packmule src]$ javac FastConnectionFailoverExampleThin.java
 *      
 *      +--------------------------------+
 *      | RUN                            |
 *      +--------------------------------+
 *      [oracle@packmule src]$ java -Doracle.ons.oraclehome=/u01/app/oracle/product/10.2.0/db_1 FastConnectionFailoverExampleThin
 *      
 *      ------------------------------------------------------------------------
 *      
 *      When this program starts, it will get two database connections from the 
 *      connection cache (conn1 and conn2). The connection cache should now
 *      reflect two connections as ACTIVE while two less connections are 
 *      AVAILABLE. The application logic in this demonstration will simply 
 *      select a singe row from the database, display it, and then sleep for a
 *      specified period. This logic will be coded in a loop that iterates [n] 
 *      number of times. While this program is running, I will take down one of
 *      two RAC instances using the abort option:
 * 
 *          srvctl stop instance -i thingdb1 -d thingdb -o abort
 *      
 *      The JDBC application will receive a FAN event from the RAC and the Fast
 *      Connection Failover mechanism will automatically start its "down event 
 *      processing" by cleaning up the connection cache and removing all invalid
 *      cached connections. It will also throw a SQLException, ORA-17008, Closed
 *      Connection for any process holding an invalid connection that tries to
 *      do work through that connection.
 * 
 *      The output from this application after failing one of the RAC instances
 *      would be similar to:
 *      
 *          ---------------------------------------------
 *          COUNT: [5]
 *          ---------------------------------------------
 *          ODS Pool: Active (2)  Available (18)
 *          Connected to thingdb1(1) on node thing1 at 13-APR-2011 18:06:21
 *          
 *          ---------------------------------------------
 *          COUNT: [6]
 *          ---------------------------------------------
 *          ODS Pool: Active (0)  Available (11)
 *          
 *          +-------------------------------+
 *          | SQL Exception in loop program |
 *          +-------------------------------+
 *          SQL Error Code    : 17008
 *          SQL Error Message : Closed Connection
 *          
 *          SQL recoverable exception.
 *          Will try to re-connect.
 *          
 *          ODS connection cache before getting a new connection.
 *          ODS Pool: Active (0)  Available (11)
 *          ODS connection cache after a getting new connection.
 *          ODS Pool: Active (1)  Available (10)
 *          
 *          ---------------------------------------------
 *          COUNT: [7]
 *          ---------------------------------------------
 *          ODS Pool: Active (1)  Available (10)
 *          Connected to thingdb2(2) on node thing2 at 13-APR-2011 18:06:41
 *       
 *      The output above shows that all invalid cached connections were
 *      destroyed and removed from the connection cache by FCF. After a few
 *      minutes, I started the failed RAC instance back up:
 * 
 *          srvctl start instance -i thingdb1 -d thingdb
 * 
 *      Again, RAC throws a FAN event to the JDBC application where it is
 *      received and Fast Connection Failover automatically starts its "up event
 *      processing" by starting new connections and balancing them accordingly
 *      to all available RAC services. The output below shows that the failed
 *      RAC instance came up between count 19 and count 20 and added 4 new
 *      connections to the pool:
 * 
 *          ---------------------------------------------
 *          COUNT: [19]
 *          ---------------------------------------------
 *          ODS Pool: Active (1)  Available (10)
 *          Connected to thingdb2(2) on node thing2 at 13-APR-2011 18:08:41
 *          
 *          ---------------------------------------------
 *          COUNT: [20]
 *          ---------------------------------------------
 *          ODS Pool: Active (1)  Available (14)
 *          Connected to thingdb2(2) on node thing2 at 13-APR-2011 18:08:51
 * 
 *      At the end of this application, all connections are released and
 *      returned back to the connection pool (cache):
 *      
 *          ---------------------------------------------
 *          COUNT: [40]
 *          ---------------------------------------------
 *          ODS Pool: Active (1)  Available (14)
 *          Connected to thingdb2(2) on node thing2 at 13-APR-2011 18:12:11
 *          
 *          ---------------------------------------------
 *          RELEASING ALL CONNECTIONS BACK TO THE CACHE
 *          ---------------------------------------------
 *          Return conn1 back to the cache.
 *          Return conn2 back to the cache.
 *          ODS Pool: Active (0)  Available (15)
 *          
 *          ---------------------------------------------
 *          CLOSING "Oracle Data Source" - (ods)
 *          ---------------------------------------------
 *          ODS Pool: Active (0)  Available (15)
 * 
 * -----------------------------------------------------------------------------
 *  
 * @version 2.0
 * @author  Jeffrey M. Hunter, Sr. Database Administrator (jhunter@idevelopment.info)
 * @author  http://www.idevelopment.info
 * -----------------------------------------------------------------------------
 */

public class FastConnectionFailoverExampleThin {

    static final String driver_class = "oracle.jdbc.driver.OracleDriver";
    static final String cacheName = "CreditexProdCache";
    static final String connectionURL = "jdbc:oracle:thin:@(DESCRIPTION = " +
                                        "                     (ADDRESS = (PROTOCOL = TCP)(HOST = thing1-vip.idevelopment.info)(PORT = 1521)) " +
                                        "                     (ADDRESS = (PROTOCOL = TCP)(HOST = thing2-vip.idevelopment.info)(PORT = 1521)) " +
                                        "                     (LOAD_BALANCE = yes) " +
                                        "                     (CONNECT_DATA = " +
                                        "                       (SERVER = DEDICATED) " +
                                        "                       (SERVICE_NAME = thingdb.idevelopment.info) " +
                                        "                     )" +
                                        "                  )";
    static final String userName      = "scott";
    static final String userPassword  = "tiger";
    OracleDataSource ods;
    OracleConnectionCacheManager occm;

    /**
     * Noarg constructor used to create this object.  Responsible for creating a
     * new OracleDataSource object, setting the implicit connection cache 
     * properties, enabling the implicit connection cache, and enabling 
     * Fast Connection Failover for the connection cache. Also gets an
     * instance of the Oracle Connection Cache Manager.
     * @see #ods
     * @see #occm
     */
    public FastConnectionFailoverExampleThin() {

        Properties prop = new Properties();
        prop.setProperty("MinLimit", "10");
        prop.setProperty("InitialLimit", "20");          
        prop.setProperty("MaxLimit", "100");
        prop.setProperty("ValidateConnection", "TRUE");

        System.out.println();
        System.out.println("---------------------------------------------");
        System.out.println("JDBC DRIVER");
        System.out.println("---------------------------------------------");

        try {
            System.out.println("  --> Loading JDBC Driver.");
            ods = new OracleDataSource();
            ods.setURL(connectionURL);
            ods.setUser(userName);
            ods.setPassword(userPassword);
            ods.setConnectionCachingEnabled(true);
            ods.setConnectionCacheProperties(prop);
            ods.setFastConnectionFailoverEnabled(true);
            // You must set up the connection cache with fast connection
            // failover before naming the cache
            ods.setConnectionCacheName(cacheName);
            occm = OracleConnectionCacheManager.getConnectionCacheManagerInstance();
        } catch (Exception e) {
            System.out.println("ERROR: Loading JDBC Driver");
            e.printStackTrace();
            System.exit(1);
        }

        System.out.println("  --> JDBC Driver Loaded Succesfully.");
        System.out.println("  --> ValidateConnection = " + prop.getProperty("ValidateConnection"));

    }

    /**
     * Used to demonstrate a typical JDBC session and what is required to
     * recover from a connection failure using Fast Connection Failover.
     * @param numIterations     the number of times to run the given SQL
     *                          statement in the database. The process will
     *                          sleep for a given duration between each 
     *                          iteration.
     */
    public void performTest(int numIterations) {

        Connection conn1, conn2;
        Statement stmt;
        String sqlStatement = "SELECT " +
                              "    instance_number " +
                              "  , instance_name " +
                              "  , host_name " +
                              "  , TO_CHAR(sysdate, 'DD-MON-YYYY HH24:MI:SS') " +
                              "FROM v$instance";

        try {

            System.out.println();
            System.out.println("---------------------------------------------");
            System.out.println("INITIAL CONNECTION");
            System.out.println("---------------------------------------------");

            // Transparently create the connection cache and retrieve an initial
            // connection from the cache. This connection will not be used nor
            // will it be closed throughout the remainder of this method. It
            // will remain open and show up as an ACTIVE connection in the cache
            // when queried using the
            // occm.getNumberOfActiveConnections(cacheName) method.
            conn1 = ods.getConnection();

            System.out.println("  --> Initial connection as DB user " + userName + ".");

            // Get another connection from the connection cache. This connection
            // will also remain open and show up as an ACTIVE connection in the
            // cache when queried using the
            // occm.getNumberOfActiveConnections(cacheName) method. This
            // connection will be used run the given SQL statement provided in
            // this demonstration.
            conn2 = ods.getConnection();
            
            // Set and determine if AutoCommit is enabled
            conn2.setAutoCommit(false);
            if (conn2.getAutoCommit()) {
                System.out.println("  --> AutoCommit is enabled.");
            } else {
                System.out.println("  --> AutoCommit is not enabled.");
            }

            // Test to determine which version of the JDBC driver we are using
            DatabaseMetaData meta = conn2.getMetaData();
            System.out.println("  --> JDBC driver version is " + meta.getDriverVersion());
            
            // Determine if Fast Connection Failover is enabled
            if (ods.getFastConnectionFailoverEnabled()) {
                System.out.println("  --> Fast Connection Failover is enabled.");
            } else {
                System.out.println("  --> Fast Connection Failover is not enabled.");
            }

            // Print current ODS Connection Cache
            System.out.println("  --> Current ODS connection cache");
            printODSConnections();

            // Get fixed Statement object
            stmt = conn2.createStatement();

            for (int i=0; i < numIterations; i++) {

                try {

                    System.out.println();
                    System.out.println("---------------------------------------------");
                    System.out.println("COUNT: [" + (i+1) + "]");
                    System.out.println("---------------------------------------------");
    
                    printODSConnections();
                
                    ResultSet rset = stmt.executeQuery(sqlStatement);
                
                    while (rset.next()) {
                        int    instanceNumber   = rset.getInt(1);
                        String instanceName     = rset.getString(2);
                        String hostName         = rset.getString(3);
                        String sysDate          = rset.getString(4);
                        System.out.println("Connected to " + instanceName + "(" + instanceNumber + ") " +
                                           "on node " + hostName + " at " + sysDate);
                    }
    
                    rset.close();

                } catch (SQLException e) {

                    System.out.println();
                    System.out.println("+-------------------------------+");
                    System.out.println("| SQL Exception in loop program |");
                    System.out.println("+-------------------------------+");
                    System.out.println("SQL Error Code    : " + e.getErrorCode());
                    System.out.println("SQL Error Message : " + e.getMessage());
                    System.out.println();

                    if (e.getErrorCode() == 17008) {    // ORA-17008: Closed Connection

                        // -----------------------------------------------------
                        // Catching a recoverable exception which appears to be
                        // a node failure in the cluster while attempting to run
                        // a SQL statement. 
                        // At this point, the cache has already been cleaned up
                        // (all invalid connections have already been cleaned up
                        // by FCF), I'll simply ask for a new connection and
                        // create a new Statement object. If any DML states were
                        // rolled back, I would need to re-play them as well, 
                        // however this example only had a failed query.
                        // -----------------------------------------------------

                        System.out.println("SQL recoverable exception.");
                        System.out.println("Will try to re-connect.");
                        System.out.println();
                    
                        System.out.println("ODS connection cache before getting a new connection.");
                        printODSConnections();
                    
                        try {
                            conn2 = ods.getConnection(); // Re-get the connection
                            stmt = conn2.createStatement();
                        } catch (SQLException e1) {
                            System.out.println("ERROR: Could not obtain a new connection from the cache.");
                            System.out.println("Exiting program.");
                            e1.printStackTrace();
                        }
                        
                        System.out.println("ODS connection cache after a getting new connection.");
                        printODSConnections();

                    } else {
                        
                        System.out.print("ERROR: This SQLException was not due to a node failure.");
                        System.out.print("       Re-throwing this Exception.\n");
                        e.printStackTrace();
                        throw e;
                        
                    }

                }

                Thread.sleep(10*1000);

            } // for...

            System.out.println();
            System.out.println("---------------------------------------------");
            System.out.println("RELEASING ALL CONNECTIONS BACK TO THE CACHE");
            System.out.println("---------------------------------------------");
            if (conn1 != null) {
                System.out.println("Return conn1 back to the cache.");
                conn1.close();
            }
            if (conn2 != null) {
                System.out.println("Return conn2 back to the cache.");
                conn2.close();
            }
            printODSConnections();
            
        } catch (InterruptedException e) {

            // Swallow this exception.
            System.out.println("ERROR: Caught InterruptedException in performTest() outside of the loop.");
            e.printStackTrace();
                
        } catch (SQLException e) {

            // Swallow this exception.
            System.out.println("ERROR: Caught SQLException in performTest() outside of the loop.");
            e.printStackTrace();

        }

    }

    /**
     * Prints the number of "Active" and "Available" connections found in the
     * connection cache to STDOUT.
     */
    public void printODSConnections() {
    
        try {
            System.out.println("ODS Pool: Active (" +
            occm.getNumberOfActiveConnections(cacheName) + ") " +
            " Available (" + 
            occm.getNumberOfAvailableConnections(cacheName) + ")");
        } catch (SQLException e) {
            // Swallow this exception.
            System.out.println("Caught SQLException in printODSConnections().");
            e.printStackTrace();
        }
    }

    /**
     * Closes the Oracle datasource and cleans up the connection cache.
     */
    public void closeODS() {
    
        try {
            System.out.println();
            System.out.println("---------------------------------------------");
            System.out.println("CLOSING \"Oracle Data Source\" - (ods)");
            System.out.println("---------------------------------------------");
            System.out.println("ODS Pool: Active (" +
            occm.getNumberOfActiveConnections(cacheName) + ") " +
                    " Available (" + 
                    occm.getNumberOfAvailableConnections(cacheName) + ")");
            ods.close();
        } catch (SQLException e) {
            // Swallow this exception.
            System.out.println("Caught SQLException in closeODS().");
            e.printStackTrace();
        }
    }

    /**
     * Sole entry point to the class and application.
     * @param args Array of String arguments.
     */
    public static void main(String[] args) {
        FastConnectionFailoverExampleThin testFailover = new FastConnectionFailoverExampleThin();
        testFailover.performTest(40);
        testFailover.closeODS();
        System.exit(0);
    }
}

