#!/usr/local/bin/perl

# +----------------------------------------------------------------------+
# | FILE         : testDBDOracle_windows.pl                              |
# | AUTHOR       : Jeff Hunter, Senior Database Administrator            |
# | PURPOSE      : This script will test the DBI/DBD installation.       |
# | OUTPUT FILES : NONE                                                  |
# +----------------------------------------------------------------------+

require "ctime.pl";
require "flush.pl";

use DBI;

&declareGlobalVariables;

&printHeader;

$dbh = &getOracleLogin("$ORACLE_SID", "$ORACLE_USERID", "$ORACLE_PASSWORD");
$dbh->{LongReadLen} = 64000;

&performTest;

&logoffOracle($dbh);

&printFooter;

exit;

# +--------------+
# | SUB ROUTINES |
# +--------------+
sub declareGlobalVariables {

  $ORACLE_SID              = "JEFFDB";
  $ORACLE_USERID           = "system";
  $ORACLE_PASSWORD         = "manager";

  $ENV{'ORACLE_SID'}       = "$ORACLE_SID";
  $ENV{'ORACLE_HOME'}      = "c:\\oracle\\ora92";

}

sub printHeader {

  print "\n";
  print "Running testDBDOracle_windows.pl...\n";
  print "\n";

}

sub printFooter {

  print "Ending testDBDOracle_windows.pl...\n";
  print "\n";

}

sub getOracleLogin {

  local ($oracle_sid, $username, $password) = @_;
  local ($temp_dbh);
  local($tempID, $tempPassword, $tempKey);

  print "  (*) Attempting Oracle Login ...\n";


  unless ( $temp_dbh = DBI->connect("dbi:Oracle:$oracle_sid", "$username", $password, {AutoCommit => 0}) ) {
    &programError("Oracle Login Failed as $username", "", "$DBI::errstr", "dba-mail", "dba-pager");
    exit;
  }

  print "      OK\n\n";

  return $temp_dbh;

}

sub logoffOracle {

  ($dbh) = @_;

  print "  (*) Attempting Oracle Logoff ...\n";

  unless ($dbh->disconnect) {

    # &programError("Could not disconnect from Oracle", "", "$DBI::errstr", "dba-mail", "dba-pager");
    # exit;

    # Commented out this section because of the errors we get: ORA-02050
    # (some remote DBs may be in doubt (DBD: disconnect error)

    1;
  }

  print "      OK\n\n";

}

