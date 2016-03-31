# +------------------------------------------------------------+
# | FILE     : produceClassDoc.sh                              |
# | PURPOSE  : The following batch file can be used to produce |
# |            HTML documentation for the "Javadoc Class       |
# |            Example" application.                           |
# +------------------------------------------------------------+

javadoc \
  -breakiterator \
  -author \
  -version \
  -verbose \
  -windowtitle "Javadoc Class Example" \
  -doctitle "Javadoc Class Example" \
  -header "<font color=\"blue\">iDevelopment.info</font>" \
  -footer "<font color=\"red\">iDevelopment.info</font>" \
  -bottom "Developed by <a target=\"_blank\" href=\"http://www.iDevelopment.info\">iDevelopment.info</a>" \
  -d "C:\export\home\jeffreyh\java\example_code_blocks\javadoc\output\ClassOutput" \
  "C:\export\home\jeffreyh\java\example_code_blocks\javadoc\JavadocClassExample\JavadocClassExample.java"
