@echo off
echo ======================================
echo    INICIANDO FinTrack
echo ======================================
echo.

echo [1] Verificando estrutura do projeto...
if not exist "fintrack\pom.xml" (
    echo ERRO: Arquivo pom.xml nao encontrado em fintrack\
    echo Verifique se voce esta no diretorio correto
    pause
    exit /b 1
)
echo ? Estrutura OK

echo.
echo [2] Navegando para o diretorio do projeto...
cd fintrack
echo ? Diretorio: %CD%

echo.
echo [3] Limpando e compilando...
mvn clean compile -Dmaven.compiler.failOnError=false -q
if %errorlevel% neq 0 (
    echo ERRO: Falha na compilacao!
    pause
    exit /b 1
)
echo ? Compilacao concluida

echo.
echo [4] Iniciando aplicacao Spring Boot...
echo    - Acesse: http://localhost:8080
echo    - Para parar: Ctrl+C
echo.
mvn spring-boot:run -Dmaven.compiler.failOnError=false

pause 