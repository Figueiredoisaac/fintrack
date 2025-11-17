@echo off
echo ========================================
echo    EXECUTANDO TESTES - FinTrack
echo ========================================
echo.

echo [1/3] Compilando projeto...
cd fintrack
mvn clean compile -q
if %errorlevel% neq 0 (
    echo ERRO: Falha na compilacao!
    pause
    exit /b 1
)
echo Compilacao concluida com sucesso!
echo.

echo [2/3] Executando testes unitarios...
mvn test -q
if %errorlevel% neq 0 (
    echo ERRO: Falha nos testes!
    pause
    exit /b 1
)
echo Testes executados com sucesso!
echo.

echo [3/3] Gerando relatorio de cobertura...
mvn jacoco:report -q
if %errorlevel% neq 0 (
    echo AVISO: Falha na geracao do relatorio de cobertura!
) else (
    echo Relatorio de cobertura gerado!
    echo Local: fintrack/target/site/jacoco/index.html
)
echo.

echo ========================================
echo    TESTES CONCLUIDOS COM SUCESSO!
echo ========================================
echo.
echo Para ver o relatorio de cobertura:
echo Abra: fintrack/target/site/jacoco/index.html
echo.
pause 