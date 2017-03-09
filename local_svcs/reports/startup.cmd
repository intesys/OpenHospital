@echo off


REM #####################################################################################################3

set OH_HOME=%~dp0
set OH_BIN=%OH_HOME%bin
set OH_LIB=%OH_HOME%lib
set CLASSPATH=%OH_LIB%\mysql-connector-java-5.1.14-bin.jar;%OH_LIB%\commons-beanutils-1.7.0.jar;%OH_LIB%\commons-collections-3.1.jar;%OH_LIB%\commons-digester.jar;%OH_LIB%\commons-logging.jar;%OH_LIB%\IDataSource.jar;%OH_LIB%\IIReports.jar;%OH_LIB%\com.lowagie.text-2.1.7.jar;%OH_LIB%\jasperreports-5.6.0.jar;%OH_BIN%\IReports.jar
IF (%PROCESSOR_ARCHITECTURE%)==(AMD64) (set NATIVE_PATH=%OH_LIB%\native\Win64) ELSE (set NATIVE_PATH=%OH_LIB%\native\Windows)


REM ################################################################################################3

cd /d %OH_HOME%\
cd ..\..

start /B "" "%JAVA7_HOME%\bin\java.exe" -showversion -Djava.library.path=%NATIVE_PATH% -cp %CLASSPATH% ireports.IReports

REM ######################################################################################################3














