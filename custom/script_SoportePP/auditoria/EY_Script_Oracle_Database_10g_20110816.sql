/*


############################################################
# Auditor Name :  Carlos Escobar
# Audit Date   :  12/31/2011
# Client Name  :  Telef?nica GT SV
# Description  :  Oracle 10
############################################################

*/



set echo on
REM **************************************************************************
REM Ernst and Young LLP UNIX Audit Script
REM Copyright - 2005
REM Updated by S. Rohit (rohit.iyer@sg.ey.com) 29Sept2005- fixed some bugs in code
REM Updated by Chad Woolf 25Aug2005- Added usability references, labels, and more associations
REM Updated by Peter Morin 29Apr2005 - Added HTML support for 8i
REM Updated by Peter Morin 15Mar2005 - Added MARKUP for HTML output
REM **************************************************************************

set echo off
set termout on
set heading on
set feedback off
set trimspool on
set linesize 200
set pagesize 200
set markup html on spool on

Spool EY_OracleDBDump.html

prompt DBA_PROFILES
prompt Control: Account Lockout
prompt Control: Idle Session Timeout
prompt Control: Password Composition
prompt Control: Password Expiration
prompt Control: Password History
prompt Control: Restrict Password Protected Roles
prompt Control: Concurrent Logins
prompt Control: Test Password Settings
prompt 
prompt Display dba_profiles table
select	* from	dba_profiles;


prompt DBA_ROLE_PRIVS
prompt Control: Application Schema owner as DBA
prompt Control: Developer Roles
prompt Control: Developer Access to Production Environment
prompt Control: Restrict Role Functionality 
prompt Control: Restrict WITH ADMIN Option
prompt Control: Role-based Privileges: Auditing
prompt Control: Role-based Privileges: Security Administration
prompt Control: Role-based Privileges: Process
prompt Control: Role-based Privileges: DBA
prompt Control: Role-based Privileges: Data Owner 
prompt Control: CONNECT and RESOURCE role
prompt Control: PUBLIC Account Privileges
prompt Control: Restrict Password Protected Roles
prompt Control: Test Access to Privileged IT Functions
prompt Control: Test Access to Production Data 
prompt Control: Test Logical Access Segregation of Duties 
prompt 
prompt Display dba_role_privs table
select	*
from	dba_role_privs
order by grantee, granted_role;


prompt Control: Test for Access Assigned to PUBLIC Role
prompt Control: Test Access to Privileged IT Functions  
prompt Control: Test Logical Access Segregation of Duties 
prompt 
prompt Display DBA_SYS_PRIVS table
prompt Fields: Grantee, Privilege, Admin_Option
SELECT  substr(grantee,1,30) "Grantee",	
	substr(privilege,1,20) "Privilege",
	substr(admin_option,1,3) "Admin_Option" 
FROM DBA_SYS_PRIVS 
WHERE 
	PRIVILEGE='CREATE USER' OR
	PRIVILEGE='BECOME USER' OR
	PRIVILEGE='ALTER USER' OR
	PRIVILEGE='DROP USER' OR
	PRIVILEGE='CREATE ROLE' OR
	PRIVILEGE='ALTER ANY ROLE' OR
	PRIVILEGE='DROP ANY ROLE' OR
	PRIVILEGE='GRANT ANY ROLE' OR
	PRIVILEGE='CREATE PROFILE' OR
	PRIVILEGE='ALTER PROFILE' OR
	PRIVILEGE='DROP PROFILE' OR
	PRIVILEGE='CREATE ANY TABLE' OR
	PRIVILEGE='ALTER ANY TABLE' OR
	PRIVILEGE='DROP ANY TABLE' OR
	PRIVILEGE='INSERT ANY TABLE' OR
	PRIVILEGE='UPDATE ANY TABLE' OR
	PRIVILEGE='DELETE ANY TABLE' OR
	PRIVILEGE='CREATE ANY PROCEDURE' OR
	PRIVILEGE='ALTER ANY PROCEDURE' OR
	PRIVILEGE='DROP ANY PROCEDURE' OR
	PRIVILEGE='CREATE ANY TRIGGER' OR
	PRIVILEGE='ALTER ANY TRIGGER' OR
	PRIVILEGE='DROP ANY TRIGGER' OR
	PRIVILEGE='CREATE TABLESPACE' OR
	PRIVILEGE='ALTER TABLESPACE' OR
	PRIVILEGE='DROP TABLESPACES' OR
	PRIVILEGE='ALTER DATABASE' OR
	PRIVILEGE='ALTER SYSTEM';


