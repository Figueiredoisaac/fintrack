# ?? FEATURE USERS - DOCUMENTAÇÃO TÉCNICA

## ?? Visão Geral
A Feature de Usuários é responsável pelo gerenciamento completo dos usuários do sistema FinTrack, incluindo autenticação, autorização e operações CRUD. Esta feature é fundamental pois serve como base para todas as outras funcionalidades do sistema.

## ??? Arquitetura da Feature

### ?? Diagrama de Camadas
```
???????????????????????????????????????????
?              CONTROLLER                 ?
?          (API REST Layer)               ?
???????????????????????????????????????????
?               SERVICE                   ?
?          (Business Logic)               ?
???????????????????????????????????????????
?             REPOSITORY                  ?
?          (Data Access Layer)            ?
???????????????????????????????????????????
?               ENTITY                    ?
?          (Domain Model)                 ?
???????????????????????????????????????????
```

## ??? ENTITY: User

### ?? Propósito
A entidade User representa um usuário do sistema FinTrack e implementa UserDetails do Spring Security para integração com autenticação.

### ?? Propriedades e Justificativas

| Propriedade | Tipo | Justificativa |
|-------------|------|---------------|
| `id` | Long | Chave primária auto-incrementada - padrão para identificação única |
| `name` | String | Nome completo do usuário - obrigatório para personalização |
| `email` | String | Identificador único de login - padrão web, obrigatório e único |
| `password` | String | Senha criptografada - segurança de acesso |
| `active` | Boolean | Controle de soft delete - preserva integridade referencial |
| `createdAt` | LocalDateTime | Auditoria - registro de quando foi criado |
| `updatedAt` | LocalDateTime | Auditoria - registro da última modificação |

### ?? Integração com Spring Security

**Por que implementa UserDetails?**
- Permite integração direta com Spring Security
- Facilita autenticação e autorização
- Padroniza interface de usuário para o framework

**Métodos implementados:**
- `getUsername()`: Retorna email (identificador único)
- `getAuthorities()`: Retorna role "USER" (podem ser expandidas)
- `isEnabled()`: Usa campo `active` para controle de acesso
- Demais métodos retornam `true` (sem expiração/bloqueio por enquanto)

### ?? Métodos de Ciclo de Vida

**`@PrePersist onCreate()`**
- Automatiza definição de timestamps na criação
- Garante consistência temporal

**`@PreUpdate onUpdate()`**
- Atualiza timestamp de modificação automaticamente
- Facilita auditoria de mudanças

## ??? REPOSITORY: UserRepository

### ?? Propósito
Interface de acesso a dados para entidade User, estendendo JpaRepository para operações básicas e definindo consultas customizadas.

### ?? Consultas Customizadas e Justificativas

| Método | Justificativa |
|--------|---------------|
| `findByEmail(String email)` | Busca por email - essencial para login |
| `existsByEmail(String email)` | Validação de duplicidade - performance otimizada |
| `findActiveByEmail(String email)` | Busca apenas usuários ativos - regra de negócio |
| `existsActiveByEmail(String email)` | Validação considerando soft delete |

### ?? Decisões de Design

**Por que consultas específicas para "active"?**
- Implementa soft delete de forma consistente
- Evita retornar usuários "deletados" em operações críticas
- Mantém integridade referencial no banco

## ?? SERVICE: UserService

### ?? Propósito
Camada de lógica de negócio que orquestra operações relacionadas aos usuários, aplicando regras de negócio e coordenando entre repository e controller.

### ?? Métodos e Lógica de Negócio

#### **CRUD Operations**

**`findAll()`**
- Retorna todos os usuários como DTOs
- Converte entidades para não expor dados sensíveis

**`findById(Long id)`**
- Busca usuário específico
- Retorna Optional para tratamento seguro de nulos

**`findByEmail(String email)`**
- Busca por email (identificador de login)
- Essencial para autenticação

**`create(UserDTO userDTO)`**
- Cria novo usuário
- **Criptografa senha automaticamente** - segurança essencial
- Aplica defaults (active = true)

**`update(Long id, UserDTO userDTO)`**
- Atualiza usuário existente
- **Preserva senha se não fornecida** - UX melhor
- **Re-criptografa se nova senha fornecida**

**`delete(Long id)`**
- **Hard delete por enquanto** - será convertido para soft delete
- Retorna boolean indicando sucesso

#### **Business Rules**

**`existsByEmail(String email)`**
- Validação de unicidade de email
- Usado antes de criar/atualizar usuários

### ?? Conversões DTO ? Entity

**Por que conversões separadas?**
- **Segurança**: DTOs não expõem campos sensíveis
- **Flexibilidade**: Diferentes DTOs para diferentes contextos
- **Versionamento**: Mudanças na API sem afetar entidade

## ?? CONTROLLER: UserController

### ?? Propósito
Camada de apresentação REST que expõe endpoints HTTP para operações de usuário, seguindo padrões RESTful.

### ??? Endpoints e Requisitos

| Endpoint | Método | Propósito | Requisitos |
|----------|--------|-----------|------------|
| `GET /api/users` | findAll | Listar todos usuários | Autenticação Admin |
| `GET /api/users/{id}` | findById | Buscar usuário específico | Autenticação |
| `GET /api/users/email/{email}` | findByEmail | Buscar por email | Autenticação |
| `POST /api/users` | create | Criar novo usuário | Validação de dados |
| `PUT /api/users/{id}` | update | Atualizar usuário | Autenticação + Autorização |
| `DELETE /api/users/{id}` | delete | Remover usuário | Autenticação + Autorização |
| `GET /api/users/check-email/{email}` | checkEmail | Verificar disponibilidade | Público |

### ?? Segurança e Validações

**Validação de Entrada**
- `@Valid` em DTOs - validação automática Jakarta
- Verificação de email duplicado antes de criar
- Tratamento de Optional para evitar NullPointer

**Códigos de Status HTTP**
- `200 OK` - Sucesso em operações de leitura
- `201 Created` - Usuário criado com sucesso
- `204 No Content` - Deleção bem-sucedida
- `400 Bad Request` - Email já existe
- `404 Not Found` - Usuário não encontrado

## ?? Melhorias Planejadas

### ?? Segurança
1. **Separar DTOs por contexto** (Create/Update/Response)
2. **Implementar roles dinâmicas** 
3. **Adicionar auditoria completa** (created_by, updated_by)

### ??? Arquitetura
1. **Implementar soft delete consistente**
2. **Adicionar eventos de domínio** (user created, updated)
3. **Implementar cache para consultas frequentes**

### ?? Performance
1. **Otimizar consultas com Projections**
2. **Implementar paginação**
3. **Adicionar índices estratégicos**

### ?? Testes
1. **Testes unitários completos**
2. **Testes de integração**
3. **Testes de segurança**

## ?? Padrões Utilizados

### ??? Arquiteturais
- **Repository Pattern** - Abstração de acesso a dados
- **DTO Pattern** - Transferência de dados entre camadas
- **Service Layer** - Lógica de negócio centralizada

### ?? Segurança
- **BCrypt** - Criptografia de senhas
- **Spring Security Integration** - Autenticação/Autorização
- **Soft Delete** - Preservação de integridade

### ?? Convenções
- **RESTful APIs** - Padrões HTTP corretos
- **Bean Validation** - Validações declarativas
- **Lifecycle Callbacks** - Auditoria automática 