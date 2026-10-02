@echo off
setlocal
cd /d "%~dp0"

where javac >nul 2>nul
if errorlevel 1 (
    echo Erro: JDK nao encontrado. Instale o JDK 8 ou superior e tente novamente.
    exit /b 1
)

if not exist build mkdir build

javac -encoding UTF-8 -d build src\br\com\x10d\dino\*.java
if errorlevel 1 exit /b %errorlevel%

java -cp "build;arquivos" br.com.x10d.dino.Main
