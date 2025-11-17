@echo off
echo ========================================
echo    TESTE DAS APIs - FinTrack
echo ========================================
echo.

echo [1/6] Testando Health Check...
curl -s http://localhost:8080/api/public/health
echo.
echo.

echo [2/6] Testando Info...
curl -s http://localhost:8080/api/public/info
echo.
echo.

echo [3/6] Criando usuário de teste...
curl -X POST http://localhost:8080/api/users -H "Content-Type: application/json" -d "{\"name\":\"Teste Usuario\",\"email\":\"teste@fintrack.com\",\"password\":\"123456\"}"
echo.
echo.

echo [4/6] Listando usuários...
curl -s http://localhost:8080/api/users
echo.
echo.

echo [5/6] Criando categoria de despesa...
curl -X POST http://localhost:8080/api/categories -H "Content-Type: application/json" -d "{\"name\":\"Transporte\",\"description\":\"Combustivel e transporte publico\",\"color\":\"#fd7e14\",\"icon\":\"??\",\"type\":\"EXPENSE\",\"userId\":1}"
echo.
echo.

echo [6/6] Listando categorias...
curl -s http://localhost:8080/api/categories
echo.
echo.

echo [7/8] Criando conta bancaria...
curl -X POST http://localhost:8080/api/bank-accounts -H "Content-Type: application/json" -d "{\"name\":\"Conta Principal\",\"description\":\"Conta corrente principal\",\"institution\":\"Banco do Brasil\",\"accountNumber\":\"12345-6\",\"agency\":\"0001\",\"accountType\":\"CHECKING\",\"initialBalance\":5000.00,\"color\":\"#007bff\",\"icon\":\"??\",\"userId\":1}"
echo.
echo.

echo [8/8] Listando contas bancarias...
curl -s http://localhost:8080/api/bank-accounts
echo.
echo.

echo ========================================
echo    TESTE CONCLUIDO!
echo ========================================
echo.
echo Para acessar o console H2:
echo http://localhost:8080/h2-console
echo.
pause 