prompt Control: Test Monitoring of User Access 
prompt 
prompt Display dba_tab_privs table 
prompt Fields: Grantee, Owner, Table_Name, Privilege
SELECT GRANTEE, OWNER, TABLE_NAME, PRIVILEGE FROM DBA_TAB_PRIVS WHERE TABLE_NAME LIKE 'AUD%';

prompt Control: Test Access to Production Data 
prompt 
prompt Display dba_tab_privs table 
prompt Fields: Grantee, Owner, Table_Name, Grantor, Privilege, Grantable, Hierarchy 
Select  substr(grantee,1,30) "Grantee", 
substr(owner,1,20) "Owner", 
substr(table_name,1,20) "Table_Name", 
substr(grantor,1,20) "Grantor", 
substr(privilege,1,20) "Privilege", 
substr(grantable,1,3) "Grantable", 
substr(hierarchy,1,3) "Hierarchy" 
from dba_tab_privs WHERE GRANTABLE = 'YES';

prompt Control: Test Access to Privileged IT Functions
prompt Control: Test Access to Production Data 
prompt Control: Test Default Accounts and Passwords 
prompt Control: Test for Host-Based Authentication Methods
prompt Control: Test for Global and Enterprise Roles
prompt Control: Test Logical Access Segregation of Duties 
prompt Control: Test New User Setup
prompt Control: Test Password Settings
prompt 
prompt Display DBA_USERS table
prompt Fields: Username, User ID, Password, Account Status, Lock Date, Expiry date, Default tablespace, Created, Assigned Profile, Consumer Group, External Name
select	substr(username,1,20) "Username",	
	substr(user_id,1,10) "User ID",
	substr(password,1,20) "Password",
	substr(account_status,1,20) "Account Status",
	substr(Lock_date,1,11) "Lock date",
	substr(Expiry_date,1,11) "Expiry date",
	substr(Default_tablespace,1,15) "Def Tablespace",
	substr(Created,1,10) "Created",
	substr(Profile,1,10) "Assgn Profile",
	substr(Initial_RSRC_Consumer_Group,1,20) "Consumer Grp",
	substr(External_Name,1,10) "Ext Name"
from 	sys.dba_users
order by username;

prompt Control: Test Access to Data Modification Utilities (SQL*Plus)  
prompt 
prompt Display Product_Profile
SELECT * FROM PRODUCT_PROFILE;

prompt Control: Test Access to Privileged IT Functions 
prompt 
prompt Display SYS.DBA_ROLE_PRIVS table
prompt Fields: Grantee, Granted_Role, Admin_Option, Default_Role
select	substr(grantee,1,30) "Grantee",	
	substr(granted_role,1,20) "granted_role",
	substr(admin_option,1,3) "Admin_Option",
                     substr(default_role,1,3) "Default_Role"
from 	SYS.DBA_ROLE_PRIVS WHERE ADMIN_OPTION = 'YES';

prompt Control: Test Password Settings
prompt 
prompt Display SYS.DBA_STMT_AUDIT_OPTS  table
prompt Fields: Grantee, Privilege, Admin_Option
SELECT USER_NAME, FAILURE FROM SYS.DBA_STMT_AUDIT_OPTS WHERE AUDIT_OPTION = 'CREATE SESSION';

prompt Control: Test for Host-Based Authentication Methods
prompt Control: Test Monitoring of User Access
prompt Control: Test Password Settings
prompt
prompt Display v$parameter2 table
prompt Fields: Num, Name, Type, Value, Isdefault, Isses_Modifiable, Issys_Modifiable, Ismodified, Isadjusted, Description, Ordinal, Update_Comment
Select  substr(num,1,20) "Num",
	substr(Name,1,20) "Name",
	substr(type,1,20) "Type",
	substr(value,1,20) "Value",
	substr(isdefault,1,20) "Isdefault",
	substr(isses_modifiable,1,20) "Isses_modifiable",
	substr(issys_modifiable,1,20) "Issys_modifiable",
	substr(ismodified,1,20) "Ismodified",
	substr(isadjusted,1,20) "Isadjusted",
	substr(description,1,20) "Description",
	substr(ordinal,1,20) "Ordinal",
	substr(update_comment,1,20) "Update_Comment"
from 	v$parameter2;

prompt Control: Test Monitoring of User Access 
prompt 
prompt Display Product_Component_Version 
prompt Fields: Product, Version, Status
SELECT * FROM PRODUCT_COMPONENT_VERSION;

Prompt Control: Test Monitoring of User Access
prompt
prompt Display V$VERSION
prompt Fields: Banner
SELECT * FROM V$VERSION;

prompt end of script
spool off
set markup html off

