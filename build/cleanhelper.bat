@ECHO OFF

ECHO.>CON

ECHO Cleaning Framework Project> CON
CALL mvn -f framework/pom.xml clean install -DskipTests
ECHO ^|Done Cleaning Framework Project^|> CON

ECHO.>CON

ECHO Cleaning DM Project> CON
CALL mvn -f dm/pom.xml clean install -DskipTests
ECHO ^|Done Cleaning DM Project^|> CON

ECHO.>CON

ECHO Cleaning DS Project> CON
CALL mvn -f ds/pom.xml clean install -DskipTests
ECHO ^|Done Cleaning DS Project^|> CON



