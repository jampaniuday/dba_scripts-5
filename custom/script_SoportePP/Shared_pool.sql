                                               SQL> set linesize 300
                                               SQL> set pagesize 9999
                                               SQL> column inst_id format a12
                                               SQL> column pool format a12
                                               SQL> column name format a12
                                               SQL> column free_mb format a12
                                               SQL> SELECT to_char(INST_ID) as inst_id
                                                                  ,pool
                                                                  ,name
                                                                  ,to_char(ROUND(BYTES/(1024*1024),2)) free_mb 
                                                           FROM GV$SGASTAT 
                                                             WHERE POOL='shared pool'
                                                                 AND NAME='free memory'
                                                                  ORDER BY BYTES DESC;

You can flush the shared pool to avoid an instance reboot:

        SQL> alter system flush shared_pool;
