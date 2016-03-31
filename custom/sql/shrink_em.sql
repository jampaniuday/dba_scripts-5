SQL> select 'alter rollback segment '||segment_name||' shrink to 2;' from dba_rollback_segs where tablespace_name='RBSTS';

no rows selected

SQL> spool off
