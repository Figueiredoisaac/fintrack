# ?? Documentação das APIs - FinTrack (com JWT)

## ?? **Base URL**
```
http://localhost:8080/api
```

## ?? **Autenticação JWT**

O FinTrack agora utiliza autenticação JWT (JSON Web Token) para proteger as APIs.

### **Como usar:**

1. **Registrar um usuário** ou **Fazer login** para obter um token
2. **Incluir o token** no header `Authorization: Bearer <token>` em todas as requisições
3. **O token expira** em 24 horas por padrão

### **Exemplo de uso:**
```bash
# Fazer login
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "isaac@fintrack.com",
    "password": "123456"
  }'

# Usar o token retornado
curl -X GET http://localhost:8080/api/users \
  -H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9..."
```

---

## ?? **APIs de Autenticação**

### **POST** `/auth/login`
Faz login e retorna um token JWT
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "isaac@fintrack.com",
    "password": "123456"
  }'
```

**Resposta:**
```json
{
  "token": "eyJhbGciOiJIUzUxMiJ9...",
  "type": "Bearer",
  "userId": 1,
  "userName": "Isaac Figueiredo",
  "userEmail": "isaac@fintrack.com",
  "expiresAt": "2024-01-27T20:00:00"
}
```

### **POST** `/auth/register`
Registra um novo usuário e retorna um token JWT
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Novo Usuário",
    "email": "novo@fintrack.com",
    "password": "123456"
  }'
```

### **POST** `/auth/validate`
Valida se um token JWT é válido
```bash
curl -X POST http://localhost:8080/api/auth/validate \
  -H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9..."
```

---

## ?? **APIs Públicas**

### **GET** `/public/health`
Health check da aplicação
```bash
curl -X GET http://localhost:8080/api/public/health
```

### **GET** `/public/info`
Informações da aplicação
```bash
curl -X GET http://localhost:8080/api/public/info
```

---

## ?? **APIs de Usuários** (Requer Autenticação)

### **GET** `/users`
Lista todos os usuários
```bash
curl -X GET http://localhost:8080/api/users \
  -H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9..."
```

### **GET** `/users/{id}`
Busca usuário por ID
```bash
curl -X GET http://localhost:8080/api/users/1 \
  -H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9..."
```

### **GET** `/users/email/{email}`
Busca usuário por email
```bash
curl -X GET http://localhost:8080/api/users/email/isaac@fintrack.com \
  -H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9..."
```

### **POST** `/users`
Cria um novo usuário
```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9..." \
  -d '{
    "name": "Isaac Figueiredo",
    "email": "isaac@fintrack.com",
    "password": "123456"
  }'
```

### **PUT** `/users/{id}`
Atualiza um usuário
```bash
curl -X PUT http://localhost:8080/api/users/1 \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9..." \
  -d '{
    "name": "Isaac Figueiredo Atualizado",
    "email": "isaac@fintrack.com",
    "password": "nova123456"
  }'
```

### **DELETE** `/users/{id}`
Remove um usuário
```bash
curl -X DELETE http://localhost:8080/api/users/1 \
  -H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9..."
```

### **GET** `/users/check-email/{email}`
Verifica se email existe
```bash
curl -X GET http://localhost:8080/api/users/check-email/isaac@fintrack.com \
  -H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9..."
```

---

## ?? **APIs de Categorias** (Requer Autenticação)

### **GET** `/categories`
Lista todas as categorias
```bash
curl -X GET http://localhost:8080/api/categories \
  -H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9..."
```

### **GET** `/categories/{id}`
Busca categoria por ID
```bash
curl -X GET http://localhost:8080/api/categories/1 \
  -H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9..."
```

### **GET** `/categories/user/{userId}`
Lista categorias de um usuário
```bash
curl -X GET http://localhost:8080/api/categories/user/1 \
  -H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9..."
```

### **GET** `/categories/user/{userId}/type/{type}`
Lista categorias de um usuário por tipo
```bash
curl -X GET http://localhost:8080/api/categories/user/1/type/EXPENSE \
  -H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9..."
```

