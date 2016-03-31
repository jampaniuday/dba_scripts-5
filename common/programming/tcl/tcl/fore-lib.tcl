#!/usr/local/bin/tclsh

# +--------------------------------------------------------------------------+
# | File         : fore-lib.tcl                                              |
# | Programmer   : Jeff Hunter                                               |
# | Date         : 29-SEP-2000                                               |
# | Purpose      : The following script provides many subroutines that will  |
# |                be common to the Web*DBA application. It also contains    |
# |                any global variables.                                      |
# +--------------------------------------------------------------------------+

proc printHeader {} {
  puts "Content-type: text/html\n\n"
}

proc endHTML {} {
  puts "</BODY></HTML>"
}

proc startHTML {title} {
  puts "<HTML><BODY><Style>A.noLinkBlackT { color:#000000; text-decoration: none; }</Style>"
  puts "<FONT SIZE=\"4\"><IMG SRC=\"../config/logo_small.gif\">&nbsp;"
  puts "<B>$title</B>"
  puts "<HR SIZE=\"3\" NOSHADE></FONT>"
  puts "<P><BR>"
}
