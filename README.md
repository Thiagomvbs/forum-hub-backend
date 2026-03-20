# 🚀 Fórum Hub - API de Autenticação e Autorização

API REST desenvolvida com foco em **segurança**, utilizando Java e Spring Boot.  
O projeto implementa um sistema completo de autenticação e autorização com **JWT**, controle de acesso por perfis e boas práticas de arquitetura backend.

---

## 🧠 Objetivo

Este projeto foi desenvolvido com o objetivo de aprofundar conhecimentos em:

- Segurança de APIs REST
- Autenticação stateless
- Controle de acesso baseado em perfis (RBAC)
- Boas práticas com Spring Security

---

## 🔐 Funcionalidades

- ✅ Cadastro de usuários
- ✅ Login com geração de **Access Token (JWT)** e **Refresh Token**
- ✅ Autenticação stateless
- ✅ Recuperação e validação de usuários via token
- ✅ Criptografia de senha com BCrypt
- ✅ Controle de acesso por perfis (roles)
- ✅ Hierarquia de permissões
- ✅ Associação de usuários com múltiplos perfis
- ✅ Envio de e-mail para verificação de conta
- ✅ Filtro de autenticação com validação de token
- ✅ Tratamento de erros com códigos HTTP adequados (401, 403)

---

## 🏗️ Tecnologias utilizadas

- Java 22+
- Spring Boot
- Spring Security
- Spring Data JPA
- JWT (JSON Web Token)
- Banco de dados (PostgreSQL ou outro)
- Maven

---

## 🧩 Arquitetura

O projeto segue uma estrutura organizada em camadas:
controller → service → repository → entity

Além disso, conta com:

- Camada de segurança (SecurityConfig, filtros)
- DTOs para entrada e saída de dados
- Tratamento global de exceções

---

## 🔄 Fluxo de autenticação

1. Usuário realiza login
2. API valida credenciais
3. Gera:
   - Access Token (curta duração)
   - Refresh Token
4. Cliente envia o token nas requisições protegidas
5. Filtro valida o token antes de liberar acesso

---

## 🛡️ Controle de acesso

- Usuários possuem **perfis (roles)**
- Relacionamento **Many-to-Many**
- Hierarquia de permissões (ex: ADMIN > INSTRUTOR > ESTUDANTE)
- Validação de acesso baseada em:
  - Perfil
  - Dono do recurso

---

## 📌 Endpoints principais

### 🔑 Autenticação
- `POST /login`
- `POST /atualizar-token`

### 👤 Usuários
- `POST /registrar`
- `GET /usuarios`
- `PUT /usuarios/{id}`

### 🔐 Protegidos
- Requerem token JWT no header:
- Authorization: Bearer {token}

- 
---

## 📧 Verificação de conta

- Token enviado por e-mail
- Validação com tempo de expiração
- Ativação do usuário após confirmação

---

## ⚠️ Segurança aplicada

- Senhas criptografadas com BCrypt
- API stateless (sem sessão)
- Proteção por token
- Validação de permissões
- Tratamento de erros padronizado

---

## 🚀 Como executar o projeto

```bash
# Clonar repositório
git clone https://github.com/seu-usuario/seu-repo.git

# Entrar na pasta
cd seu-repo

# Rodar aplicação
./mvnw spring-boot:run