sub performTest {

  local ($rows1, $rows2, $rows3, $rows4);
  local ($test_dbi_intr_no, $test_dbi_name);
  local ($user, $sysdate);

  # +-----------------------+
  # | CREATE TABLE test_dbi |
  # +-----------------------+

  print "  (*) Creating table TEST_DBI ...\n";

  $sql_statement = "
    CREATE TABLE test_dbi (
        test_dbi_intr_no    NUMBER(15)
      , test_dbi_name       VARCHAR2(100)
    )
  ";
  unless ($rows = $dbh->do("$sql_statement")) {
    &programError("Could not create table TEST_DBI", "$sql_statement", "$DBI::errstr", "dba-mail", "dba-pager");
    $dbh->rollback;
    &logoffOracle($dbh);
    exit;
  }

  print "      OK\n\n";

  # +----------------------------+
  # | INSERT INTO TABLE test_dbi |
  # +----------------------------+

  print "  (*) Insert into TEST_DBI ...\n";

  $sql_statement = "
    INSERT INTO test_dbi (
        test_dbi_intr_no
      , test_dbi_name
    ) VALUES (
        1000
      , 'Jeff Hunter'
    ) 
  ";

  unless ($rows1 = $dbh->do("$sql_statement")) {
    &programError("Could not do INSERT_TEST_DBI (Jeff) cursor", "$sql_statement", "$DBI::errstr", "dba-mail", "dba-pager");
    $dbh->rollback;
    &logoffOracle($dbh);
    exit;
  }

  print "      $rows1 rows inserted.\n";

  $sql_statement = "
    INSERT INTO test_dbi (
        test_dbi_intr_no
      , test_dbi_name
    ) VALUES (
        1001
      , 'Melody Hunter'
    ) 
  ";

  unless ($rows2 = $dbh->do("$sql_statement")) {
    &programError("Could not do INSERT_TEST_DBI (Melody) cursor", "$sql_statement", "$DBI::errstr", "dba-mail", "dba-pager");
    $dbh->rollback;
    &logoffOracle($dbh);
    exit;
  }

  print "      $rows2 rows inserted.\n";

  $sql_statement = "
    INSERT INTO test_dbi (
        test_dbi_intr_no
      , test_dbi_name
    ) VALUES (
        1002
      , 'Alex Hunter'
    ) 
  ";

  unless ($rows3 = $dbh->do("$sql_statement")) {
    &programError("Could not do INSERT_TEST_DBI (Alex) cursor", "$sql_statement", "$DBI::errstr", "dba-mail", "dba-pager");
    $dbh->rollback;
    &logoffOracle($dbh);
    exit;
  }

  print "      $rows3 rows inserted.\n";

  unless ($dbh->commit) {
    &programError("Could not commit INSERT_TEST_DBI transaction", "$sql_statement", "$DBI::errstr", "dba-mail", "dba-pager");
    $dbh->rollback;
    &logoffOracle($dbh);
    exit;
  }

  print "      OK\n\n";

  # +----------------------------+
  # | SELECT FROM TABLE test_dbi |
  # +----------------------------+

  print "  (*) Select from TEST_DBI ...\n";

  $sql_statement = "
    SELECT
        test_dbi_intr_no
      , test_dbi_name
    FROM
        test_dbi
  ";

  unless ($cursor = $dbh->prepare("$sql_statement")) {
    &programError("Could not prepare SELECT_TEST_DBI cursor", "$sql_statement", "$DBI::errstr", "dba-mail", "dba-pager");
    $dbh->rollback;
    &logoffOracle($dbh);
    exit;
  }

  unless ($cursor->execute) {
   &programError("Could not execute SELECT_TEST_DBI cursor", "$sql_statement", "$DBI::errstr", "dba-mail", "dba-pager");
   $dbh->rollback;
   &logoffOracle($dbh);
   exit;
  }


  while ((   $test_dbi_intr_no
           , $test_dbi_name) = $cursor->fetchrow_array) {

    print "\n";
    print "        --> TEST_DBI_INTR_NO :  $test_dbi_intr_no\n";
    print "        --> TEST_DBI_NAME    :  $test_dbi_name\n";
    print "\n";

  }

  unless ($cursor->finish) {
   &programError("Could not finish SELECT_TEST_DBI cursor", "$sql_statement", "$DBI::errstr", "dba-mail", "dba-pager");
   $dbh->rollback;
   &logoffOracle($dbh);
   exit;
  }


  print "      OK\n\n";


  # +----------------------------+
  # | DELETE FROM TABLE test_dbi |
  # +----------------------------+

  print "  (*) Delete from TEST_DBI ...\n";

  $sql_statement = "
    DELETE FROM test_dbi
  ";

  unless ($rows4 = $dbh->do("$sql_statement")) {
    &programError("Could not do DELETE_TEST_DBI (All Names) cursor", "$sql_statement", "$DBI::errstr", "dba-mail", "dba-pager");
    $dbh->rollback;
    &logoffOracle($dbh);
    exit;
  }

  print "      $rows4 rows deleted.\n";

  unless ($dbh->commit) {
    &programError("Could not commit DELETE_TEST_DBI transaction", "$sql_statement", "$DBI::errstr", "dba-mail", "dba-pager");
    $dbh->rollback;
    &logoffOracle($dbh);
    exit;
  }

  print "      OK\n\n";


  # +---------------------+
  # | DROP TABLE test_dbi |
  # +---------------------+

  print "  (*) Drop table TEST_DBI ...\n";

  $sql_statement = "
    DROP TABLE test_dbi
  ";

  unless ($rows = $dbh->do("$sql_statement")) {
    &programError("Could not drop table TEST_DBI", "$sql_statement", "$DBI::errstr", "dba-mail", "dba-pager");
    $dbh->rollback;
    &logoffOracle($dbh);
    exit;
  }

  print "      OK\n\n";


  # +-------------------+
  # | GET USER and DATE |
  # +-------------------+

  print "  (*) Select USER and SYSTEM ...\n";

  $sql_statement = "
    SELECT
        user
      , TO_CHAR(sysdate, 'DD-MON-YYYY HH24:MI:SS')
    FROM
        dual
  ";

  unless ($cursor = $dbh->prepare("$sql_statement")) {
    &programError("Could not prepare SELECT_SINGLE cursor", "$sql_statement", "$DBI::errstr", "dba-mail", "dba-pager");
    $dbh->rollback;
    &logoffOracle($dbh);
    exit;
  }

  unless ($cursor->execute) {
   &programError("Could not execute SELECT_SINGLE cursor", "$sql_statement", "$DBI::errstr", "dba-mail", "dba-pager");
   $dbh->rollback;
   &logoffOracle($dbh);
   exit;
  }


  ($user, $sysdate) = $cursor->fetchrow_array;

  print "\n";
  print "        --> USER             :  $user\n";
  print "        --> SYSDATE          :  $sysdate\n";
  print "\n";

  unless ($cursor->finish) {
   &programError("Could not finish SELECT_SINGLE cursor", "$sql_statement", "$DBI::errstr", "dba-mail", "dba-pager");
   $dbh->rollback;
   &logoffOracle($dbh);
   exit;
  }

  print "      OK\n\n";

}


