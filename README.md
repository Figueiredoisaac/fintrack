# fintrack

# 🚀 FinTrack - Controle Financeiro Pessoal

**FinTrack** é um sistema completo de gestão financeira pessoal, desenvolvido como projeto de portfólio para demonstrar competências em backend (Java + Spring Boot), frontend (Next.JS), arquitetura limpa, testes automatizados, uso de Docker e organização profissional de tarefas. O foco é a experiência do usuário e a clareza na previsão e controle das finanças.

[![LinkedIn](https://img.shields.io/badge/Autor-Isaac_Figueiredo-blue?logo=linkedin)](https://www.linkedin.com/in/figueiredoisaac/)

---

## 📊 Demonstração ao Vivo (Kanban)

Acompanhe o desenvolvimento em tempo real via quadro Kanban com todas as tarefas, histórias e prioridades:

👉 [Board FinTrack no Notion (Kanban)](https://www.notion.so/figueiredoisaac/KANBAN-22d7f786363680628f7ff0668e1f015e)

---

## 📌 Funcionalidades do Projeto

### 🌟 Core Essencial

* 📒 **Gestão de Categorias** &#x20;
  Cadastro, edição e exclusão de categorias financeiras para organização dos lançamentos.

* 💸 **Registro de Lançamentos Financeiros** &#x20;
  Registros detalhados de entradas e saídas financeiras para controle efetivo do fluxo financeiro.

* 📊 **Dashboard Financeiro** &#x20;
  Gráficos e resumos visuais para análise rápida da situação financeira.

* 🔝 **Autenticação de Usuários** &#x20;
  Cadastro e autenticação de usuários garantindo privacidade e segurança dos dados financeiros.

* 📦 **Aplicação Containerizada** &#x20;
  Ambiente Dockerizado para execução simples e consistente da aplicação.

* 🝦 **Gestão de Contas Bancárias** &#x20;
  Cadastro e gerenciamento de contas bancárias diversas com acompanhamento individual de saldo e movimentações.

### 🔹 Complementares (planejadas)

* 💳 **Cadastro de Compras no Cartão** &#x20;
  Registro detalhado das compras feitas com cartão de crédito.

* 🔝 **Compras Recorrentes no Cartão** &#x20;
  Gerenciamento das despesas automáticas recorrentes em cartão de crédito.

* 📅 **Custos Recorrentes** &#x20;
  Gerenciamento de despesas fixas pagas por diversos meios (aluguel, contas).

* 💲 **Custos Avulsos** &#x20;
  Lançamento rápido de despesas não recorrentes.

* 📈 **Previsão de Receitas** &#x20;
  Cadastro e visualização das receitas futuras esperadas.

* 📆 **Planejamento Anual** &#x20;
  Visão anual das finanças com metas, receitas e despesas planejadas.

* 💰 **Cadastro de Receitas** &#x20;
  Registro e acompanhamento das diferentes fontes de receitas (salário, investimentos, etc.).

---

## 🧪 Testes Automatizados

Todas as funcionalidades possuem testes unitários e/ou de integração em Java, usando **JUnit 5** e **Mockito**, com foco em confiabilidade e manutenção.

---

## 🧰 Tecnologias Utilizadas

| Camada         | Tecnologia                                               |
| -------------- | -------------------------------------------------------- |
| Backend        | Java 21, Spring Boot 3, Spring Security, JPA, PostgreSQL |
| Frontend       | Next.js, TypeScript, HTML, SCSS                          |
| Banco de Dados | PostgreSQL                                               |
| Testes         | JUnit, Mockito, Spring Boot Test                         |
| DevOps         | Docker, Docker Compose                                   |
| Planejamento   | Notion (Kanban), Git                                     |

---

## 🔗 APIs - Coleção Postman

Testes e acompanhamento de desenvolvimento também podem ser feitos via Postman:

👉 [Coleção FinTrack no Postman](EM BREVE)

---

## 🤝 Contato

Caso deseje conversar sobre o projeto, oportunidades ou colaborações:

* [LinkedIn: Isaac Figueiredo](https://www.linkedin.com/in/figueiredoisaac/)
* [GitHub: figueiredoisaac](https://github.com/figueiredoisaac)

---

## ?? Como Executar o Projeto

### Pr�-requisitos
- Java 21 ou superior
- Maven 3.6+
- Docker e Docker Compose
- PostgreSQL (opcional, se n�o usar Docker)

### 1. Clone o Reposit�rio
```bash
git clone https://github.com/figueiredoisaac/fintrack.git
cd fintrack
```

### 2. Configure o Banco de Dados
```bash
# Inicie o PostgreSQL com Docker Compose
docker-compose up -d

# Acesse o PgAdmin em: http://localhost:5050
# Email: admin@fintrack.com
# Senha: admin
```

### 3. Execute a Aplica��o
```bash
# Na pasta fintrack/
mvn spring-boot:run

# A aplica��o estar� dispon�vel em: http://localhost:8080/api
```

### 4. Testar as APIs
Execute o script de teste para verificar se tudo est� funcionando:
```bash
# Windows (com JWT)
test_apis_jwt.bat

# Ou teste manualmente:
curl -X GET http://localhost:8080/api/public/health
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"isaac@fintrack.com","password":"123456"}'
```

### 5. Acessar o Banco de Dados
- **H2 Console**: http://localhost:8080/h2-console
- **JDBC URL**: jdbc:h2:mem:testdb
- **Username**: sa
- **Password**: password

### 6. Documenta��o das APIs
Consulte o arquivo `API_DOCUMENTATION_JWT.md` para ver todos os endpoints dispon�veis com autentica��o JWT.

### 7. Executar Testes
Execute os testes automatizados para verificar a qualidade do c�digo:
```bash
# Windows
run_tests.bat

# Ou manualmente:
mvn test
```

**Cobertura de Testes:**
- ? Testes unit�rios para servi�os
- ? Testes de integra��o para controllers
- ? Testes de seguran�a JWT
- ? Configura��o de teste separada

---

## ?? Status do Desenvolvimento

### ? Implementado
- [x] Estrutura base Spring Boot
- [x] Entidades: User, Category, BankAccount, Transaction
- [x] Reposit�rios JPA
- [x] Configura��o do H2 (desenvolvimento)
- [x] DTOs e Servi�os
- [x] APIs REST completas (Usu�rios, Categorias, Contas Banc�rias, Transa��es)
- [x] Autentica��o JWT completa
- [x] Dashboard e Relat�rios
- [x] Testes unit�rios e de integra��o
- [x] Documenta��o das APIs
- [x] Scripts de teste
- [x] Atualiza��o autom�tica de saldos

### ?? Em Desenvolvimento
- [ ] Upload de arquivos
- [ ] Frontend Angular

### ?? Pr�ximos Passos
- [ ] Implementar upload de arquivos
- [ ] Desenvolver frontend Angular

---

> Este projeto segue princípios de código limpo, arquitetura modular e boas práticas de engenharia de software com foco profissional e escalável.
