@echo off

REM #####################################################################################################


set APP_HOME=%~dp0
set APP_BIN=%APP_HOME%bin
set APP_LIB=%APP_HOME%lib
set CLASSPATH=%APP_LIB%\IDbUpdates.jar;%APP_BIN%\DbUpdateNotifier.jar
IF (%PROCESSOR_ARCHITECTURE%)==(AMD64) (set NATIVE_PATH=%OH_LIB%\native\Win64) ELSE (set NATIVE_PATH=%OH_LIB%\native\Windows)


REM #######################################################################################################


cd /d %APP_HOME%
start /B "" "%JAVA7_HOME%\bin\java.exe" -showversion -Djava.library.path=%NATIVE_PATH% -cp %CLASSPATH% dbupdatenotifier.DbUpdateNotifier


REM ######################################################################################################3














