-- trigger status por tabla
-- Sergio Cruz
-- 07 - 11 - 2006

select table_name,trigger_name,status
from user_triggers where table_name = '&tabla'