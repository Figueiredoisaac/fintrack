@echo off
echo ======================================
echo     TESTE SIMPLES - FinTrack
echo ======================================
echo.

echo Aguardando aplicacao inicializar...
timeout /t 5 /nobreak > nul

echo.
echo [1] Testando Health Check...
curl -f -s http://localhost:8080/api/public/health
if %errorlevel% equ 0 (
    echo.
    echo ? Health Check: OK
) else (
    echo.
    echo ? Health Check: FALHOU
    echo Verifique se a aplicacao esta rodando em http://localhost:8080
    goto :end
)

echo.
echo [2] Testando registro de usuario...
curl -f -s -X POST http://localhost:8080/api/auth/register ^
  -H "Content-Type: application/json" ^
  -d "{\"name\":\"Teste Usuario\",\"email\":\"teste@fintrack.com\",\"password\":\"123456\"}"
if %errorlevel% equ 0 (
    echo.
    echo ? Registro: OK
) else (
    echo.
    echo ? Registro: FALHOU
)

echo.
echo [3] Testando login...
curl -f -s -X POST http://localhost:8080/api/auth/login ^
  -H "Content-Type: application/json" ^
  -d "{\"email\":\"teste@fintrack.com\",\"password\":\"123456\"}"
if %errorlevel% equ 0 (
    echo.
    echo ? Login: OK
) else (
    echo.
    echo ? Login: FALHOU
)

:end
echo.
echo ======================================
echo     TESTE CONCLUIDO
echo ======================================
pause 