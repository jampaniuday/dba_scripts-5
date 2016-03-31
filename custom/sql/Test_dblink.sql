SET TERMOUT OFF;
COLUMN current_instance NEW_VALUE current_instance NOPRINT;
SELECT rpad(instance_name, 17) current_instance FROM v$instance;
SET TERMOUT ON;

PROMPT 
PROMPT +------------------------------------------------------------------------+
PROMPT | Report   : Test del DBLINK                                             |
PROMPT | Instance : &current_instance                                           |
PROMPT +------------------------------------------------------------------------+

SET ECHO        OFF
SET FEEDBACK    6
SET HEADING     ON
SET LINESIZE    180
SET PAGESIZE    50000
SET TERMOUT     ON
SET TIMING      OFF
SET TRIMOUT     ON
SET TRIMSPOOL   ON
SET VERIFY      OFF

CLEAR COLUMNS
CLEAR BREAKS
CLEAR COMPUTES
/*
create or replace function is_link_active(
  p_link_name varchar2
) return number is
  v_query_link varchar2(100) := 'select count(*) alive from dual@'||p_link_name;
  type db_link_cur is REF CURSOR;
  cur db_link_cur;
  v_status number;
begin
  open cur FOR v_query_link; 
  loop
    fetch cur INTO v_status; 
    exit when cur%notfound;
    dbms_output.put_line('v_status='||v_status);
    return v_status;
  end loop;
  close cur;
exception when others then
  close cur;
  return 0; 
end is_link_active;

declare 
x integer; 
begin 
x:=is_link_active('&dblink'); 
dbms_output.put_line(str(x)); 
end;

/*
COLUMN status NEW_VALUE status NOPRINT;
select is_link_active('&dblink') status from dual;
*/
COLUMN Version_oracle NEW_VALUE Version_oracle NOPRINT;
select banner Version_oracle from v$version@&dblink where rownum=1;

PROMPT 
PROMPT &Version_oracle

/