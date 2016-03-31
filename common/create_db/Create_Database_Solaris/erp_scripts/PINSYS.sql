rem  ENTRIES BY JPD ON 4.28.98 TO PIN THE FOLLOWING SYS OBJECTS
rem  THIS WILL KEEP THE EXECUTION OF THESE PACKAGES IN THE SGA

connect sys/oral1nux;
execute dbms_shared_pool.keep('SYS.STANDARD');
execute dbms_shared_pool.keep('SYS.DBMS_STANDARD');
execute dbms_shared_pool.keep('SYS.DBMS_DESCRIBE');
execute dbms_shared_pool.keep('SYS.DBMS_UTILITY');
execute dbms_shared_pool.keep('SYS.DBMS_PIPE');
execute dbms_shared_pool.keep('SYS.DBMS_LOCK');
execute dbms_shared_pool.keep('SYS.DBMS_OUTPUT');
exit;