### **POST** `/categories`
Cria uma nova categoria
```bash
curl -X POST http://localhost:8080/api/categories \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9..." \
  -d '{
    "name": "Alimentação",
    "description": "Gastos com alimentação",
    "color": "#dc3545",
    "icon": "???",
    "type": "EXPENSE",
    "userId": 1
  }'
```

### **PUT** `/categories/{id}`
Atualiza uma categoria
```bash
curl -X PUT http://localhost:8080/api/categories/1 \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9..." \
  -d '{
    "name": "Alimentação Atualizada",
    "description": "Gastos com alimentação e restaurantes",
    "color": "#dc3545",
    "icon": "???",
    "type": "EXPENSE",
    "userId": 1
  }'
```

### **DELETE** `/categories/{id}`
Remove uma categoria
```bash
curl -X DELETE http://localhost:8080/api/categories/1 \
  -H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9..."
```

### **GET** `/categories/types`
Lista os tipos de categoria disponíveis
```bash
curl -X GET http://localhost:8080/api/categories/types \
  -H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9..."
```

---

## ?? **APIs de Contas Bancárias** (Requer Autenticação)

### **GET** `/bank-accounts`
Lista todas as contas bancárias
```bash
curl -X GET http://localhost:8080/api/bank-accounts \
  -H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9..."
```

### **GET** `/bank-accounts/{id}`
Busca conta bancária por ID
```bash
curl -X GET http://localhost:8080/api/bank-accounts/1 \
  -H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9..."
```

### **GET** `/bank-accounts/user/{userId}`
Lista contas bancárias de um usuário
```bash
curl -X GET http://localhost:8080/api/bank-accounts/user/1 \
  -H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9..."
```

### **GET** `/bank-accounts/user/{userId}/type/{accountType}`
Lista contas bancárias de um usuário por tipo
```bash
curl -X GET http://localhost:8080/api/bank-accounts/user/1/type/CHECKING \
  -H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9..."
```

### **GET** `/bank-accounts/user/{userId}/balance`
Obtém o saldo total de um usuário
```bash
curl -X GET http://localhost:8080/api/bank-accounts/user/1/balance \
  -H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9..."
```

### **POST** `/bank-accounts`
Cria uma nova conta bancária
```bash
curl -X POST http://localhost:8080/api/bank-accounts \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9..." \
  -d '{
    "name": "Conta Principal",
    "description": "Conta corrente principal",
    "institution": "Banco do Brasil",
    "accountNumber": "12345-6",
    "agency": "0001",
    "accountType": "CHECKING",
    "initialBalance": 5000.00,
    "color": "#007bff",
    "icon": "??",
    "userId": 1
  }'
```

### **PUT** `/bank-accounts/{id}`
Atualiza uma conta bancária
```bash
curl -X PUT http://localhost:8080/api/bank-accounts/1 \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9..." \
  -d '{
    "name": "Conta Principal Atualizada",
    "description": "Conta corrente principal atualizada",
    "institution": "Banco do Brasil",
    "accountNumber": "12345-6",
    "agency": "0001",
    "accountType": "CHECKING",
    "initialBalance": 5000.00,
    "color": "#007bff",
    "icon": "??",
    "userId": 1
  }'
```

### **DELETE** `/bank-accounts/{id}`
Remove uma conta bancária
```bash
curl -X DELETE http://localhost:8080/api/bank-accounts/1 \
  -H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9..."
```

### **GET** `/bank-accounts/types`
Lista os tipos de conta disponíveis
```bash
curl -X GET http://localhost:8080/api/bank-accounts/types \
  -H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9..."
```

---

## ?? **APIs de Transações** (Requer Autenticação)

### **GET** `/transactions`
Lista todas as transações
```bash
curl -X GET http://localhost:8080/api/transactions \
  -H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9..."
```

### **GET** `/transactions/{id}`
Busca transação por ID
```bash
curl -X GET http://localhost:8080/api/transactions/1 \
  -H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9..."
```

