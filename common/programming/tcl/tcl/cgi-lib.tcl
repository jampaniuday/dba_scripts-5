#!/usr/local/bin/tclsh

# +--------------------------------------------------------------------------+
# | File         : cgi-lib.tcl                                               |
# | Programmer   : Jeff Hunter                                               |
# | Date         : 29-SEP-2000                                               |
# | Purpose      : The following script provides many subroutines that can   |
# |                be used to read HTML Forms.                               |
# +--------------------------------------------------------------------------+


# TclX replacements:
proc read_file {fname} {
    set f [open $fname]
    set r [read $f]
    close $f
    set r
}
proc cequal {s1 s2} {
    expr {[string compare $s1 $s2]==0}
}

#
# UnCgi Translation hack, in Tcl, v1.5 5/1995 by L@demailly.com
# this version should be updated to newer, using subst and taking
# care of ::
proc uncgi {buf} {
    # ncsa httpd (at least) \ quotes some chars, including \ so :
    regsub -all {\\(.)} $buf {\1} buf ;
    regsub -all {\\} $buf {\\\\} buf ;
    regsub -all { }  $buf {\ } buf ;
    regsub -all {\+} $buf {\ } buf ;
    regsub -all {\$} $buf {\$} buf ;
    regsub -all \n   $buf {\n} buf ;
    regsub -all {;}  $buf {\;} buf ;
    regsub -all {\[} $buf {\[} buf ;
    regsub -all \" $buf \\\" buf ;
    # the next one can probably be skipped as the first char is prolly not
    # an \{, but, hey who knows... lets be safe...
    regsub  ^\{ $buf \\\{ buf ;
    # I think everything has been escaped, now the real work :
    regsub -all -nocase {%([a-fA-F0-9][a-fA-F0-9])} $buf {[format %c 0x\1]} buf
    # And now lets replace all those escaped back, along with excuting of
    # the format :
    eval return \"$buf\"
    # now everything is in buf, but translated, nice trick no ?
}

#
# text -> html + auto link of urls
#
proc escape {str {auto 1}} {
    regsub -all {&} $str {\&amp;} str;
    regsub -all {<} $str {\&lt;} str;
    regsub -all {>} $str {\&gt;} str;
    regsub -all {"} $str {\&quot;} str;
    regsub -all "\[\t\r\n\]\[ \t\r\n\]*" $str { } str;
    if {$auto} {
        regsub -all {(http|ftp|gopher)://([^& ,;)|]+)} $str {<a href="\0">\0</a>} str;
    }
    return $str;
}

# returns in the 'cgi' array all the parameters sent to the script
# through 'message' (each array cell is a list (ie if only one value
# is expected through 'test' variable, use [lindex $cgi(test) 0] to get it.
proc parse_cgi_message {message} {
    global cgi;
    foreach pair [split $message &] {
        set plst [split $pair =];
        set name [uncgi [lindex $plst 0]];
        set val  [uncgi [lindex $plst 1]];
        lappend cgi($name) $val;
    }
}

# process form values, accept only post method
set message "";
if {[info exist env(REQUEST_METHOD)] && [string compare $env(REQUEST_METHOD) "POST"]==0} {
  set message [read stdin $env(CONTENT_LENGTH)];
}

if {[info exist env(REQUEST_METHOD)] && [string compare $env(REQUEST_METHOD) "GET"]==0} {
  set message $env(QUERY_STRING);
}

set cgi() ""
parse_cgi_message $message;
