@echo off
call ..\config.bat
set BUILD_DIR=%~dp0
set TOOL_DIR=%BUILD_DIR%\tools\
set TEMP_DIR=%BUILD_DIR%\temp\
REM copy Setup jmkit resources

call jmkit_copysrc.bat

REM update Manifest file
if "%AUTO_INCREATE_VERSION_CODE%"=="1" (
	if not exist %TEMP_DIR%\tools MKDIR %TEMP_DIR%\tools
	cd %TOOL_DIR%
	javac UpdateManifest.java
	MOVE /Y UpdateManifest.class %TEMP_DIR%\tools\UpdateManifest.class > nul
	cd %TEMP_DIR%\tools
	java UpdateManifest %PROJECT_DIR%\AndroidManifest.xml %VERSION_NAME%
	cd %BUILD_DIR%
)

REM copy specific resouces
if "%OVERRIDE_ANDROID_RESOURCES%"=="1" (
	if not exist %TEMP_DIR%\tools MKDIR %TEMP_DIR%\tools
	cd %TOOL_DIR%
	javac UpdateSpecificResources.java
	MOVE /Y UpdateSpecificResources.class %TEMP_DIR%\tools\UpdateSpecificResources.class > nul
	cd %TEMP_DIR%\tools
	java UpdateSpecificResources %RES_DIR% %RES_SPECIFIC_NAME% %PROJECT_DIR%
	cd %BUILD_DIR%
)

SET ADMOB_LAYOUT=admob_item.xml
