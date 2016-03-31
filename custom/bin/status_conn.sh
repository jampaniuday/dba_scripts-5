netstat -an|grep $1|awk 'BEGIN \
{ESTABLISHED=0;TIME_WAIT=0;FIN_WAIT1=0;FIN_WAIT2=0;LAST_ACK=0;CLOSING=0;\
TOTAL=0;SUBTOTAL=0;SYN_RECV=0;CLOSE_WAIT=0;FALTANTE=0}\
{TOTAL++}
{if ($6 == "ESTABLISHED") {ESTABLISHED++;SUBTOTAL++;next}}\
{if ($6 == "TIME_WAIT")	{TIME_WAIT++;SUBTOTAL++;next}}\
{if ($6 == "FIN_WAIT1")	{FIN_WAIT1++;SUBTOTAL++;next}}\
{if ($6 == "FIN_WAIT2")	{FIN_WAIT2++;SUBTOTAL++;next}}\
{if ($6 == "LAST_ACK")	{LAST_ACK++;SUBTOTAL++;next}}\
{if ($6 == "CLOSING")	{CLOSING++;SUBTOTAL++;next}}\
{if ($6 == "SYN_RECV")	{SYN_RECV++;SUBTOTAL++;next}}\
{if ($6 == "CLOSE_WAIT"){SYN_RECV++;SUBTOTAL++;next}}\
{if ($6 == "LISTEN")	{next}}\
{print "Status Faltantes de filtrar"}\
{print "****************************"}\
{FALTANTE++;print $6}\
{print "****************************"}\
END \
{print "****************************";\
 print "CONEXIONES ACTIVAS: "TOTAL;\
 print "****************************";\
 print "ESTABLISHED: "ESTABLISHED;\
 print "TIME_WAIT: "TIME_WAIT;\
 print "FIN_WAIT1: "FIN_WAIT1;\
 print "FIN_WAIT2: "FIN_WAIT2;\
 print "LAST_ACK: "LAST_ACK;\
 print "CLOSING: "CLOSING;\
 print "SYN_RECV: "SYN_RECV;\
 print "CLOSE_WAIT: "CLOSE_WAIT;\
 print "***************************";\
 print "FALTANTE = "FALTANTE;\
 print "TOTAL = "SUBTOTAL}'