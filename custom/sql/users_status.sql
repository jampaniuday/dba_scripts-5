SET ECHO        OFF
SET FEEDBACK    6
SET HEADING     ON
SET LINESIZE    200
SET PAGESIZE    50000
SET TERMOUT     ON
SET TIMING      OFF
SET TRIMOUT     ON
SET TRIMSPOOL   ON
SET VERIFY      OFF

CLEAR COLUMNS
CLEAR BREAKS
CLEAR COMPUTES

select username,account_status status, to_char(lock_date,'dd/mm/yyyy HH:MM:SS') lock_date from sys.dba_users
where username in(&user);