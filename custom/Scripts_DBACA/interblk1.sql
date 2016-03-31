select session_id "sid",SERIAL#  "Serial",substr(OBJECT_NAME,1,20) "Object",substr(OS_USER_NAME,1,10) "Terminal",
substr(ORACLE_USERNAME,1,10) "Locker",NVL(lockwait,'ACTIVE') "Wait",DECODE(LOCKED_MODE,
    2, 'ROW SHARE',
    3, 'ROW EXCLUSIVE',
    4, 'SHARE',
    5, 'SHARE ROW EXCLUSIVE',
    6, 'EXCLUSIVE',  'UNKNOWN') "Lockmode",
  OBJECT_TYPE "Type"
FROM SYS.V_$LOCKED_OBJECT A,SYS.ALL_OBJECTS B,SYS.V_$SESSION c
WHERE A.OBJECT_ID = B.OBJECT_ID AND C.SID = A.SESSION_ID ORDER BY 1 asc, 6 desc
/