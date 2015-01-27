@echo off
if "%JMKIT_DIR%"=="" goto :end
if not exist %JMKIT_DIR% goto :end


if exist %JMKIT_DIR%\android-jmkits\src RMDIR %JMKIT_DIR%\android-jmkits\src\ /S /Q
MKDIR %JMKIT_DIR%\android-jmkits\src\
echo -------------------UPDATE JMKIT SOURCES-------------------
echo XCOPY %JMKIT_DIR%\source\common\* %JMKIT_DIR%\Android-JMKit\src\* /e
XCOPY %JMKIT_DIR%\source\common\* %JMKIT_DIR%\android-jmkits\src\* /e
XCOPY %JMKIT_DIR%\source\android\* %JMKIT_DIR%\android-jmkits\src\* /e
echo JMKIT SOURCES HAVE BEEN UPDATED
:end
CD %ROOT%