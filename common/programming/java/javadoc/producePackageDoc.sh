# +------------------------------------------------------------+
# | FILE     : producePackageDoc.sh                            |
# | PURPOSE  : The following batch file can be used to produce |
# |            HTML documentation for the "Javadoc Package     |
# |            Example" application.                           |
# +------------------------------------------------------------+

javadoc \
  -breakiterator \
  -author \
  -version \
  -verbose \
  -docfilessubdirs \
  -windowtitle "Javadoc Package Example" \
  -doctitle "Javadoc Package Example" \
  -header "<font color=\"blue\">iDevelopment.info</font>" \
  -footer "<font color=\"red\">iDevelopment.info</font>" \
  -bottom "Developed by <a target=\"_blank\" href=\"http://www.iDevelopment.info\">iDevelopment.info</a>" \
  -d "C:\export\home\jeffreyh\java\example_code_blocks\javadoc\output\PackageOutput" \
  -sourcepath "C:\export\home\jeffreyh\java\example_code_blocks\javadoc\JavadocPackageExample" \
  -overview "C:\export\home\jeffreyh\java\example_code_blocks\javadoc\JavadocPackageExample\overview.html" \
  info.idevelopment.math \
  info.idevelopment.utils
