DECLARE
  l_rindex     PLS_INTEGER;
  l_slno       PLS_INTEGER;
  l_totalwork  NUMBER;
  l_sofar      NUMBER;
  l_obj        PLS_INTEGER;
BEGIN
  l_rindex    := DBMS_APPLICATION_INFO.set_session_longops_nohint;
  l_sofar     := 0;
  l_totalwork := 10;

  WHILE l_sofar < 10 LOOP
    -- Do some work
    DBMS_LOCK.sleep(5);

    l_sofar := l_sofar + 1;

    DBMS_APPLICATION_INFO.set_session_longops(
      rindex      => l_rindex,
      slno        => l_slno,
      op_name     => 'BATCH_LOAD',
      target      => l_obj,
      context     => 0,
      sofar       => l_sofar,
      totalwork   => l_totalwork,
      target_desc => 'BATCH_LOAD_TABLE',
      units       => 'rows');
  END LOOP;
END;
/