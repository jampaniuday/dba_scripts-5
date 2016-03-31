// -----------------------------------------------------------------------------
// SerialOracle.java
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
import java.lang.*;
import java.sql.*;
import java.util.*;
import oracle.jdbc.driver.*;
import oracle.sql.*;

/**
 * -----------------------------------------------------------------------------
 * 
 * The following class provides an example of how to serialize a Java object
 * to the Oracle database. Use the following SQL statement to create the 
 * 
 * -----------------------------------------------------------------------------
 * 
 *      connect scott/tiger
 * 
 *      DROP SEQUENCE java_obj_seq
 *      /
 *      
 *      CREATE SEQUENCE java_obj_seq
 *             INCREMENT BY 1
 *             START WITH 1
 *             NOMAXVALUE
 *             NOCYCLE
 *      /
 *      
 *      DROP TABLE java_objects
 *      /
 *      
 *      CREATE TABLE java_objects (
 *          obj_id_no   NUMBER
 *        , obj_name    VARCHAR2(2000)
 *        , obj_value   BLOB   DEFAULT empty_blob()
 *      )
 *      /
 * 
 * -----------------------------------------------------------------------------
 * 
 * The following SQL statement can be used to check objects that have been
 * serialized to the database:
 * 
 * connect scott/tiger
 * 
 * CREATE OR REPLACE FUNCTION readBLOB (p_raw IN BLOB)
 *   RETURN VARCHAR2
 *   AS
 * 
 *   l_tmp     LONG     DEFAULT utl_raw.cast_to_varchar2(dbms_lob.substr(p_raw,2000,1));
 *   l_char    CHAR(1);
 *   l_return  LONG;
 * 
 * BEGIN
 *   FOR i IN 1 .. length(l_tmp)
 *   LOOP
 *     l_char := substr(l_tmp, i, 1);
 *     IF ( ascii(l_char) BETWEEN 32 and 127) THEN
 *       l_return := l_return || l_char;
 *     ELSE
 *       l_return := l_return || '.';
 *     END IF;
 *   END LOOP;
 *   RETURN l_return;
 * END;
 * /
 *
 *
 *
 * SET PAGESIZE 9000
 * 
 * SELECT
 *     obj_id_no obj_id
 *   , DBMS_LOB.getLength(obj_value) obj_length
 *   , readBLOB(obj_value) obj_value
 * FROM java_objects
 * /
 * -----------------------------------------------------------------------------
 * @version 1.0
 * @author  Jeffrey M. Hunter  (jhunter@idevelopment.info)
 * @author  http://www.idevelopment.info
 * -----------------------------------------------------------------------------
 */

class SerialOracle {

    static final String driver_class   = "oracle.jdbc.driver.OracleDriver";
    static final String connectionURL  = "jdbc:oracle:thin:@localhost:1521:CUSTDB";
    static final String userID         = "scott";
    static final String userPassword   = "tiger";
    static final String getSequenceSQL = "SELECT java_obj_seq.nextval FROM dual";
    static final String writeObjSQL    = "BEGIN " +
                                         "  INSERT INTO java_objects(obj_id_no, obj_name, obj_value) " +
                                         "  VALUES (?, ?, empty_blob()) " +
                                         "  RETURN obj_value INTO ?; " +
                                         "END;";
    static final String readObjSQL     = "SELECT obj_value FROM java_objects WHERE obj_id_no = ?";

    /*
     * +--------------------------------------------------+
     * | METHOD: writeObj                                 |
     * +--------------------------------------------------+
     */
    public static long writeObj(Connection conn, Object obj) throws Exception {

        long id = getNextSeqVal(conn);
        String className = obj.getClass().getName();
        CallableStatement stmt = conn.prepareCall(writeObjSQL);
        stmt.setLong(1, id);
        stmt.setString(2, className);
        stmt.registerOutParameter(3, java.sql.Types.BLOB);
        stmt.executeUpdate();
        BLOB blob = (BLOB) stmt.getBlob(3);
        OutputStream os = blob.getBinaryOutputStream();
        ObjectOutputStream oop = new ObjectOutputStream(os);
        oop.writeObject(obj);
        oop.flush();
        oop.close();
        os.close();
        stmt.close();
        System.out.println("Done serializing: " + className);
        return id;

    }


    /*
     * +--------------------------------------------------+
     * | METHOD: readObj                                  |
     * +--------------------------------------------------+
     */
    public static Object readObj(Connection conn, long id) throws Exception {

        PreparedStatement stmt = conn.prepareStatement(readObjSQL);
        stmt.setLong(1, id);
        ResultSet rs = stmt.executeQuery();
        rs.next();
        InputStream is = rs.getBlob(1).getBinaryStream();
        ObjectInputStream oip = new ObjectInputStream(is);
        Object obj = oip.readObject();
        String className = obj.getClass().getName();
        oip.close();
        is.close();
        stmt.close();
        System.out.println("Done de-serializing: " + className);
        return obj;

    }


    /*
     * +--------------------------------------------------+
     * | METHOD: getNextSeqVal                            |
     * +--------------------------------------------------+
     */
    private static long getNextSeqVal (Connection conn) throws SQLException {

        Statement stmt = conn.createStatement();
        ResultSet rs   = stmt.executeQuery(getSequenceSQL);
        rs.next();
        long id = rs.getLong(1);
        rs.close();
        stmt.close();
        return id;

    }


    /*
     * +--------------------------------------------------+
     * | METHOD: main                                     |
     * +--------------------------------------------------+
     */
    public static void main (String args[]) throws SQLException {

        Connection conn = null;
        Statement stmt = null;
        ResultSet rset = null;
        int insertResults;
        int deleteResults;

        try {

            /*
             * LOAD AND REGISTER THE JDBC DRIVER
             * There are two basic ways to handle this:
             *   - DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
             *   - Class.forName ("oracle.jdbc.driver.OracleDriver").newInstance();
             */
            System.out.print("\n");
            System.out.print("Loading JDBC Driver  -> " + driver_class + "\n");
            Class.forName (driver_class).newInstance();

            /*
             * CONNECT TO THE DATABASE
             */
            System.out.print("Connecting to        -> " + connectionURL + "\n");
            conn = DriverManager.getConnection(connectionURL, userID, userPassword);
            System.out.print("Connected as         -> " + userID + "\n\n");

            /*
             * TURN OFF AutoCommit
             */
            conn.setAutoCommit(false);

            LinkedList obj = new LinkedList();
            obj.add("This is a test");
            obj.add(new Long(123456789));
            obj.add(new java.util.Date());

            long obj_id_no = writeObj(conn, obj);
            conn.commit();

            System.out.print("Serialized OBJECT_ID => " + obj_id_no + "\n\n");

            System.out.print("OBJECT VALUES  => " + readObj(conn, obj_id_no) + "\n\n");
            conn.close();

        } catch (Exception e) {

            e.printStackTrace();
            
        } finally {
        
            if (conn != null) {
                try {
                    System.out.print("Closing down all connections...\n\n");
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}

