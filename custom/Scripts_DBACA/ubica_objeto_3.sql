-- ubica cualquier objeto por nombre
--Luis Toledo
col owner format a12
col SUBOBJECT_NAME format a12
select * from all_objects
where UPPER(object_name) like UPPER('%&OBJETO%');