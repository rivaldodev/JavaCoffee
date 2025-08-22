@REM ----------------------------------------------------------------------------
@REM Apache Maven Wrapper startup script for Windows
@REM ----------------------------------------------------------------------------
@echo off
setlocal ENABLEDELAYEDEXPANSION

set MAVEN_WRAPPER_VERBOSE=false
if "%MAVEN_WRAPPER_VERBOSE%"=="true" echo ^> Iniciando Maven Wrapper

set SCRIPT_DIR=%~dp0
if "%SCRIPT_DIR%"=="" set SCRIPT_DIR=.
for %%i in ("%SCRIPT_DIR%.") do set MAVEN_PROJECTBASEDIR=%%~fi
if not defined MAVEN_PROJECTBASEDIR set MAVEN_PROJECTBASEDIR=%SCRIPT_DIR%

set WRAPPER_DIR=%MAVEN_PROJECTBASEDIR%\.mvn\wrapper
set WRAPPER_JAR=%WRAPPER_DIR%\maven-wrapper.jar
set WRAPPER_PROPERTIES=%WRAPPER_DIR%\maven-wrapper.properties

if not exist "%WRAPPER_PROPERTIES%" (
  echo ERRO: %WRAPPER_PROPERTIES% não encontrado >&2
  exit /b 1
)

for /f "usebackq tokens=1,2 delims==" %%A in ("%WRAPPER_PROPERTIES%") do (
  if /i "%%A"=="wrapperUrl" set WRAP_URL=%%B
)

if not exist "%WRAPPER_JAR%" (
  if "%MAVEN_WRAPPER_VERBOSE%"=="true" echo Baixando maven-wrapper.jar
  powershell -NoLogo -NoProfile -Command "[Net.ServicePointManager]::SecurityProtocol=[Net.SecurityProtocolType]::Tls12; (New-Object Net.WebClient).DownloadFile('%WRAP_URL%','%WRAPPER_JAR%')" || (
    echo Falha ao baixar maven-wrapper.jar >&2
    exit /b 1
  )
)

set JAVA_EXE=
if defined JAVA_HOME if exist "%JAVA_HOME%\bin\java.exe" set JAVA_EXE="%JAVA_HOME%\bin\java.exe"
if not defined JAVA_EXE set JAVA_EXE=java

set WRAPPER_LAUNCHER=org.apache.maven.wrapper.MavenWrapperMain

%JAVA_EXE% %MAVEN_OPTS% -Dmaven.multiModuleProjectDirectory="%MAVEN_PROJECTBASEDIR%" -classpath "%WRAPPER_JAR%" %WRAPPER_LAUNCHER% %*
set EXIT_CODE=%ERRORLEVEL%
endlocal & exit /b %EXIT_CODE%