### **GET** `/transactions/user/{userId}`
Lista transações de um usuário (com paginação)
```bash
curl -X GET "http://localhost:8080/api/transactions/user/1?page=0&size=20" \
  -H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9..."
```

### **GET** `/transactions/user/{userId}/date-range`
Lista transações de um usuário por período
```bash
curl -X GET "http://localhost:8080/api/transactions/user/1/date-range?startDate=2024-01-01&endDate=2024-12-31" \
  -H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9..."
```

### **GET** `/transactions/user/{userId}/account/{accountId}`
Lista transações de um usuário por conta
```bash
curl -X GET http://localhost:8080/api/transactions/user/1/account/1 \
  -H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9..."
```

### **GET** `/transactions/user/{userId}/category/{categoryId}`
Lista transações de um usuário por categoria
```bash
curl -X GET http://localhost:8080/api/transactions/user/1/category/1 \
  -H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9..."
```

### **GET** `/transactions/user/{userId}/recurring`
Lista transações recorrentes de um usuário
```bash
curl -X GET http://localhost:8080/api/transactions/user/1/recurring \
  -H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9..."
```

### **GET** `/transactions/user/{userId}/total`
Obtém total de transações por tipo e período
```bash
curl -X GET "http://localhost:8080/api/transactions/user/1/total?type=EXPENSE&startDate=2024-01-01&endDate=2024-12-31" \
  -H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9..."
```

### **GET** `/transactions/user/{userId}/count`
Conta transações de um usuário por período
```bash
curl -X GET "http://localhost:8080/api/transactions/user/1/count?startDate=2024-01-01&endDate=2024-12-31" \
  -H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9..."
```

### **POST** `/transactions`
Cria uma nova transação
```bash
curl -X POST http://localhost:8080/api/transactions \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9..." \
  -d '{
    "description": "Supermercado",
    "notes": "Compras do mês",
    "amount": -800.00,
    "transactionDate": "2024-01-15",
    "type": "EXPENSE",
    "status": "CONFIRMED",
    "categoryId": 1,
    "bankAccountId": 1,
    "userId": 1,
    "recurring": false
  }'
```

### **PUT** `/transactions/{id}`
Atualiza uma transação
```bash
curl -X PUT http://localhost:8080/api/transactions/1 \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9..." \
  -d '{
    "description": "Supermercado Atualizado",
    "notes": "Compras do mês atualizadas",
    "amount": -850.00,
    "transactionDate": "2024-01-15",
    "type": "EXPENSE",
    "status": "CONFIRMED",
    "categoryId": 1,
    "bankAccountId": 1,
    "userId": 1,
    "recurring": false
  }'
```

### **DELETE** `/transactions/{id}`
Remove uma transação
```bash
curl -X DELETE http://localhost:8080/api/transactions/1 \
  -H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9..."
```

### **GET** `/transactions/types`
Lista os tipos de transação disponíveis
```bash
curl -X GET http://localhost:8080/api/transactions/types \
  -H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9..."
```

### **GET** `/transactions/statuses`
Lista os status de transação disponíveis
```bash
curl -X GET http://localhost:8080/api/transactions/statuses \
  -H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9..."
```

---

## ??? **Console H2**

### **Acesso ao Banco de Dados**
```
URL: http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:testdb
Username: sa
Password: password
```

---

## ?? **Tipos de Dados**

### **CategoryType**
- `INCOME` - Receita
- `EXPENSE` - Despesa
- `TRANSFER` - Transferência

### **AccountType**
- `CHECKING` - Conta Corrente
- `SAVINGS` - Conta Poupança
- `INVESTMENT` - Conta Investimento
- `CREDIT_CARD` - Cartão de Crédito
- `WALLET` - Carteira
- `OTHER` - Outro

### **TransactionType**
- `INCOME` - Receita
- `EXPENSE` - Despesa
- `TRANSFER` - Transferência

### **TransactionStatus**
- `PENDING` - Pendente
- `CONFIRMED` - Confirmado
- `CANCELLED` - Cancelado

---