sub programError {

  local($message, $sql_statement, $ora_errstr, $email_who, $page_who) = @_;

  print "+--------------------------+\n";
  print "| SUB: programError        |\n";
  print "+--------------------------+\n";
  print "\n";

  unless($message) {$message = "No message provided from calling module.";}

  print "+-------------------------------------------------------+\n";
  print "| ******************* PROGRAM ERROR ******************* |\n";
  print "+-------------------------------------------------------+\n";
  print "\n";
  print "\n";
  print "Message:\n";
  print "--------------------------------------------------------\n";
  print "$message\n";
  print "\n";
  if ($sql_statement) {
    print "SQL:\n";
    print "--------------------------------------------------------\n";
    print "$sql_statement\n";
    print "\n";
  }

  if ($ora_errstr) {
    print "Oracle Error:\n";
    print "--------------------------------------------------------\n";
    print "$ora_errstr\n";
  }

  # +-------------------------------------+
  # | SEND THIS OUTPUT TO THE MAIL SYSTEM |
  # +-------------------------------------+

  if ($email_who) {

    $AUTO_MESSAGE = "\n";
    $AUTO_MESSAGE .= "+-------------------------------------------------------+\n";
    $AUTO_MESSAGE .= "| The following message was automatically               |\n";
    $AUTO_MESSAGE .= "| genereated by the Sysmon System.                      |\n";
    $AUTO_MESSAGE .= "+-------------------------------------------------------+\n";
    $AUTO_MESSAGE .= "\n";

    @EMAIL_ARRAY = split(/ /, $email_who);

    foreach (@EMAIL_ARRAY) {

      $TO = $_."\@your_company.com";
      $FROM_FULL = "\"Sysmon Admin\"";
      $FROM = "\"dba\"";
      $Subject = "Sysmon Mail Error";

      open (MAIL,"|/usr/lib/sendmail -f $FROM -F $FROM_FULL $TO");
      print MAIL "To: $TO\n";
      print MAIL "From: $FROM\n";
      print MAIL "Reply-To: $FROM\n";
      print MAIL "Subject: $Subject\n\n";

      print MAIL "$AUTO_MESSAGE";

      print MAIL "+-------------------------------------------------------+\n";
      print MAIL "| ******************* PROGRAM ERROR ******************* |\n";
      print MAIL "+-------------------------------------------------------+\n";
      print MAIL "\n";
      print MAIL "\n";
      print MAIL "Message:\n";
      print MAIL "--------------------------------------------------------\n";
      print MAIL "$message\n";
      print MAIL "\n";
      if ($sql_statement) {
        print MAIL "SQL:\n";
        print MAIL "$sql_statement\n";
        print MAIL "--------------------------------------------------------\n";
        print MAIL "\n";
      }

      if ($ora_errstr) {
        print MAIL "Oracle Error:\n";
        print MAIL "--------------------------------------------------------\n";
        print MAIL "$ora_errstr\n";
      }

      close MAIL;
    }
  }

  if ($page_who) {

    @PAGER_ARRAY = split(/ /, $page_who);

    foreach (@PAGER_ARRAY) {

      $TO = $_."\@your_company.com";
      $FROM_FULL = "\"Sysmon Admin\"";
      $FROM = "\"dba\"";
      $Subject = "Sysmon Program Error";

      open (MAIL,"|/usr/lib/sendmail -f $FROM -F $FROM_FULL $TO");
      print MAIL "To: $TO\n";
      print MAIL "From: $FROM\n";
      print MAIL "Reply-To: $FROM\n";
      print MAIL "Subject: $Subject\n\n";

      print MAIL "$message\n";
      close MAIL;
    }
  }

}
