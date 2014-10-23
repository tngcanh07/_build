@echo off
if exist %JMKIT_DIR%\Android-JMKit\src RMDIR %JMKIT_DIR%\Android-JMKit\src\ /S /Q
MKDIR %JMKIT_DIR%\Android-JMKit\src\
echo -------------------UPDATE JMKIT SOURCES-------------------
echo XCOPY %JMKIT_DIR%\source\common\* %JMKIT_DIR%\Android-JMKit\src\* /e
XCOPY %JMKIT_DIR%\source\common\* %JMKIT_DIR%\Android-JMKit\src\* /e
XCOPY %JMKIT_DIR%\source\android\* %JMKIT_DIR%\Android-JMKit\src\* /e
echo JMKIT SOURCES HAVE BEEN UPDATED
CD %ROOT%