## ?? **Exemplos de Teste**

### **1. Registrar um usuário**
```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Teste Usuário",
    "email": "teste@fintrack.com",
    "password": "123456"
  }'
```

### **2. Fazer login**
```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "teste@fintrack.com",
    "password": "123456"
  }'
```

### **3. Criar uma categoria (com token)**
```bash
curl -X POST http://localhost:8080/api/categories \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer SEU_TOKEN_AQUI" \
  -d '{
    "name": "Transporte",
    "description": "Combustível e transporte público",
    "color": "#fd7e14",
    "icon": "??",
    "type": "EXPENSE",
    "userId": 1
  }'
```

### **4. Criar uma conta bancária (com token)**
```bash
curl -X POST http://localhost:8080/api/bank-accounts \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer SEU_TOKEN_AQUI" \
  -d '{
    "name": "Conta Principal",
    "description": "Conta corrente principal",
    "institution": "Banco do Brasil",
    "accountNumber": "12345-6",
    "agency": "0001",
    "accountType": "CHECKING",
    "initialBalance": 5000.00,
    "color": "#007bff",
    "icon": "??",
    "userId": 1
  }'
```

### **5. Criar uma transação (com token)**
```bash
curl -X POST http://localhost:8080/api/transactions \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer SEU_TOKEN_AQUI" \
  -d '{
    "description": "Supermercado",
    "notes": "Compras do mês",
    "amount": -800.00,
    "transactionDate": "2024-01-15",
    "type": "EXPENSE",
    "status": "CONFIRMED",
    "categoryId": 1,
    "bankAccountId": 1,
    "userId": 1,
    "recurring": false
  }'
```

---

## ?? **APIs de Dashboard** (Requer Autenticação)

### **GET** `/dashboard/user/{userId}`
Obtém dashboard completo para um usuário
```bash
curl -X GET "http://localhost:8080/api/dashboard/user/1?startDate=2024-01-01&endDate=2024-12-31" \
  -H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9..."
```

### **GET** `/dashboard/user/{userId}/current-month`
Obtém dashboard do mês atual
```bash
curl -X GET http://localhost:8080/api/dashboard/user/1/current-month \
  -H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9..."
```

### **GET** `/dashboard/user/{userId}/current-year`
Obtém dashboard do ano atual
```bash
curl -X GET http://localhost:8080/api/dashboard/user/1/current-year \
  -H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9..."
```

**Resposta do Dashboard:**
```json
{
  "totalBalance": 15000.00,
  "totalIncome": 25000.00,
  "totalExpense": -10000.00,
  "monthlyBalance": 5000.00,
  "monthlyIncome": 8000.00,
  "monthlyExpense": -3000.00,
  "accountBalances": [
    {
      "accountId": 1,
      "accountName": "Conta Principal",
      "institution": "Banco do Brasil",
      "accountType": "CHECKING",
      "balance": 10000.00,
      "color": "#007bff",
      "icon": "??"
    }
  ],
  "topCategories": [
    {
      "categoryId": 1,
      "categoryName": "Alimentação",
      "categoryType": "EXPENSE",
      "totalAmount": 3000.00,
      "transactionCount": 15,
      "color": "#dc3545",
      "icon": "???",
      "percentage": 30.00
    }
  ],
  "monthlyChart": {
    "Jan/2024": 5000.00,
    "Fev/2024": 3000.00,
    "Mar/2024": 7000.00
  },
  "recentTransactions": [
    {
      "id": 1,
      "description": "Supermercado",
      "amount": -800.00,
      "transactionDate": "2024-01-15",
      "type": "EXPENSE",
      "status": "CONFIRMED",
      "categoryName": "Alimentação",
      "accountName": "Conta Principal",
      "categoryColor": "#dc3545",
      "categoryIcon": "???"
    }
  ],
  "periodStart": "2024-01-01",
      "periodEnd": "2024-12-31"
}
```

---

## ?? **Próximas Implementações**

- [ ] Upload de arquivos
- [ ] Notificações
- [ ] Frontend Angular 