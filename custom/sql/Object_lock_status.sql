


SELECT --/*+ CHOOSE */
         a.INST_ID,
--         c.spid,
--         a.sid,
--         a.serial#,
         NVL (a.username, '(oracle)') AS "DB User",
         a.MACHINE,
--         a.osuser,
         a.status,
         a.terminal,
         TO_CHAR (a.logon_time, 'dd-mm-yyyy hh24:mi') LOGON,
--         a.module,
--         a.TYPE ptype,
         b.owner,
         b.object,
--         b.TYPE,
--         'kill -9 ' || c.spid KILLPROCESS,
         'alter system kill session ''' || a.sid || ',' || a.serial# || ''';'
            KILLSESSION
    FROM gv$session a 
         join gv$access b on a.sid = b.sid
--         join gv$process c on c.addr = a.paddr
   WHERE     
         a.inst_id = b.inst_id
         AND b.TYPE <> 'NON-EXISTENT'
--         AND (b.owner IS NOT NULL)
--         AND (b.owner <> 'SYSTEM')
--         AND (b.owner <> 'SYS')
--		 AND b.TYPE ='TABLE'
         AND b.OBJECT LIKE UPPER ('&1')
ORDER BY a.inst_id, a.STATUS, b.OBJECT, b.TYPE;