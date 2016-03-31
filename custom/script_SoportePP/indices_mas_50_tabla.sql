select substr(si.segment_name, 1, 30) as INX, si.SEGMENT_TYPE from dba_segments st, dba_segments si, all_indexes i
where st.segment_name = i.table_name and si.segment_name = i.index_name and si.bytes > 0.5*st.bytes and
st.segment_name =upper('PPGA_TARBONOS_CAPR') and si.bytes/1024/1024 > 1 group by st.segment_name,si.segment_name,si.SEGMENT_TYPE;



select substr(si.segment_name, 1, 30) as INX, si.SEGMENT_TYPE 
from dba_segments st, dba_segments si, all_indexes i
where st.segment_name = i.table_name and 
st.owner = i.owner and
si.segment_name = i.index_name and 
si.owner = i.owner and
si.bytes > 0.5*st.bytes and
i.owner = 'OPGE' and
st.segment_name =upper('POGE_CAMBESTBOLSAS') and si.bytes/1024/1024 > 1 
group by st.segment_name,si.segment_name,si.SEGMENT_TYPE;