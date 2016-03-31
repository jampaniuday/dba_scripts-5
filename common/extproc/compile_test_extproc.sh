gcc -G -c test_extproc.c
ld -r -o test_extproc.so test_extproc.o
chmod 775 test_extproc.so
rm test_extproc.o


sqlplus -silent scott/tiger <<EOF
CREATE OR REPLACE LIBRARY test_extproc_lib is '/u01/app/oracle/common/extproc/test_extproc.so';
/
CREATE OR REPLACE PROCEDURE shell(command IN char)
  AS EXTERNAL
     NAME "sh"
     LIBRARY test_extproc_lib
     LANGUAGE C
     PARAMETERS (command string);
/
CREATE OR REPLACE PROCEDURE mailx(send_to IN char, subject IN char, message IN char)
  AS EXTERNAL
     NAME "mailx"
     LIBRARY test_extproc_lib
     LANGUAGE C
     PARAMETERS (send_to string, subject string, message string);
/
EOF


sqlplus -silent scott/tiger <<EOF
set serveroutput on
exec shell('ls');
exec mailx('JeffreyH@CoManage.net', 'EXTPROC Test', 'This is a test');
EOF
