REM  +------------------------------------------------------------+
REM  | FILE     : producePackageDoc.bat                           |
REM  | PURPOSE  : The following batch file can be used to produce |
REM  |            HTML documentation for the "Javadoc Package     |
REM  |            Example" application.                           |
REM  +------------------------------------------------------------+

SET OUTPUT_DIR=C:\export\home\jeffreyh\java\example_code_blocks\javadoc\output\PackageOutput
SET SRC_DIR=c:\export\home\jeffreyh\java\example_code_blocks\javadoc\JavadocPackageExample
SET HEADER="<font color="blue">iDevelopment.info</font>"
SET FOOTER="<font color="red">iDevelopment.info</font>"
SET BOTTOM="Developed by <a target="_blank" href="http://www.iDevelopment.info">www.iDevelopment.info</a>"

javadoc -breakiterator -author -version -verbose -docfilessubdirs -windowtitle "Javadoc Package Example" -doctitle "Javadoc Package Example" -header %HEADER% -footer %FOOTER% -bottom %BOTTOM% -d %OUTPUT_DIR% -sourcepath %SRC_DIR% -overview %SRC_DIR%\overview.html info.idevelopment.math info.idevelopment.utils
