--Indexes Planos:
SELECT 'alter index '||UPPER('&1')||'.'||index_name||' rebuild tablespace '||tablespace_name ||';'
FROM   dba_indexes
WHERE  status = 'UNUSABLE';

--Index partitions:
SELECT 'alter index '||UPPER('&1')||'.'||index_name ||' rebuild partition '||PARTITION_NAME||' TABLESPACE '||tablespace_name ||';'
FROM   dba_ind_partitions
WHERE  status = 'UNUSABLE';

