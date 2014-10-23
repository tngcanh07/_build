@echo off
cd tools
if(%1 == nul) goto :end
echo abc
:end
cd ..
pause