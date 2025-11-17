@echo off
echo ========================================
echo    TESTE DAS APIs - FinTrack (JWT)
echo ========================================
echo.

echo [1/8] Testando Health Check...
curl -s http://localhost:8080/api/public/health
echo.
echo.

echo [2/8] Registrando novo usuario...
curl -X POST http://localhost:8080/api/auth/register -H "Content-Type: application/json" -d "{\"name\":\"Teste Usuario\",\"email\":\"teste@fintrack.com\",\"password\":\"123456\"}"
echo.
echo.

echo [3/8] Fazendo login...
curl -X POST http://localhost:8080/api/auth/login -H "Content-Type: application/json" -d "{\"email\":\"teste@fintrack.com\",\"password\":\"123456\"}"
echo.
echo.

echo [4/8] Listando usuarios (sem token - deve falhar)...
curl -s http://localhost:8080/api/users
echo.
echo.

echo [5/8] Validando token...
curl -X POST http://localhost:8080/api/auth/validate -H "Authorization: Bearer SEU_TOKEN_AQUI"
echo.
echo.

echo [6/8] Listando categorias (sem token - deve falhar)...
curl -s http://localhost:8080/api/categories
echo.
echo.

echo [7/8] Listando contas bancarias (sem token - deve falhar)...
curl -s http://localhost:8080/api/bank-accounts
echo.
echo.

echo [8/8] Listando transacoes (sem token - deve falhar)...
curl -s http://localhost:8080/api/transactions
echo.
echo.

echo ========================================
echo    TESTE CONCLUIDO!
echo ========================================
echo.
echo Para testar com token:
echo 1. Execute o passo 3 para obter um token
echo 2. Copie o token da resposta
echo 3. Use o token nos headers: -H "Authorization: Bearer SEU_TOKEN_AQUI"
echo.
echo Para acessar o console H2:
echo http://localhost:8080/h2-console
echo.
pause 