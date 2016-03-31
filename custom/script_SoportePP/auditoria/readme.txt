*** Auditor Instructions ***
ORACLE: (MS-SQL directions are below)
This Oracle Database Script has been tested on the following operating systems:
- Oracle 9i
- Oracle 10g
Provide the .sql script file to the client with the Client/System Admin Instructions below.

The script output is potentially 4 html files called:
 - EY_OracleDBDump.html
 - EY_OracleDBDump_GrantOpt.html
 - EY_OracleDBDump_Dep.html
 - EY_OracleDBDump_SysPrivs.html
   (note that these last 3 files could be very large)

You can review these files in a web browser or in Excel. To import the output into Excel, do the following:
1. Open a blank spreadsheet
2. Click Data>Import External Data>Import data
3. Find your export file and click Open
4. After the data is read by Excel (which may take a while if the file is large), click Import when prompted
5. Select the starting cell to place the data and click OK


NOTE: This script should be run in a development or QA environment before it is run in production. There is a risk, as with any sort of a script, that the server's performance or availability may be affected while the script is running.

If you have any questions about these scripts, please contact the TSRS Technologies Service Desk:
   http://chs.iweb.ey.com/852569b2004b0365
   Click the Technologies tab
   Select Technology Help under the Technology Contacts section

*** Client/System Admin Instructions ***
NOTE: This script should be run in a development or QA environment before it is run in production. There is a risk, as with any sort of a script, that the server's performance or availability may be affected while the script is running.

1. Login to the host UNIX system
2. Copy the script to the host UNIX system
3. From the same directory where the script was copied to, login to the Oracle Database via SQLPLUS as SYSTEM or any account with SYSTEM-like privileges 
4. Execute the script using the @ScriptName command
5. Exit the SQLPlus utility
6. The data is output to potentially 4 html files to the same directory called:
 - EY_OracleDBDump.html
 - EY_OracleDBDump_GrantOpt.html
 - EY_OracleDBDump_Dep.html
 - EY_OracleDBDump_SysPrivs.html
   (note that these last 3 files could be very large)
7. Return the text output file(s) to the E&Y Professional. 

MS-SQL 2000:
*** Auditor Instructions ***

This MS-SQL database Script has been tested on the following operating systems:
- MS-SQL Server 7
- MS-SQL Server 2000

Provide the .sql script file to the client with the Client/System Admin Instructions below.

*** Client/System Admin Instructions ***
NOTE: This script should be run in a development or QA environment before it is run in production. There is a risk, as with any sort of a script, that the server's performance or availability may be affected while the script is running.

1. Login to the host Windows Server
2. Launch the SQL Server Query Analyzer Utility
3. In the SQL Server Dialogue Box, select the server to be reviewed, and login as a user with administrative privileges to the SQL Server (i.e. SA)
4. *IMPORTANT* You must update the results format to display in ASCII Text format. Select TOOLS>OPTIONS then the RESULTS TAB. The Default Results Target should be set to Results to Text.
5. Copy the script to the host system
6. Select File>Open and select the SQL script to be run
7. Hit F5 to execute the script
8. Select all the data in the results window by placing your cursor in the results windows and holding down the CNTRL-A keys
9. While the data is selected, save to a report by selecting Save As from the File Menu
9. Exit the Query Analyzer utility
10. Return the text output file to the E&Y Professional. 


MS-SQL 2005:
*** Auditor Instructions ***

This MS-SQL database Script has been tested on the following operating systems:
- MS-SQL Server 2005

Provide the .sql script file to the client with the Client/System Admin Instructions below.

*** Client/System Admin Instructions ***
NOTE: This script should be run in a development or QA environment before it is run in production. There is a risk, as with any sort of a script, that the server's performance or availability may be affected while the script is running.

1. Login to the host Windows Server
2. Launch the Microsoft SQL Server Management Studio
3. In the SQL Server Dialogue Box, select the server to be reviewed, and login as a user with administrative privileges to the SQL Server (i.e. SA)
4. *IMPORTANT* You must update the results format to display in ASCII Text format. Select TOOLS > OPTIONS then expand the RESULTS option. The Default destination for results should be set to "Results to text".  Make sure to modify the Default location for saving query results to a folder you will remember.  Click OK when you are done making changes.
5. Copy the script to the host system
6. Select File > Open and select the SQL script to be run
7. Hit F5 to execute the script
8. Select all the data in the results window by placing your cursor in the results windows and holding down the CNTRL-A keys
9. While the data is selected, save to a report by selecting Save Results As from the File Menu
9. Exit Microsoft SQL Server Management Studio
10. Return the text output file to the E&Y Professional. 

*** Legal Disclosure ***
The information contained in these instructions and materials provided by Ernst & Young LLP ("E&Y") and any materials or files generated by following these instructions (collectively, "E&Y Materials") contains proprietary, privileged and confidential information (collectively, "Confidential Information") of E&Y, other members of the global Ernst & Young network or their respective affiliates  (collectively "Owners").  Unauthorized use or disclosure of the Confidential Information is prohibited and will result in irreparable harm to the Owners.  Accordingly, none of the Confidential Information may be disclosed, used, referenced, quoted, summarized, or duplicated, in whole or in part, for any purpose other than as specified by E&Y or its client for which the materials or files are being generated.  If you are executing these instructions on behalf of E&Y, deliver the E&Y Materials to E&Y (at 5 Times Square, New York, NY 10036 if not directed otherwise) or an individual authorized by the Owners to receive them.  After doing so, delete the instructions and the E&Y Materials from your computer and do not retain any copies thereof.