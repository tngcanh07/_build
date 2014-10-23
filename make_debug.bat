@echo off
SET IS_RELEASE=0

call ..\config.bat
call build_setup.bat

cd %PROJECT_DIR%

%APACHE_ANT%\ant make-debug

cd %BUILD_DIR%

