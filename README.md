# API de Controle de Estoque

API REST para cadastro e consulta de materiais e registro de movimentações de estoque. O projeto usa Java 21, Spring Boot, Spring Data JPA, Flyway e MySQL.

Este repositório contém somente o backend. A interface frontend não faz parte da publicação.

## Requisitos

- Java 21
- MySQL 8 ou compatível

## Configuração

Crie o banco de dados local:

```sql
CREATE DATABASE estoqueTi;
```

Configure as variáveis de ambiente antes de iniciar a aplicação:

| Variável | Padrão | Descrição |
| --- | --- | --- |
| `DB_URL` | `jdbc:mysql://localhost:3306/estoqueTi` | URL JDBC do MySQL |
| `DB_USERNAME` | `root` | Usuário do banco |
| `DB_PASSWORD` | sem padrão | Senha do banco |

As migrações do Flyway criam e atualizam as tabelas automaticamente ao iniciar a aplicação. Não coloque senhas reais nos arquivos do projeto.

## Como executar

No Windows PowerShell, defina as variáveis e execute:

```powershell
$env:DB_URL = "jdbc:mysql://localhost:3306/estoqueTi"
$env:DB_USERNAME = "root"
$env:DB_PASSWORD = "sua_senha"
.\mvnw.cmd spring-boot:run
```

No Linux ou macOS:

```bash
export DB_URL="jdbc:mysql://localhost:3306/estoqueTi"
export DB_USERNAME="root"
export DB_PASSWORD="sua_senha"
./mvnw spring-boot:run
```

A API inicia, por padrão, em `http://localhost:8080`.

## Principais rotas

### Materiais

- `GET /materiais` — lista materiais
- `GET /materiais/{id}` — consulta material por ID
- `POST /materiais` — cadastra material
- `PUT /materiais/{id}` — atualiza material
- `DELETE /materiais/desativar/{id}` — desativa material
- `PUT /materiais/ativar/{id}` — reativa material
- `GET /materiais/estoque_baixo` — lista materiais ativos com estoque baixo

### Movimentações

- `GET /movimentacao` — lista movimentações
- `POST /movimentacao` — registra movimentação
- `GET /movimentacao/material/{id}` — lista movimentações de um material
- `GET /movimentacao/data/{AAAA-MM-DD}` — consulta por data
- `DELETE /movimentacao/{id}` — remove movimentação

Os endpoints de cadastro e atualização recebem JSON. Os DTOs em `src/main/java/br/com/upa/estoque/dto` descrevem os campos esperados.

## Tecnologias

- Java 21
- Spring Boot
- Spring Web MVC e Spring Data JPA
- MySQL e Flyway
- Maven Wrapper
