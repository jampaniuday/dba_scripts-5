-- |--------------------------------------------------------------------------------|
-- | DATABASE     : Oracle                                                          |
-- | FILE         : analyzetable.sql                                                |
-- | CLASS        : Database Administration                                         |
-- | Call Syntax  : @analyzetable (Schema)(Table)(Partition)(Sample)				|
-- | Example	  :	@analyzetable &table &sample									|
-- |				@analyzetable ta_plantarif 1									|
-- |				@analyzetable user ta_plantarif none auto						|
-- | PURPOSE      : Realiza Analyse a la tabla pasada por parametro con el sample	|
-- |				indicado.														|
-- | Detalles	  : 06/09/2014 - Creado con pase de variables schema, table,		|
-- |				partition y sample, sino es a una partition poner "none" y si	|
-- | 				se esta loggeado con el usuario de la tabla poner "user"...		|
-- +--------------------------------------------------------------------------------+
set define on
exec dbms_stats.gather_table_stats(user,trim('&1'),null,&2,null,'FOR ALL COLUMNS SIZE AUTO',16,'all',true);