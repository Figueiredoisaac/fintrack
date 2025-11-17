# ?? Documentação das APIs - FinTrack

## ?? **Base URL**
```
http://localhost:8080/api
```

## ?? **Autenticação**
Atualmente as APIs estão em modo de desenvolvimento (sem autenticação obrigatória).

---

## ?? **APIs de Usuários**

### **GET** `/users`
Lista todos os usuários
```bash
curl -X GET http://localhost:8080/api/users
```

### **GET** `/users/{id}`
Busca usuário por ID
```bash
curl -X GET http://localhost:8080/api/users/1
```

### **GET** `/users/email/{email}`
Busca usuário por email
```bash
curl -X GET http://localhost:8080/api/users/email/isaac@fintrack.com
```

### **POST** `/users`
Cria um novo usuário
```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
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
  -d '{
    "name": "Isaac Figueiredo Atualizado",
    "email": "isaac@fintrack.com",
    "password": "nova123456"
  }'
```

### **DELETE** `/users/{id}`
Remove um usuário
```bash
curl -X DELETE http://localhost:8080/api/users/1
```

### **GET** `/users/check-email/{email}`
Verifica se email existe
```bash
curl -X GET http://localhost:8080/api/users/check-email/isaac@fintrack.com
```

---

## ?? **APIs de Categorias**

### **GET** `/categories`
Lista todas as categorias
```bash
curl -X GET http://localhost:8080/api/categories
```

### **GET** `/categories/{id}`
Busca categoria por ID
```bash
curl -X GET http://localhost:8080/api/categories/1
```

### **GET** `/categories/user/{userId}`
Lista categorias de um usuário
```bash
curl -X GET http://localhost:8080/api/categories/user/1
```

### **GET** `/categories/user/{userId}/type/{type}`
Lista categorias de um usuário por tipo
```bash
curl -X GET http://localhost:8080/api/categories/user/1/type/EXPENSE
```

### **POST** `/categories`
Cria uma nova categoria
```bash
curl -X POST http://localhost:8080/api/categories \
  -H "Content-Type: application/json" \
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
curl -X DELETE http://localhost:8080/api/categories/1
```

### **GET** `/categories/types`
Lista os tipos de categoria disponíveis
```bash
curl -X GET http://localhost:8080/api/categories/types
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

### **1. Criar um usuário**
```bash
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Teste Usuário",
    "email": "teste@fintrack.com",
    "password": "123456"
  }'
```

### **2. Criar uma categoria de despesa**
```bash
curl -X POST http://localhost:8080/api/categories \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Transporte",
    "description": "Combustível e transporte público",
    "color": "#fd7e14",
    "icon": "??",
    "type": "EXPENSE",
    "userId": 1
  }'
```

### **3. Criar uma categoria de receita**
```bash
curl -X POST http://localhost:8080/api/categories \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Salário",
    "description": "Salário mensal",
    "color": "#28a745",
    "icon": "??",
    "type": "INCOME",
    "userId": 1
  }'
```

### **4. Criar uma conta bancária**
```bash
curl -X POST http://localhost:8080/api/bank-accounts \
  -H "Content-Type: application/json" \
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

### **5. Criar uma transação**
```bash
curl -X POST http://localhost:8080/api/transactions \
  -H "Content-Type: application/json" \
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

## ?? **APIs de Contas Bancárias**

### **GET** `/bank-accounts`
Lista todas as contas bancárias
```bash
curl -X GET http://localhost:8080/api/bank-accounts
```

### **GET** `/bank-accounts/{id}`
Busca conta bancária por ID
```bash
curl -X GET http://localhost:8080/api/bank-accounts/1
```

### **GET** `/bank-accounts/user/{userId}`
Lista contas bancárias de um usuário
```bash
curl -X GET http://localhost:8080/api/bank-accounts/user/1
```

### **GET** `/bank-accounts/user/{userId}/type/{accountType}`
Lista contas bancárias de um usuário por tipo
```bash
curl -X GET http://localhost:8080/api/bank-accounts/user/1/type/CHECKING
```

### **GET** `/bank-accounts/user/{userId}/balance`
Obtém o saldo total de um usuário
```bash
curl -X GET http://localhost:8080/api/bank-accounts/user/1/balance
```

### **POST** `/bank-accounts`
Cria uma nova conta bancária
```bash
curl -X POST http://localhost:8080/api/bank-accounts \
  -H "Content-Type: application/json" \
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
curl -X DELETE http://localhost:8080/api/bank-accounts/1
```

### **GET** `/bank-accounts/types`
Lista os tipos de conta disponíveis
```bash
curl -X GET http://localhost:8080/api/bank-accounts/types
```

---

## ?? **APIs de Transações**

### **GET** `/transactions`
Lista todas as transações
```bash
curl -X GET http://localhost:8080/api/transactions
```

### **GET** `/transactions/{id}`
Busca transação por ID
```bash
curl -X GET http://localhost:8080/api/transactions/1
```

### **GET** `/transactions/user/{userId}`
Lista transações de um usuário (com paginação)
```bash
curl -X GET "http://localhost:8080/api/transactions/user/1?page=0&size=20"
```

### **GET** `/transactions/user/{userId}/date-range`
Lista transações de um usuário por período
```bash
curl -X GET "http://localhost:8080/api/transactions/user/1/date-range?startDate=2024-01-01&endDate=2024-12-31"
```

### **GET** `/transactions/user/{userId}/account/{accountId}`
Lista transações de um usuário por conta
```bash
curl -X GET http://localhost:8080/api/transactions/user/1/account/1
```

### **GET** `/transactions/user/{userId}/category/{categoryId}`
Lista transações de um usuário por categoria
```bash
curl -X GET http://localhost:8080/api/transactions/user/1/category/1
```

### **GET** `/transactions/user/{userId}/recurring`
Lista transações recorrentes de um usuário
```bash
curl -X GET http://localhost:8080/api/transactions/user/1/recurring
```

### **GET** `/transactions/user/{userId}/total`
Obtém total de transações por tipo e período
```bash
curl -X GET "http://localhost:8080/api/transactions/user/1/total?type=EXPENSE&startDate=2024-01-01&endDate=2024-12-31"
```

### **GET** `/transactions/user/{userId}/count`
Conta transações de um usuário por período
```bash
curl -X GET "http://localhost:8080/api/transactions/user/1/count?startDate=2024-01-01&endDate=2024-12-31"
```

### **POST** `/transactions`
Cria uma nova transação
```bash
curl -X POST http://localhost:8080/api/transactions \
  -H "Content-Type: application/json" \
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
curl -X DELETE http://localhost:8080/api/transactions/1
```

### **GET** `/transactions/types`
Lista os tipos de transação disponíveis
```bash
curl -X GET http://localhost:8080/api/transactions/types
```

### **GET** `/transactions/statuses`
Lista os status de transação disponíveis
```bash
curl -X GET http://localhost:8080/api/transactions/statuses
```

---

## ?? **Próximas Implementações**

- [ ] Autenticação JWT
- [ ] Dashboard e Relatórios
- [ ] Upload de arquivos
- [ ] Notificações
- [ ] Frontend Angular 