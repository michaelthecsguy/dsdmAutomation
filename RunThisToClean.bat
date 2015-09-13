@ECHO OFF
ECHO Attempting to clean CPO Automation Projects...> CON

CALL build\cleanhelper.bat > CleanLog.txt

find "BUILD FAILURE" CleanLog.txt 
if %errorlevel%==0 (
 ECHO.
 ECHO ^|BUILD FAILURE has occurred for one or more projects!^|
 ECHO Review CleanLog.txt for information on builds.
) else (
 ECHO.
 ECHO ^|SUCCESS^|
 ECHO ^|Projects built successfully!^|
)

ECHO.

PAUSE
