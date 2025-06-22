@echo off
echo Compiling Java application...

:: Create bin directory if it doesn't exist
if not exist "bin" mkdir bin

:: Compile with classpath including JAR files
javac -cp ".;lib/miglayout-4.0.jar;lib/TimingFramework-0.55.jar;lib/mysql-connector-j-9.3.0.jar;lib/flatlaf-demo-3.6.jar" -d bin src\Main.java src\connection\*.java src\model\*.java src\panels\*.java src\window\*.java src\component\*.java src\swing\*.java

:: Copy icons to bin directory for resource loading
xcopy /E /Y src\icon bin\icon\

if %ERRORLEVEL% EQU 0 (
    echo Compilation successful!
    echo Running application...
    java -cp "bin;.;lib/miglayout-4.0.jar;lib/TimingFramework-0.55.jar;lib/mysql-connector-j-9.3.0.jar;lib/flatlaf-demo-3.6.jar" Main
) else (
    echo Compilation failed!
    pause
) 