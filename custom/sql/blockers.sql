-- Interbloqueos
col sess format a20
SELECT DECODE(request,0,'Holder: ','Waiter: ')||sid sess,
          id1, id2, lmode, request, type,ctime
     FROM V$LOCK
    WHERE (id1, id2, type) IN
              (SELECT id1, id2, type FROM V$LOCK WHERE request>0)
    ORDER BY ctime desc,sess, id1, request;