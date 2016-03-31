REM  +------------------------------------------------------------+
REM  | FILE     : produceClassDoc.bat                             |
REM  | PURPOSE  : The following batch file can be used to produce |
REM  |            HTML documentation for the "Javadoc Class       |
REM  |            Example" application.                           |
REM  +------------------------------------------------------------+

SET OUTPUT_DIR=C:\export\home\jeffreyh\java\example_code_blocks\javadoc\output\ClassOutput
SET SRC_FILE=c:\export\home\jeffreyh\java\example_code_blocks\javadoc\JavadocClassExample\JavadocClassExample.java
SET HEADER="<font color="blue">iDevelopment.info</font>"
SET FOOTER="<font color="red">iDevelopment.info</font>"
SET BOTTOM="Developed by <a target="_blank" href="http://www.iDevelopment.info">www.iDevelopment.info</a>"

javadoc -breakiterator -author -version -verbose -windowtitle "Javadoc Class Example" -doctitle "Javadoc Class Example" -header %HEADER% -footer %FOOTER% -bottom %BOTTOM% -d %OUTPUT_DIR% %SRC_FILE%
