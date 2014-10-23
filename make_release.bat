@echo off
SET IS_RELEASE=1

call ..\config.bat
call build_setup.bat

cd %PROJECT_DIR%

%APACHE_ANT%\ant make-release

cd %BUILD_DIR%

